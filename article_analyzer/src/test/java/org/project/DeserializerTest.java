package org.project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeserializerTest {
    private Deserializer deserializer;
    private Serializer serializer;

    private final String fileName = "res" + File.separator + "test_res" + File.separator + "pages" + File.separator + "TestDeserializer.xml";
    @BeforeAll
    @DisplayName("This creates an instance of both a serializer and deserializer.")
    void InstanceCreator() throws IOException {
        deserializer = new Deserializer();
        serializer = new Serializer();
    }

    @Test
    @DisplayName("Testing if the Deserializer read CSVArticle from file correctly.")
    void testDeserializerCSVArticle() throws IOException {
        CSVArticle[] articles = new CSVArticle[3];
        articles[0] = new CSVArticle("head1","body1","identifier1",
                new URL("https://it.wikipedia.org"), "date1","sourceSet1","source1");
        articles[1] = new CSVArticle("head2","body2","identifier2",
                new URL("https://stackoverflow.com"), "date2","sourceSet2","source2");
        articles[2] = new CSVArticle("head3","body3","identifier3",
                new URL("https://www.ieee.org"), "date3","sourceSet3","source3");
        serializer.serialize(articles,fileName);
        Article[] articles1 = (deserializer.deserialize(fileName));
        for(int i = 0; i < 3; i++ ) {
            assertThat(articles[i], samePropertyValuesAs((CSVArticle) articles1[i]));
        }
        articles1[0].setBody("different");
        articles1[1].setBody("different");
        articles1[2].setBody("different");
        for(int i = 0; i < 3; i++){
            assertThat(articles[i], not(samePropertyValuesAs((CSVArticle) articles1[i])));
        }
    }

    @Test
    @DisplayName("Testing if Deserializer read APIArticle from the file correctly.")
    void testSerializeAPIArticle() throws IOException {
        APIArticle[] articles = new APIArticle[3];
        articles[0] = new APIArticle("head1","body1","Id1", "type1", "sectionId1",
                "sectionName1","webPublicationDate1","webTitle1",new URL("https://www.ieee.org"),
                new URL("https://developer.ieee.org"),true,"pillarId1","pillarName1",1);
        articles[1] = new APIArticle("head2","body2","Id2", "type2", "sectionId2",
                "sectionName2","webPublicationDate2","webTitle2",new URL("https://stackoverflow.com"),
                new URL("https://api.stackexchange.com"),true,"pillarId2","pillarName2",1);
        articles[2] = new APIArticle("head3","body3","Id3", "type3", "sectionId3",
                "sectionName3","webPublicationDate3","webTitle3",new URL("https://it.wikipedia.org"),
                new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId3","pillarName3",1);
        serializer.serialize(articles,fileName);
        Article[] articles1 = (Article[]) (deserializer.deserialize(fileName));
        for(int i = 0; i < 3; i++ ) {
            assertThat(articles[i], samePropertyValuesAs((APIArticle) articles1[i]));
        }
        articles1[0].setBody("different");
        articles1[1].setBody("different");
        articles1[2].setBody("different");
        for(int i = 0; i < 3; i++){
            assertThat(articles[i], not(samePropertyValuesAs((APIArticle) articles1[i])));
        }
    }
    @Test
    @DisplayName("Testing the exception if the filename is not valid.")
    void IOExcptionThrown(){
        String invalidFileName = "invalid" + File.separator + " invalid.json";
        assertThrows(
                IOException.class,
                () -> deserializer.deserialize(invalidFileName),
                "Expected serializer to throw IOException but didn't."
        );
    }
}