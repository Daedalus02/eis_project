package org.project;

import java.io.IOException;
import java.net.MalformedURLException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.text.BadLocationException;

/**
 * This class is used to parse the head and body fields in the article from html format
 */

    public class HTMLParser {

    public HTMLParser(){}

    /**
     * this method allow the program to store the parsed content of the head and body elements in the given article object relative fields
     *
     * @param article
     * @throws MalformedURLException
     * @throws IOException
     * @throws BadLocationException
     */
    public void parse(Article article) throws MalformedURLException, IOException, BadLocationException {
        //this allows format the html article content, saving its head and body in 2 variables
        //notice: replace remove all invalid characters in xml11 format
        Document doc =Jsoup.parse(article.getHead());
        String head = doc.text();
        doc = Jsoup.parse(article.getBody());
        String body = doc.text();

        //setting body and head fields in the given article object
        article.setBody(body);
        article.setHead(head);

    }
}
