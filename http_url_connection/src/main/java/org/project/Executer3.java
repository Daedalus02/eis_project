package org.project;

import com.opencsv.exceptions.CsvValidationException;
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


public class Executer3 implements Runnable {
    private final String filePath = "res\\pages\\";
    private final String fileExtension = ".xml";
    private final  String csvPath = "res\\csv\\";
    private final String csvExtension = ".csv";
    private String fileName = "";
    private String csvName = "";
    private BlockingQueue<String> readingQueue;
    private BlockingQueue<String> writingQueue;

    public Executer3(BlockingQueue<String> reading, BlockingQueue<String> writing) {
        writingQueue = writing;
        readingQueue = reading;
    }

    @Override
    public void run() {
        try {
            List<Article> articles = new ArrayList<Article>();
            Tokenizer tokenizer = new Tokenizer("", true);
            Deserializer deserializer = new Deserializer();
            String pageText = "";

            //READING PHASE
            //checking to see if there's an element in the blocking queue
            System.out.println("Executer waiting");
            while (true) {
                Thread.sleep(100);
                if (readingQueue.size() != 0) {
                    break;
                }
            }
            //reading elements from reading queue
            String element1 = "";
            String element2 = "";
            if((element1 = readingQueue.poll()) == null){
                throw new IllegalStateException();
            }
            if((element2 = readingQueue.poll()) == null){
                throw new IllegalStateException();
            }
            csvName = csvPath + element1 + csvExtension;
            fileName = filePath + element2 + fileExtension;
            System.out.println("Executer stooped waiting");


            //CSV READING PHASE
            csvParser csvparser = new csvParser(csvName);
            articles = csvparser.getArticles();

            //SERIALIZING
            Serializer serializer = new Serializer();
            serializer.serialize(articles, fileName);

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
                    writingQueue.put("WORD: " + pair.getValue().get(index) + "\nFREQUENCY: " + pair.getKey());
                    wordCounter++;
                }
                pair = iter.next();
            }

        } catch (IOException | CsvValidationException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

