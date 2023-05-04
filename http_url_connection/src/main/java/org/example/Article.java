package org.example;

import java.net.MalformedURLException;
import java.net.URL;

public class Article {
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
    private String head = "";
    private String body = "";
    public Article(){

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
    public String getId(){
        return Id;
    }
    public String getType(){
        return type;
    }
    public URL getWebUrl(){
        return webUrl;
    }
    public URL getApiUrl(){
        return apiUrl;
    }
    public String getSectionId(){
        return sectionId;
    }
    public String getSectionName(){
        return sectionName;
    }
    public String getWebPubblicationDate(){
        return sectionId;
    }
    public String getWebTitle(){
        return webTitle;
    }
    public boolean getHosted(){
        return isHosted;
    }
    public String getPillarId(){
        return pillarId;
    }
    public String getPillarName(){
        return pillarName;
    }
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public void printArticle(){
        System.out.println("Id : "+Id);
        System.out.println("type : "+type);
        System.out.println("sectionId : "+sectionId);
        System.out.println("sectionName : "+sectionName);
        System.out.println("webPubblicationDate : "+webPubblicationDate);
        System.out.println("webTitle : "+webTitle);
        System.out.println("webUrl : "+webUrl);
        System.out.println("apiUrl : "+apiUrl);
        System.out.println("isHosted : "+isHosted);
        System.out.println("pillarName : "+pillarName);
        System.out.println("pillarId : "+pillarId);
        System.out.println("pillarId : "+pillarId);
    }

}
