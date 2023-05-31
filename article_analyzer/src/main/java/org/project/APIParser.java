package org.project;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to parse the given JSON formatted String content assuming it has fields to
 * describe an API response from the "The Guardian" and to each one of the Articles it contains.
 *
 * NOTE: only getters could be useful for this class because all the fields are part of a coherent
 * API response and should not be altered singularly.
 */
public class APIParser {
    /** This is used to store the API endpoint response. */
    private String JSONString;
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
     * This constructor is used to set the JSON formatted string{@link APIParser#JSONString}
     *
     * @param str which is a JSON formatted string.
     * @throws JSONException         if the string parameter is not in a valid JSON format.
     * @throws MalformedURLException if the URLs contained in the articles fields are not correct.
     */
    public APIParser(String str) throws JSONException, MalformedURLException {
        JSONString = str;
        // Initializing the JsonObject with given string.
        JSONObject obj = new JSONObject(str);

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
        APIArticle article;     // This is used to store each elaborated article from the API response.
        for (int i = 0; i < arr.length(); i++)
        {
            article = new APIArticle();
            if(arr.getJSONObject(i).has("id")){
                article.setId(arr.getJSONObject(i).getString("id"));
            }else{
                article.setId("");
            }
            if(arr.getJSONObject(i).has("type")){
                article.setType(arr.getJSONObject(i).getString("type"));
            }else{
                article.setType("");
            }
            if(arr.getJSONObject(i).has("sectionName")){
                article.setSectionName(arr.getJSONObject(i).getString("sectionName"));
            }else{
                article.setSectionName("");
            }
            if(arr.getJSONObject(i).has("sectionId")){
                article.setSectionId(arr.getJSONObject(i).getString("sectionId"));
            }else{
                article.setSectionId("");
            }
            if(arr.getJSONObject(i).has("webTitle")){
                article.setWebTitle(arr.getJSONObject(i).getString("webTitle"));
            }else{
                article.setWebTitle("");
            }
            if(arr.getJSONObject(i).has("webUrl")){
                article.setWebUrl(arr.getJSONObject(i).getString("webUrl"));
            }else{
                article.setWebUrl("");
            }
            if(arr.getJSONObject(i).has("webPublicationDate")){
                article.setWebPublicationDate(arr.getJSONObject(i).getString("webPublicationDate"));
            }else{
                article.setWebPublicationDate("");
            }
            if(arr.getJSONObject(i).has("apiUrl")){
                article.setApiUrl(arr.getJSONObject(i).getString("apiUrl"));
            }else{
                article.setApiUrl("");
            }
            if(arr.getJSONObject(i).has("pillarId")){
                article.setPillarId(arr.getJSONObject(i).getString("pillarId"));
            }else{
                article.setPillarId("");
            }
            if(arr.getJSONObject(i).has("pillarName")){
                article.setPillarName(arr.getJSONObject(i).getString("pillarName"));
            }else{
                article.setPillarName("");
            }
            if(arr.getJSONObject(i).getJSONObject("fields").has("body")){
                article.setBody(arr.getJSONObject(i).getJSONObject("fields").getString("body"));
            }
            if(arr.getJSONObject(i).getJSONObject("fields").has("headline")){
                article.setHead(arr.getJSONObject(i).getJSONObject("fields").getString("headline"));
            }
            if(arr.getJSONObject(i).getJSONObject("fields").has("wordcount")){
                article.setWordcount(arr.getJSONObject(i).getJSONObject("fields").getString("wordcount"));
            }
            // Adding the elaborated article to the List.
            articles.add(article);
        }
    }


    /*GETTERS*/


    /**
     * Gets JSON string{@link APIParser#JSONString}.
     *
     * @return JSON formatted string
     */
    public String getJSONString() {
        return JSONString;
    }

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
