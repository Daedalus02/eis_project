package org.project;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public class csvSource implements articleSource{
    private csvParser csvparser;
    private List<Article> articles;
    public csvSource(String filePath) throws CsvValidationException, IOException {
        csvparser = new csvParser(filePath);
        articles = csvparser.getArticles();
    }
    @Override
    public List<Article> getArticles() {
        return articles;
    }
}
