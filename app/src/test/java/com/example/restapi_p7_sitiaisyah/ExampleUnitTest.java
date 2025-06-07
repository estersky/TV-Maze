package com.example.restapi_p7_sitiaisyah;


import org.junit.Test;
import static org.junit.Assert.*;

import com.example.restapi_p7_sitiaisyah.domain.utils.AuthValidator;

public class ExampleUnitTest {

    @Test
    public void testIsEmailValid() {
        // email yg benar / valid
        assertTrue(AuthValidator.isEmailValid("siti@gmail.com"));

        // email tanpa '@'
        assertFalse(AuthValidator.isEmailValid("siti.com"));

        // email null
        assertFalse(AuthValidator.isEmailValid(null));

        // email kosong
        assertFalse(AuthValidator.isEmailValid(""));
    }

    @Test
    public void testIsPasswordValid() {
        // password valid (>=6 karakter)
        assertTrue(AuthValidator.isPasswordValid("123456"));

        // password kurang dari 6 karakter
        assertFalse(AuthValidator.isPasswordValid("12345"));

        // password null
        assertFalse(AuthValidator.isPasswordValid(null));

        // password kosong
        assertFalse(AuthValidator.isPasswordValid(""));
    }

}