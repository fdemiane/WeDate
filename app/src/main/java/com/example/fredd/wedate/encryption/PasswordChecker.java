package com.example.fredd.wedate.encryption;

public class PasswordChecker {

    private static char[] specialCharacters = {'_' ,'@','#' , '!','?'};
    public static boolean checkPasswordValidity(String password) {

        boolean length = (password.length()>=8)?true:false;
        boolean special = false;
        for(int i = 0 ; i <specialCharacters.length ; i++)
            if(password.contains(""+specialCharacters[i]))
                special = true;
        boolean capital = false;
        int begin = 'A';
        int end = 'Z';
        for(int i = 0 ; i<password.length() ; i++)
            if(password.charAt(i)>=begin || password.charAt(i)<=end)
                capital = true;

        return length&&special&&capital;

    }
}
