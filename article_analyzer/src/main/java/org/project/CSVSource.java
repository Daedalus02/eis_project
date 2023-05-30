package org.project;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;

/**
 * This class represent an Article source where the Articles are read from a CSV file.
 */
public class CSVSource implements ArticleSource {
    /** This parser is used to read the different value contained in the CSV file. */
    private CSVParser parser;
    /** This is used to keep track of the article List to eventually returning it when asked. */
    private List<CSVArticle> articles;

    /**
     * This constructor initialize the csvParser with the filepath passed as argument
     * and the articles with the fields read in the CSV file.
     *
     * @param filePath which is a complete relative path of the file from the root directory of the project.
     * @throws CsvValidationException if the file content is not formatted as a CSV.
     * @throws IOException if filepath is not valid.
     */
    public CSVSource(String filePath) throws CsvValidationException, IOException, ClassNotFoundException {
        // Parsing the content of the CSV file.
        parser = new CSVParser(filePath);
        // Setting the Articles with the ones contained in the CSV file.
        articles = parser.getArticles();
    }

    /**
     * This method is used to simply return the elaborated List of Articles {@link CSVSource#articles}
     *
     * @return Article List
     */
    @Override
    public List<CSVArticle> getArticles() {
        return articles;
    }
}
