package com.fincons.mapper;

import com.fincons.dto.RoleDTO;
import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import com.fincons.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAndRoleMapper {

    @Autowired
    private ModelMapper modelMapperUser;
    @Autowired
    private ModelMapper modelMapperRole;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepo;
    public User dtoToUser(UserDTO userDTO) {
        User userToSave = modelMapperUser.map(userDTO, User.class);
        userToSave.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userToSave;
    }

    public UserDTO userToUserDto(User user) {
        UserDTO userDTO = modelMapperUser.map(user, UserDTO.class);
        userDTO.setRoles(user.getRoles().stream().map(this::roleToRoleDto).toList());
        return userDTO;
    }


    public RoleDTO roleToRoleDto(Role role) {
        return modelMapperRole.map(role, RoleDTO.class);
    }
    public Role dtoToRole(RoleDTO roleDTO) {
        return modelMapperRole.map(roleDTO, Role.class);
    }
}
