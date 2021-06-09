package com.narcyber.mvpbasics.utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    final static String EMAIL_DEFAULT_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final static String PASSWORD_DEFAULT_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    final static String FULL_NAME_PATTERN = "^[\\p{L} .'-]+$"; // all Unicode characters at least
    final static String USERNAME_PATTERN = "^[A-Za-z]\\w{5,29}$";

    //patterns
    final static Pattern namePattern = Pattern.compile(FULL_NAME_PATTERN);
    final static Pattern passwordPattern = Pattern.compile(PASSWORD_DEFAULT_PATTERN);
    final static Pattern userNamePattern = Pattern.compile(USERNAME_PATTERN);


    public static boolean isEmailValid(String value) {
        if (value.trim().length() >= 6 && Patterns.EMAIL_ADDRESS.matcher(value).matches()) {

            return true;
        }
        return false;

    }

    public static boolean isPasswordValid(String password) {
        final Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isFullNameValid(String name) {
        final Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isUsernameValid(String userName) {
        final Matcher matcher = userNamePattern.matcher(userName);

        return matcher.matches();
    }


}
