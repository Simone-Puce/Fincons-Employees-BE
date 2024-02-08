package com.fincons.controller;

import com.fincons.exception.EmailException;
import com.fincons.exception.PasswordException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.exception.RoleException;
import com.fincons.service.authService.UserService;
import com.fincons.dto.UserDTO;
import com.fincons.jwt.JwtAuthResponse;
import com.fincons.jwt.LoginDto;
import com.fincons.utility.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin("http://localhost:81")
@CrossOrigin("*")
@RequestMapping("/company-employee-management")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("${registered.users}")
    public String registeredUsers() {
        return "Registered users";
    }

    @GetMapping("${admin.base.uri}")
    public String adminEndpoint() {
        return "Admin!";
    }

    @GetMapping("${user.base.uri}")
    public String userEndpoint() {
        return "User!";
    }

    @GetMapping("${error.base.uri}")
    public String errorEndpoint() {
        return "Error!";
    }

    @GetMapping("${employees.base.uri}")
    public String employeesEndPoint() {
        return "employees!";
    }


    @PostMapping("${logout.base.uri}")
    public String logoutEmployee() {
        return "logout!";
    }

    // Login
    @PostMapping("${login.base.uri}")
    public ResponseEntity<GenericResponse<JwtAuthResponse>> login(@RequestBody LoginDto loginDto) {
        try {
            // your code goes here
            String token = userService.login(loginDto);

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setAccessToken(token);

            return ResponseEntity.ok(GenericResponse.<JwtAuthResponse>builder()
                    .status(HttpStatus.OK)
                    .success(true)
                    .message("Logged Succesfully!!!")
                    .data(jwtAuthResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(200).body(GenericResponse.<JwtAuthResponse>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }


    @PostMapping(value = "${register.base.uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<UserDTO>> register(
            @RequestBody UserDTO userDTO,
            @RequestParam(name = "admin", required = false) String passwordForAdmin) {
        try {
            UserDTO userToShow = userService.registerNewUser(userDTO, passwordForAdmin);
            return ResponseEntity.ok(GenericResponse.<UserDTO>builder()
                    .status(HttpStatus.OK)
                    .success(true)
                    .message("Registered Succesfully!!!")
                    .data(userToShow)
                    .build());

        } catch (EmailException ee) {

            return ResponseEntity.status(200).body(GenericResponse.<UserDTO>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message(EmailException.emailInvalidOrExist())
                    .build());

        } catch ( PasswordException pdnre) {
            return ResponseEntity.status(200).body(GenericResponse.<UserDTO>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message(pdnre.getMessage())
                    .build());
        }
    }


    @PutMapping("${modify.user}")
    public ResponseEntity<GenericResponse<UserDTO>> updateUserByEmail
            (
                    @RequestParam(name = "email") String email,
                    @RequestBody UserDTO userModified,
                    @RequestParam(name = "admin", required = false) String passwordForAdmin
            ) throws Exception {
        try {

            UserDTO isUserModified = userService.updateUser(email, userModified, passwordForAdmin);
            return ResponseEntity.status(HttpStatus.OK).body(
                    GenericResponse.<UserDTO>builder()
                            .status(HttpStatus.OK)
                            .success(true)
                            .message("User modified succesfully!")
                            .data(isUserModified).build()
            );

        } catch (RoleException re) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    GenericResponse.<UserDTO>builder()
                            .status(HttpStatus.FORBIDDEN)
                            .success(false)
                            .message(re.getMessage())
                            .build());
        } catch (ResourceNotFoundException r) {
            return ResponseEntity.status(409).body(
                    GenericResponse.<UserDTO>builder()
                            .status(HttpStatus.resolve(409))
                            .success(false)
                            .message("Resource not found!")
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(409).body(
                    GenericResponse.<UserDTO>builder()
                            .status(HttpStatus.resolve(409))
                            .success(false)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @PutMapping("${update.user.password}")
    public ResponseEntity<GenericResponse<UserDTO>> updateUserPassword(
            @RequestParam String email,
            @RequestParam  String password,
            @RequestParam String newPassword){
        try{
            UserDTO userDTO = userService.updateUserPassword(email, password, newPassword);
            return ResponseEntity.status(HttpStatus.OK).body(
                    GenericResponse.<UserDTO>builder()
                            .status(HttpStatus.resolve(200))
                            .success(true)
                            .message("Password changed succesfully")
                            .data(userDTO)
                            .build()
            );
        }catch(EmailException | PasswordException ep){
            return ResponseEntity.status(HttpStatus.OK).body(
                    GenericResponse.<UserDTO>builder()
                            .status(HttpStatus.resolve(409))
                            .success(false)
                            .message(ep.getMessage()).build());
        }
    }


    @GetMapping("${detail.userdto}")
    public ResponseEntity<GenericResponse<UserDTO>> userDetails(@RequestParam String email) {
        UserDTO userDTO = userService.getUserDtoByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(
                GenericResponse.<UserDTO>builder()
                        .status(HttpStatus.OK)
                        .success(true)
                        .message("User fuond!")
                        .data(userDTO)
                        .build()
        );
    }

    @DeleteMapping("${delete.user-by-email}")
    public ResponseEntity<GenericResponse<Boolean>> deleteUserByEmail(@RequestParam String email){
        try{
            userService.deleteUserByEmail(email);

            return ResponseEntity.status(HttpStatus.OK).body(
                    GenericResponse.<Boolean>builder()
                            .status(HttpStatus.OK)
                            .success(true)
                            .message("User deleted succesfully!!")
                            .build());
        }catch( EmailException ednee){
            return ResponseEntity.status(HttpStatus.OK).body(
                    GenericResponse.<Boolean>builder()
                            .status(HttpStatus.resolve(409))
                            .success(false)
                            .message(ednee.getMessage())
                            .build());

        }

    }







}

