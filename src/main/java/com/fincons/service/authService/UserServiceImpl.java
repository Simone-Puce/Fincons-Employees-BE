package com.fincons.service.authService;

import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import com.fincons.exception.EmailException;
import com.fincons.exception.PasswordException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.jwt.JwtTokenProvider;
import com.fincons.jwt.LoginDto;
import com.fincons.mapper.UserAndRoleMapper;
import com.fincons.utility.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fincons.repository.RoleRepository;
import com.fincons.repository.UserRepository;
import com.fincons.utility.EmailValidator;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService{


    public UserServiceImpl(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepo;
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAndRoleMapper userAndRoleMapper;

    private JwtTokenProvider jwtTokenProvider;

    @Value("${admin.password}")
    private String passwordAdmin;

    @Override
    public User registerNewUser(UserDTO userDTO, String passwordForAdmin) throws EmailException, PasswordException {

        String emailDto = userDTO.getEmail().toLowerCase().replace(" ", "");

        if (emailDto.isEmpty() || !EmailValidator.isValidEmail(emailDto) || userRepo.existsByEmail(emailDto)) {
            LOG.warn("Invalid or existing email!!");
            throw new EmailException(EmailException.emailInvalidOrExist());
        }
            User userToSave = userAndRoleMapper.dtoToUser(userDTO);
            Role role;

            if(!PasswordValidator.isValidPassword(userDTO.getPassword())){
                LOG.warn("Password dos not respect Regex!!");
                throw new PasswordException( PasswordException.passwordDoesNotRespectRegexException());
            }
            if (passwordForAdmin != null && passwordForAdmin.equals(passwordAdmin)) {
                role = roleToAssign("ROLE_ADMIN");
            } else {
                role = roleToAssign("ROLE_USER");
            }
            userToSave.setRoles(List.of(role));
            userToSave.setGeneratedPassword(false);
            User userSaved = userRepo.save(userToSave);

            return userSaved;
    }

    @Override
    public String login(LoginDto loginDto) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public User updateUser(String email, UserDTO userModified, String passwordForAdmin) {
        if (email.isEmpty() && !EmailValidator.isValidEmail(email)) {
            throw new ResourceNotFoundException("Invalid email!");
        }
            User userFound = userRepo.findByEmail(email);
            if (userFound == null) {
                throw new ResourceNotFoundException("There isn't an user with this email!");
            }
            userFound.setFirstName(userModified.getFirstName());
            userFound.setLastName(userModified.getLastName());
            userFound.setPassword(passwordEncoder.encode(userModified.getPassword()));
            if (passwordForAdmin != null && passwordForAdmin.equals(passwordAdmin)) {

                userFound.setRoles(userModified.getRoles()
                        .stream()
                        .map(role -> userAndRoleMapper.dtoToRole(role))
                        .collect(Collectors.toList()));
            }
        return userRepo.save(userFound);

    }

    @Override
    public User updateUserPassword(String email, String currentPassword, String newPassword) throws EmailException, PasswordException {

        if(userRepo.findByEmail(email) == null && !EmailValidator.isValidEmail(email)) {
            throw new EmailException(EmailException.emailInvalidOrExist());
        }

        User userToModify = userRepo.findByEmail(email);
        boolean passwordMatch = passwordEncoder.matches(currentPassword , userToModify.getPassword());

        if(!passwordMatch){
            throw new PasswordException(PasswordException.invalidPasswordException());
        }

        if(!PasswordValidator.isValidPassword(newPassword)){
            throw new PasswordException(PasswordException.passwordDoesNotRespectRegexException());
        }

        userToModify.setPassword(passwordEncoder.encode(newPassword));

        return userRepo.save(userToModify);

    }

    @Override
    public void deleteUserByEmail(String email) throws EmailException {
        User userToRemove = userRepo.findByEmail(email);
        if(userToRemove==null){
            throw new EmailException(EmailException.emailInvalidOrExist());
        }
        userRepo.delete(userToRemove);
    }

    @Override
    public List<User> findAllUsers() {
        return  userRepo.findAll();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        if(users.isEmpty()){
            return List.of();
        }else{
            return users.stream().map(user -> userAndRoleMapper.userToUserDto(user)).toList();
        }
    }
    @Override
    public User getUserDtoByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Role roleToAssign(String nomeRuolo) {
        Role role = roleRepo.findByName(nomeRuolo);
        if (role == null) {
            Role newRole = new Role();
            newRole.setName(nomeRuolo);
            role = roleRepo.save(newRole);
        }
        return role;
    }





}
