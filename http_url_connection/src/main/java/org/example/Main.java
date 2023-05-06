package org.example;

import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


/**
 *
 */
public class Main {
    public static void main(String Args[]) {
        Scanner console = new Scanner(System.in);
        System.out.println("enter the topic you want to investigate: ");
        String query = console.nextLine();

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

        System.out.println("Enter the max number of articles to elaborate: ");
        int maxArticle = console.nextInt();
        int pageCount = 1;
        int articleCount = 0;

        try {
            Tokenizer tokenizer = new Tokenizer("", true);

            String pageText = " ";
            String url = "";
            httpGetter getter;
            String apiString ="";
            jsonParser jsonparser;
            List<Article> articles = new ArrayList<Article>();
            htmlDownloader downloader;
            String fileName = "res\\pages\\test.xml";

            while(articleCount < maxArticle) {
                url = (new urlSetter("https://content.guardianapis.com", "c9d442dd-66ec-43a8-aa3d-26047fa8780e", pageCount, query, new String[]{}, new String[]{})).getUrl();
                System.out.println("from " + url + " :");
                getter = new httpGetter(new URL(url));
                apiString = getter.getHttpString();

                jsonparser = new jsonParser(apiString);
                articles .addAll(Arrays.stream(jsonparser.getArticles()).toList());

                if(jsonparser.getPages() < maxArticle){
                    maxArticle = jsonparser.getPages();
                    System.out.println("Limited to "+maxArticle+" pages...");
                }

                downloader = new htmlDownloader();

                for (Article article : articles.subList(articleCount,articles.size())) {
                    if(articleCount == maxArticle){
                        break;
                    }
                    System.out.println("Anayzing site number: "+(articleCount+1)+" with title: "+article.getWebTitle());
                    downloader.download(article);
                    pageText = article.getHead() + article.getBody();
                    tokenizer.addDocument(pageText);
                    articleCount++;
                }

                pageCount++;
            }
            Serializer serializer = new Serializer();
            articles = new ArrayList<Article>(articles.subList(0,maxArticle));
            serializer.serialize(articles,fileName);
            //printing 50 more frequent tokens
            System.out.println("The 100 more frequent words in the analyzed articles are: ");
            Set<Map.Entry<Integer, List<String>>> set = tokenizer.getOrderedTokens(100);

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
