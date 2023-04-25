package org.example;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
public class Main {
    public static void main(String Args[]){
        String str = "";
        try {
            httpGetter getter = new httpGetter(new URL("https://www.digitalocean.com/community/tutorials/java-httpurlconnection-example-java-http-request-get-post"));
            getter.closeConnection();
            getter.setUrl(new URL("https://content.guardianapis.com/search?api-key=c9d442dd-66ec-43a8-aa3d-26047fa8780e"));
            str = getter.getHttpString();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
        //System.out.println(str);
        Tokenizer tokenizer = new Tokenizer(str);
        tokenizer.printTokens();
        System.out.println();
        tokenizer.printFirst();
    }
}
