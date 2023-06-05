package org.project;

import org.json.JSONException;
import org.junit.jupiter.api.*;

import javax.swing.text.BadLocationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Tag("API")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class APISourceTest {

    private APISource source;
    @BeforeAll
    void instanceCreator() throws JSONException, IOException, ClassNotFoundException, BadLocationException {
        String apiKey;
        FileInputStream fis = new FileInputStream("resources" + File.separator + "private" + File.separator + "private.properties");
        Properties props = new Properties();
        props.load(fis);
        apiKey = props.getProperty("apiKey");
        source = new APISource("https://content.guardianapis.com",apiKey,new String[]{},new String[]{},1000);
    }
    @Test
    @DisplayName("Testing the article returned by the APISource.")
    void testGetArticles() {
        List<APIArticle> articleList = source.getArticles();
        assertEquals(articleList.size(),1000);
    }
}