package com.fincons.service;

import com.fincons.entity.Users;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.UsersMapper;
import com.fincons.models.UsersDTO;
import com.fincons.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService<Users, UsersDTO>{
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UsersMapper usersMapper;
    @Override
    public Users createUsers(UsersDTO usersDTO) {
        Users users =  usersMapper.mapUserDTOToUsers(usersDTO);
        Users newusers = usersRepository.save(users);
        return usersRepository.save(newusers);
    }
    @Override
    public List<UsersDTO> getAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        return usersMapper.mapUserListToUserDTOList(usersList);
    }
    @Override
    public UsersDTO getUsersById(Long id) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        return null; //?
    }
    @Override
    public Users addUsers(UsersDTO newUsers) {
        Users users = usersMapper.mapUserDTOToUsers(newUsers);
        return usersRepository.save(users);
    }

    @Override
    public ResponseEntity<Users> updateUsers(Long id, Users usersDetails) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users not exists with id: " + id));
        users.setFirst_name(usersDetails.getFirst_name());
        users.setLast_name(usersDetails.getLast_name());
        users.setEmail(usersDetails.getEmail());
        users.setPassword(usersDetails.getPassword());
        Users updateUsers = usersRepository.save(users);
        return ResponseEntity.ok(updateUsers);

    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteById(Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users not exists with id:" + id));
        usersRepository.delete(users);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
