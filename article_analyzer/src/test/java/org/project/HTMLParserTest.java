package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* This class contains method used to check the correct functioning of the HTMLParser class.*/
@Tag("API")
class HTMLParserTest {

    /* This method checks the functioning of the HTML parser with a correctly formatted HTML String. */
    @DisplayName("Testing if the HTML parser correctly parses HTML formatted string")
    @Test
    void testParse() {
        HTMLParser parser = new HTMLParser();
        String HTMLString = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>head</title>" +
                "</head>" +
                "<body>" +
                "<h1>body</h1>" +
                "</body>" +
                "</html>";
        String expected = "head body";
        assertEquals(expected,parser.parse(HTMLString));
    }

    /* This method checks the functioning of the HTML parser with a normal String. */
    @DisplayName("Testing if the HTML parser return the correct string if the argument is not in HTML format.")
    @Test
    void testParseWithNormalString() {
        HTMLParser parser = new HTMLParser();
        // Note: this String is not formatted in HTML
        String normalString = "Just a normal string";
        String expected = "Just a normal string";
        assertEquals(expected, parser.parse(normalString));
    }

    /* This method checks the functioning of the HTML parser with incorrectly HTML formatted String. */
    @DisplayName("testing an incorrectly HTML formatted string.")
    @Test
    void testInvalidHTMLString(){
        HTMLParser parser = new HTMLParser();
        // Note: this String is not formatted in HTML
        String invalidHTMLString= "<!DOCTYPE html>" +
                "<html>" +
                "<>" +
                "head" +  // No valid tag is present so "head" shouldn't be read.
                "</>" +
                "<body>" +
                "<h1>body</h1>" +
                "</body>" +
                "</html>";
        String expected = "head body";
        assertNotEquals(expected, parser.parse(invalidHTMLString));
    }
}