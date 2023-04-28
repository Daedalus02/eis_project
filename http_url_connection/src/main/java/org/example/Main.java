package org.example;
import org.json.JSONException;

import javax.swing.text.BadLocationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Main {
    public static void main(String Args[]) {



        try {
            String apiString = "";
            httpGetter getter = new httpGetter(new URL("https://content.guardianapis.com/search?api-key=c9d442dd-66ec-43a8-aa3d-26047fa8780e"));
            apiString = getter.getHttpString();
            jsonParser jsonparser = new jsonParser(apiString);
            article[] articles = jsonparser.getArticles();
            htmlDownloader downloader = new htmlDownloader();
            String[] fileNames = new String[articles.length];
            int counter = 0;
            String pageContent = "";
            String pageText = "";
            Tokenizer tokenizer = new Tokenizer(pageContent,true);
            htmlParser htmlparser = new htmlParser();
            for (article  article : articles) {
                fileNames[counter] = downloader.download(article.getWebUrl().toString());
                Scanner reader = new Scanner(new File(fileNames[counter]));
                while(reader.hasNextLine()){
                    pageContent += reader.nextLine();
                }
                pageText = htmlparser.parse(pageContent);
                tokenizer.switchDocument(pageText);

                System.out.println("The more 50 more frequent words in article number "+(counter+1)+" are: ");
                Set<Map.Entry<Integer, List<String>>> set =  tokenizer.getOrderedTokens(50);

                //printing 50 more frequent tokens
                int wordCounter = 0;
                Iterator<Map.Entry<Integer, List<String>>> iter = set.iterator();
                Map.Entry<Integer, List<String>> pair = iter.next();
                while(iter.hasNext()) {
                    int index = pair.getValue().size();
                    while(index > 0){
                        wordCounter++;
                        index--;
                        System.out.println("          "+wordCounter+": "+pair.getValue().get(index)+" "+pair.getKey());
                    }
                    pair = iter.next();
                }

                counter++;
                pageContent = "";
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
