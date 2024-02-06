package com.fincons.service.authService;

import com.fincons.dto.UserDTO;
import com.fincons.exception.EmailException;
import com.fincons.exception.PasswordException;
import com.fincons.jwt.LoginDto;
import java.util.List;

public interface UserService {

    UserDTO registerNewUser(UserDTO newUserDTO, String passwordForAdmin) throws EmailException, PasswordException;

    List<UserDTO> getAllUsers();

    UserDTO getUserDtoByEmail(String email);

    String login(LoginDto loginDto);

    UserDTO updateUser(String email, UserDTO userModified, String passwordForAdmin) throws Exception;


    UserDTO updateUserPassword(String email, String password, String newPassword) throws EmailException, PasswordException;

    void deleteUserByEmail(String email) throws EmailException, EmailException;
}
