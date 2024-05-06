package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void testFindWords() {
        Main main = new Main();
        Set<String> validWords = new HashSet<>(Arrays.asList("STARTLING", "STARTING", "STARING", "STRING", "STING", "SING", "SIN", "IN", "I"));

        try {
            main.loadAllWords();
        } catch (IOException e) {
            fail("Error loading words from URL");
        }

        main.findWords();
        Set<String> foundWords = Stream.of("STARTLING", "STARTING", "STARING", "STRING", "STING", "SING", "SIN", "IN", "I")
                .filter(main::isValidWord).collect(Collectors.toSet());

        assertEquals(validWords, foundWords, "Found words do not match expected words");
    }

    @Test
    void testRemoveCharAt() {
        Main main = new Main();
        assertEquals("staring", main.removeCharAt("starting", 4), "Failed to remove character at index 1");
        assertEquals("string", main.removeCharAt("staring", 2), "Failed to remove character at index 6");
    }

    @Test
    void testIsValidWord() {
        Main main = new Main();
        try {
            main.loadAllWords();
        } catch (IOException e) {
            fail("Error loading words from URL");
        }
        assertTrue(main.isValidWord("STARTING"), "STARTING should be a valid word");
        assertFalse(main.isValidWord("XYZ"), "XYZ should not be a valid word");
    }

    @Test
    void testCheckConditions() {
        Main main = new Main();
        try {
            main.loadAllWords();
        } catch (IOException e) {
            fail("Error loading words from URL");
        }
        assertTrue(main.checkConditions("STARTLING"), "STARTLING should meet the conditions");
        assertFalse(main.checkConditions("XYZ"), "XYZ should not meet the conditions");
    }
}
