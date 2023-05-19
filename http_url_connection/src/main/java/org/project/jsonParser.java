package org.project;
import org.json.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to parse the give api response content, assuming it has fields to describe the response and fields to describe each one of the pages it contains
 */
public class jsonParser
{
    private String jsonString;
    private String status;
    private String userTier;
    private int total;
    private int startIndex;
    private int pageSize;
    private int currentPage;
    private int pages;
    private String orderBy;
    private List<Article> articles;

    /**
     * @param str
     * @throws JSONException
     * @throws MalformedURLException
     */
    public jsonParser(String str) throws JSONException, MalformedURLException {
        jsonString = str;
        //initializing the JsonObject with given string
        JSONObject obj = new JSONObject(str);

        //parsing the string to see if it contains attributes of a "the Guardian" api response
        status = obj.getJSONObject("response").getString("status");
        userTier = obj.getJSONObject("response").getString("userTier");
        total = Integer.parseInt( obj.getJSONObject("response").getString("total"));
        startIndex = Integer.parseInt(obj.getJSONObject("response").getString("startIndex"));
        pageSize = Integer.parseInt(obj.getJSONObject("response").getString("pageSize"));
        currentPage = Integer.parseInt(obj.getJSONObject("response").getString("currentPage"));
        pages = Integer.parseInt(obj.getJSONObject("response").getString("pages"));
        orderBy = obj.getJSONObject("response").getString("orderBy");

        //printing information if needed
        /*System.out.println("\nPage info: \n");
        System.out.println("status = "+status);
        System.out.println("userTier = "+userTier);
        System.out.println("total = "+total);
        System.out.println("startIndex = "+startIndex);
        System.out.println("pageSize = "+pageSize);
        System.out.println("currentPage = "+currentPage);
        System.out.println("pages = "+pages);
        System.out.println("orderBy = "+orderBy);
        System.out.println("\nArticles inf: \n");*/

        //reading all the contained articles properties, and initializing array of articles with those (after checking if those fields are contained, not all are necessary)
        JSONArray arr = obj.getJSONObject("response").getJSONArray("results");
        articles = new ArrayList<Article>();
        Article article;
        for (int i = 0; i < arr.length(); i++)
        {
            article = new Article();
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
            article.setMediaGroup("The Guardian");
            articles.add(article);
        }
    }

    /**
     *
     * @return article of arrays parsed from the given string
     */
    public List<Article> getArticles(){
        return articles;
    }


    /*GETTERS*/
    public String getJsonString() {
        return jsonString;
    }
    public String getStatus() {
        return status;
    }
    public int getTotal() {
        return total;
    }
    public int getStartIndex() {
        return startIndex;
    }
    public int getPageSize() {
        return pageSize;
    }
    public String getUserTier() {
        return userTier;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public String getOrderBy() {
        return orderBy;
    }
    public int getPages() {
        return pages;
    }


    /*SETTERS*/
    public void setPages(int pages) {
        this.pages = pages;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
    public void setUserTier(String userTier) {
        this.userTier = userTier;
    }
}
