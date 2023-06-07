package org.project;

import org.apache.http.ConnectionClosedException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/* This class is used to test the HTTPClient client connect,getHttpString and closeConnection methods. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("API")
class HTTPClientTest {

    // This variable is used to represent a CloseableHttpClient with different settings.
    private static CloseableHttpClient client;
    // This variable is used to represent a CloseableHttpResponse with different settings.
    private static CloseableHttpResponse response;
    // This variable is used to represent a StatusLine with different settings.
    private static StatusLine statusLine;

    /*
    * Creating the Mocks for HTTP connection simulation elements (response, status and client).
    * */
    @BeforeAll
    static void init(){
        client = Mockito.mock(CloseableHttpClient.class);
        response = Mockito.mock(CloseableHttpResponse.class);
        statusLine = Mockito.mock(StatusLine.class);
    }

    /*
    * Testing all the possible HTTP connection status codes :
    * -Informational status code(100, 101, 102, 103);
    * -Acceptance status code (201, 202, 203, 204, 205, 206, 207, 208, 226) different from 200;
    * -Redirection status code(300, 301, 302, 303, 304,305, 306, 307, 308);
    * -Client error status code(400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413,
    * 414, 415, 416, 417, 418, 421, 422, 423, 424, 425, 426, 428, 429, 431, 451);
    * -Server error status code (500, 501, 502, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511);
    * */
    @DisplayName("Testing HTTPClient to throw exception whenever the Status code is different from 200 (\"OK\")")
    @ParameterizedTest
    @ValueSource(ints = {100, 101, 102, 103, 201, 202, 203, 204, 205, 206, 207, 208, 226, 300, 301, 302, 303, 304,
                        305, 306, 307, 308, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413,
                        414, 415, 416, 417, 418, 421, 422, 423, 424, 425, 426, 428, 429, 431, 451, 500, 501, 502,
                        501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511})
    void testConnectionStatusException(int statusCode) throws IOException {
        // Specifying client, response and statusLine behavior.
        when(statusLine.getStatusCode()).thenReturn(statusCode);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(client.execute(Mockito.any(HttpGet.class))).thenReturn(response);

        // Initializing the client with mock parameter.
        HTTPClient httpClient = new HTTPClient(client);

        // Asserting that for all the possible status code different from OK (200) the Client will throw an exception
        assertThrows(
                ConnectException.class,
                () -> httpClient.connect(new URL("https://content.guardianapis.com/search?api-key=test")),
                "Exepected ConnectTimeoutException to be thrown but wasn't."
        );
    }

    /*
    * This method checks that the HTTP client will not throw an exception if the status code is 200 (OK).
    * */
    void testCorrectStatusConnection() throws IOException {
        // Specifying client, response and statusLine behavior.
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(client.execute(Mockito.any(HttpGet.class))).thenReturn(response);
        // Initializing the client with mock parameter.
        HTTPClient httpClient = new HTTPClient(client);
        // Asserting that for all the possible status code different from OK (200) the Client will throw an exception
        assertDoesNotThrow(
                () -> httpClient.connect(new URL("https://content.guardianapis.com/search?api-key=test"))
        );
    }

    /* This method test the behavior od HTTPClient when the connection take too much time.  */
    @Test
    @DisplayName("Testing HTTPClient to throw an IOException if the time for the connection is over the max possible(five seconds)")
    void testConnectionTimeoutException() throws IOException {
        // Specifying client, response and statusLine behavior.
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        // Creating the connection timeout exception.
        ConnectTimeoutException timeoutException = new ConnectTimeoutException();
        // Setting Mockito so that it throw the exception at get HTTP request, instead of response.
        when(client.execute(Mockito.any(HttpGet.class))).thenThrow(timeoutException);
        // Initializing the client with mock parameter.
        HTTPClient httpClient = new HTTPClient(client);
        // Asserting that the HTTPClient will not accept a connection timout.
        assertThrows(
                ConnectTimeoutException.class,
                () -> httpClient.connect(new URL("https://content.guardianapis.com/search?api-key=test")),
                "Expected ConnectTimeoutException to be thrown but wasn't."
        );
    }

    /* This method test the behavior of the HTTPClient when the data take too much time to reach the client. */
    @Test
    @DisplayName("Testing HTTPClient to throw an IOException if the time for the connection is over the max possible(five seconds)")
    void testSocketTimeoutException() throws IOException {
        // Specifying client, response and statusLine behavior.
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        // Creating the connection timeout exception.
        SocketTimeoutException timeoutException = new SocketTimeoutException();
        // Setting Mockito so that it throw the exception at get HTTP request, instead of response.
        when(client.execute(Mockito.any(HttpGet.class))).thenThrow(timeoutException);
        // Initializing the client with mock parameter.
        HTTPClient httpClient = new HTTPClient(client);
        // Asserting that the HTTPClient will not accept a socket connection timout.
        assertThrows(
                SocketTimeoutException.class,
                () -> httpClient.connect(new URL("https://content.guardianapis.com/search?api-key=test")),
                "Expected SocketTimeoutException to be thrown but wasn't."
        );
    }

    /* This method test the ConnectionClosedException exception thrown by the getHttpString method. */
    @Test
    @DisplayName("Testing the behavior of the metho getHttpString when the connection is closed.")
    void testConnctionClosedMessageGetter() throws IOException {
        // Setting the client with a valid URL.
        HTTPClient client = new HTTPClient();
        client.connect(new URL("https://content.guardianapis.com/search?api-key=test"));
        // Closing the connection.
        client.closeConnection();
        assertThrows(
                ConnectionClosedException.class,
                () -> client.getHttpString(),
                "Expected ConnectionClosedException to be thrown but wasn't."
        );
    }

    /* This method is used to test if the closeConnection method close actually the connection. */
    @Test
    @DisplayName("Testing if the closeConnection method actually close the HTTP client connection.")
    void testCloseConnection() throws IOException {
        // Setting the client with a valid URL.
        HTTPClient client = new HTTPClient();
        client.connect(new URL("https://content.guardianapis.com/search?api-key=test"));
        // Closing the connection.
        client.closeConnection();
        assertFalse(client.isConnected());
        //Note: the getHttpString should throw an exception if invoked when the connection is closed.
        assertThrows(
                ConnectionClosedException.class,
                () -> client.getHttpString(),
                "Expected ConnectionClosedException to be thrown but wasn't."
        );
    }

    /* This test checks if the connect method in HTTPClient actually create the connection. */
    @Test
    @DisplayName("Testing if the connect method actually create the HTTP client connection.")
    void testConnect() throws IOException {
        // Setting the client with a valid URL.
        HTTPClient client = new HTTPClient();
        client.connect(new URL("https://content.guardianapis.com/search?api-key=test"));
        assertTrue(client.isConnected());
        //Note: the getHttpString should not throw an exception if invoked when the connection is closed.
        assertDoesNotThrow(
                () -> client.getHttpString()
        );
        // Closing the connection.
        client.closeConnection();
    }
}