package org.project;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * this class is used throughout the entire program to represent an article with all it's properties
 * (which are identical to the ones of the "The Guardian" api response except for the fields head and body )
 *
 * notice: it does not describe the parameter like pages, page limit because they are characteristics of the api response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    private String Id = "";
    private String type = "";
    private String sectionId = "";
    private String sectionName = "";
    private String webPublicationDate = "";
    private String webTitle = "";
    private URL webUrl = null;
    private URL apiUrl = null;
    private boolean isHosted = false;
    private String pillarId = "";
    private String pillarName = "";
    private String head = "";
    private String body = "";
    private String wordcount = "";
    private String mediaGroup = "";

    public Article(){

    }

    /*Setters*/
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
    public void setWebPublicationDate(String webPublicationDate1){
        webPublicationDate = webPublicationDate1;
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

    public void setWordcount(String wordcount1) {
        wordcount = wordcount1;
    }
    public void setMediaGroup(String mediaGroup1) {
        mediaGroup = mediaGroup1;
    }
    public void setHead(String head1) {
        head = head1;
    }

    public void setBody(String body1) {body = body1;
    }

    /*getters*/
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
    public String getWebPublicationDate(){
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
    public String getMediaGroup() {
        return mediaGroup;
    }




    public String getBody() {
        return body;
    }


    public String getWordcount() {
        return wordcount;
    }

    //this method can be used for testing purposes to see the actual state of the article it is invoked to
    public void printArticle(){
        System.out.println("Id : "+Id);
        System.out.println("type : "+type);
        System.out.println("sectionId : "+sectionId);
        System.out.println("sectionName : "+sectionName);
        System.out.println("webPubblicationDate : "+ webPublicationDate);
        System.out.println("webTitle : "+webTitle);
        System.out.println("webUrl : "+webUrl);
        System.out.println("apiUrl : "+apiUrl);
        System.out.println("isHosted : "+isHosted);
        System.out.println("pillarName : "+pillarName);
        System.out.println("pillarId : "+pillarId);
        System.out.println("pillarId : "+pillarId);
        System.out.println("mediaGroup : "+mediaGroup);
        System.out.println("head : "+head);
        System.out.println("body : "+body);
        System.out.println("wordCount : "+wordcount);
    }

}
