package org.example;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
public class test {
    public static void main(String Args[]){
        String str = "";
        try {
            httpGetter getter = new httpGetter(new URL("https://www.digitalocean.com/community/tutorials/java-httpurlconnection-example-java-http-request-get-post"));
            str = getter.getHttpString();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(str);
    }
}
