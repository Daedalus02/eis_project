package org.project;

import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/*
* Here we test the correct functioning of the API parser with a possible(little) API response,
* we also test that the class throw a JSONException if the API response is not in the correct format.
* (Between all the multiple possible way an API response from the "The Guardian" could be invalid we
* consider the case there is not formatting for the JSON array of articles)
* */
@Tag("API")
class APIParserTest {

    /* Testing the correct functioning of the API parser with valid JSON formatted string. */
    @Test
    @DisplayName("Test if the API endpoint response parser is correctly capable of parsing a JSON containing an article array.")
    void getArticles() throws MalformedURLException, JSONException, ParseException {
        // Possible API response with three articles(described by all the possible fields) and a response description
        // in the beginning.
        String validJSON= "{" +
                "   \"response\":{" +       // Here start the response description.
                "      \"status\":\"ok\"," +
                "      \"userTier\":\"developer\"," +
                "      \"total\":100," +
                "      \"startIndex\":1," +
                "      \"pageSize\":100," +
                "      \"currentPage\":1," +
                "      \"pages\":1," +
                "      \"orderBy\":\"newest\"," +
                "      \"results\":[" +
                "         {" +      // Here start the Articles description.
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
                "               \"wordcount\":1" +
                "            },\n" +
                "            \"isHosted\":true," +
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
                "               \"wordcount\":1" +
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
                "               \"wordcount\":1" +
                "            },\n" +
                "            \"isHosted\":true," +
                "            \"pillarId\":\"pillarId3\"," +
                "            \"pillarName\":\"pillarName3\"" +
                "         }" +
                "]" +
                "}" +
                "}";
        // Initializing the parser with the valid response.
        APIParser parser = new APIParser();
        parser.parse(validJSON);
        // Calculating the output articles of the
        // response.
        List<APIArticle> result = parser.getArticles();

        // Creating the expected articles list  with same fields values.
        List<APIArticle> expected = new ArrayList<APIArticle>();
        APIArticle APIArticle1 = new APIArticle("head1","body1","id1", "type1", "sectionId1",
                "sectionName1","webPublicationDate1","webTitle1",new URL("https://it.wikipedia.org"),
                            new URL("https://www.mediawiki.org/w/api.php"),true,"pillarId1","pillarName1",1);
        APIArticle APIArticle2 = new APIArticle("head2","body2","id2", "type2", "sectionId2",
                "sectionName2","webPublicationDate2","webTitle2",new URL("https://www.ieee.org"),
                new URL("https://developer.ieee.org"),true,"pillarId2","pillarName2",1);
        APIArticle APIArticle3 = new APIArticle("head3","body3","id3", "type3", "sectionId3",
                "sectionName3","webPublicationDate3","webTitle3",new URL("https://stackoverflow.com"),
                new URL("https://api.stackexchange.com"),true,"pillarId3","pillarName3",1);
        expected.add(APIArticle1);
        expected.add(APIArticle2);
        expected.add(APIArticle3);

        // Testing the equality between the parsed list of articles and the expected list.
        assertEquals(expected, result);

    }



    /* Testing the API parser exception with valid and invalid formatted string. */

    /* Testing the necessity of the API parser to have a JSON array format to correctly work. */
    @DisplayName("Testing if the JSON exception is thrown when the JSON formatted string is in invalid format.")
    @Test
    void testJSONException(){
        // Here we remove the JSONArray format to test the necessity of its presence the API
        // endpoint response of the "The Guardian".
        String invalidJSON = "{" +
                "   \"response\":{" +
                "      \"status\":\"ok\"," +
                "      \"userTier\":\"developer\"," +
                "      \"total\":100," +
                "      \"startIndex\":1," +
                "      \"pageSize\":100," +
                "      \"currentPage\":1," +
                "      \"pages\":1," +
                "      \"orderBy\":\"newest\"," +
                "         {" +      // Note: here no JSON array was open(with "["), and it is also never closed (with "]").
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
                "}" +
                "}";
        // Testing the fact that the parser in this given condition (invalidity od the JSON formatted string argument) will throw
        // a JSONException.
        APIParser parser = new APIParser();
        assertThrows(
                JSONException.class,
                () ->parser.parse(invalidJSON),
                "Expected APIParser to throw JSONException but didn't."
        );
    }

    /*
    * Note: in this test we associate a position in the String array to each field af an article JSON description in the response.
    * Then we remove each one of them and test the APIParser functioning after their removal.
    *
    * Note: element related to JSON response and structural element(0-11 + 20 + 24 + 28-31) must be present while
    * article fields(12-19 + 21-23) would be filled with standard value if absent in the response.
    * */
    @DisplayName("Testing the removal of each fields in the API response.")
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31})
    void testFieldsRemoval(int positionField) throws MalformedURLException, JSONException {
        // Possible API response with three articles(described by all the possible fields) and a response description
        // in the beginning.
        String[] JSONResponse = new String[]{
                "{",    //0
                "   \"response\":{",                    //1
                "      \"status\":\"ok\"," ,            //2
                "      \"userTier\":\"developer\"," ,   //3
                "      \"total\":100," ,                //4
                "      \"startIndex\":1," ,             //5
                "      \"pageSize\":100," ,             //6
                "      \"currentPage\":1," ,            //7
                "      \"pages\":1," ,                  //8
                "      \"orderBy\":\"newest\"," ,       //9
                "      \"results\":[" ,                 //10
                "         {" ,                          //11
                "            \"id\":\"id1\"," ,                                              //12
                "            \"type\":\"type1\"," ,                                          //13
                "            \"sectionId\":\"sectionId1\"," ,                                //14
                "            \"sectionName\":\"sectionName1\"," ,                            //15
                "            \"webPublicationDate\":\"webPublicationDate1\"," ,              //16
                "            \"webTitle\":\"webTitle1\"," ,                                  //17
                "            \"webUrl\":\"https://it.wikipedia.org\",\n" ,                   //18
                "            \"apiUrl\":\"https://www.mediawiki.org/w/api.php\",\n" ,        //19
                "            \"fields\":{\n" ,                      //20
                "               \"headline\":\"head1\",\n" ,        //21
                "               \"body\":\"body1\"," ,              //22
                "               \"wordcount\":1" ,                  //23
                "            },\n" ,                              //24
                "            \"isHosted\":true," ,                //25
                "            \"pillarId\":\"pillarId1\"," ,       //26
                "            \"pillarName\":\"pillarName1\"" ,    //27
                "         }" ,                                    //28
                "]" ,//29
                "}" ,//30
                "}"  //31
        };

        // Creating the JSON response string removing one of the line per time.
        String JSONSTring = "";
        for(int i = 0; i < JSONResponse.length; i++ ){
            if(i != positionField){
                JSONSTring += JSONResponse[i];
            }
        }
        APIParser parser = new APIParser();
        // Here we consider cases where the removal is not relevant and the article is still created.
        if(positionField >11 && positionField <20 | positionField > 20 && positionField < 24 | positionField >24 && positionField < 28){
            String finalJSONString = JSONSTring;

            assertDoesNotThrow(
                    () -> parser.parse(finalJSONString)
            );
        }else{      // Here we consider the cases when the JSONString has some structural removal.
            String finalJSONString1 = JSONSTring;
            assertThrows(
                    JSONException.class,
                    () -> parser.parse(finalJSONString1),
                    "Expected to throw JSONException but didn't."
            );
        }
    }
}