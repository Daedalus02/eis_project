package org.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * this class is used to get the response from the "The guardian" api
 */
public class httpGetter {
    private URL url;
    private HttpURLConnection connection;
    private StringBuffer responseContent;
    private int status;
    private String message;
    private boolean connected = false;

    /**
     * This constructor the http Method, connection timeout, read timeout and gets the status code and the message of the http response
     * @param url
     * @throws MalformedURLException
     * @throws IOException
     */
    public httpGetter(URL url ) throws MalformedURLException, IOException{
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        responseContent = new StringBuffer();
        status = connection.getResponseCode();
        message = connection.getResponseMessage();
        connected = true;
    }

    /**
     * this method is used to change the url of the connection (restoring its settings)
     * @param url
     * @throws MalformedURLException
     * @throws IOException
     * @throws impossibleConnection
     */
    public void setUrl(URL url ) throws IOException, impossibleConnection{
        if(connected) throw new impossibleConnection();
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        status = connection.getResponseCode();
        connected = true;
    }

    /**
     * this method is used to get the response, checking status code for errors
     * @return response content (string)
     * @throws IOException
     */
    public String getHttpString() throws  IOException {
        if(!connected) throw new IllegalStateException("The httpGetter is not connceted!");
        BufferedReader reader;
        String line;

        //status handling
        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
                responseContent.append('\n');
            }
            reader.close();
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
                responseContent.append('\n');
            }
            reader.close();
        }

        return responseContent.toString();

    }

    /**
     * this method is used to close the connection if needed
     */
    public void closeConnection(){
        connection.disconnect();
        connected = false;
    }
}
