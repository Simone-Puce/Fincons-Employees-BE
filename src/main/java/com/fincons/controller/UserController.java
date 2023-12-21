package com.fincons.controller;

import com.fincons.entity.User;
import com.fincons.models.UserDTO;
import com.fincons.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService<User, UserDTO> userService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers (){
        return userService.getAllUsers();
    }
    @PostMapping("/users")
    public User createUsers (@RequestBody UserDTO userDTO) {
        return userService.createUsers(userDTO);
    }
    @GetMapping ("/users/{id}")
    public UserDTO getUsersById (@PathVariable Long id ){
        return userService.getUsersById(id);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUsers(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUsers(id, userDetails);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteById (@PathVariable Long id){
        return userService.deleteById (id);
     }

}
