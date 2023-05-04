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
        Scanner console = new Scanner(System.in);
        System.out.println("enter the topic you want to investigate: ");
        String query = console.next();

        int tagNumber = 0;
        System.out.println("Enter the number of tag you want to search: ");
        tagNumber = console.nextInt();
        String[] tags = new String[tagNumber];
        for(int i = 0; i < tagNumber; i++ ){
            tags[i] = console.next();
            System.out.println();
        }

        int contentNunber = 0;
        System.out.println("Enter the number of contents you want to search: ");
        contentNunber = console.nextInt();
        String[] contents = new String[contentNunber];
        for(int i = 0; i < contentNunber; i++){
            contents[i] = console.next();
            System.out.println();
        }

        System.out.println("Enter the max number of page to elaborate: ");
        int maxPage = console.nextInt();
        int pageCount = 0;

        try {
            Tokenizer tokenizer = new Tokenizer("", true);
            htmlParser htmlparser = new htmlParser();

            String pageContent = " "; //must have length different from 0
            String pageText = " ";
            String url = "";
            httpGetter getter;
            String apiString ="";
            jsonParser jsonparser;
            article[] articles;
            htmlDownloader downloader;
            String[] fileNames;
            Scanner reader;
            int availablePage = 0;
            while(pageCount < maxPage) {
                url = (new urlSetter("https://content.guardianapis.com", "c9d442dd-66ec-43a8-aa3d-26047fa8780e", pageCount+1, query, new String[]{}, new String[]{})).getUrl();

                getter = new httpGetter(new URL(url));
                apiString = getter.getHttpString();

                jsonparser = new jsonParser(apiString);
                articles = jsonparser.getArticles();
                if(jsonparser.getPages() < maxPage){
                    maxPage = jsonparser.getPages();
                    System.out.println("Limited to "+maxPage+" pages...");
                }

                downloader = new htmlDownloader();
                fileNames = new String[articles.length];



                for (article article : articles) {
                    if(pageCount == maxPage){
                        break;
                    }
                    System.out.println("Anayzing site number: "+(pageCount+1)+" with title: "+article.getWebTitle());
                    fileNames[pageCount%10] = downloader.download(article.getWebUrl().toString());
                    reader = new Scanner(new File(fileNames[pageCount%10]));
                    while (reader.hasNextLine()) {
                        pageContent += reader.nextLine();
                    }
                    pageText = htmlparser.parse(pageContent);
                    tokenizer.addDocument(pageText);
                    pageCount++;
                    pageContent = "";
                }


            }
            //printing 50 more frequent tokens
            System.out.println("The 50 more frequent words in the analyzed articles are: ");
            Set<Map.Entry<Integer, List<String>>> set = tokenizer.getOrderedTokens(50);

            int wordCounter = 0;
            Iterator<Map.Entry<Integer, List<String>>> iter = set.iterator();
            Map.Entry<Integer, List<String>> pair = iter.next();
            while (iter.hasNext()) {
                int index = pair.getValue().size();
                while (index > 0) {
                    index--;
                    System.out.println("          " + (wordCounter + 1) + ": " + pair.getValue().get(index) + " " + pair.getKey());
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
