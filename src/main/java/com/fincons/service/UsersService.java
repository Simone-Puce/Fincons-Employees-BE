package com.fincons.service;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface UsersService<Users, UsersDTO> {

    Users createUsers (UsersDTO usersDTO);
    List<UsersDTO> getAllUsers();
    UsersDTO getUsersById (Long id);
    Users addUsers (UsersDTO newUsers);
    ResponseEntity<Users> updateUsers(Long id, Users usersDetails);
    ResponseEntity<Map<String, Boolean>> deleteById (Long id);


}
