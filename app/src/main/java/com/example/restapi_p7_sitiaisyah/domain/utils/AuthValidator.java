package com.example.restapi_p7_sitiaisyah.domain.utils;

public class AuthValidator {
    public static boolean isEmailValid(String email) {
        return email != null && email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }
}

