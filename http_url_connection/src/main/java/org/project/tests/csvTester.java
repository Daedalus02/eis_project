package org.project.tests;

import com.opencsv.exceptions.CsvValidationException;
import org.project.Article;
import org.project.csvParser;

import java.io.IOException;
import java.util.List;

public class csvTester {

    public static void main(String Args[]){
        try {
            csvParser parser = new csvParser("res\\csv\\nytimes_articles_v2.csv");
            List<Article> articles = parser.getArticles();
            System.out.println(articles.size());
        }catch(IOException e){
            e.printStackTrace();
        }catch (CsvValidationException e){
            e.printStackTrace();
        }
    }
}
