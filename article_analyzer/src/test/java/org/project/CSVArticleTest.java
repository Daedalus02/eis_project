package org.project;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
}