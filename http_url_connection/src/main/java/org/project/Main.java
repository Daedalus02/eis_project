package org.project;

import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class Main {

    public static void main(String Args[]) {
        Scanner console = new Scanner(System.in);

        try {
            List<Article> articles = new ArrayList<Article>();
            String fileName = "res\\pages\\test.xml";
            Tokenizer tokenizer = new Tokenizer("", true);
            Deserializer deserializer = new Deserializer();
            String pageText = "";
            String downloadAnswer = "n";
            String csvAnswer = "n";
            String dataAnswer = "n";
            String csvName = "";


            System.out.println("Do you wanna load data from csv file? (y/n)");
            csvAnswer = console.nextLine().toLowerCase();
            while(!(csvAnswer.equals("y") || csvAnswer.equals("n"))){
                System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                csvAnswer = console.nextLine().toLowerCase();
            }

            if(csvAnswer.equals("y")) {
                //CSV READING PHASE
                System.out.println("Enter the relative path to csv file: ");
                csvName = console.nextLine().toLowerCase();
                csvParser csvparser = new csvParser(csvName);
                articles = csvparser.getArticles();
            }else {

                System.out.println("Do you wanna load data from file?(y/n)");
                dataAnswer = console.nextLine().toLowerCase();
                while (!(dataAnswer.equals("y") || dataAnswer.equals("n"))) {
                    System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                    dataAnswer = console.nextLine().toLowerCase();
                }

                if (dataAnswer.equals("n")) {

                    //SETTINGS QUESTIONS PHASE

                    //setting api_key
                    System.out.println("Enter your api key to access the \"the Guardian\" articles: ");
                    String apiKey = console.nextLine();

                    //setting query
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
                    }

                    //setting tags
                    int tagNumber = 0;
                    System.out.println("Enter the number of tag you want to search (0 if none): ");
                    tagNumber = console.nextInt();
                    String[] tags = new String[tagNumber];
                    for (int i = 0; i < tagNumber; i++) {
                        System.out.println("Enter the tag: ");
                        tags[i] = console.next();
                        System.out.println();
                    }

                    //setting max articles number
                    System.out.println("Enter the max number of articles to elaborate: ");
                    int maxArticle = console.nextInt();

                    int pageCount = 1;
                    int pageSize = 100;
                    int articleCount = 0;
                    String url = "";
                    httpGetter getter;
                    String apiString = "";
                    jsonParser jsonparser;
                    htmlParser htmlparser;

                    //SERIALIZING PHASE
                    while (articleCount < maxArticle) {
                        //setting url basing on the fields required for the api request
                        url = (new urlSetter("https://content.guardianapis.com", apiKey, pageCount, pageSize, query, tags)).getUrl();
                        System.out.println("from " + url + " :");

                        //getting response from the api point
                        getter = new httpGetter(new URL(url));
                        apiString = getter.getHttpString();

                        //parsing the response
                        jsonparser = new jsonParser(apiString);
                        articles.addAll(jsonparser.getArticles());

                        //setting the max number of article to analyze, also basing on the number of available ones
                        if (jsonparser.getPages() < maxArticle) {
                            maxArticle = jsonparser.getPages();
                            System.out.println("Limited to " + maxArticle + " pages...");
                        }

                        //initializing parser
                        htmlparser = new htmlParser();

                        //analyzing each of the single new articles
                        for (Article article : articles.subList(articleCount, articles.size())) {
                            if (articleCount == maxArticle) {
                                break;
                            }
                            System.out.println("Anayzing site number: " + (articleCount + 1) + " with title: " + article.getWebTitle());
                            htmlparser.parse(article);
                            articleCount++;
                        }
                        pageCount++;
                    }

                    //TRIM
                    articles = new ArrayList<Article>(articles.subList(0, maxArticle));


                    System.out.println("Do you want to read the 50 most frequent words in the downloaded articles? (y/n)");
                    downloadAnswer = console.next();
                    while (!(downloadAnswer.equals("y") || downloadAnswer.equals("n"))) {
                        System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                        downloadAnswer = console.nextLine().toLowerCase();
                    }
                }
            }

            //SERIALIZING
            if (dataAnswer.equals("n")){
                Serializer serializer = new Serializer();
                serializer.serialize(articles, fileName);
            }

            //DESERIALIZING/READING PHASE
            if (csvAnswer.equals("y") || dataAnswer.equals("y") || downloadAnswer.equals("y")) {

                //DESERIALIZING
                articles = deserializer.deserialize(fileName);
                for (Article article : articles) {
                    pageText = article.getHead() + article.getBody();
                    tokenizer.enterTokens(pageText);
                }

                //PRINTING/ORDERING PHASE
                System.out.println("The " + 50 + " more frequent words in the analyzed articles are: ");

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
            }
        } catch (IOException | CsvValidationException | JSONException |BadLocationException e) {
            e.printStackTrace();
        }
    }
}
