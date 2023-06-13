package org.project;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@Tag("CSV")
class CSVArticleTest {
    /*
     * This test verify symmetry, transitivity, mutability(for hashMap) and the subclass-superclass equality of the class.
     * Also checks if NullPointerException is thrown, if all significant fields are considered, if a class is equals to itself and
     * if the class passed as argument need to be of the same type.
     * */
    @DisplayName("Testing equals and hashCode.")
    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.forClass(CSVArticle.class).verify();
    }
    @DisplayName("Testing hashCode correct equality.")
    @Test
    void testHashCodeEquality() throws MalformedURLException {
        CSVArticle article1 = new CSVArticle("title1","body1","identifier1",new URL("https://it.wikipedia.org"),"date1","source_set1","source1");
        CSVArticle article2 = new CSVArticle("title1","body1","identifier1",new URL("https://it.wikipedia.org"),"date1","source_set1","source1");
        assertTrue(article1.equals(article2));
        assertEquals(article1.hashCode(), article2.hashCode());
    }
    @DisplayName("Testing hashCode inequality.")
    @Test
    void testHashCodeInequality() throws MalformedURLException {
        CSVArticle article1 = new CSVArticle("title1","body1","identifier1",new URL("https://it.wikipedia.org"),"date1","source_set1","source1");
        CSVArticle article2 = new CSVArticle("title2","body2","identifier2",new URL("https://stackoverflow.com/"),"date2","source_set2","source2");
        assertFalse(article1.equals(article2));
        assertNotEquals(article1.hashCode(), article2.hashCode());
    }
}