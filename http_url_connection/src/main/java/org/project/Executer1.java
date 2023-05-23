package org.project;

import org.json.JSONException;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;


public class Executer1 implements Runnable {
    private String filePath = "res\\pages\\";
    private String fileExtension = ".xml";
    private BlockingQueue<String> readingQueue;
    private BlockingQueue<String> writingQueue;

    public Executer1(BlockingQueue<String> reading, BlockingQueue<String> writing) {
        readingQueue = reading;
        writingQueue = writing;
    }

    @Override
    public void run() {
        //DECLARING PHASE
        int pageCount = 1;
        int pageSize = 100;
        int articleCount = 0;
        String url = "";
        httpGetter getter;
        String apiString = "";
        jsonParser jsonparser;
        htmlParser htmlparser;
        List<Article> articles = new ArrayList<Article>();
        String fileName = "res\\pages\\test.xml";
        Tokenizer tokenizer = new Tokenizer("", true);
        Deserializer deserializer = new Deserializer();
        String pageText = "";
        String downloadAnswer = "n";
        String query = "";
        String apiKey = "";
        String[] tags = new String[]{};
        int maxArticle = 0;

        try {

            //READING PHASE
            //checking to see if there's an element in the blocking queue
            boolean smtRead = true;
            System.out.println("Executer waiting");
            while (true) {
                Thread.sleep(100);
                if (readingQueue.size() != 0) {
                    break;
                }
            }
            System.out.println("Executer stooped waiting");

            //reading elements
            String element = "";
            for (int i = 0; i < 4; i++) {
                if ((element = readingQueue.poll()) == null) {
                    smtRead = false;
                    break;
                } else {
                    if (i == 0) {
                        apiKey = element;
                    } else if (i == 1) {
                        query = element;
                    } else if (i == 2) {
                        maxArticle = Integer.parseInt(element);
                    } else {
                        downloadAnswer = element;
                    }
                }
            }

            //this also allow me to ensure all the other fields have been filled
            while (!(downloadAnswer.equals("y") || downloadAnswer.equals("n"))) {
                throw new IllegalArgumentException();
            }

            //ARTICLE RETRIEVING PHASE
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

            //SERIALIZING
            Serializer serializer = new Serializer();
            serializer.serialize(articles, fileName);


            //DESERIALIZING/READING PHASE
            if (downloadAnswer.equals("y")) {

                //DESERIALIZING
                articles = deserializer.deserialize(fileName);
                for (Article article : articles) {
                    pageText = article.getHead() + article.getBody();
                    tokenizer.addDocument(pageText);
                }

                //PRINTING/ORDERING PHASE
                System.out.println("The " + 50 + " more frequent words in the analyzed articles are: ");

                Set<Map.Entry<Integer, java.util.List<String>>> set = tokenizer.getOrderedTokens(50);
                int wordCounter = 0;
                Iterator<Map.Entry<Integer, java.util.List<String>>> iter = set.iterator();
                Map.Entry<Integer, List<String>> pair = iter.next();


                while (iter.hasNext()) {
                    int index = pair.getValue().size();
                    while (index > 0) {
                        index--;
                        System.out.println("          " + (wordCounter + 1) + ": " + pair.getValue().get(index) + " " + pair.getKey());
                        wordCounter++;
                        writingQueue.put("WORD: " + pair.getValue().get(index) + "\nFREQUENCY: " + pair.getKey());
                    }
                    pair = iter.next();
                }
                System.out.println("Executer finished entering words");

            }else{
                System.exit(0);
            }
            } catch(IOException | JSONException | BadLocationException | InterruptedException e)
            {
                e.printStackTrace();
            }
    }
}

