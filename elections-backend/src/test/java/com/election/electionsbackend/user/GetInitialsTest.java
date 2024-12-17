package com.election.electionsbackend.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GetInitialsTest {

    public static String getInitials(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        String[] words = fullName.trim().split("\\s+");
        if (words.length == 1) {
            return words[0].substring(0, 1).toUpperCase();
        }
        String firstInitial = words[0].substring(0, 1).toUpperCase();
        String lastInitial = words[words.length - 1].substring(0, 1).toUpperCase();
        return firstInitial + lastInitial;
    }

    @Test
    void testSingleName() {
        assertEquals("A", getInitials("Alice"));
    }

    @Test
    void testFirstAndLastName() {
        assertEquals("JD", getInitials("John Doe"));
    }

    @Test
    void testMultipleNames() {
        assertEquals("JD", getInitials("John William Doe"));
    }

    @Test
    void testExtraSpaces() {
        assertEquals("JD", getInitials("  John    Doe  "));
    }

    @Test
    void testEmptyString() {
        assertEquals("", getInitials(""));
    }

    @Test
    void testNullInput() {
        assertEquals("", getInitials(null));
    }
}

