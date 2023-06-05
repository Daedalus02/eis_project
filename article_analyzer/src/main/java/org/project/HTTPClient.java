package org.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * This class is used to create an HTTP client capable of using the HTTP method "GET"
 * to retrieve the content of a web page. Its features are setting URL, getting
 * the response string and closing the connection.
 *
 * NOTE: This class is a wrapper that contains the HTTP client from
 * the package org.apache.http.impl.client {@link org.apache.http.impl.client.CloseableHttpClient}.
 * */
public final class HTTPClient {
    /** This is the HTTP client. */
    private CloseableHttpClient client;
    /** This variable stores the configuration for the request. */
    private RequestConfig config;
    /** This is the variable that stores all the information related to the request. */
    private HttpGet request;
    /** This is the variable that holds the response of the web page. */
    private CloseableHttpResponse response;
    /** This variable is used to tell whether the Client is connected or not in a given moment . */
    private boolean connected = false;
    /** This is the max value of time (ms) given to the connection, the connection request, socket, and cookie specification. */
    private final int TIMEOUT = 5000;
    /** This is the value of a correctly initialized connection. */
    private final int CORRECT_STATUS = 200;

    /**
     * This constructor sets the connection and stores all the data related to it: ({@link HTTPClient#client}, {@link HTTPClient#config},
     * {@link HTTPClient#connected}, {@link HTTPClient#request}, {@link HTTPClient#response}).
     *
     * @param url which is the URL of the Web page.
     * @throws IOException if the {@link HTTPClient#TIMEOUT} is surpassed.
     */
    public HTTPClient(URL url) throws IOException {
        // Setting the configuration (max time for the connection, connection request, cookies specification, packets).
        config = RequestConfig.custom().setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).setCookieSpec(CookieSpecs.STANDARD).build();
        // Building the client with the set configuration.
        client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        // Building the request.
        request = new HttpGet(url.toString());
        // Executing the request and getting the page response.
        response = client.execute(request);
        // Now connected so setting the connected variable to true.
        connected = true;
    }

    /**
     * This method is used to get the {@link HTTPClient#response} of the web page to the HTTP client {@link HTTPClient#request}
     *
     * @return a String containing the response of the web page to the HTTP client request.
     * @throws IOException if the connection was not possible.
     * @throws IllegalStateException if the HTTP client {@link HTTPClient#client} is not connected.
     */
    public String getHttpString() throws IOException, IllegalStateException{
        // Checking to see if the HTTP client is still connected.
        if(!connected) throw new IllegalStateException("The HTTP client is not connected.");

        // Getting the response status and checking if everything went ok in the connection phase.
        int responseCode = response.getStatusLine().getStatusCode();
        if(responseCode != CORRECT_STATUS){
            System.err.println("STATUS_CODE = " + responseCode);
            throw new ImpossibleConnection();
        }

        // Reading the actual response.
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while((line = reader.readLine()) != null){
            result.append(line);
            result.append('\n');
        }
        reader.close();
        // Closing the connection.
        closeConnection();

        return result.toString();
    }

    /**
     * This method is used to set the URL of the web page (only when the {@link HTTPClient#client}  is not {@link HTTPClient#connected}).
     *
     * @param url which is the URL of the web page it is trying to connect.
     * @throws IOException if the {@link HTTPClient#request} was not correctly done.
     * @throws IllegalStateException if the HTTP {@link HTTPClient#client} is already connected.
     */
    public void setUrl(URL url) throws IOException, IllegalStateException {
        // Checks if the client is connected, if so throws an exception.
        if(connected) throw new IllegalStateException("The HTTP client is already connected!");
        // Rebuilding the client with default configuration.
        client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        // Setting again the request for the web page.
        request = new HttpGet(url.toString());
        // Getting the web page response.
        response = client.execute(request);
        // Now connected so setting connected to true.
        connected = true;
    }

    /**
     * This method checks to see if the connection is open and then closes it.
     *
     * @throws IOException if the client and response connection closure was not possible.
     */
    public void closeConnection() throws IOException {
        if(connected) {
            client.close();
            response.close();
            // Now not connected so setting connected to false.
            connected = false;
        }
    }
}
