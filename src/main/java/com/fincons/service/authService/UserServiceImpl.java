package com.fincons.service.authService;

import com.fincons.auth.CustomAuthenticationProvider;
import com.fincons.dto.RoleDTO;
import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import com.fincons.exception.DuplicateEmailException;
import com.fincons.jwt.JwtTokenProvider;
import com.fincons.jwt.LoginDto;
import com.fincons.service.authService.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fincons.repository.RoleRepository;
import com.fincons.repository.UserRepository;
import com.fincons.utility.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.List;

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

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public UserDTO updateUser(String email, UserDTO userModified) throws Exception {
        return null;
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



}
