package org.project;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/*
 * csv Structure:
 *  -Identifier
 *  -URL
 *  -Title
 *  -Body
 *  -Date
 *  -Source Set
 *  -Source
 *
 * */
public class csvParser {
    List<Article> articles;
    public csvParser(String fileName) throws IOException, CsvValidationException, InvalidPropertiesFormatException {
        CSVReader reader = new CSVReader(new FileReader(fileName));
        articles = new ArrayList<Article>();

        Article article;
        String[] Record;

        //skip reading first line (only has structural information)
        reader.readNext();
        while((Record = reader.readNext()) !=null){
            if(Record.length != 7){
                throw new InvalidPropertiesFormatException("The csv is in incorrect format to fit an article!");
            }
            article = new Article();
            article.setId(Record[0]);
            article.setWebUrl(Record[1]);
            article.setWebTitle(Record[2]);
            article.setBody(Record[3]);
            article.setWebPublicationDate(Record[4]);
            article.setMediaGroup(Record[5]);
            article.setSectionName(Record[6]);
            articles.add(article);
        }
    }
    public List<Article> getArticles(){
        return articles;
    }
}
