package org.project;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertThrows;
class httpGetterTest
{
    @Test
    public void throwIOException() throws IOException{
       /* try {
            URL url = new URL("https://www.jetbrais.com");
            assertThrows(
                    IOException.class,
                    () ->{
                        //wrong url
                        httpGetter getter = new httpGetter(url);
                    }
            );
        }catch(MalformedURLException e){
            e.printStackTrace();
        }*/
        URL url = new URL("https://www.jetbrais.com");
        httpGetter getter = new httpGetter(url);
    }
}