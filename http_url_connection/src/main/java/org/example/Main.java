package org.example;


import org.json.JSONException;

import javax.swing.text.BadLocationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Main {
    public static void main(String Args[]) {
        try {
            String url = (new urlSetter("https://content.guardianapis.com", "c9d442dd-66ec-43a8-aa3d-26047fa8780e", 2, "politics", new String[]{}, new String[]{})).getUrl();
            String apiString = "";
            httpGetter getter = new httpGetter(new URL(url));
            apiString = getter.getHttpString();

            jsonParser jsonparser = new jsonParser(apiString);
            article[] articles = jsonparser.getArticles();

            htmlDownloader downloader = new htmlDownloader();
            String[] fileNames = new String[articles.length];

            int counter = 0;
            String pageContent = " "; //must have length different from 0
            String pageText = " ";

            Tokenizer tokenizer = new Tokenizer(pageContent,true);
            htmlParser htmlparser = new htmlParser();

            for (article  article : articles) {
                fileNames[counter] = downloader.download(article.getWebUrl().toString());
                Scanner reader = new Scanner(new File(fileNames[counter]));
                while(reader.hasNextLine()){
                    pageContent += reader.nextLine();
                }
                pageText = htmlparser.parse(pageContent);
                tokenizer.addDocument(pageText);
                //System.out.println(tokenizer.getSize());
                counter++;
                pageContent = "";
            }

            //printing 50 more frequent tokens
            System.out.println("The 50 more frequent words in the analyzed articles are: ");
            Set<Map.Entry<Integer, List<String>>> set =  tokenizer.getOrderedTokens(50);

            int wordCounter = 0;
            Iterator<Map.Entry<Integer, List<String>>> iter = set.iterator();
            Map.Entry<Integer, List<String>> pair = iter.next();
            while(iter.hasNext()) {
                int index = pair.getValue().size();
                while(index > 0){
                    index--;
                    System.out.println("          "+(wordCounter+1)+": "+pair.getValue().get(index)+" "+pair.getKey());
                    wordCounter++;
                }
                pair = iter.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }
}
