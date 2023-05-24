package org.project;

import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class apiSource implements articleSource{
    private httpGetter httpgetter;
    private htmlParser htmlparser;
    private urlSetter urlsetter;
    private jsonParser jsonparser;
    private int maxArticle;
    public final int intialCount = 1;
    private final int pageSize = 100;

    private List<Article> articles;
    public apiSource(String apiKey, String[] tags, String[] query, int maxArticle1) throws IOException, JSONException, BadLocationException {
        //setting the max number of articles to read (could be less)
        maxArticle = maxArticle1;
        //setting url basing on the fields required for the api request
        urlsetter = new urlSetter("https://content.guardianapis.com", apiKey, intialCount, pageSize, query, tags);
        //setting articles variable
        articles = new ArrayList<Article>();
        readArticle();
    }

    private void readArticle() throws BadLocationException, IOException, JSONException {
        int articleCount = 0;
        String url = "";
        String apiString = "";


        while (articleCount < maxArticle) {
            //setting url basing on the fields required for the api request
            urlsetter.incrementPage();
            url = urlsetter.getUrl();
            System.out.println("from " + url + " :");

            //getting response from the api point
            httpgetter = new httpGetter(new URL(url));
            apiString = httpgetter.getHttpString();

            //parsing the response
            jsonparser = new jsonParser(apiString);
            articles.addAll(jsonparser.getArticles());

            //setting the max number of article to analyze, also basing on the number of available ones
            if (jsonparser.getPages() < maxArticle) {
                maxArticle = jsonparser.getPages();
                System.out.println("Limited to " + maxArticle + " pages...");
            }

            //initializing parser
            htmlparser = new htmlParser();

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

        //TRIM
        articles = new ArrayList<Article>(articles.subList(0, maxArticle));
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }
}
