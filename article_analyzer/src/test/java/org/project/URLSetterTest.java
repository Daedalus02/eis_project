package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/* This class contains methods to test the correct functioning of the URLSetter class methods. */
@Tag("API")
class URLSetterTest {

    /* This method checks if increment page method in URLSetter class correctly work. */
    @Test
    @DisplayName("Testing if the method incrementPage correctly increment the page in the URL.")
    void testIncrementPage() throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int pageSize = 100;
        int page = 1;
        // Creating the URL with initial page value: 1.
        URLSetter setter = new URLSetter(apiKey,page,pageSize,queries,tags);
        // Note: here the field page in the expected URL has the value 2.
        String expected = "https://content.guardianapis.com/search?page-size=100&page=2&show-fields=body,headline,wordcount&tag=tag1&q=query1&api-key=test";
        // Incrementing the page value
        setter.incrementPage();
        // Testing if the expected result (with incremented page value) is equal to the calculated URL.
        assertEquals(new URL(expected), setter.getURL());
    }


    /*@TestFactory
    static Stream<Arguments> generetedTags(){
        return Stream.of(
          Arguments.of(new String[]{"tag1","tag2","tag3"}),
                Arguments.of(new String[]{})
        );
    }*/
    /*@ParameterizedTest
    @MethodSource("generatedTags")*/
    @Test
    @DisplayName("Testing if the URLSetter class correctly create the URL from the parameters.")
    void testGetURL() throws IOException {
        // Setting the parameters for the test.
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int pageSize = 100;
        int page = 1;
        // Creating the URL with parameters: base URL, api key, page number, page size, queries, tags
        URLSetter setter = new URLSetter(apiKey, page, pageSize, queries, tags);
        // Expected result with given parameters.
        String expected =  "https://content.guardianapis.com/search?page-size=100&page=1&show-fields=body,headline,wordcount&tag=tag1&q=query1&api-key=test";
        // Testing the equality of the expected result and the actual URL
        assertEquals(new URL(expected), setter.getURL());
    }
}