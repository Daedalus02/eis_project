package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@Tag("API")
class HTTPClientTest {

    @DisplayName("Testing the correct working of the getHttpString of the HTTPClient class.")
    @Test
    void testGetHttpString() throws IOException {
        HTTPClient client = new HTTPClient(new URL(""));
        String HTTPString = client.getHttpString();
    }

    @Test
    void testSetUrl() {
    }

    @Test
    void testCloseConnection() {
    }
}