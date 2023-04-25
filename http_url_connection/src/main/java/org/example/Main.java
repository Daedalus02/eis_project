package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            //URL url = new URL("https://content.guardianapis.com/search?api-key=c9d442dd-66ec-43a8-aa3d-26047fa8780e");
            URL url = new URL("https://www.digitalocean.com/community/tutorials/java-httpurlconnection-example-java-http-request-get-post");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(500);

            int status = connection.getResponseCode();
            //System.out.println(status == 200);

            //status handling
            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                    responseContent.append('\n');
                }
                reader.close();
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null){
                    responseContent.append(line);
                    responseContent.append('\n');
                }
                reader.close();
            }

            System.out.println(responseContent.toString());
            connection.disconnect();
        }catch(MalformedURLException e ){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}