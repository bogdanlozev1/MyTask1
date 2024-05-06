package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
     static final Logger logger = Logger.getLogger(Main.class.getName());
     static final Set<String> dictionary = new HashSet<>();
     static int foundWordCount = 0; // Counter to keep track of found words

    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.loadAllWords();

            main.findWords();

            System.out.println("Total found words: " + foundWordCount); // Print the total count of found words

        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while reading the words from the URL", e);
        }
    }

    // Load the words from the URL and cache them
     void loadAllWords() throws IOException {
        if (dictionary.isEmpty()) {
            URL url = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                dictionary.addAll(reader.lines().collect(Collectors.toSet()));
            }
        }
    }

    // Find and print words that meet the conditions
     void findWords() {
        for (String word : dictionary) {
            if (word.length() == 9 && (word.contains("A") || word.contains("I")) && checkConditions(word)) {
                System.out.println(word);
                foundWordCount++; // Increment the counter for each found word
            }
        }

    }

    // Recursively check conditions after removing a letter
     boolean checkConditions(String word) {
        for (int i = 0; i < word.length(); i++) {
            String reducedWord = removeCharAt(word, i);
            if (isValidWord(reducedWord)) {
                if (reducedWord.length() == 1 || checkConditions(reducedWord)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Remove character at a specific position
     String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    // Check if the word is valid, including 'A' and 'I' letters (words)
     boolean isValidWord(String word) {
        return word.equals("I") || word.equals("A") || dictionary.contains(word);
    }
}
