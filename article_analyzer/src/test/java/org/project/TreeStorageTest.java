package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/*
* Testing multiple possible combination of all the method exposed in the class TreeStorage.
* */
@Tag("Log")
@Tag("CSV")
@Tag("API")
class TreeStorageTest {

    /* Testing the removeTokens method in some relevant combinations. */
    @Test
    @DisplayName("Testing if the method removeTokens really remove tokens in treeStorage class.")
    void testRemoveTokensWithFilledStorage() {
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
    void testRemoveTokensWithEmptyStorage() {
        TreeStorage storage = new TreeStorage();
        storage.removeTokens();
        assertTrue(storage.isEmpty());
    }

    /* Testing the getTokens method in some relevant combinations. */
    @DisplayName("Testing if the Tokens getter in TreeStorage class correctly returns the tokens entered.")
    @Test
    void testTokensGetter(){
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        tokens.add("Apples");
        tokens.add("taste");
        tokens.add("good");
        storage.enterTokens(tokens);
        assertEquals(tokens, storage.getTokens());
    }
    @DisplayName("Testing if the tokens getter work correctly even if the TreeStorage is empty.")
    @Test
    void testTokensGetterWhenEmpty(){
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        assertEquals(tokens, storage.getTokens());
    }

    /* Testing the enterTokens method in some relevant combinations. */
    @DisplayName("Testing if the method enterTokens really enter tokens in TreeStorage class.")
    @Test
    void testEnterTokens() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        tokens.add("Apples");
        tokens.add("taste");
        tokens.add("good");
        storage.enterTokens(tokens);
        assertEquals(tokens, storage.getTokens());
    }
    @Test
    @DisplayName("Testing if the method enterTokens in TreeStorage class does " +
                 "nothing when passed an empty String List.")
    void testEnterTokensWithEmptyList() {
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        storage.enterTokens(tokens);
        assertTrue(storage.isEmpty());
    }

    /* Testing the size method in some relevant combinations. */
    @Test
    @DisplayName("Testing if the size returned by the size method of TreeStorage is correct.")
    void testStorageCorrectSize(){
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
        int expected = 1000 + 2000 + 3000;
        storage.enterTokens(tokens);
        assertEquals(expected, storage.size());
    }
    @Test
    @DisplayName("Testing if the size returned by the size method of TreeStorage is correct when no token is entered.")
    void testStorageCorrectSizeWhenEmpty(){
        TreeStorage storage = new TreeStorage();
        int expected = 0;
        assertEquals(expected, storage.size());
    }

    /* Testing the isEmpty method in some relevant combinations. */
    @Test
    @DisplayName("Testing the empty method with empty TreeStorage.")
    void testIsEmptyWithEmptyStorage(){
        TreeStorage storage = new TreeStorage();
        assertTrue(storage.isEmpty());
    }
    @Test
    @DisplayName("Testing the empty method with not empty TreeStorage.")
    void testIsEmptyWithNotEmptyStorage(){
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        tokens.add("Apples");
        tokens.add("taste");
        tokens.add("good");
        storage.enterTokens(tokens);
        assertFalse(storage.isEmpty());
    }

    /* Testing the getOrderedTokens method in some relevant combinations. */
    @Test
    @DisplayName("Testing if the method getOrderedTokens in TreeStorage class really return " +
                 "ordered tokens in TreeStorage class.")
    void testGetOrderedTokens() {
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
    @DisplayName("Testing if the method getOrderedTokens in TreeStorage class return ordered tokens" +
            " even if max is bigger than available.")
    void testGetOrderedTokensWithImpossibleMaxDimension() {
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
    @DisplayName("Testing if the method getOrderedTokens in TreeStorage work correctly even if the storage is empty.")
    void testGetOrderedTokensWhenEmpty() {
        TreeStorage storage = new TreeStorage();
        Set<Map.Entry<Integer, List<String>>> expected = new HashSet<Map.Entry<Integer, List<String>>>();
        int casualDimension = 10;      // Should never return tokens for any dimension.
        assertEquals(expected, storage.getOrderedTokens(casualDimension));
    }

    /* Testing the getFrequency method in some relevant combinations. */
    @DisplayName("Testing if the frequency method correctly return the frequency of a token.")
    @Test
    void testFrequencyGetter(){
        TreeStorage storage = new TreeStorage();
        List<String> tokens = new ArrayList<String>();
        int expected = 1122;
        String token = "Apples";
        for(int i = 0; i < 1122; i++){
            tokens.add(token);
        }
        storage.enterTokens(tokens);
        assertEquals(expected, storage.getFrequency(token));
    }
    @DisplayName("Testing if the frequency method correctly return the frequency of a token " +
                 "if that token is never entered.")
    @Test
    void testFrequencyGetterWithAbsentToken(){
        TreeStorage storage = new TreeStorage();
        int expected = 0;
        String token = "Apples";    // Note: this token is not entered.
        assertEquals(expected, storage.getFrequency(token));
    }


    /* Testing the lexicographical order in Tokens with same frequency.*/
    @ParameterizedTest
    @DisplayName(" Testing the lexicographical order in Tokens with same frequency.")
    @ValueSource(ints = {1,10,100,1000})
    void testTokensOrder(int frequencyParam){
        List<String> tokens =  new ArrayList<String>();
        TreeStorage storage = new TreeStorage();
        int frequency = frequencyParam;
        for(int i = 0; i < frequency; i++){
;
            tokens.add("post");
            tokens.add("zero");
            tokens.add("eric");
            tokens.add("fukushima");
            tokens.add("gallons");
            tokens.add("helicopter");
            tokens.add("imagination");
            tokens.add("joke");
            tokens.add("key");
            tokens.add("stop");
            tokens.add("test");
            tokens.add("ultra");
            tokens.add("velocity");
            tokens.add("query");
            tokens.add("rust");
            tokens.add("let");
            tokens.add("my");
            tokens.add("dash");
            tokens.add("oblivion");
            tokens.add("word");
            tokens.add("you");
            tokens.add("xenon");
            tokens.add("at");
            tokens.add("best");
            tokens.add("call");
            tokens.add("neat");
        }

        String[] expected = new String[]{"zero","you", "xenon", "word", "velocity", "ultra", "test", "stop", "rust", "query", "post","oblivion", "neat",
                                         "my",  "let", "key", "joke", "imagination", "helicopter", "gallons", "fukushima", "eric", "dash", "call", "best",
                                         "at"};
        storage.enterTokens(tokens);
        /*
        * Here we get a String list of tokens with the same frequency(the one of  entered tokens) by first converting the return set of entries into a stream,
        * then reduced to the entries associated with the given frequency, then reconverted into the associated strings.
        * */
        List<String> tokensList = storage.getOrderedTokens(50).stream().filter(e -> e.getKey().equals(frequency)).map(Map.Entry::getValue).collect(Collectors.toList())
                                                                  .stream().flatMap(List::stream).collect(Collectors.toList());
        String[] result = tokensList.toArray(new String[]{});
        assertArrayEquals(expected, result);
        }

}