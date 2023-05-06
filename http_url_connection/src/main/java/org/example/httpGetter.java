package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class httpGetter {
    private URL url;
    private HttpURLConnection connection;
    private StringBuffer responseContent;
    private int status;
    private boolean connected = false;


    /**
     *
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
        connected = true;
    }

    /**
     *
     * @param url
     * @throws MalformedURLException
     * @throws IOException
     * @throws impossibleConnection
     */
    public void setUrl(URL url ) throws MalformedURLException, IOException, impossibleConnection{
        if(connected) throw new impossibleConnection();
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        status = connection.getResponseCode();
        connected = true;
    }

    /**
     *
     * @return
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
     *
     */
    public void closeConnection(){
        connection.disconnect();
        connected = false;
    }
}
