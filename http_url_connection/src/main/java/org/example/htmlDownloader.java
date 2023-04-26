package org.example;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class htmlDownloader {
    private static int page_count = 0;
    public htmlDownloader(){

    }
    public String download(String stringUrl) throws MalformedURLException, IOException {
        URL url = new URL(stringUrl);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String file_name ="res\\pages\\"+"page"+page_count+".html";
        File file = new File(file_name);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file_name));
        String line = "";

        while((line = reader.readLine()) != null) {
            writer.write(line);
        }

        writer.close();
        reader.close();
        page_count++;
        return file_name;
    }
}
