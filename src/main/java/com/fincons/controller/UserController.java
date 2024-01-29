package com.fincons.controller;

import com.fincons.exception.NoPermissionException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.service.authService.UserService;
import com.fincons.dto.UserDTO;
import com.fincons.exception.DuplicateEmailException;
import com.fincons.jwt.JwtAuthResponse;
import com.fincons.jwt.LoginDto;

import com.fincons.utility.GenericResponse;
import jakarta.servlet.http.HttpSession;
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


    @GetMapping("${registered.users.uri}")
    public String registeredUsers() {
        return "Registered users";
    }

    @GetMapping("${admin.uri}")
    public String adminEndpoint() {
        return "Admin!";
    }

    @GetMapping("${user.uri}")
    public String userEndpoint() {
        return "User!";
    }

    @GetMapping("${error.uri}")
    public String errorEndpoint() {
        return "Error!";
    }

    @GetMapping("${employees.uri}")
    public String employeesEndPoint() {
        return "employees!";
    }


    @PostMapping("${logout.uri}")
    public String logoutEmployee() {
        return "logout!";
    }

    // Login
    @PostMapping(value = "${login.uri}")
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
            return ResponseEntity.status(409).body(GenericResponse.<JwtAuthResponse>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message("Invalid or existing email!!")
                    .build());
        }

    }


    @PostMapping(value = "${register.uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<UserDTO>> register(
            @RequestBody UserDTO userDTO,
            @RequestParam(name = "admin", required = false) String passwordForAdmin) {
        try {
            UserDTO userToShow = userService.registerNewUser(userDTO, passwordForAdmin);
            return ResponseEntity.ok(GenericResponse.<UserDTO>builder()
                    .status(HttpStatus.OK)
                    .success(true)
                    .message("Logged Succesfully!!!")
                    .data(userToShow)
                    .build());

        } catch (DuplicateEmailException e) {

            return ResponseEntity.status(409).body(GenericResponse.<UserDTO>builder()
                    .status(HttpStatus.resolve(409))
                    .success(false)
                    .message("Invalid or existing email!!")
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

        } catch (NoPermissionException npe) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    GenericResponse.<UserDTO>builder()
                            .status(HttpStatus.FORBIDDEN)
                            .success(false)
                            .message(npe.getMessage())
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

    @GetMapping("${detail.userdto.uri}")
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
}

