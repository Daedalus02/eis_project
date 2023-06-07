package org.project;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/*
* Note: the test done for CSVSource are identical to the ones done with CSVParser
* because there are very few difference between the behavior of this two, so here
* we're only testing the functioning of CSVSource basing on the same criteria of
* CSVParser.
* */
@Tag("CSV")
class CSVSourceTest {
    // This variable contains the relative path of the testing CSV formatted file.
    private final String FILE_PATH = "test_resources" + File.separator + "CSV_sources" + File.separator + "CSVTest.csv";

    /* This method checks the working of a CSV source when given the path of a correctly formatted CSV file. */
    @Test
    @DisplayName("Testing if the CSV parser correctly extract the CSV articles from a CSV formatted file.")
    void testGetArticles() throws IOException, CsvValidationException {
        // Creating the Article fields records.
        String[] format = new String[]{"identifier","url","title","body","date","source_set","source"};
        String[] record1 = new String[]{"identifier1","https://it.wikipedia.org","title1","body1","date1","source_set1","source1"};
        String[] record2 = new String[]{"identifier2","https://stackoverflow.com","title2","body2","date2","source_set2","source2"};
        String[] record3 = new String[]{"identifier3","https://ieee.org","title3","body3","date3","source_set3","source3"};

        // Formatting invalid articles fields, and structural information in CSV format.
        String CSV1 = Stream.of(format).collect(Collectors.joining(","));
        String CSV2 = Stream.of(record1).collect(Collectors.joining(","));
        String CSV3 = Stream.of(record2).collect(Collectors.joining(","));
        String CSV4 = Stream.of(record3).collect(Collectors.joining(","));

        // Writing the article fields in CSV format to file.
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(CSV1);
        writer.newLine();
        writer.write(CSV2);
        writer.newLine();
        writer.write(CSV3);
        writer.newLine();
        writer.write(CSV4);
        writer.newLine();
        writer.close();

        // Producing the expected result.
        List<CSVArticle> expected = new ArrayList<CSVArticle>();
        expected.add(new CSVArticle("title1","body1","identifier1",new URL("https://it.wikipedia.org"),"date1","source_set1","source1"));
        expected.add(new CSVArticle("title2","body2","identifier2",new URL("https://stackoverflow.com"),"date2","source_set2","source2"));
        expected.add(new CSVArticle("title3","body3","identifier3",new URL("https://ieee.org"),"date3","source_set3","source3"));

        // Initializing a CSV source, also implicitly parsing Articles from test file.
        CSVSource source = new CSVSource(FILE_PATH);

        // Checking if result is equals to expected article list
        assertEquals(expected, source.getArticles());
    }

    /* This test add a new more field to the CSV formatted article and checks
     * if that provoke an exception. */
    @DisplayName("Testing if an invalid formatted CSV create an exception.")
    @Test
    void testInvalidPropertiesFormatException() throws  IOException {
        // Creating the Article fields records.
        String[] format = new String[]{"identifier","url","title","body","date","source_set","source"};
        // Note: here we have an additional invalid field.
        String[] record1 = new String[]{"identifier","https://it.wikipedia.org","title","body","date","source_set","source","error"};

        // Formatting invalid article fields, and structural information in CSV format.
        String CSV1 = Stream.of(format).collect(Collectors.joining(","));
        String CSV2 = Stream.of(record1).collect(Collectors.joining(","));

        // Writing the article fields in CSV format to file.
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
        writer.write(CSV1);
        writer.newLine();
        writer.write(CSV2);
        writer.newLine();
        writer.close();

        // Checking if the implicit call to CSVParser throw an InvalidPropertiesFormatException.
        assertThrows(
                InvalidPropertiesFormatException.class,
                () -> new CSVSource(FILE_PATH),
                "Expected serializer to throw IOException but didn't."
        );
    }
}