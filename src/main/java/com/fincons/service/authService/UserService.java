package com.fincons.service.authService;

import com.fincons.dto.UserDTO;
import com.fincons.jwt.LoginDto;

import java.util.List;

public interface UserService {

    UserDTO registerNewUser(UserDTO newUserDTO, String passwordForAdmin);

    List<UserDTO> getAllUsers();

    UserDTO getUserDtoByEmail(String email);

    String login(LoginDto loginDto);

    UserDTO updateUser(String email, UserDTO userModified) throws Exception;
}
