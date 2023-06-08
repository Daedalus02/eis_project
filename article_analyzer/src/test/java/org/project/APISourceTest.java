package org.project;

import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Tag("API")
class APISourceTest {

    /*
    * This test verify that the API Source get the correct number of articles
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
        APISource source = new APISource(apiKey,tags,queries,maxArticles);
        List<APIArticle> articleList = source.getArticles();
        assertEquals(articleList.size(),maxParam);
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
        // Specifying 2 queries --> nuclear, power
        String[] queries = new String[]{"nuclear", "power"};
        // Setting the max number of articles (and also a token max frequency)
        int maxArticles = maxParam;
        // Setting the tags to none.
        String[] tags = new String[]{};
        // Setting the APISource with the previous parameters.
        APISource source = new APISource(apiKey,tags,queries,maxArticles);
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

}