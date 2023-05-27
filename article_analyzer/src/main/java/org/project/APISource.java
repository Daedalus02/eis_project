package org.project;

import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent an Articles source where the articles are read from an api response.
 */
public class APISource implements ArticleSource {
    private HTTPGetter httpgetter;
    private HTMLParser htmlparser;
    private URLSetter urlsetter;
    private JSONParser jsonparser;
    private int maxArticle;
    public final int intialCount = 1;
    private final int pageSize = 100;
    private final String baseUrl = "https://content.guardianapis.com";
    private List<Article> articles;


    /**
     * This constructor sets the url and the max number of article to return, initializer the "urlsetter" with first value
     * and the articles.
     *
     * @param apiKey which needs to by a valid api key of the "The Guardian" api page.
     * @param tags which are used to specify a set of articles related to the strings passed.
     * @param query which are used to search a specific set oof words inside alle possible articles.
     * @param maxArticle1 which is the max number of article to return (so the actual number could be less)
     * @throws IOException
     * @throws JSONException
     * @throws BadLocationException
     */
    public APISource(String apiKey, String[] tags, String[] query, int maxArticle1) throws IOException, JSONException, BadLocationException {
        //setting the max number of articles to read (could be less)
        maxArticle = maxArticle1;
        //setting url basing on the fields required for the api request
        urlsetter = new URLSetter(baseUrl, apiKey, intialCount, pageSize, query, tags);
        //setting articles variable
        articles = new ArrayList<Article>();
        readArticle();
    }
    /**
     * This method read the Articles and parse(from html) the content of the head and body fields of the json response.
     * @throws BadLocationException
     * @throws IOException
     * @throws JSONException
     */
    private void readArticle() throws BadLocationException, IOException, JSONException {
        int articleCount = 0;
        String url = "";
        String apiString = "";


        while (articleCount < maxArticle) {
            //setting url basing on the fields required for the api request
            url = urlsetter.getUrl();
            urlsetter.incrementPage();
            System.out.println("from " + url + " :");

            //getting response from the api point
            httpgetter = new HTTPGetter(new URL(url));
            apiString = httpgetter.getHttpString();

            //parsing the response
            jsonparser = new JSONParser(apiString);
            articles.addAll(jsonparser.getArticles());

            //setting the max number of article to analyze, also basing on the number of available ones
            if (jsonparser.getTotal() < maxArticle) {
                maxArticle = jsonparser.getPages();
                System.out.println("Limited to " + maxArticle + " pages...");
            }

            //initializing parser
            htmlparser = new HTMLParser();

            //analyzing each of the single new articles
            for (Article article : articles.subList(articleCount, articles.size())) {
                if (articleCount == maxArticle) {
                    break;
                }
                System.out.println("Anayzing site number: " + (articleCount + 1) + " with title: " + article.getWebTitle());
                htmlparser.parse(article);
                articleCount++;
            }
        }

        //TRIM --> reducing the size the number of actual articles read
        articles = new ArrayList<Article>(articles.subList(0, maxArticle));
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }
}
