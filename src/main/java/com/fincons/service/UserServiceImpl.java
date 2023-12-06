package com.fincons.service;

import com.fincons.dto.RoleDTO;
import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import com.fincons.exception.DuplicateEmailException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fincons.repository.RoleRepository;
import com.fincons.repository.UserRepository;
import com.fincons.utility.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService{
    private RoleRepository roleRepo;

    private UserRepository userRepo;

    private PasswordEncoder passwordEncoder;

    ModelMapper modelMapper = new ModelMapper();

    @Value("${admin.password}")
    private String passwordAmministratore;

    public UserServiceImpl(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO registerNewUser(UserDTO userDTO, String passwordForAdmin) {

        String emailDto = userDTO.getEmail().toLowerCase().replace(" ", "");

        // Controllo se l'indirizzo email è valido
        if (!emailDto.isEmpty() && EmailValidator.isValidEmail(emailDto) && !userRepo.existsByEmail(emailDto)) {
            User userToSave = dtoToUser(userDTO);
            Role role;
            if (passwordForAdmin != null && passwordForAdmin.equals(passwordAmministratore)) {
                role = roleToAssign("ROLE_ADMIN");
            } else {
                role = roleToAssign("ROLE_USER");
            }
            userToSave.setRoles(List.of(role));
            User userDaSalvare = userRepo.save(userToSave);
            RoleDTO roleDto = entityToRoleDto(role);
            UserDTO userDto = entityToDTO(userDaSalvare);
            userDto.setRoles(List.of(roleDto));//added
            return entityToDTO(userDaSalvare);
        } else {
            // L'indirizzo email non è valido o esiste già nella repository
            throw new DuplicateEmailException("Invalid or existing email!!");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        if(users.isEmpty()){
            return List.of();
        }else{
            return users.stream().map(this::entityToDTO).toList();
        }
    }

    public User dtoToUser(UserDTO userDTO) {
        User userToSave = modelMapper.map(userDTO, User.class);
        userToSave.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userToSave;
    }

    public UserDTO entityToDTO(User user) {

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoles(user.getRoles().stream().map(this::entityToRoleDto).toList());
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

    public RoleDTO entityToRoleDto(Role role) {
        return this.modelMapper.map(role, RoleDTO.class);
    }

    public Role dtoToRole(RoleDTO roleDTO) {
        return this.modelMapper.map(roleDTO, Role.class);
    }



}
