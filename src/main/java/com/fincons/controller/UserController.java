package com.fincons.controller;

import com.fincons.dto.UserDTO;
import com.fincons.exception.DuplicateEmailException;
import com.fincons.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/company-employee-management")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("${home.uri}")
    public String home(){
        return "You are on the home page";
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


    @GetMapping("${logout.uri}")
    public String logoutEmployee() {
        return "logout!";
    }

    @PostMapping(value = "${login.uri}")
    public String login() {
        // your code goes here
        return "login";
    }


    @PostMapping(value = "${register.uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(
            @RequestBody UserDTO userDTO,
            @RequestParam(name = "admin", required = false) String passwordForAdmin) {
        //boolean, isUserInRole(java.lang.String role) Returns a boolean indicating whether the authenticated user is included in the specified logical "role"
        try {
            UserDTO userToShow = userService.registerNewUser(userDTO, passwordForAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body(userToShow);

        }catch (DuplicateEmailException e) {
            return ResponseEntity.status(409).body("Invalid or existing email!!");
        }
    }
}
