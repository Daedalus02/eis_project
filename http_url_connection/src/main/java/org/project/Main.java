package org.project;

import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Main {

    //print the first "n" ordered tokens
    public static void printTokens(Tokenizer tokenizer, int number){
        System.out.println("The "+number+" more frequent words in the analyzed articles are: ");
        Set<Map.Entry<Integer, List<String>>> set = tokenizer.getOrderedTokens(number);
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
    }

    public static void main(String Args[]) {
        Scanner console = new Scanner(System.in);

        try {
            System.out.println("Do you wanna load data from file?(y/n)");
            String dataAnswer = console.nextLine().toLowerCase();
            while(!(dataAnswer.equals("y") || dataAnswer.equals("n"))){
                System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                dataAnswer = console.nextLine().toLowerCase();
            }
            if(dataAnswer.equals("y")){
                Deserializer deserializer = new Deserializer();
                List<Article> articleList = deserializer.deserialize("res\\pages\\test.xml");
                int articleCount = 0;
                String pageText = "";
                Tokenizer tokenizer = new Tokenizer("",true);
                //analyzing each of the single new articles
                for (Article article : articleList) {
                    System.out.println("Anayzing site number: " + (articleCount + 1) + " with title: " + article.getWebTitle());
                    pageText = article.getHead() + article.getBody();
                    tokenizer.addDocument(pageText);
                    articleCount++;
                }
                printTokens(tokenizer,50);
            }else {

                System.out.println("Do you want to use a query?(y/n)");
                String queryAnswer = console.nextLine().toLowerCase();
                String query = "";
                while (!(queryAnswer.equals("y") || queryAnswer.equals("n"))) {
                    System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                    queryAnswer = console.nextLine().toLowerCase();
                }
                if (queryAnswer.equals("y")) {
                    System.out.println("Enter the topic you want to investigate: ");
                    query = console.nextLine();
                } else {
                }

                int tagNumber = 0;
                System.out.println("Enter the number of tag you want to search (0 if none): ");
                tagNumber = console.nextInt();
                String[] tags = new String[tagNumber];
                for (int i = 0; i < tagNumber; i++) {
                    System.out.println("Enter the tag: ");
                    tags[i] = console.next();
                    System.out.println();
                }

                System.out.println("Enter the max number of articles to elaborate: ");
                int maxArticle = console.nextInt();
                int pageCount = 1;
                int pageSize = 100;
                int articleCount = 0;

                Tokenizer tokenizer = new Tokenizer("", true);

                String pageText = " ";
                String url = "";
                httpGetter getter;
                String apiString = "";
                jsonParser jsonparser;
                List<Article> articles = new ArrayList<Article>();
                htmlParser htmlparser;
                String fileName = "res\\pages\\test.xml";

                while (articleCount < maxArticle) {
                    //setting url basing on the fields required for the api request
                    url = (new urlSetter("https://content.guardianapis.com", "your_api_key", pageCount, pageSize, query, tags)).getUrl();
                    System.out.println("from " + url + " :");

                    //getting response from the api point
                    getter = new httpGetter(new URL(url));
                    apiString = getter.getHttpString();

                    //parsing the response
                    jsonparser = new jsonParser(apiString);
                    articles.addAll(Arrays.stream(jsonparser.getArticles()).toList());

                    //setting the max number of article to analyze, also basing on the number of available ones
                    if (jsonparser.getPages() < maxArticle) {
                        maxArticle = jsonparser.getPages();
                        System.out.println("Limited to " + maxArticle + " pages...");
                    }

                    htmlparser = new htmlParser();

                    //analyzing each of the single new articles
                    for (Article article : articles.subList(articleCount, articles.size())) {
                        if (articleCount == maxArticle) {
                            break;
                        }
                        System.out.println("Anayzing site number: " + (articleCount + 1) + " with title: " + article.getWebTitle());
                        htmlparser.parse(article);
                        pageText = article.getHead() + article.getBody();
                        tokenizer.addDocument(pageText);
                        articleCount++;
                    }

                    pageCount++;
                }

                //serializing in XML file
                Serializer serializer = new Serializer();
                articles = new ArrayList<Article>(articles.subList(0, maxArticle));
                serializer.serialize(articles, fileName);

                //printing 50 more frequent tokens
                printTokens(tokenizer,50);
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
