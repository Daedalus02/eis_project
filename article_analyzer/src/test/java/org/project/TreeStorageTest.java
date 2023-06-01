package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreeStorageTest {

    @Test
    @DisplayName("Testing if the method removeTokens really remove tokens in treeStorage class.")
    void testRemoveTokens() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        tokens.add("Apples");
        tokens.add("taste");
        tokens.add("good");
        storage.enterTokens(tokens);
        storage.removeTokens();
        assertTrue(storage.isEmpty());
    }

    @Test
    @DisplayName("Testing if the method enterTokens really enter tokens in a TreeStorage class.")
    void enterTokens() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        tokens.add("Apples");
        tokens.add("taste");
        tokens.add("good");

    }

    @Test
    void getOrderedTokens() {
    }
}