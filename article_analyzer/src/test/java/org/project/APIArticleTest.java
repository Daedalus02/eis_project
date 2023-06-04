package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class APIArticleTest {

    @DisplayName("Testing if subclass fields get tested for equality.")
    @Test
    void testEquals() throws MalformedURLException {
        APIArticle article = new APIArticle("head","body","Id", "type", "sectionId",
                "sectionName","webPublicationDate","webTitle",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId","pillarName",1);
        APIArticle expected = new APIArticle("head","body","Id", "type", "sectionId",
                "sectionName","webPublicationDate","webTitle",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId","pillarName",1);
        assertEquals(article,expected);
        article.setHead("different");
        assertNotEquals(article,expected);
    }

    @DisplayName("Testing if null is never equal to an object of APIArticle class")
    @Test
    void test_Inequality_Comparing_With_Null() throws MalformedURLException {
        APIArticle article = new APIArticle("head","body","Id", "type", "sectionId",
                "sectionName","webPublicationDate","webTitle",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId","pillarName",1);
        assertNotEquals(null, article);
    }
}