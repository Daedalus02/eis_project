package org.project;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;


/**
 * This class represent an Article source where the Articles are read from a csv.
 */
public class CSVSource implements ArticleSource {
    private CSVParser csvparser;
    private List<Article> articles;

    /**
     * This constructor initialize the csvParser with the filepath passed as argument,
     * and the articles with the ones read in the csv file.
     * @param filePath which is a complete relative path of the file from the root directory of the project.
     * @throws CsvValidationException if the file content is not formatted as a csv.
     * @throws IOException if filepath is not valid.
     */
    public CSVSource(String filePath) throws CsvValidationException, IOException {
        //parsing the content of the csv file
        csvparser = new CSVParser(filePath);
        //setting the Articles with the ones contained in the csv file
        articles = csvparser.getArticles();
    }
    @Override
    public List<Article> getArticles() {
        return articles;
    }
}
