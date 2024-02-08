package com.fincons.exception;

public class RoleException extends Exception{


    public RoleException(String meessage){
        super(meessage);
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


}
