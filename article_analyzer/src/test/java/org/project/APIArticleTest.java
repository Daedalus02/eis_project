package org.project;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

@Tag("API")
class APIArticleTest {
    @DisplayName("Testing equals and hashCode.")
    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.forClass(APIArticle.class).verify();
    }
}