package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TreeStorageTest {

    @Test
    @DisplayName("Testing if the method removeTokens really remove tokens in treeStorage class.")
    void test1RemoveTokens() {
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
    @DisplayName("Testing if the method removeTokens in treeStorage class does nothing when empty.")
    void test2RemoveTokens() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        storage.removeTokens();
        assertTrue(storage.isEmpty());
    }

    @Test
    @DisplayName("Testing if the method enterTokens really enter tokens in TreeStorage class.")
    void test1EnterTokens() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        tokens.add("Apples");
        tokens.add("taste");
        tokens.add("good");
        storage.enterTokens(tokens);
        assertEquals(tokens, storage.getTokens());
    }

    @Test
    @DisplayName("Testing if the method enterTokens in TreeStorage class does nothing when passed an empty String List.")
    void test2EnterTokens() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        storage.enterTokens(tokens);
        assertTrue(storage.isEmpty());
    }

    @Test
    @DisplayName("Testing if the method getOrderedTokens in TreeStorage class really return ordered tokens in TreeStorage class.")
    void test1GetOrderedTokens() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        for(int i = 0; i < 1000; i++){
            tokens.add("Apples");
        }
        for(int i = 0; i < 2000; i++){
            tokens.add("taste");
        }
        for(int i = 0; i < 3000; i++){
            tokens.add("good");
        }
        Set<Map.Entry<Integer, List<String>>> expected = new HashSet<Map.Entry<Integer, List<String>>>();
        List<String> list1 = new ArrayList<>();
        list1.add("Apples");
        List<String> list2 = new ArrayList<>();
        list2.add("taste");
        List<String> list3 = new ArrayList<>();
        list3.add("good");
        expected.add(new AbstractMap.SimpleEntry(1000,list1));
        expected.add(new AbstractMap.SimpleEntry(2000,list2));
        expected.add(new AbstractMap.SimpleEntry(3000,list3));
        storage.enterTokens(tokens);
        assertEquals(expected, storage.getOrderedTokens(3));
    }
    @Test
    @DisplayName("Testing if the method getOrderedTokens in TreeStorage class return ordered tokens even if max is bigger than available.")
    void test2GetOrderedTokens() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        for(int i = 0; i < 1000; i++){
            tokens.add("Apples");
        }
        for(int i = 0; i < 2000; i++){
            tokens.add("taste");
        }
        for(int i = 0; i < 3000; i++){
            tokens.add("good");
        }
        Set<Map.Entry<Integer, List<String>>> expected = new HashSet<Map.Entry<Integer, List<String>>>();
        List<String> list1 = new ArrayList<>();
        list1.add("Apples");
        List<String> list2 = new ArrayList<>();
        list2.add("taste");
        List<String> list3 = new ArrayList<>();
        list3.add("good");
        expected.add(new AbstractMap.SimpleEntry(1000,list1));
        expected.add(new AbstractMap.SimpleEntry(2000,list2));
        expected.add(new AbstractMap.SimpleEntry(3000,list3));
        storage.enterTokens(tokens);
        assertEquals(expected, storage.getOrderedTokens(1000));
    }
}