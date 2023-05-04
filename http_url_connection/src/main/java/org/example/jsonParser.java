package org.example;
import org.json.*;

import java.net.MalformedURLException;

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
    private Article[] articles;


    public jsonParser(String str) throws JSONException, MalformedURLException {
        jsonString = str;
        JSONObject obj = new JSONObject(str);
        status = obj.getJSONObject("response").getString("status");
        userTier = obj.getJSONObject("response").getString("userTier");
        total = Integer.parseInt( obj.getJSONObject("response").getString("total"));
        startIndex = Integer.parseInt(obj.getJSONObject("response").getString("startIndex"));
        pageSize = Integer.parseInt(obj.getJSONObject("response").getString("pageSize"));
        currentPage = Integer.parseInt(obj.getJSONObject("response").getString("currentPage"));
        pages = Integer.parseInt(obj.getJSONObject("response").getString("pages"));
        orderBy = obj.getJSONObject("response").getString("orderBy");
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

        JSONArray arr = obj.getJSONObject("response").getJSONArray("results");
        articles = new Article[arr.length()];
        for (int i = 0; i < arr.length(); i++)
        {
            articles[i] = new Article();
            if(arr.getJSONObject(i).has("id")){
                articles[i].setId(arr.getJSONObject(i).getString("id"));
            }else{
                articles[i].setId("");
            }
            if(arr.getJSONObject(i).has("type")){
                articles[i].setType(arr.getJSONObject(i).getString("type"));
            }else{
                articles[i].setType("");
            }
            if(arr.getJSONObject(i).has("sectionName")){
                articles[i].setSectionName(arr.getJSONObject(i).getString("sectionName"));
            }else{
                articles[i].setSectionName("");
            }
            if(arr.getJSONObject(i).has("sectionId")){
                articles[i].setSectionId(arr.getJSONObject(i).getString("sectionId"));
            }else{
                articles[i].setSectionId("");
            }
            if(arr.getJSONObject(i).has("webTitle")){
                articles[i].setWebTitle(arr.getJSONObject(i).getString("webTitle"));
            }else{
                articles[i].setWebTitle("");
            }
            if(arr.getJSONObject(i).has("webUrl")){
                articles[i].setWebUrl(arr.getJSONObject(i).getString("webUrl"));
            }else{
                articles[i].setWebUrl("");
            }
            if(arr.getJSONObject(i).has("webPublicationDate")){
                articles[i].setWebPubblicationDate(arr.getJSONObject(i).getString("webPublicationDate"));
            }else{
                articles[i].setWebPubblicationDate("");
            }
            if(arr.getJSONObject(i).has("apiUrl")){
                articles[i].setApiUrl(arr.getJSONObject(i).getString("apiUrl"));
            }else{
                articles[i].setApiUrl("");
            }
            if(arr.getJSONObject(i).has("pillarId")){
                articles[i].setPillarId(arr.getJSONObject(i).getString("pillarId"));
            }else{
                articles[i].setPillarId("");
            }
            if(arr.getJSONObject(i).has("pillarName")){
                articles[i].setPillarName(arr.getJSONObject(i).getString("pillarName"));
            }else{
                articles[i].setPillarName("");
            }
        }
    }
    public Article[] getArticles(){
        return articles;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setArticles(Article[] articles) {
        this.articles = articles;
    }

    public String getUserTier() {
        return userTier;
    }

    public void setUserTier(String userTier) {
        this.userTier = userTier;
    }
}
