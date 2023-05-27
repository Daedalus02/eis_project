package org.project;

import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Set;
import java.util.Properties;

public class Main {

    private static final String filePath = "res\\pages\\";
    private static final String fileExtension = ".xml";
    private static final String csvPath = "res\\csv\\";
    private static final String csvExstension = ".csv";
    private static final String apiFile = "res\\private\\private.properties";
    private static final String errorString = "structure of input from terminal \njava -cp target/article_analyzer-1.0-jar-with-dependencies.jar org.project.Main -source ... \n-if source is file then  the syntax is:\n... -file filename\n-if source is The Guardian api then the syntax is:\n... -api -queries query1 query2 queryN -tags tag1 tag2 tagN -max max_number of articles -apiKey your_api_Key -store filename\n-if the source is csv then the syntax is:\n... -csv filename -store filename\nwhere filepath for csv is res/csv/ and for old research file is res/pages/ notice apiKey, store, tags, queries are optional.";
    public static void main(String Args[]) {


        Scanner console = new Scanner(System.in);

        try {
            List<Article> articles = new ArrayList<Article>();
            String fileName = filePath + "test" + fileExtension;
            Tokenizer tokenizer = new Tokenizer("", true);
            Deserializer deserializer = new Deserializer();
            String pageText = "";
            String downloadAnswer = "n";
            String csvAnswer = "n";
            String dataAnswer = "n";
            String csvName = "";
            String apiKey = "";
            int maxArticle = 0;
            List<String> queries = new ArrayList<String>();
            List<String> tags = new ArrayList<String>();
            ArticleSource source;

            /*
             * structure of input from terminal
             *
             * java -cp target/article_analyzer-1.0-jar-with-dependencies.jar org.project.Main -source ...
             * -if source is file then  the syntax is:
             *      ... -file filename
             * -if source is The Guardian api then the syntax is:
             *      ... -api -queries query1 query2 queryN -tags tag1 tag2 tagN -max max_number of articles -apiKey your_api_Key -store filename
             * -if the source is csv then the syntax is:
             *      ... -csv filename -store filename
             *
             * where filepath for csv is res/csv/ and for old research file is res/pages
             * (notice apiKey, store, tags, queries are optional.)
             * */

            //COMMAND LINE INPUT
            if(Args.length != 0){
                if(Args[0].equals("-csv")) {
                    boolean validCsv = false;
                    for (int i = 0; i < Args.length; i++) {
                        source = new CSVSource(Args[1]);
                        articles = source.getArticles();
                        csvAnswer = "y";
                        if (Args[i].contains("-name")) {
                            fileName = csvPath + Args[i + 1] + csvExstension;
                            validCsv = true;
                        }
                        if (Args[i].contains("-store")) {
                            fileName = filePath + Args[i + 1] + fileExtension;
                        }
                    }
                    if(!validCsv){
                        throw new IllegalArgumentException(errorString);
                    }
                }else if(Args[0].equals("-api")){
                    boolean validApi = false;
                    boolean validMax = false;
                    for(int i = 0; i < Args.length; i++) {
                        if(Args[i].contains("-tags")){
                            while(i < (Args.length-1) && Args[i+1].toCharArray()[0] != '-'){
                                i++;
                                tags.add(Args[i]);
                            }
                        }
                        if(Args[i].contains("-apiKey")){
                            apiKey = Args[i+1];
                            validApi = true;
                        }
                        if(Args[i].contains("-max")){
                            maxArticle = Integer.parseInt(Args[i+1]);
                            validMax = true;
                        }
                        if(Args[i].contains("-queries")){
                            while(i < (Args.length-1) && Args[i+1].toCharArray()[0] != '-') {
                                i++;
                                queries.add(Args[i]);
                            }
                        }
                        if(Args[i].contains("-show")){
                            downloadAnswer = "y";
                        }
                        if(Args[i].contains("-store")){
                            fileName = filePath + Args[i+1] + fileExtension;
                        }
                    }
                    if(!validMax){
                        throw new IllegalArgumentException(errorString);
                    }
                    if(!validApi){
                        FileInputStream fis = new FileInputStream(apiFile);
                        Properties props = new Properties();
                        props.load(fis);
                        apiKey = props.getProperty("apiKey");
                        if(apiKey.equals("your_api_key")){
                            throw new IllegalArgumentException("No default api key detected from the properties file in \"article_analyzer/res/private/private.properties\"");
                        }
                    }
                    source = new APISource(apiKey, tags.toArray(new String[]{}), queries.toArray(new String[]{}), maxArticle);
                    articles = source.getArticles();

                }else if(Args[0].equals("-file")){

                    if(Args.length > 1) {
                        fileName = filePath + Args[1] + fileExtension;
                    }else{
                        throw new IllegalArgumentException(errorString);
                    }
                    dataAnswer = "y";
                }
            }else {
                //USER INPUT (SETTINGS QUESTIONS PHASE)

                //asking if the user want to use older data
                System.out.println("Do you want to load data from file?(y/n)");
                dataAnswer = console.nextLine().toLowerCase();
                while (!(dataAnswer.equals("y") || dataAnswer.equals("n"))) {
                    System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                    dataAnswer = console.nextLine().toLowerCase();
                }
                if (dataAnswer.equals("y")) {
                    //selecting file name
                    System.out.println("Enter the name of the file you saved a previous search");
                    fileName = filePath + console.nextLine() + fileExtension;
                } else {
                    //asking the user to set the name of the file for storing the research
                    System.out.println("Enter the name of the file you want to save your research(enter \"test\" if not important): ");
                    fileName = filePath + console.nextLine() + fileExtension;

                    //setting csv file reading
                    System.out.println("Do you want to load data from csv file? (y/n)");
                    csvAnswer = console.nextLine().toLowerCase();
                    while (!(csvAnswer.equals("y") || csvAnswer.equals("n"))) {
                        System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                        csvAnswer = console.next().toLowerCase();
                    }

                    if (csvAnswer.equals("y")) {
                        //CSV READING PHASE
                        System.out.println("Enter the name of csv file (without extension): ");
                        csvName = csvPath + console.nextLine().toLowerCase() + csvExstension;
                        //elaborating articles from csv source
                        source = new CSVSource(csvName);
                        articles = source.getArticles();
                    } else {
                            //setting api_key
                            System.out.println("Do you want to use the default apiKey?");
                            String defaultAnswer = console.nextLine().toLowerCase();
                            while (!(defaultAnswer.equals("y") || defaultAnswer.equals("n"))) {
                                System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                                defaultAnswer = console.nextLine().toLowerCase();
                            }
                            if (defaultAnswer.equals("n")) {
                                System.out.println("Enter your api key to access the \"the Guardian\" articles: ");
                                apiKey = console.nextLine();
                            } else {
                                //reading api key from properties file
                                FileInputStream fis = new FileInputStream(apiFile);
                                Properties props = new Properties();
                                props.load(fis);
                                apiKey = props.getProperty("apiKey");
                            }

                            //setting query
                            System.out.println("Enter the number of queries to consider (0 if none): ");
                            int queriesSize = console.nextInt();
                            for (int i = 0; i < queriesSize; i++) {
                                System.out.println("Enter the topic you want to investigate: ");
                                queries.add(console.next());
                            }

                            //setting tags
                            System.out.println("Enter the number of tag you want to consider (0 if none): ");
                            int tagNumber = console.nextInt();
                            for (int i = 0; i < tagNumber; i++) {
                                System.out.println("Enter the tag: ");
                                tags.add(console.next());
                            }

                            //setting max articles number
                            System.out.println("Enter the max number of articles to elaborate: ");
                            maxArticle = console.nextInt();

                            //elaborating Articles from The Guardian api
                            source = new APISource(apiKey, tags.toArray(new String[]{}), queries.toArray(new String[]{}), maxArticle);
                            articles = source.getArticles();

                            //asking the user if it's required to print the 50 most frequent words
                            System.out.println("Do you want to read the 50 most frequent words in the downloaded articles? (y/n)");
                            downloadAnswer = console.next().toLowerCase();
                            while (!(downloadAnswer.equals("y") || downloadAnswer.equals("n"))) {
                                System.out.println("Sorry i didn't understood your answer please enter a valid one(y/n): ");
                                downloadAnswer = console.nextLine().toLowerCase();
                            }
                        }
                    }
                }





            //SERIALIZING
            if (dataAnswer.equals("n")){
                //checking if articles is empty
                if(articles.size() == 0){
                    System.out.println("No articles was found!");
                    System.exit(0);
                }
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
