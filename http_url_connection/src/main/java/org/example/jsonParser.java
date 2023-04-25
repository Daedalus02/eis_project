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
    private article[] articles;


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
        articles = new article[arr.length()];
        for (int i = 0; i < arr.length(); i++)
        {
            articles[i] = new article();
            articles[i].setId(arr.getJSONObject(i).getString("id"));
            articles[i].setHosted(arr.getJSONObject(i).getString("isHosted"));
            articles[i].setType(arr.getJSONObject(i).getString("type"));
            articles[i].setSectionName(arr.getJSONObject(i).getString("sectionName"));
            articles[i].setSectionId(arr.getJSONObject(i).getString("sectionId"));
            articles[i].setWebTitle(arr.getJSONObject(i).getString("webTitle"));
            articles[i].setWebUrl(arr.getJSONObject(i).getString("webUrl"));
            articles[i].setWebPubblicationDate(arr.getJSONObject(i).getString("webPublicationDate"));
            articles[i].setApiUrl(arr.getJSONObject(i).getString("apiUrl"));
            articles[i].setPillarId(arr.getJSONObject(i).getString("pillarId"));
            articles[i].setPillarName(arr.getJSONObject(i).getString("pillarName"));
            //articles[i].printArticle();
            //System.out.println();
        }
    }
    public article[] getArticles(){
        return articles;
    }

}
