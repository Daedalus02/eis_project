package org.project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This class is used to parse text content from HTML format.
 */
public final class HTMLParser {

    /**
     * Initialize instance of this class.
     */
    public HTMLParser(){}

    /**
     * This method parses the content the string passed by argument, from HTML format
     * to a string containing the extracted text.
     *
     * @param HTMLString which is the String containing HTML formatted text.
     */
    public String parse(String HTMLString) {
        // This stores the HTML content in a Document class.
        Document doc =Jsoup.parse(HTMLString);
        // This allows to extract the HTML textual content.
        String head = doc.text();
        return head;
    }
}
