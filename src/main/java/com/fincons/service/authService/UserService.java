package com.fincons.service.authService;

import com.fincons.dto.UserDTO;
import com.fincons.exception.EmailDoesNotExistException;
import com.fincons.exception.IncorrectPasswordException;
import com.fincons.exception.PasswordDoesNotRespectRegexException;
import com.fincons.jwt.LoginDto;
import java.util.List;

public interface UserService {

    UserDTO registerNewUser(UserDTO newUserDTO, String passwordForAdmin) throws PasswordDoesNotRespectRegexException;

    List<UserDTO> getAllUsers();

    UserDTO getUserDtoByEmail(String email);

    String login(LoginDto loginDto);

    UserDTO updateUser(String email, UserDTO userModified, String passwordForAdmin) throws Exception;


    UserDTO updateUserPassword(String email, String password, String newPassword) throws EmailDoesNotExistException, IncorrectPasswordException, PasswordDoesNotRespectRegexException;

    void deleteUserByEmail(String email) throws EmailDoesNotExistException;
}
