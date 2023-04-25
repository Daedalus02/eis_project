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

        System.out.println("status = "+status);
        System.out.println("userTier = "+userTier);
        System.out.println("total = "+total);
        System.out.println("startIndex = "+startIndex);
        System.out.println("pageSize = "+pageSize);
        System.out.println("currentPage = "+currentPage);
        System.out.println("pages = "+pages);
        System.out.println("orderBy = "+orderBy);

        System.out.println("Articles :");

        JSONArray arr = obj.getJSONObject("response").getJSONArray("results");

        for (int i = 0; i < arr.length(); i++)
        {
            articles[i] = new article();
            articles[i].setId(arr.getJSONObject(i).getString("id"));
            articles[i].setHosted(arr.getJSONObject(i).getString("isHosted"));
            articles[i].setType("type");
            articles[i].setSectionName("sectionName");
            articles[i].setSectionId("sectionId");
            articles[i].setWebTitle("webTitle");
            articles[i].setWebUrl("webUrl");
            articles[i].setWebPubblicationDate("webPublicationDate");
            articles[i].setApiUrl("apiURl");
            articles[i].setPillarId("pillarId");
            articles[i].setPillarName("pillarName");
        }
    }
}
