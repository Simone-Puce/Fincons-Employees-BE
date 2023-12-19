package com.fincons.controller;

import com.fincons.entity.Users;
import com.fincons.models.UsersDTO;
import com.fincons.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UsersController {
    @Autowired
    UsersService<Users, UsersDTO> usersService;

    @GetMapping("/users")
    public List<UsersDTO> getAllUsers (){
        return usersService.getAllUsers();
    }
    @PostMapping("/users")
    public Users createUsers (@RequestBody UsersDTO usersDTO) {
        return usersService.createUsers(usersDTO);
    }
    @GetMapping ("/users/{id}")
    public UsersDTO getUsersById (@PathVariable Long id ){
        return usersService.getUsersById(id);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUsers( @PathVariable Long id, @RequestBody Users usersDetails) {
        return usersService.updateUsers(id, usersDetails);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteById (@PathVariable Long id){
        return usersService.deleteById (id);
     }

}
