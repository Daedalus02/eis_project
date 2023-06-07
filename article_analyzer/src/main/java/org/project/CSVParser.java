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
    /** This is the actual CSV parser capable of reading the fields contained in a CSV formatted file. */
    private CSVReader reader;
    /** This is used to keep track of the parse List of Articles. */
    private List<CSVArticle> articles;

    /**
     * This constructor is used to initialize the list of articles.
     */
    public CSVParser(){
        // This variable is used to store the articles read from the CSV.
        articles = new ArrayList<CSVArticle>();
    }

    /**
     * This method is used to parse the articles from the CSV formatted file at the specified address.
     *
     * @throws CsvValidationException if the filePath is a valid one but the content is not formatted as a CSV.
     * @throws IOException if the structure of the file is not the expected one.
     */
    public void parse(String filePath) throws IOException, CsvValidationException {
        // Initializing the CSVReader.
        reader = new CSVReader(new FileReader(filePath));
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
     * This method is used to return the articles read from the CSV file.
     *
     * @return Article List.
     */
    public List<CSVArticle> getArticles() throws CsvValidationException, IOException {

        return articles;
    }


}
