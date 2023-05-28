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
 * This class is used to create an HTTP client only capable of using the HTTP verb "get"
 * to then retrieve the content of a web page. The given feature are setting URL getting
 * the response string and closing the connection.
 *
 * NOTICE: This class is actually a wrapper that contains the effective HTTP client from
 * the package org.apache.http.impl.client {@link org.apache.http.impl.client.CloseableHttpClient}.
 * */
public class HTTPClient {
    /** This is the actual HTTP client. */
    private CloseableHttpClient client;
    /** This variable store the configuration for the request. */
    private RequestConfig config;
    /** This is the variable that store all the information related to the request. */
    private HttpGet request;
    /** This is the variable that holds the response of the web page. */
    private CloseableHttpResponse response;
    /** This variable is used to tell whether the Client is connected or not in a give moment . */
    private boolean connected = false;
    /** This is the max value of time give to the connection, the connection request, socket, and cookie specification. */
    private final int TIMEOUT = 5000;
    /** This is the value of a correctly initialized connection. */
    private final int CORRECT_STATUS = 200;

    /**
     * This constructor set the connection and store all the data related to it({@link HTTPClient#client}, {@link HTTPClient#config},
     * {@link HTTPClient#connected}, {@link HTTPClient#request}, {@link HTTPClient#response}).
     *
     * @param url which is the URL of the Web page we want to connect to.
     * @throws IOException which is thrown if the {@link HTTPClient#TIMEOUT} is surpassed.
     */
    public HTTPClient(URL url) throws IOException {
        // Setting the configuration
        config = RequestConfig.custom().setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).setCookieSpec(CookieSpecs.STANDARD).build();
        client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        request = new HttpGet(url.toString());
        response = client.execute(request);
        connected = true;
    }

    /**
     * This method is used to get the response{@link HTTPClient#response} of the web page to the HTTP client request ({@link HTTPClient#request})
     *
     * @return a String containing the response of the web page to the HTTP client request.
     * @throws IOException which is thrown if the connection was not possible.
     * @throws IllegalStateException which is thrown if the HTTP client {@link HTTPClient#client} is not connected.
     */
    public String getHttpString() throws IOException, IllegalStateException{
        if(!connected) throw new IllegalStateException("The httpGetter is not connceted!");

        int responseCode = response.getStatusLine().getStatusCode();
        if(responseCode != CORRECT_STATUS){
            throw new ImpossibleConnection();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while((line = reader.readLine()) != null){
            result.append(line);
            result.append('\n');
        }
        reader.close();
        closeConnection();

        return result.toString();
    }

    /**
     * This method is used to set the URL of the web page.
     *
     * @param url which is the URL of the web page it is trying to connect.
     * @throws IOException which is thrown if the request{@link HTTPClient#request} was not correctly done.
     * @throws IllegalStateException
     */
    public void setUrl(URL url) throws IOException, IllegalStateException {
        if(!connected) throw new IllegalStateException("The httpGetter is not connected!");
        client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        request = new HttpGet(url.toString());
        response = client.execute(request);
        connected = true;
    }

    /**
     * This method check to see if the connection is open and then close it.
     *
     * @throws IOException which is thrown if the Client and response connection close was not possible.
     */
    public void closeConnection() throws IOException {
        client.close();
        response.close();
        connected = false;
    }
}
