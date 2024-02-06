package com.fincons.exception;

import com.fincons.dto.UserDTO;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.internal.util.GenericsHelper;

import java.util.List;

public class RoleException extends Exception{


    public RoleException(String message){
        super(message);
    }

    public static String userHasNotPermission(){
        return "User does not have permission to modify the role!.";
    }

    public static String roleDoesNotRespectRegex(){
        return "Role does not respect regex of ROLE_***** .";
    }
    public static String roleExistException(){
        return "Role exist yet! .";
    }

    // REVIEW
    public static String usersWithRoleToChange(List<UserDTO> list){  // vorrei inserire un generis invece di UserDTO
        return "List" + list.toString();
    }

}
