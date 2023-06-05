package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@Tag("API")
class URLSetterTest {
    private final String API_FILE = "resources" + File.separator + "private" + File.separator + "private.properties";
    private final String SEARCH_TAG = "/search?";
    private final String PAGE_TAG = "page";
    private final String PAGE_SIZE_TAG = "page-size";
    private final String TAGS_TAG = "tags";
    private final String QUERIES_TAG = "q";
    private final String API_KEY_TAG = "api-key";
    private final String FIELD_VISIBILITY_TAG = "show-fields";
    private final String VISIBILITY_TAG_SEPARATOR = ",";
    private final String ASSIGN_TAG = "=";
    private final String AND_TAG = "&";
    private final String SEPARATOR = "/";
    private final int FIELD_NUMBER = 3;
    private final String[] RELEVANT_FIELD_TAGS = new String[]{"body","headline","wordcount"};
    @Test
    @DisplayName("Testing if the method incrementPage correctly increment the page in the URL.")
    void testIncrementPage() throws IOException {
        // Setting the parameters for the test.
        String baseURL = "https://content.guardianapis.com";
        String apiKey = "test";
        String[] tags = new String[]{"tag1"};
        String[] queries = new String[]{"query1"};
        int pageSize = 100;
        int page = 1;
        // Creating the URL with initial page value: 1.
        URLSetter setter = new URLSetter(baseURL,apiKey,page,pageSize,queries,tags);
        // Note: here the field page in the expected URL has the value 2.
        // Expected value: "https://content.guardianapis.com/search?page-size=100&page=2&show-fields=body,headline,wordcount&tag=tag1/d&q=query1&api-key=test".
        String expected = baseURL + SEARCH_TAG + PAGE_SIZE_TAG + ASSIGN_TAG + pageSize + AND_TAG+ PAGE_TAG + ASSIGN_TAG + (page + 1) + AND_TAG + FIELD_VISIBILITY_TAG + ASSIGN_TAG;
        for(int i = 0; i < FIELD_NUMBER; i++){
            if(i == (FIELD_NUMBER-1)){
                expected += RELEVANT_FIELD_TAGS[i];
            }else {
                expected += RELEVANT_FIELD_TAGS[i] + VISIBILITY_TAG_SEPARATOR;
            }
        }
        expected += AND_TAG + TAGS_TAG + ASSIGN_TAG;
        // Adding the tags.
        for( int i = 0; i < tags.length; i++){
            if(i == (tags.length-1)) {
                expected += tags[i];
            }else{
                expected += tags[i] + SEPARATOR;
            }
        }
        // Adding the queries
        expected += AND_TAG + QUERIES_TAG + ASSIGN_TAG;
        for( int i = 0; i < queries.length; i++){
            if(i == (queries.length-1)) {
                expected += queries[i];
            }else{
                expected += queries[i] + SEPARATOR;
            }
        }
        // Adding the api key.
        expected += AND_TAG + API_KEY_TAG + ASSIGN_TAG + apiKey;
        setter.incrementPage();
        assertEquals(new URL(expected), setter.getURL());
    }

    @Test
    @DisplayName("Testing if the URLSetter class correctly create the URL from the parameters.")
    void testGetURL() throws IOException {
        // Setting the parameters for the test.
        String baseURL = "https://content.guardianapis.com";
        String apiKey = "test";
        String[] tags = new String[]{"tag1","d"};
        String[] queries = new String[]{"query1"};
        int pageSize = 100;
        int page = 1;
        // Creating the URL with parameters: base URL, api key, page number, page size, queries, tags
        URLSetter setter = new URLSetter(baseURL, apiKey, page, pageSize, queries, tags);
        // Expected value: "https://content.guardianapis.com/search?page-size=100&page=1&show-fields=body,headline,wordcount&tag=tag1/d&q=query1&api-key=test".
        String expected = "https://content.guardianapis.com/search?page-size=" + pageSize + "&page="+ (page) + "&show-fields=body,headline,wordcount&tag=";
        // Adding the tags.
        for( int i = 0; i < tags.length; i++){
            if(i == (tags.length-1)) {
                expected += tags[i];
            }else{
                expected += tags[i] + "/";
            }
        }
        // Adding the queries
        expected += "&q=";
        for( int i = 0; i < queries.length; i++){
            if(i == (queries.length-1)) {
                expected += queries[i];
            }else{
                expected += queries[i] + "/";
            }
        }
        // Adding the api key.
        expected += "&api-key=" + apiKey;
        assertEquals(new URL(expected), setter.getURL());
    }
}