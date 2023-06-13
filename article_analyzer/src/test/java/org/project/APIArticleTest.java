package org.project;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@Tag("API")
class APIArticleTest {
    /*
    * This test verify symmetry, transitivity, mutability(for hashMap) and the subclass-superclass equality of the class.
    * Also checks if NullPointerException is thrown, if all significant fields are considered, if a class is equals to itself and
    * if the class passed as argument need to be of the same type.
    * */
    @DisplayName("Testing equals and hashCode.")
    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.forClass(APIArticle.class).verify();
    }

    @DisplayName("Testing hashCode with equality.")
    @Test
    void testHashCodeEquality() throws MalformedURLException {
        APIArticle article1 = new APIArticle("head1","body1","id1", "type1", "sectionId1",
                "sectionName1","webPublicationDate1","webTitle1",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId1","pillarName1",1);
        APIArticle article2 = new APIArticle("head1","body1","id1", "type1", "sectionId1",
                "sectionName1","webPublicationDate1","webTitle1",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId1","pillarName1",1);
        assertTrue(article1.equals(article2));
        assertEquals(article1.hashCode(),article2.hashCode());
    }
    @DisplayName("Testing hashCode with inequality.")
    @Test
    void testHashCodeInequality() throws MalformedURLException {
        APIArticle article1 = new APIArticle("head1","body1","id1", "type1", "sectionId1",
                "sectionName1","webPublicationDate1","webTitle1",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId1","pillarName1",1);
        APIArticle article2 = new APIArticle("head2","body2","id2", "type2", "sectionId2",
                "sectionName2","webPublicationDate2","webTitle2",new URL("https://www.ieee.org"),
                new URL("https://developer.ieee.org"),true,"pillarId2","pillarName2",1);
        assertFalse(article1.equals(article2));
        assertNotEquals(article1.hashCode(),article2.hashCode());
    }
}