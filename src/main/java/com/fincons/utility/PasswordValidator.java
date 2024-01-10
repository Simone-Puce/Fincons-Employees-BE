package com.fincons.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordValidator {
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])\" + \"(?=.*[a-z])(?=.*[A-Z])\" + \"(?=.*[@#$%^&+=])\" + \"(?=\\\\S+$).{8,20}$";//Vedere quanti caratteri deve essere la password
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}