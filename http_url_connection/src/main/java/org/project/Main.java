package org.project;

import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Set;

public class Main {

    public static void main(String Args[]) {
        /*
        * structure of input from terminal
        *
        * java -cp /t
        *
        * */
        if(Args.length != 0){

        }

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
            articleSource source;


            System.out.println("Do you want to load data from csv file? (y/n)");
            csvAnswer = console.nextLine().toLowerCase();
            while(!(csvAnswer.equals("y") || csvAnswer.equals("n"))){
                System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                csvAnswer = console.nextLine().toLowerCase();
            }

            if(csvAnswer.equals("y")) {
                //CSV READING PHASE
                System.out.println("Enter the relative path to csv file: ");
                csvName = console.nextLine().toLowerCase();
                //elaborating articles from csv source
                source = new csvSource(csvName);
                articles = source.getArticles();
            }else {

                System.out.println("Do you want to load data from file?(y/n)");
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
                    String[] queries = new String[]{};
                    int queriesSize = 0;
                    while (!(queryAnswer.equals("y") || queryAnswer.equals("n"))) {
                        System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                        queryAnswer = console.nextLine().toLowerCase();
                    }
                    if (queryAnswer.equals("y")) {
                        System.out.println("Enter the number of queries to consider: ");
                        queriesSize = Integer.parseInt(console.nextLine()); //throw exception if it's not a number
                        queries = new String[queriesSize];
                        for (int i = 0; i< queriesSize; i++) {
                            System.out.println("Enter the topic you want to investigate: ");
                            queries[i] = console.nextLine();
                        }
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

                    //elaborating Articles from The Guardian api
                    source = new apiSource(apiKey, tags, queries,maxArticle);
                    articles = source.getArticles();

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
                Map.Entry<Integer, List<String>> pair;

                while (iter.hasNext()) {
                    pair = iter.next();
                    int index = pair.getValue().size();
                    while (index > 0) {
                        index--;
                        System.out.println("          " + (wordCounter + 1) + ": " + pair.getValue().get(index) + " " + pair.getKey());
                        wordCounter++;
                    }
                }
            }
        } catch (IOException | CsvValidationException | JSONException |BadLocationException e) {
            e.printStackTrace();
        }
    }
}
