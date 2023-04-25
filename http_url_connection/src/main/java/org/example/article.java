package org.example;

import java.net.MalformedURLException;
import java.net.URL;

public class article {
    //private final String[] relatedInfo= {"Id","type","sectionId","sectionName","webPubblicationDate","webTitle","webUrl","apiUrl","isHosted","pillarName","pillarId"};
    private String Id = "";
    private String type = "";
    private String sectionId = "";
    private String sectionName = "";
    private String webPubblicationDate = "";
    private String webTitle = "";
    private URL webUrl = null;
    private URL apiUrl = null;
    private boolean isHosted = false;
    private String pillarId = "";
    private String pillarName = "";
    public article (){

    }
    public void setId(String id1){
        Id = id1;
    }
    public void setType(String type1){
        type = type1;
    }
    public void setSectionId(String sectionId1){
        sectionId = sectionId1;
    }
    public void setSectionName(String sectionName1){
        sectionName = sectionName1;
    }
    public void setWebPubblicationDate(String webPublicationDate1){
        webPubblicationDate = webPublicationDate1;
    }
    public void setWebTitle(String webTitle1){
        webTitle = webTitle1;
    }
    public void setHosted(String hosted1){
        isHosted = Boolean.parseBoolean(hosted1);
    }
    public void setWebUrl(String webUrl1) throws MalformedURLException{
        webUrl = new URL(webUrl1);
    }
    public void setApiUrl(String apiUrl1) throws MalformedURLException {
        apiUrl = new URL(apiUrl1);
    }
    public void setPillarId(String pillarId1){
        pillarId = pillarId1;
    }
    public void setPillarName(String pillarName1){
        pillarName = pillarName1;
    }
    /*public String[] getRelatedInfo(){
        return relatedInfo;
    }*/
}
