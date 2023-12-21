package com.fincons.service;

import com.fincons.entity.User;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.mapper.UserMapper;
import com.fincons.models.UserDTO;
import com.fincons.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService<User, UserDTO> {
    @Autowired
    UserRepository usersRepository;
    @Autowired
    UserMapper userMapper;
    @Override
    public User createUsers(UserDTO userDTO) {
        User user =  userMapper.mapUserDTOToUsers(userDTO);
        User newusers = usersRepository.save(user);
        return usersRepository.save(newusers);
    }
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = usersRepository.findAll();
        return userMapper.mapUserListToUserDTOList(userList);
    }
    @Override
    public UserDTO getUsersById(Long id) {
        Optional<User> optionalUsers = usersRepository.findById(id);
        return null; //?
    }
    @Override
    public User addUsers(UserDTO newUsers) {
        User user = userMapper.mapUserDTOToUsers(newUsers);
        return usersRepository.save(user);
    }

    @Override
    public ResponseEntity<User> updateUsers(Long id, User userDetails) {
        User user = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users not exists with id: " + id));
        user.setFirst_name(userDetails.getFirst_name());
        user.setLast_name(userDetails.getLast_name());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        User updateUser = usersRepository.save(user);
        return ResponseEntity.ok(updateUser);

    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteById(Long id) {
        User user = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users not exists with id:" + id));
        usersRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
