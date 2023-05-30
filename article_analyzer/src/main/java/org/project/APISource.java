package org.project;

import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent a source of articles which are read from the API response of the "The Guardian" API endpoint.
 */
public class APISource implements ArticleSource{
    /** This is used to actually connect to the API endpoint.*/
    private HTTPClient client;
    /** This is used to parse the content of the articles body and head fields.*/
    private HTMLParser htmlParser;
    /** This is used to facilitate setting and storing the URL.*/
    private URLSetter urlSetter;
    /** This is used to parse the field of an article in the response of the API endpoint which is formatted in JSON.*/
    private APIParser jsonParser;
    /** This is used to elaborate a given maximum number of article (the number of available ones could be bigger or lower).*/
    private int maxArticle;
    /** This is used to start the research in the response from the API endpoint starting by the first group of articles.*/
    public final int INITIALCOUNT = 1;
    /** This is used to set a max number of articles present in the response of the API endpoint.*/
    private final int PAGESIZE = 100;
    /** This is used to keep track of the article List to eventually return it when asked. */
    private List<APIArticle> articles;


    /**
     * This constructor sets the url and the max number of article to return, initialize the URL setter {@link URLSetter} with first value
     * and the articles.
     *
     * @param APIKey which needs to be a valid API key of the "The Guardian" API page.
     * @param tags which are used to specify a set of articles related to the strings passed.
     * @param queries which are used to search a specific set of words inside all possible articles.
     * @param maxArticle which is the max number of article to return (so the actual number could be lower)
     * @throws IOException
     * @throws JSONException
     * @throws BadLocationException
     */
    public APISource(String baseURL, String APIKey, String[] tags, String[] queries, int maxArticle) throws IOException, JSONException, BadLocationException, ClassNotFoundException {
        // Setting the max number of articles to read (could be less or more).
        this.maxArticle = maxArticle;
        // Setting URL based on the fields required for the API request.
        urlSetter = new URLSetter(baseURL, APIKey, INITIALCOUNT, PAGESIZE, queries, tags);
        // Initializing the articles variable.
        articles = new ArrayList<APIArticle>();
        // This reads all the articles from the JSON response of the API endpoint.
        readArticle();
    }


    /**
     * This method reads the Articles from the API JSON response and parses (from HTML) the content of the head and body fields of the articles.
     *
     * @throws IOException if the connection went wrong and the HTTP client was not able to connect to the endpoint.
     * @throws JSONException if the API response does not have the expected fields.
     */
    private void readArticle() throws IOException, JSONException {
        int articleCount = 0;
        String URL = "";
        String APIString = "";


        while (articleCount < maxArticle) {
            // Setting url based on the fields required for the API request.
            URL = urlSetter.getURL();
            // Incrementing the Article page in the response for the next cycle.
            urlSetter.incrementPage();
            System.out.println("from " + URL + " :");

            // Getting the response from the API endpoint.
            client = new HTTPClient(new URL(URL));
            APIString = client.getHttpString();

            // Parsing the response from JSON format.
            jsonParser = new APIParser(APIString);
            // Adding the Articles read in the new Article page.
            articles.addAll(jsonParser.getArticles());

            // Setting the max number of Articles to analyze, based on the number of available ones.
            if (jsonParser.getTotal() < maxArticle) {
                maxArticle = jsonParser.getPages();
                System.out.println("Limited to " + maxArticle + " pages...");
            }

            // Initializing the HTMLParser.
            htmlParser = new HTMLParser();
            // Parsing the content of each of the single new articles from HTML.
            for (APIArticle article : articles.subList(articleCount, articles.size())) {
                if (articleCount == maxArticle) {
                    break;
                }
                System.out.println("Analyzing site number: " + (articleCount + 1) + " with title: " + article.getWebTitle());
                htmlParser.parse(article);
                articleCount++;
            }
        }
        // Reducing the size of Articles to the actual number of Articles read.
        articles = new ArrayList<APIArticle>(articles.subList(0, maxArticle));
    }

    /**
     * This method is used to simply return the elaborated List of Articles {@link APISource#articles}
     *
     * @return Article List
     */
    @Override
    public List<APIArticle> getArticles() {
        return articles;
    }
}
