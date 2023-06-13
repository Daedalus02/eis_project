package org.project;

import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


/* Testing the article source APISource.
*
* Note: here some coherence(page, page size, apiKey values) checks are skipped since APISource delegate URLSetter to test the validity of the URL.
* */
@Tag("API")
class APISourceTest {

    @BeforeAll
    static void printMessage(){
        System.out.println("APISource tests might take a while (depending on connection)...");
    }
    /*
    * This test verify that the API Source get the correct number of articles.
    * */
    @ParameterizedTest
    @ValueSource(ints = {1,10,100})    // Possible valid values.
    @DisplayName("Testing the number of articles returned by the APISource.")
    void testNumberOfArticles(int maxParam) throws IOException, JSONException {
        // Setting API key for tests.
        String apiKey = "test";
        // Specifying the queries to none
        String[] queries = new String[]{"nuclear", "power"};
        // Setting the max number of articles (and also a token max frequency)
        int maxArticles = maxParam;
        // Setting the tags to none.
        String[] tags = new String[]{};
        APISource source = new APISource(apiKey,tags,queries,maxArticles,false);
        List<APIArticle> articleList = source.getArticles();
        assertTrue(articleList.size() <= maxParam);
    }


    /*
     * Note here we accept a margin in the response given basing on the presence of queries since we noticed the
     * response of the "The guardian" endpoint is not precise. So we set a margin to 10%.
     * */
    @ParameterizedTest
    @ValueSource(ints = {1,10,100})    // Possible valid values.
    @DisplayName("Testing the frequency of queries words in articles returned by the APISource.")
    void testFrequencyOfQueriesWords(int maxParam) throws IOException, JSONException {
        // Using a test API key.
        String apiKey = "test";
        // Specifying only 2 queries --> nuclear, power
        String[] queries = new String[]{"nuclear", "power"};
        // Setting the max number of articles (and also a token max frequency)
        int maxArticles = maxParam;
        // Setting the tags to none.
        String[] tags = new String[]{};
        // Setting the APISource with the previous parameters.
        APISource source = new APISource(apiKey,tags,queries,maxArticles,false);
        // Calculating the articles list from the API response
        List<APIArticle> articleList = source.getArticles();
        // Creating the expected result for max frequency (equals to actual number of articles matching the research parameters).
        int expected = articleList.size();
        int delta = expected/10;    // Specifying a delta of 10%.

        // Calculating Tokens
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(true, storage);
        for(APIArticle article : articleList){
            // Also converting queries to list.
            tokenizer.tokenize(article.getHead() + article.getBody(), Arrays.stream(queries).collect(Collectors.toList()));
        }
        assertEquals(storage.getFrequency(queries[0]),expected, delta);
    }


    /* Method for generating arrays od queries as arguments. */
    static Stream<Arguments> generatedQueries(){
        return Stream.of(
                Arguments.of((Object) new String[]{"nuclear","power","france"}),
                Arguments.of((Object) new String[]{"","",""}),
                Arguments.of((Object) new String[]{"very","long","set","of","queries"})
        );
    }
    // Testing the presence of different queries words in articles returned by the APISource.
    @ParameterizedTest
    @MethodSource("generatedQueries")    // Possible valid queries.
    @DisplayName("Testing the presence of different queries words in articles returned by the APISource.")
    void testPresenceOfQueriesWords(String[] queriesParam) throws IOException, JSONException {
        // Using a test API key.
        String apiKey = "test";
        // Specifying 2 queries --> nuclear, power
        String[] queries = queriesParam;
        // Setting the max number of articles (and also a token max frequency)
        int maxArticles = 100;
        // Setting the tags to none.
        String[] tags = new String[]{};
        // Setting the APISource with the previous parameters.
        APISource source = new APISource(apiKey,tags,queries,maxArticles,false);
        // Calculating the articles list from the API response
        List<APIArticle> articleList = source.getArticles();
        // Creating the expected result for max frequency (equals to actual number of articles matching the research parameters).
        int expected = articleList.size();
        int delta = (expected*2)/10;    // Specifying a delta of 20%.

        // Calculating Tokens
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(true, storage);
        for(APIArticle article : articleList){
            // Also converting queries to list.
            tokenizer.tokenize(article.getHead() + article.getBody(), Arrays.stream(queries).collect(Collectors.toList()));
        }
        for(int i = 0; i < queries.length; i++){
            assertEquals(storage.getFrequency(queries[i]),expected, delta);
        }
    }


    /* Method for generating arrays of valid tags as arguments. */
    static Stream<Arguments> generatedTags(){
        return Stream.of(
                Arguments.of((Object) new String[]{"environment","recycling"    }),
                Arguments.of((Object) new String[]{"",""}),
                Arguments.of((Object) new String[]{"world","france"})
        );
    }
    // Testing the presence of different tags words in articles returned by the APISource.
    @ParameterizedTest
    @MethodSource("generatedTags")    // Possible valid queries.
    @DisplayName("Testing the presence of different queries words in articles returned by the APISource.")
    void testPresenceOfTagsWords(String[] tagsParam) throws IOException, JSONException {
        // Using a test API key.
        String apiKey = "test";
        // Specifying 2 queries --> nuclear, power
        String[] queries = new String[]{};
        // Setting the max number of articles (and also a token max frequency)
        int maxArticles = 100;
        // Setting the tags to none.
        String[] tags = tagsParam;
        // Setting the APISource with the previous parameters.
        APISource source = new APISource(apiKey,tags,queries,maxArticles,false);
        // Calculating the articles list from the API response
        List<APIArticle> articleList = source.getArticles();
        // Creating the expected result for max frequency (equals to actual number of articles matching the research parameters).
        int expected = articleList.size();
        int delta = (expected*9)/10;    // Specifying a delta of 10%.

        // Calculating Tokens
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(true, storage);
        for(APIArticle article : articleList){
            // Also converting queries to list.
            tokenizer.tokenize(article.getHead() + article.getBody(), Arrays.stream(queries).collect(Collectors.toList()));
        }
        for(int i = 0; i < tags.length; i++){
            // Asserting that when searching for a tag it should be present at least in 10% of the articles
            assertEquals(storage.getFrequency(tags[i]),expected, delta);
        }
    }

    /* Method for generating arrays of valid tags as arguments. */
    static Stream<Arguments> generatedInvalidTags(){
        return Stream.of(
                Arguments.of((Object) new String[]{"invalid1","invalid2"}),
                Arguments.of((Object) new String[]{"",""}),
                Arguments.of((Object) new String[]{"world","invalid"})
        );
    }
    // Testing the presence of different tags words in articles returned by the APISource.
    @ParameterizedTest
    @MethodSource("generatedInvalidTags")    // Possible valid queries.
    @DisplayName("Testing the presence of different queries words in articles returned by the APISource.")
    void testInvalidTagsWords(String[] tagsParam) throws IOException, JSONException {
        // Using a test API key.
        String apiKey = "test";
        // Specifying 0 queries.
        String[] queries = new String[]{};
        // Setting the max number of articles (and also a token max frequency).
        int maxArticles = 100;
        // Setting the tags to none.
        String[] tags = tagsParam;
        // Setting the APISource with the previous parameters.
        APISource source = new APISource(apiKey,tags,queries,maxArticles,false);
        // Calculating the articles list from the API response
        List<APIArticle> articleList = source.getArticles();
        // Creating the expected result for max frequency (equals to actual number of articles matching the research parameters).
        int expected = articleList.size();
        int delta = (expected*9)/10;    // Specifying a delta of 10%.

        // Calculating Tokens
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(true, storage);
        for(APIArticle article : articleList){
            // Also converting queries to list.
            tokenizer.tokenize(article.getHead() + article.getBody(), Arrays.stream(queries).collect(Collectors.toList()));
        }
        for(int i = 0; i < tags.length; i++){
            // Asserting that when searching for a tag it should be present at least in 10% of the articles
            assertEquals(storage.getFrequency(tags[i]),expected, delta);
        }
    }

    /* Parametrized Test that take as argument multiple possible max number of article values to test the
     * correct functioning of the API source.*/
    @ParameterizedTest
    @ValueSource(ints = {0, 100, -100})
    @DisplayName("Testing if the APISource class correctly create the URL with various possible page size values.")
    void testGetURLWithPageSize(int sizeParam){
        // Using a test API key.
        String apiKey = "test";
        // Specifying 0 tags.
        String[] tags = new String[]{};
        // Specifying 0 queries.
        String[] queries = new String[]{};
        // Setting the max number of articles (and also a token max frequency).
        int maxArticle = sizeParam;

        // Trying to create the APISource with parameters.
        // Should not throw any exception when page size is a positive number.
        if(sizeParam > 0){
            assertDoesNotThrow(
                    () -> new APISource(apiKey, tags, queries, maxArticle,false)
            );
        }else{
            // Should throw an exception when page size is a negative number or zero
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new APISource(apiKey, tags, queries, maxArticle,false),
                    "Expected IllegalArgumentException to be thrown but wasn't!"
            );
        }
    }

    /* Parametrized Test that take as argument multiple possible valid API key Strings to test the correct functioning of the APISource.*/
    @ParameterizedTest
    @ValueSource(strings = {"test",""})     // Valid API keys.
    @DisplayName("Testing if the APISource class correctly create the URL with various possible API key values.")
    void testAPISourceWithAPIKey(String keyParam) throws IOException {
        // Setting the parameters for the test.
        // Using the given API keys.
        String apiKey = keyParam;
        // Specifying 0 tags.
        String[] tags = new String[]{};
        // Specifying 0 queries.
        String[] queries = new String[]{};
        // Setting the max number of articles (and also a token max frequency).
        int maxArticle = 100;
        // Trying to create the URL with parameters: base URL, api key, page number, page size, queries, tags
        // Should not throw any exception when apiKey is not empty.
        if(!keyParam.equals("")){
            assertDoesNotThrow(
                    () -> new APISource(apiKey, tags, queries, maxArticle,false)
            );
        }else{
            // Should throw an exception when API key is empty string
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new APISource(apiKey, tags, queries, maxArticle,false),
                    "Expected IllegalArgumentException to be thrown but wasn't!"
            );
        }
    }

    /* Parametrized Test that take as argument multiple possible invalid API key Strings to test the correct functioning of the APISource.*/
    @ParameterizedTest
    @ValueSource(strings = {"short", "varylongAPIkey", "mixedCASEAPIkey","APIKEY"})     // Invalid API keys.
    @DisplayName("Testing if the APISource class correctly create the URL with various possible API key values.")
    void testAPISourceWithInvalidAPIKey(String keyParam) {
        // Setting the parameters for the test.
        // Using the given API keys.
        String apiKey = keyParam;
        // Specifying 0 tags.
        String[] tags = new String[]{};
        // Specifying 0 queries.
        String[] queries = new String[]{};
        // Setting the max number of articles.
        int maxArticle = 100;

        // Trying to create the URL with parameters.
        // The HTTP client should throw an exception since all possible API keys entered are invalid.
        assertThrows(
                ConnectException.class,
                () -> new APISource(apiKey, tags, queries, maxArticle,false),
                "Expected ConnectException to be thrown but wasn't!"
        );
    }

}