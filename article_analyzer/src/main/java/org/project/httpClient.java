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

public class httpClient {
    private CloseableHttpClient client;
    private RequestConfig config;
    private HttpGet request;
    private CloseableHttpResponse response;
    private boolean connected = false;
    private final int timeout = 5000;
    private final int correctStatus = 200;
    public httpClient(URL url) throws IOException {
        config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).setCookieSpec(CookieSpecs.STANDARD).build();
        client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        request = new HttpGet(url.toString());
        response = client.execute(request);
        connected = true;
    }
    public String getHttpString() throws IOException, IllegalStateException{
        if(!connected) throw new IllegalStateException("The httpGetter is not connceted!");

        int responseCode = response.getStatusLine().getStatusCode();
        if(responseCode != correctStatus){
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
    public void setUrl(URL url) throws IOException, IllegalStateException {
        if(!connected) throw new IllegalStateException("The httpGetter is not connceted!");
        client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        request = new HttpGet(url.toString());
        response = client.execute(request);
        connected = true;
    }
    public void closeConnection() throws IOException {
        client.close();
        response.close();
        connected = false;
    }
}
