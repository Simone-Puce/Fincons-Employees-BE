package com.fincons.mapper;

import com.fincons.entity.Users;
import com.fincons.models.UsersDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UsersMapper {
    public UsersDTO mapUserToUserDTO(Users users) {
        UsersDTO usersDTO = new UsersDTO(users.getFirst_name(), users.getLast_name(), users.getEmail(), users.getPassword());
        return usersDTO;
    }
    public List<UsersDTO> mapUserListToUserDTOList(List<Users> usersList) {
       List<UsersDTO> usersDTOList = new ArrayList<UsersDTO>();
       for (int i=0; i< usersList.size(); i++ ){
           UsersDTO userDTO =mapUserToUserDTO(usersList.get(i));
           usersDTOList.add(userDTO);
       }
       return usersDTOList;
    }
    public Users mapUserDTOToUsers (UsersDTO usersDTO) {
        Users users = new Users();
        return users;
    }
    public List<Users> mapUserDTOListToUserList (List<UsersDTO> usersDTOList) {
        List<Users> usersList = new ArrayList<Users>();
        for (int i=0; i<usersList.size(); i++){
            Users users = mapUserDTOToUsers(usersDTOList.get(i));
            usersList.add(users);
        }
        return usersList;
    }
}
