package com.fincons.service.authService;

import com.fincons.auth.CustomAuthenticationProvider;
import com.fincons.dto.RoleDTO;
import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import com.fincons.exception.DuplicateEmailException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.jwt.JwtTokenProvider;
import com.fincons.jwt.LoginDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fincons.repository.RoleRepository;
import com.fincons.repository.UserRepository;
import com.fincons.utility.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService{

    public UserServiceImpl(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder, CustomAuthenticationProvider customAuthenticationProvider, JwtTokenProvider jwtTokenProvider) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    private CustomAuthenticationProvider customAuthenticationProvider;
    private RoleRepository roleRepo;
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private JwtTokenProvider jwtTokenProvider;


    @Value("${admin.password}")
    private String passwordAdmin;


    @Override
    public UserDTO registerNewUser(UserDTO userDTO, String passwordForAdmin) {

        String emailDto = userDTO.getEmail().toLowerCase().replace(" ", "");


        if (!emailDto.isEmpty() && EmailValidator.isValidEmail(emailDto) && !userRepo.existsByEmail(emailDto)) {
            User userToSave = dtoToUser(userDTO);
            Role role;
            if (passwordForAdmin != null && passwordForAdmin.equals(passwordAdmin)) {
                role = roleToAssign("ROLE_ADMIN");
            } else {
                role = roleToAssign("ROLE_USER");
            }
            userToSave.setRoles(List.of(role));
            User userSaved = userRepo.save(userToSave);
            return userToUserDto(userSaved);
        } else {

            throw new DuplicateEmailException("Invalid or existing email!!");
        }
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = customAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public UserDTO updateUser(String email, UserDTO userModified, String passwordForAdmin) {
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
                        .map(this::dtoToRole)
                        .collect(Collectors.toList()));
            }
            userRepo.save(userFound);
            return userToUserDto(userFound);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        if(users.isEmpty()){
            return List.of();
        }else{
            return users.stream().map(this::userToUserDto).toList();
        }
    }
    @Override
    public UserDTO getUserDtoByEmail(String email) {
        User userFounded = userRepo.findByEmail(email);
        return userToUserDto(userFounded);
    }
    public User dtoToUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User userToSave = modelMapper.map(userDTO, User.class);
        userToSave.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userToSave;
    }

    public UserDTO userToUserDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoles(user.getRoles().stream().map(this::roleToRoleDto).toList());
        return userDTO;
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
    public RoleDTO roleToRoleDto(Role role) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(role, RoleDTO.class);
    }
    public Role dtoToRole(RoleDTO roleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(roleDTO, Role.class);
    }


    //METODO PER AGGIUNGERE UN NUOVO USER IN SEGUITO ALLA LETTURA DI UN FILE.
    public User addNewUser(User user) {
        String emailUser = user.getEmail().toLowerCase().replace(" ", "");
        // Controllo se l'indirizzo email è valido
        if (!emailUser.isEmpty() && EmailValidator.isValidEmail(emailUser) && !userRepo.existsByEmail(emailUser)) {
            Role role;
            role = roleToAssign("ROLE_USER");
            user.setRoles(List.of(role));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User userToSave = userRepo.save(user);
            return userToSave;
        } else {
            // L'indirizzo email non è valido o esiste già nella repository
            throw new DuplicateEmailException("Invalid or existing email!!");
        }
    }


}
