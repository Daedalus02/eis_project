package org.project;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;


public class Executer2 implements Runnable {
    private final String filePath = "res\\pages\\";
    private final String fileExtension = ".xml";
    private String fileName = "";
    private BlockingQueue<String> readingQueue;
    private BlockingQueue<String> writingQueue;

    public Executer2(BlockingQueue<String> reading, BlockingQueue<String> writing) {
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
            String element = "";
            if((element = readingQueue.poll()) == null){
                throw new IllegalStateException();
            }
            fileName = filePath + element + fileExtension;
            System.out.println("Executer stooped waiting");


            //DESERIALIZING
            articles = deserializer.deserialize(fileName);
            for (Article article : articles) {
                pageText = article.getHead() + article.getBody();
                tokenizer.addDocument(pageText);
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

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

