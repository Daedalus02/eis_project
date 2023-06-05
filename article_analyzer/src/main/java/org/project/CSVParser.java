package org.project;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
 *
 * This class elaborates the content of a CSV file extracting a List of Articles.
 * NOTE: This class is just a wrapper that exposes the functionalities of the
 * actual parser CSVReader from the package com.opencsv{@link com.opencsv}.
 *
 * It supposes that the CSV organization is the following:
 * CSV Structure:
 *  * -Identifier
 *  * -URL
 *  * -Title
 *  * -Body
 *  * -Date
 *  * -Source Set
 *  * -Source
 */
public final class CSVParser {
    /** This is used to keep track of the Article List to eventually return it when asked. */
    private List<CSVArticle> articles;
    /** This is the actual CSV parser capable of reading the fields contained in a CSV formatted file. */
    private CSVReader reader;

    /**
     * This constructor takes the path of the files formatted in CSV. It also initializes the CSVReader {@link CSVParser#reader}
     * and the List of Articles {@link CSVParser#articles} used to store the articles that will be read from the CSV file.
     *
     * @param filePath which is the complete address of the CSV file .
     * @throws IOException if the filePath is not found or if the structure of the CSV file is not correct.
     * @throws CsvValidationException if the filePath is a valid one but the content is not formatted as a CSV.
     */
    public CSVParser(String filePath) throws IOException, CsvValidationException {
        // Initializing the CSVReader.
       reader = new CSVReader(new FileReader(filePath));
        // Initializing the Article List.
        articles = new ArrayList<CSVArticle>();
        // Reading the articles from the file.
        readArticles();
    }

    /**
     * This method is internally used to read the articles from the CSV file.
     *
     * @throws CsvValidationException if the filePath is a valid one but the content is not formatted as a CSV.
     * @throws IOException if the structure of the file is not the expected one.
     */

    private void readArticles() throws CsvValidationException, IOException {
        CSVArticle article;
        String[] Record;        // This variable is used to store the fields in the CSV file records.
        // This skips reading the first line because it only contains structural information.
        reader.readNext();
        // Setting all the articles with the read parameters (the csv must have the structure shown in this class comment).
        while((Record = reader.readNext()) !=null){
            if(Record.length != 7){
                throw new InvalidPropertiesFormatException("The CSV is in the incorrect format.");
            }
            articles.add(new CSVArticle(Record[2],Record[3],Record[0],new URL(Record[1]),Record[4],Record[5],Record[6]));
        }
    }

    /**
     * This method is used to return the elaborated List of Articles {@link CSVParser#articles}
     *
     * @return Article List.
     */
    public List<CSVArticle> getArticles(){
        return articles;
    }
}
