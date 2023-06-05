package org.project;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("CSV")
class CSVArticleTest {

    @DisplayName("Testing equals and hashCode.")
    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.forClass(CSVArticle.class).verify();
    }
}