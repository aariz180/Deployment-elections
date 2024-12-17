package com.election.electionsbackend.user;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidationTest {

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @Test
    void testValidEmail() {
        assertTrue(isValidEmail("test@example.com"));
    }

    @Test
    void testValidEmailWithSubdomain() {
        assertTrue(isValidEmail("user@mail.mail2.com"));
    }

    @Test
    void testInvalidEmailNoAtSymbol() {
        assertFalse(isValidEmail("testexample.com"));
    }

    @Test
    void testInvalidEmailNoDomain() {
        assertFalse(isValidEmail("user@"));
    }

    @Test
    void testInvalidEmailInvalidCharacters() {
        assertFalse(isValidEmail("user@'[]{}@example.com"));
    }

    @Test
    void testInvalidEmailMissingTLD() {
        assertFalse(isValidEmail("user@example"));
    }

    @Test
    void testEmptyString() {
        assertFalse(isValidEmail(""));
    }

    @Test
    void testNullInput() {
        assertFalse(isValidEmail(null));
    }
}

