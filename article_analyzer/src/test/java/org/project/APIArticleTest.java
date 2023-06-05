package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@Tag("API")
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
    }

    @DisplayName("Testing if null is never equal to an object of APIArticle class")
    @Test
    void test_Inequality_Comparing_With_Null() throws MalformedURLException {
        APIArticle article = new APIArticle("head","body","Id", "type", "sectionId",
                "sectionName","webPublicationDate","webTitle",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId","pillarName",1);
        assertNotEquals(null, article);
    }

    /* Note that article1 and article2 are two distinct object in memory while they're considered equals by the APIArticle equals
     * method, so they should produce the same hashCode.
     * */
    @DisplayName("Testing hashCode method for APIArticle")
    @Test
    void testHashCode() throws MalformedURLException {
        APIArticle article1 = new APIArticle("head","body","Id", "type", "sectionId",
                "sectionName","webPublicationDate","webTitle",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId","pillarName",1);
        APIArticle article2 = new APIArticle("head","body","Id", "type", "sectionId",
                "sectionName","webPublicationDate","webTitle",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId","pillarName",1);
        assertEquals(article1, article2);
        assertEquals(article1.hashCode(), article2.hashCode());
    }
}