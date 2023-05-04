package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.text.BadLocationException;

public class htmlParser
{
    private String parsedHtml;
    public htmlParser(){
    }
    public String parse(String htmlString) throws BadLocationException {
        Document doc =Jsoup.parse(htmlString);
        parsedHtml = doc.text();
        return parsedHtml;
    }
    public String getParsedHtml(){
        return parsedHtml;
    }
}