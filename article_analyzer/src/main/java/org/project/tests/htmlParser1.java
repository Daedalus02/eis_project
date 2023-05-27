package org.project.tests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.text.BadLocationException;

/**
 * This class allow to
 */
public class htmlParser1
{
    private String parsedHtml;
    public htmlParser1(){
    }

    /**
     *
     * @param htmlString
     * @return
     * @throws BadLocationException
     */
    public String parse(String htmlString) throws BadLocationException {
        Document doc =Jsoup.parse(htmlString);
        parsedHtml = doc.head().text() + doc.body().text();
        return parsedHtml;
    }
    public String getParsedHtml(){
        return parsedHtml;
    }
}
