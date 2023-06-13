package org.project;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to parse the given JSON formatted String content assuming it has fields to
 * describe an API response from the "The Guardian" and to each one of the Articles it contains.
 *
 * NOTE: only getters could be useful for this class because all the fields are part of a coherent
 * API response and should not be altered singularly.
 */
public final class APIParser {
    /** This variable represents the status of the connection to the API endpoint. */
    private String status;
    /** This is the description of the user level in the "The Guardian".  */
    private String userTier;
    /** This is the Max possible number of article for the specified parameters. */
    private int total;
    /** This is the lowest number associated to a page. */
    private int startIndex;
    /** This is the number of pages in the API response. */
    private int pageSize;
    /** This is the current page out of a maximum {@link APIParser#total}. */
    private int currentPage;
    /** This is the max number of pages possible. */
    private int pages;
    /** This is the way articles are ordered in the API response. */
    private String orderBy;
    /** This is used to keep track of the parse List of Articles. */
    private List<APIArticle> articles;

    /**
     * This constructor is a default constructor used to initialize an APIParser instance.
     */
    public APIParser() {}

    /**
     * This method is used to parse a string formatted in JSON which contains fields to describe a "The Guardian" API endpoint
     * response and the articles.
     *
     * @param JSONString which is a JSON formatted string.
     * @throws JSONException         if the string parameter is not in a valid JSON format.
     * @throws MalformedURLException if the URLs contained in the articles fields are not correct.
     */
    public void parse(String JSONString) throws JSONException, MalformedURLException {
        // Initializing the JsonObject with given string.
        JSONObject obj = new JSONObject(JSONString);
        /* Parsing the string to see if it contains attributes of a "The Guardian" API response.
         * NOTE: the response is assumed to be in the correct format
         * */
        status = obj.getJSONObject("response").getString("status");
        userTier = obj.getJSONObject("response").getString("userTier");
        total = Integer.parseInt( obj.getJSONObject("response").getString("total"));
        startIndex = Integer.parseInt(obj.getJSONObject("response").getString("startIndex"));
        pageSize = Integer.parseInt(obj.getJSONObject("response").getString("pageSize"));
        currentPage = Integer.parseInt(obj.getJSONObject("response").getString("currentPage"));
        pages = Integer.parseInt(obj.getJSONObject("response").getString("pages"));
        orderBy = obj.getJSONObject("response").getString("orderBy");

        /* Reading all the contained articles properties and initializing List of articles with them.
         * (after checking if those fields are contained, not all are necessary)
         */
        JSONArray arr = obj.getJSONObject("response").getJSONArray("results");
        articles = new ArrayList<APIArticle>();
        // Declaring the HTML parser for head and body fields.
        HTMLParser htmlParser = new HTMLParser();
        /* Declaring all the variable to store articles fields. */
        String body = "";
        String head = "";
        String id = "";
        String type = "";
        String sectionId = "";
        String sectionName = "";
        String webPublicationDate = "";
        String webTitle = "";
        URL webUrl = null;
        URL apiUrl = null;
        boolean isHosted = false;
        String pillarId = "";
        String pillarName = "";
        int wordcount = 0;
        for (int i = 0; i < arr.length(); i++)
        {
            if(arr.getJSONObject(i).has("id")){
                id = arr.getJSONObject(i).getString("id");
            }
            if(arr.getJSONObject(i).has("type")){
                type = arr.getJSONObject(i).getString("type");
            }
            if(arr.getJSONObject(i).has("sectionName")){
                sectionName =arr.getJSONObject(i).getString("sectionName");
            }
            if(arr.getJSONObject(i).has("sectionId")){
                sectionId = arr.getJSONObject(i).getString("sectionId");
            }
            if(arr.getJSONObject(i).has("webTitle")){
                webTitle = arr.getJSONObject(i).getString("webTitle");
            }
            if(arr.getJSONObject(i).has("webUrl")){
                webUrl = new URL(arr.getJSONObject(i).getString("webUrl"));
            }
            if(arr.getJSONObject(i).has("webPublicationDate")){
                webPublicationDate = arr.getJSONObject(i).getString("webPublicationDate");
            }
            if(arr.getJSONObject(i).has("apiUrl")){
                apiUrl = new URL(arr.getJSONObject(i).getString("apiUrl"));
            }
            if(arr.getJSONObject(i).has("pillarId")){
                pillarId = arr.getJSONObject(i).getString("pillarId");
            }
            if(arr.getJSONObject(i).has("pillarName")){
                pillarName = arr.getJSONObject(i).getString("pillarName");
            }
            if(arr.getJSONObject(i).has("isHosted")){
                isHosted = Boolean.parseBoolean(arr.getJSONObject(i).getString("isHosted"));
            }
            if(arr.getJSONObject(i).getJSONObject("fields").has("body")){
                body = arr.getJSONObject(i).getJSONObject("fields").getString("body");
            }
            if(arr.getJSONObject(i).getJSONObject("fields").has("headline")){
                head = arr.getJSONObject(i).getJSONObject("fields").getString("headline");
            }
            if(arr.getJSONObject(i).getJSONObject("fields").has("wordcount")){
                wordcount = Integer.parseInt(arr.getJSONObject(i).getJSONObject("fields").getString("wordcount"));
            }
            // Parsing the content of each of the single new articles from HTML.
            head = htmlParser.parse(head);
            body = htmlParser.parse(body);
            // Adding the elaborated article to the List.
            articles.add(new APIArticle(head,body,id,type,sectionId,sectionName,webPublicationDate,webTitle,webUrl,apiUrl,isHosted,pillarId,pillarName,wordcount));
        }
    }

    /*GETTERS*/
    /**
     * Gets status {@link APIParser#status}.
     *
     * @return the status of the API response.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets total{@link APIParser#total}.
     *
     * @return the total number of articles with the given parameters.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Gets start index{@link APIParser#startIndex}.
     *
     * @return the starting index.
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Gets page size{@link APIParser#pageSize}.
     *
     * @return the page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Gets user tier{@link APIParser#userTier}.
     *
     * @return the user tier in the "The Guardian".
     */
    public String getUserTier() {
        return userTier;
    }

    /**
     * Gets current page{@link APIParser#currentPage}.
     *
     * @return the current page.
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Gets order method {@link APIParser#orderBy}.
     *
     * @return the order method.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Gets pages{@link APIParser#pages}.
     *
     * @return the max number of pages.
     */
    public int getPages() {
        return pages;
    }

    /**
     * Gets articles list {@link APIParser#articles}.
     *
     * @return the List with all articles read in the API response.
     */
    public List<APIArticle> getArticles(){
        return articles;
    }

}
