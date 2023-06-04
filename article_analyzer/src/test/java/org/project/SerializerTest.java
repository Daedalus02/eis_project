package org.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

class SerializerTest {
    private final String fileName = "test_resources" + File.separator + "articles" + File.separator + "TestSerializer.xml";

    @Test
    @DisplayName("Testing if Serializer store CSVArticle in the file correctly.")
    void testSerializeCSVArticle() throws IOException {
        Serializer serializer = new Serializer();
        Deserializer deserializer  = new Deserializer();
        CSVArticle[] expected = new CSVArticle[3];
        expected[0] = new CSVArticle("head1","body1","identifier1",
                new URL("https://it.wikipedia.org"), "date1","sourceSet1","source1");
        expected[1] = new CSVArticle("head2","body2","identifier2",
                new URL("https://stackoverflow.com"), "date2","sourceSet2","source2");
        expected[2] = new CSVArticle("head3","body31","identifier3",
                new URL("https://www.ieee.org"), "date3","sourceSet3","source3");
        serializer.serialize(expected,fileName);
        Article[] articles = (Article[]) (deserializer.deserialize(fileName));
        for(int i = 0; i < 3; i++ ) {
            assertEquals(expected[i], (CSVArticle) articles[i]);
        }
    }

    @Test
    @DisplayName("Testing if Serializer store APIArticle in the file correctly.")
    void testSerializeAPIArticle() throws IOException {
        Serializer serializer = new Serializer();
        Deserializer deserializer  = new Deserializer();
        APIArticle[] expected = new APIArticle[3];
        expected[0] = new APIArticle("head1","body1","Id1", "type1", "sectionId1",
                "sectionName1","webPublicationDate1","webTitle1",new URL("https://www.ieee.org"),
                new URL("https://developer.ieee.org"),true,"pillarId1","pillarName1",1);
        expected[1] = new APIArticle("head2","body2","Id2", "type2", "sectionId2",
                "sectionName2","webPublicationDate2","webTitle2",new URL("https://stackoverflow.com"),
                new URL("https://api.stackexchange.com"),true,"pillarId2","pillarName2",1);
        expected[2] = new APIArticle("head3","body3","Id3", "type3", "sectionId3",
                "sectionName3","webPublicationDate3","webTitle3",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId3","pillarName3",1);
        serializer.serialize(expected,fileName);
        Article[] articles = (Article[]) (deserializer.deserialize(fileName));
        for(int i = 0; i < 3; i++ ) {
            assertEquals(expected[i], (APIArticle) articles[i]);
        }
    }

    @Test
    @DisplayName("Testing the exception if the filename is not valid.")
    void IOExcptionThrown() throws IOException {
        Serializer serializer = new Serializer();
        String invalidFileName = "invalid" + File.separator + " invalid.json";
        assertThrows(
                IOException.class,
                () -> serializer.serialize(new Article[]{},invalidFileName),
                "Expected serializer to throw IOException but didn't."
        );
    }
    }
