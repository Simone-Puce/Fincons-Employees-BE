package com.fincons.service;

import com.fincons.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerNewUser(UserDTO newUserDTO, String passwordForAdmin);


    List<UserDTO> getAllUsers();

    UserDTO getUserDtoByEmail(String email);
}
