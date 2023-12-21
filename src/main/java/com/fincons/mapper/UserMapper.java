package com.fincons.mapper;

import com.fincons.entity.User;
import com.fincons.models.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserMapper {
    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO(user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword());
        return userDTO;
    }
    public List<UserDTO> mapUserListToUserDTOList(List<User> userList) {
       List<UserDTO> userDTOList = new ArrayList<UserDTO>();
       for (int i = 0; i< userList.size(); i++ ){
           UserDTO userDTO =mapUserToUserDTO(userList.get(i));
           userDTOList.add(userDTO);
       }
       return userDTOList;
    }
    public User mapUserDTOToUsers (UserDTO userDTO) {
        User user = new User();
        return user;
    }
    public List<User> mapUserDTOListToUserList (List<UserDTO> userDTOList) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i< userList.size(); i++){
            User user = mapUserDTOToUsers(userDTOList.get(i));
            userList.add(user);
        }
        return userList;
    }
}
