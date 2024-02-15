package com.fincons.service.authService;

import com.fincons.dto.RoleDTO;
import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import com.fincons.exception.EmailException;
import com.fincons.exception.PasswordException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.exception.RoleException;
import com.fincons.jwt.JwtTokenProvider;
import com.fincons.jwt.LoginDto;
import com.fincons.mapper.UserAndRoleMapper;
import com.fincons.utility.PasswordValidator;
import com.fincons.utility.RoleValidator;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
    public List<User> findAllUsers() {
        return  userRepo.findAll();
    }

    @Override
    public void deleteUserByEmail(String email) throws EmailException, RoleException {
        User userToRemove = userRepo.findByEmail(email);
        if(userToRemove==null){
            throw new EmailException(EmailException.emailInvalidOrExist());
        }
        if(userToRemove.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"))){
            throw new RoleException("User with role admin can't be deleted!");
        }
        userRepo.delete(userToRemove);
    }

    @Override
    public User updateUser(String email, UserDTO userModified, String passwordForAdmin, String currentPassword) throws EmailException, PasswordException,  RoleException {

        // verifico che l'email sia valida
        if (email.isEmpty() || !EmailValidator.isValidEmail(email)) {
            throw new EmailException(EmailException.emailInvalidOrExist());
        }

        //trovo l'user e vedo se esiste altrimenti eccezione
        User userFound = userRepo.findByEmail(email);
        if (userFound == null) {
            throw new ResourceNotFoundException("There isn't an user with this email!");
        }

        // password valorizzata ?
        if (currentPassword != null) {
            if (!passwordEncoder.matches(currentPassword, userFound.getPassword())) {
                throw new PasswordException("The password entered does not match the user's password");
            }

            // Verifico che la nuova password rispetti la regex
            if (!PasswordValidator.isValidPassword(userModified.getPassword())) {
                throw new PasswordException("The new password does not respect regex!");
            }

            //se nuova password valida l'aggiorno
            userFound.setPassword(passwordEncoder.encode(userModified.getPassword()));
            return userRepo.save(userFound);
        }

        //  password per l'amministratore valorizzata ?, verifica e aggiorna il ruolo
        if (passwordForAdmin != null && passwordForAdmin.equals(passwordAdmin)) {
            if (RoleValidator.isValidRole(String.valueOf(userModified.getRoles().get(0)))) {
                throw new RoleException(RoleException.roleDoesNotRespectRegex());
            }
            List<User> usersWithRoleAdmin = userRepo.findAll()
                    .stream()
                    .filter(user -> Arrays.toString(user.getRoles().toArray()).contains("ROLE_ADMIN"))
                    .toList();

            if(usersWithRoleAdmin.size() < 2 ){
                throw new RoleException("Can't modify the only admin remained!");
            }
            List<Role> list = new ArrayList<>();
            for (RoleDTO role : userModified.getRoles()) {
                Role dtoToRole = userAndRoleMapper.dtoToRole(role);
                list.add(dtoToRole);
            }
            userFound.setRoles(list);
        }

        // Aggiorno altri campi dell'utente se sono stati forniti
        if (userModified.getFirstName() != null) {
            userFound.setFirstName(userModified.getFirstName());
        }

        if (userModified.getLastName() != null) {
            userFound.setLastName(userModified.getLastName());
        }

        if(userModified.getPassword() != null &&  !PasswordValidator.isValidPassword(userModified.getPassword()) ){
            throw new PasswordException("New Password does not respect regex");
        }
        userFound.setPassword(userModified.getPassword());

        userFound.setPassword(passwordEncoder.encode(userModified.getPassword()));

        // salvataggio e restituzione dell'utente aggiornato
        return userRepo.save(userFound);
    }

    @Override
    public User getUserDtoByEmail(String email) {
        if(userRepo.findByEmail(email)==null){
            throw new ResourceNotFoundException("User with this email doesn't exist");
        }
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




    //METODO PER AGGIUNGERE UN NUOVO USER IN SEGUITO ALLA LETTURA DI UN FILE.
    public User addNewUser(User user) throws RuntimeException {

        String emailUser = user.getEmail().toLowerCase().replace(" ", "");
        // Controllo se l'indirizzo email è valido e non esiste un utente registrato con la mail specificata
    //    if (!emailUser.isEmpty() && EmailValidator.isValidEmail(emailUser) && !userRepo.existsByEmail(emailUser)) {
            Role role;
            role = roleToAssign("ROLE_USER");
            user.setRoles(List.of(role));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return user;
    //    } else{
    //        return false;
    //    }
    }


}
