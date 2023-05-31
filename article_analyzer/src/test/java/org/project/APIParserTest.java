package org.project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.Matchers.not;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class APIParserTest {
    private APIParser parser;
    private final String JSONString = "{" +
            "   \"response\":{" +
            "      \"status\":\"ok\"," +
            "      \"userTier\":\"developer\"," +
            "      \"total\":100," +
            "      \"startIndex\":1," +
            "      \"pageSize\":100," +
            "      \"currentPage\":1," +
            "      \"pages\":1," +
            "      \"orderBy\":\"newest\"," +
            "      \"results\":[" +
            "         {" +
            "            \"id\":\"id1\"," +
            "            \"type\":\"type1\"," +
            "            \"sectionId\":\"sectionId1\"," +
            "            \"sectionName\":\"sectionName1\"," +
            "            \"webPublicationDate\":\"webPublicationDate1\"," +
            "            \"webTitle\":\"webTitle1\"," +
            "            \"webUrl\":\"https://it.wikipedia.org\",\n" +
            "            \"apiUrl\":\"https://www.mediawiki.org/w/api.php\",\n" +
            "            \"fields\":{\n" +
            "               \"headline\":\"head1\",\n" +
            "               \"body\":\"body1\"," +
            "               \"wordcount\":\"1\"" +
            "            },\n" +
            "            \"isHosted\":true,\n" +
            "            \"pillarId\":\"pillarId1\"," +
            "            \"pillarName\":\"pillarName1\"" +
            "         }," +
            "         {" +
            "            \"id\":\"id2\"," +
            "            \"type\":\"type2\"," +
            "            \"sectionId\":\"sectionId2\"," +
            "            \"sectionName\":\"sectionName2\"," +
            "            \"webPublicationDate\":\"webPublicationDate2\"," +
            "            \"webTitle\":\"webTitle2\"," +
            "            \"webUrl\":\"https://www.ieee.org\"," +
            "            \"apiUrl\":\"https://developer.ieee.org\"," +
            "            \"fields\":{" +
            "               \"headline\":\"head2\"," +
            "               \"body\":\"body2\"," +
            "               \"wordcount\":\"1\"" +
            "            },\n" +
            "            \"isHosted\":true," +
            "            \"pillarId\":\"pillarId2\"," +
            "            \"pillarName\":\"pillarName2\"" +
            "         },\n" +
            "         {\n" +
            "            \"id\":\"id3\"," +
            "            \"type\":\"type3\"," +
            "            \"sectionId\":\"sectionId3\"," +
            "            \"sectionName\":\"sectionName3\"," +
            "            \"webPublicationDate\":\"webPublicationDate3\"," +
            "            \"webTitle\":\"webTitle3\"," +
            "            \"webUrl\":\"https://stackoverflow.com\"," +
            "            \"apiUrl\":\"https://api.stackexchange.com\"," +
            "            \"fields\":{" +
            "               \"headline\":\"head3\"," +
            "               \"body\":\"body3\"," +
            "               \"wordcount\":\"1\"" +
            "            },\n" +
            "            \"isHosted\":true," +
            "            \"pillarId\":\"pillarId3\"," +
            "            \"pillarName\":\"pillarName3\"" +
            "         }" +
            "]" +
            "}" +
            "}";

    @BeforeAll
    void instanceCreator() throws MalformedURLException, JSONException {
        parser = new APIParser(JSONString);
    }
    @Test
    @DisplayName("Test if the API endpoint response parser is correctly capable of parsing a JSON containing an article array.")
    void getArticles() throws MalformedURLException {
        List<APIArticle> result = parser.getArticles();
        List<APIArticle> expected = new ArrayList<APIArticle>();
        APIArticle APIArticle1 = new APIArticle("head1","body1","Id1", "type1", "sectionId1",
                "sectionName1","webPublicationDate1","webTitle1",new URL("https://it.wikipedia.orgh"),
                            new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId1","pillarName1",1);
        APIArticle APIArticle2 = new APIArticle("head2","body2","Id2", "type2", "sectionId2",
                "sectionName2","webPublicationDate2","webTitle2",new URL("https://www.ieee.org"),
                new URL("https://developer.ieee.org"),true,"pillarId2","pillarName2",1);
        APIArticle APIArticle3 = new APIArticle("head3","body3","Id3", "type3", "sectionId3",
                "sectionName3","webPublicationDate3","webTitle3",new URL("https://stackoverflow.com"),
                new URL("https://api.stackexchange.com"),true,"pillarId3","pillarName3",1);
        expected.add(APIArticle1);
        expected.add(APIArticle2);
        expected.add(APIArticle3);
        assertThat(expected, samePropertyValuesAs(result));
    }
}