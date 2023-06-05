package org.project;

import java.io.FileNotFoundException;
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
    /** This is the actual CSV parser capable of reading the fields contained in a CSV formatted file. */
    private CSVReader reader;

    /**
     * This constructor takes the path of the files formatted in CSV. It also initializes the CSVReader {@link CSVParser#reader}
     * will be used to read articles from the CSV file.
     *
     * @param filePath which is the complete address of the CSV file .
     * @throws FileNotFoundException if the filePath is not correct.
     */
    public CSVParser(String filePath) throws FileNotFoundException {
        // Initializing the CSVReader.
       reader = new CSVReader(new FileReader(filePath));
    }

    /**
     * This method is internally used to read the articles from the CSV file.
     *
     * @return Article List.
     * @throws CsvValidationException if the filePath is a valid one but the content is not formatted as a CSV.
     * @throws IOException if the structure of the file is not the expected one.
     */

    public List<CSVArticle> getArticles() throws CsvValidationException, IOException {
        List<CSVArticle> articles = new ArrayList<CSVArticle>();        // This variable is used to store the articles read from the CSV
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
        return articles;
    }

    /**
     * This method is used to change the CSV formatted file and setting the {@link CSVParser#reader}.
     *
     * @param filePath the new CSV formatted file path(relative).
     * @throws FileNotFoundException if the file path is not correct.
     */
    public void changeFile(String filePath) throws FileNotFoundException {
        // Reinitializing the CSVReader.
        reader = new CSVReader(new FileReader(filePath));
    }

}
