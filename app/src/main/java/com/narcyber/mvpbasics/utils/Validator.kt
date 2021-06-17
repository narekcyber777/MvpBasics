package com.narcyber.mvpbasics.utils

import android.util.Patterns
import java.util.regex.Pattern

class Validator {
    companion object {
        val EMAIL_DEFAULT_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val PASSWORD_DEFAULT_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        val FULL_NAME_PATTERN = "^[\\p{L} .'-]+$" // all Unicode characters at least

        val USERNAME_PATTERN = "^[A-Za-z]\\w{5,29}$"

        //patterns
        val namePattern = Pattern.compile(FULL_NAME_PATTERN)
        val passwordPattern = Pattern.compile(PASSWORD_DEFAULT_PATTERN)
        val userNamePattern = Pattern.compile(USERNAME_PATTERN)

        fun isEmailValid(value: String): Boolean {
            return value.trim { it <= ' ' }.length >= 6 && Patterns.EMAIL_ADDRESS.matcher(value)
                .matches()
        }

        fun isPasswordValid(password: String?): Boolean {
            val matcher = passwordPattern.matcher(password)
            return matcher.matches()
        }

        fun isFullNameValid(name: String?): Boolean {
            val matcher = namePattern.matcher(name)
            return matcher.matches()
        }

        fun isUsernameValid(userName: String?): Boolean {
            val matcher = userNamePattern.matcher(userName)
            return matcher.matches()
        }
    }

}