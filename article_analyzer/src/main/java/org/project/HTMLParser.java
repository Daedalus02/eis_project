package org.project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This class is used to parse the head and body fields in the article from HTML format.
 */
public class HTMLParser {

    /**
     * Initializing instance of this class..
     */
    public HTMLParser(){}

    /**
     * This method parse the content of the head and body fields of the given Article
     * and then store it in the same article object.
     *
     * @param article which the Article containing the unparsed body and head fields.
     */
    public void parse(APIArticle article) {
        // This allows format the HTML article content, saving its head and body in 2 variables.
        Document doc =Jsoup.parse(article.getHead());
        String head = doc.text();
        doc = Jsoup.parse(article.getBody());
        String body = doc.text();

        // Setting body and head fields in the given Article object.
        article.setBody(body);
        article.setHead(head);

    }
}
