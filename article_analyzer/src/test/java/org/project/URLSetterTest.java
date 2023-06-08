package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/* This class contains methods to test the correct functioning of the URLSetter class methods. */
@Tag("API")
class URLSetterTest {

    /* This method checks if increment page method in URLSetter class correctly work. */
    @ParameterizedTest
    @ValueSource(ints = {0,10,100,1000})
    @DisplayName("Testing if the method incrementPage correctly increment the page in the URL.")
    void testIncrementPage(int increments) throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int page = 1;
        int pageSize = page + increments;       // Setting a valid value for page increments(should not throw any exception).
        // Creating the URL with initial page value: 1.
        URLSetter setter = new URLSetter(apiKey,page,pageSize,queries,tags);
        // Calculating the excepted result basing on the number of pages increment.
        page += increments;
        String expected = "https://content.guardianapis.com/search?page-size=" + pageSize + "&page=" + page
                          + "&show-fields=body,headline,wordcount&tag=" + tags[0] + "&q=" + queries[0] + "&api-key=" + apiKey;
        // Doing the given number of increments.
        for(int i = 0; i < increments; i++) {
            setter.incrementPage();
        }
        // Testing if the expected result (with incremented page value) is equal to the calculated URL.
        assertEquals(new URL(expected), setter.getURL());
    }


    /* This method check the coherence checks on the page increment operation. */
    @ParameterizedTest
    @ValueSource(ints = {0,10,100,1000})
    @DisplayName("Testing if the method incrementPage correctly checks the page value after during increments in the URL.")
    void testIncrementPageException(int increments) throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int page = 1;
        int pageSize = 100;
        // Creating the URL with initial page value: 1.
        URLSetter setter = new URLSetter(apiKey,page,pageSize,queries,tags);
        // Doing the given number of increments.
        for(int i = 0; i < increments; i++) {
            if((i + page) < pageSize){       // Checking that the next increment will not make page bigger than page size.
                // Each attempt to increment page before it become equals to page size should not result in an Exception.
                assertDoesNotThrow(
                        () -> setter.incrementPage()
                );
            }else{
                // Each attempt to increment page after it is equals to page size should result in an Exception.
                assertThrows(
                        IllegalArgumentException.class,
                        () -> setter.incrementPage(),
                        "Expected IllegalArgumentException to be thrown but wasn't."
                );
            }
        }
    }

    /* Method for generating arrays of tags as arguments. */
    static Stream<Arguments> generatedTags(){
        return Stream.of(
                Arguments.of((Object) new String[]{"tag1","tag2","tag3"}),
                Arguments.of((Object) new String[]{}),
                Arguments.of((Object) new String[]{"tag1","tag2","tag3","tag4","tag5","tag6","tag7","tag8","tag9"})
        );
    }
    /* Parametrized Test that take as argument multiple possible tags Strings arrays to test the correct functioning of the URL Setter.*/
    @ParameterizedTest
    @MethodSource("generatedTags")
    @DisplayName("Testing if the URLSetter class correctly create the URL with various arrays of tags.")
    void testGetURLTags(String[] tagsParam) throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = tagsParam;
        String[] queries = new String[]{"query1"};
        int pageSize = 100;
        int page = 1;
        // Creating the URL with parameters: base URL, api key, page number, page size, queries, tags
        URLSetter setter = new URLSetter(apiKey, page, pageSize, queries, tags);
        // Expected result with given parameters.
        String expected =  "https://content.guardianapis.com/search?";      // This is the base URL for search in the "The Guardian" response.
        expected += "page-size=" + pageSize;        // Setting the number of page in one response.
        expected += "&page=" + page;        // Setting the page.
        expected += "&show-fields=body,headline,wordcount";     // These fields are always set to be shown in URLSetter.
        // Setting tags (optional).
        if(tags.length != 0) {
            expected += "&tag=";
            for (int i = 0; i < tags.length; i++) {
                if (i == (tags.length - 1)) {
                    expected += tags[i];
                } else {
                    expected += tags[i] + "/";
                }
            }
        }
        // Setting queries (only one in this case).
        expected += "&q=" + queries[0];
        expected += "&api-key=" + apiKey;
        // Testing the equality of the expected result and the actual URL
        assertEquals(new URL(expected), setter.getURL());
    }

    /* Method for generating arrays od queries as arguments. */
    static Stream<Arguments> generatedQueries(){
        return Stream.of(
                Arguments.of((Object) new String[]{"query1","query2","query3"}),
                Arguments.of((Object) new String[]{"","",""}),
                Arguments.of((Object) new String[]{}),
                Arguments.of((Object) new String[]{"query1","query2","query3","query4","query5","query6","query7","query8","query9"})
        );
    }
    /* Parametrized Test that take as argument multiple possible queries Strings arrays to test the correct functioning of the URL Setter.*/
    @ParameterizedTest
    @MethodSource("generatedQueries")
    @DisplayName("Testing if the URLSetter class correctly create the URL with various arrays of queries.")
    void testGetURLWithQueries(String[] queriesParam) throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = queriesParam;
        int pageSize = 100;
        int page = 1;
        // Creating the URL with parameters: base URL, api key, page number, page size, queries, tags
        URLSetter setter = new URLSetter(apiKey, page, pageSize, queries, tags);
        // Expected result with given parameters.
        String expected =  "https://content.guardianapis.com/search?";      // This is the base URL for search in the "The Guardian" response.
        expected += "page-size=" + pageSize;        // Setting the number of page in one response.
        expected += "&page=" + page;        // Setting the page.
        expected += "&show-fields=body,headline,wordcount";     // These fields are always set to be shown in URLSetter.
        // Setting tags (only one in this case).
        expected += "&tag="+tags[0];
        // Setting queries (optional).
        if(queries.length != 0) {
            expected += "&q=";
            for (int i = 0; i < queries.length; i++) {
                if (i == (queries.length - 1)) {
                    expected += queries[i];
                } else {
                    expected += queries[i] + "%20AND%20";
                }
            }
        }
        expected += "&api-key=" + apiKey;
        // Testing the equality of the expected result and the actual URL
        assertEquals(new URL(expected), setter.getURL());
    }

    /* Parametrized Test that take as argument multiple possible page values to test the correct functioning of the URL Setter.*/
    @ParameterizedTest
    @ValueSource(ints = {0, 100, -100})
    @DisplayName("Testing if the URLSetter class correctly create the URL with various possible page values.")
    void testGetURLWithPage(int pageParam) throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int pageSize = pageParam + 1;       // Should ensure that while testing the coherence of the value of page
        // it won't be incoherent with page size value.
        int page = pageParam;
        // Trying to create the URL with parameters: base URL, api key, page number, page size, queries, tags
        // Should not throw any exception when page is a positive number.
        if(page > 0){
            assertDoesNotThrow(
                    () -> new URLSetter(apiKey, page, pageSize, queries, tags)
            );
        }else{
            // Should throw an exception when page is a negative number or zero.
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new URLSetter(apiKey, page, pageSize, queries, tags),
                    "Expected IllegalArgumentException to be thrown but wasn't!"
            );
        }
    }

    /* Parametrized Test that take as argument multiple possible page values to test whether the URL
     * setter consider the coherence of its value with the page size one or not. */
    @ParameterizedTest
    @ValueSource(ints = {1, 100, 1000})     // Positive values.
    @DisplayName("Testing if the URLSetter class correctly create the URL with various possible page values.")
    void testGetURLWithPageAndPageSize(int pageParam) throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int pageSize = 100;       // Should ensure that while testing the coherence of the value of page
        // it won't be incoherent with page size value.
        int page = pageParam;
        // Trying to create the URL with parameters: base URL, api key, page number, page size, queries, tags
        // Should not throw any exception when page is lower than page size.
        if(page <= pageSize){
            assertDoesNotThrow(
                    () -> new URLSetter(apiKey, page, pageSize, queries, tags)
            );
        }else{
            // Should throw an exception when page bigger than page size.
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new URLSetter(apiKey, page, pageSize, queries, tags),
                    "Expected IllegalArgumentException to be thrown but wasn't!"
            );
        }
    }

    /* Parametrized Test that take as argument multiple possible page size values to test the correct functioning of the URL Setter.*/
    @ParameterizedTest
    @ValueSource(ints = {0, 100, -100})
    @DisplayName("Testing if the URLSetter class correctly create the URL with various possible page size values.")
    void testGetURLWithPageSize(int sizeParam) throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int pageSize = sizeParam;
        int page = 1;
        // Trying to create the URL with parameters: base URL, api key, page number, page size, queries, tags
        // Should not throw any exception when page size is a positive number.
        if(sizeParam > 0){
            assertDoesNotThrow(
                    () -> new URLSetter(apiKey, page, pageSize, queries, tags)
            );
        }else{
            // Should throw an exception when page size is a negative number or zero
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new URLSetter(apiKey, page, pageSize, queries, tags),
                    "Expected IllegalArgumentException to be thrown but wasn't!"
            );
        }
    }

    /* Parametrized Test that take as argument multiple possible queries Strings arrays to test the correct functioning of the URL Setter.*/
    @ParameterizedTest
    @ValueSource(strings = {"short", "varylongAPIkey", "mixedCASEAPIkey","","APIKEY"})
    @DisplayName("Testing if the URLSetter class correctly create the URL with various possible API key values.")
    void testGetURLWithAPIKey(String keyParam) throws IOException {
        // Setting the parameters for the test.
        String apiKey = keyParam;
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int pageSize = 100;
        int page = 1;
        // Trying to create the URL with parameters: base URL, api key, page number, page size, queries, tags
        // Should not throw any exception when apiKey is not empty.
        if(!keyParam.equals("")){
            assertDoesNotThrow(
                    () -> new URLSetter(apiKey, page, pageSize, queries, tags)
            );
        }else{
            // Should throw an exception when API key is empty string
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new URLSetter(apiKey, page, pageSize, queries, tags),
                    "Expected IllegalArgumentException to be thrown but wasn't!"
            );
        }
    }
}