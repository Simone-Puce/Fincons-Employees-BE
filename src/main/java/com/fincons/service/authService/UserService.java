package com.fincons.service.authService;

import com.fincons.dto.UserDTO;
import com.fincons.entity.User;
import com.fincons.exception.EmailException;
import com.fincons.exception.PasswordException;
import com.fincons.exception.RoleException;
import com.fincons.jwt.LoginDto;
import java.util.List;

public interface UserService {

    User registerNewUser(UserDTO newUserDTO, String passwordForAdmin) throws EmailException, PasswordException;

    User getUserDtoByEmail(String email);

    String login(LoginDto loginDto);

    User updateUser(String email, UserDTO userModified, String passwordForAdmin) throws Exception;


    User updateUserPassword(String email, String currentPassword, String newPassword) throws EmailException, PasswordException;

    List<User> findAllUsers();

    void deleteUserByEmail(String email) throws EmailException, EmailException;
}
