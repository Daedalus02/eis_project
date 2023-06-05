package org.project;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;

/**
 * This class represents a source of articles which are read from a CSV file.
 */
public final class CSVSource implements ArticleSource {
    /** This parser is used to read the different values contained in the CSV file. */
    private CSVParser parser;
    /** This is used to keep track of the articles List to eventually return it when asked. */
    private List<CSVArticle> articles;

    /**
     * This constructor initialize the CSVParser with the filepath passed as an argument
     * and the articles with the fields read in the CSV file.
     *
     * @param filePath which is a complete relative path of the file from the root directory of the project.
     * @throws CsvValidationException if the file content is not formatted as a CSV.
     * @throws IOException if the filepath is not valid.
     */
    public CSVSource(String filePath) throws CsvValidationException, IOException {
        // Parsing the content of the CSV file.
        parser = new CSVParser(filePath);
        // Setting the Articles with the ones contained in the CSV file.
        articles = parser.getArticles();
    }

    /**
     * This method is used to return the elaborated List of Articles {@link CSVSource#articles}
     *
     * @return Article List
     */
    @Override
    public List<CSVArticle> getArticles() {
        return articles;
    }
}
