package org.project;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is used to represent an article with all properties af an article of the "The Guardian" media group.
 */
public class APIArticle extends Article {
    /** This is the article identifier.*/
    private String Id;
    /** This is the article type. */
    private String type;
    /** This is the article section identifier. */
    private String sectionId;
    /** This is the article section name. */
    private String sectionName;
    /** This is the date of publication of the article. */
    private String webPublicationDate;
    /** This is the name of the article. */
    private String webTitle;
    /** This is the url of the article. */
    private URL webUrl = null;
    /** This is the url of the API of the article. */
    private URL apiUrl = null;
    /** This tells whether the article is hosted or not (always set to true if not specified). */
    private boolean isHosted = true;
    /** This is the column identifier. */
    private String pillarId;
    /** This is the name od the column the article is located at. */
    private String pillarName;
    /** This is the number of words contained in the article. */
    private int wordcount;


    /**
     * Initialises a new Api article setting its fields with the given parameters.
     *
     * @param head               the head.
     * @param body               the body.
     * @param id                 the id {@link APIArticle#Id}.
     * @param type               the type {@link APIArticle#type}.
     * @param sectionId          the section id {@link APIArticle#sectionId}.
     * @param sectionName        the section name {@link APIArticle#sectionName}.
     * @param webPublicationDate the web publication date {@link APIArticle#webPublicationDate}.
     * @param webTitle           the web title {@link APIArticle#webTitle}.
     * @param webUrl             the web url {@link APIArticle#webUrl} .
     * @param apiUrl             the api url {@link APIArticle#apiUrl}.
     * @param isHosted           the is hosted {@link APIArticle#isHosted}.
     * @param pillarId           the pillar id {@link APIArticle#pillarId}.
     * @param pillarName         the pillar name {@link APIArticle#pillarName}.
     * @param wordcount          the word count {@link APIArticle#wordcount}.
     */
    public APIArticle(String head, String body, String id, String type, String sectionId, String sectionName, String webPublicationDate, String webTitle, URL webUrl, URL apiUrl, boolean isHosted, String pillarId, String pillarName, Integer wordcount)  {
        super(head, body);
        Id = id;
        this.type = type;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.apiUrl = apiUrl;
        this.isHosted = isHosted;
        this.pillarId = pillarId;
        this.pillarName = pillarName;
        this.wordcount = wordcount;
    }

    /**
     * This is just a dummy constructor needed by other classes.
     */
    public APIArticle() {
        // Calling the superclass Article dummy constructor.
        super();
    }

    /* SETTERS */

    /**
     * Sets id{@link APIArticle#Id}.
     *
     * @param Id which is the article identifier.
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * Sets type {@link APIArticle#type}.
     *
     * @param type which is the article type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets section id {@link APIArticle#sectionId}.
     *
     * @param sectionId which is the article section id.
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * Sets section name{@link APIArticle#sectionName}.
     *
     * @param sectionName which is the article section name.
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * Sets web publication date{@link APIArticle#webPublicationDate}.
     *
     * @param webPublicationDate which is the article web publication date.
     */
    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    /**
     * Sets web title{@link APIArticle#webTitle}.
     *
     * @param webTitle which is the article title.
     */
    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    /**
     * Sets web URL{@link APIArticle#webUrl}.
     *
     * @param webUrl which is the URL of the article web page.
     * @throws MalformedURLException if webUrl is not a valid URL.
     */
    public void setWebUrl(String webUrl) throws MalformedURLException {
        this.webUrl = new URL(webUrl);
    }

    /**
     * Sets API URL{@link APIArticle#apiUrl}.
     *
     * @param apiUrl which is the url of the article API endpoint.
     * @throws MalformedURLException if apiUrl is not a valid URL.
     */
    public void setApiUrl(String apiUrl) throws MalformedURLException {
        this.apiUrl = new URL(apiUrl);
    }

    /**
     * Sets isHosted{@link APIArticle#isHosted}.
     *
     * @param hosted which tells whether the site is hosted or not.
     */
    public void setHosted(boolean hosted) {
        isHosted = hosted;
    }

    /**
     * Sets pillar id {@link APIArticle#pillarId}.
     *
     * @param pillarId which is the article column identifier.
     */
    public void setPillarId(String pillarId) {
        this.pillarId = pillarId;
    }

    /**
     * Sets pillar name{@link APIArticle#pillarName}.
     *
     * @param pillarName which is the article column name.
     */
    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    /**
     * Sets head.
     *
     * @param head the head of the super abstract class.
     */
    public void setHead(String head) {
        super.setHead(head);
    }

    /**
     * Sets body.
     *
     * @param body the body of the super abstract class.
     */
    public void setBody(String body) {
        super.setBody(body);
    }

    /**
     * Sets word count {@link APIArticle#wordcount}.
     *
     * @param wordcount which is the word in contained in the article.
     */
    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }

    /* GETTERS */

    /**
     * Gets Id string {@link APIArticle#Id}.
     *
     * @return the article identifier.
     */
    public String getId(){
        return Id;
    }

    /**
     * Gets type string {@link APIArticle#type}.
     *
     * @return the article type.
     */
    public String getType(){
        return type;
    }

    /**
     * Gets web URL {@link APIArticle#webUrl}.
     *
     * @return the URL of the article web page .
     */
    public URL getWebUrl(){
        return webUrl;
    }

    /**
     * Gets API URL {@link APIArticle#apiUrl}.
     *
     * @return the URL of the article API endpoint.
     */
    public URL getApiUrl(){
        return apiUrl;
    }

    /**
     * Gets section id string {@link APIArticle#sectionId}.
     *
     * @return article section identifier.
     */
    public String getSectionId(){
        return sectionId;
    }

    /**
     * Gets section name string {@link APIArticle#sectionName}.
     *
     * @return article section name.
     */
    public String getSectionName(){
        return sectionName;
    }

    /**
     * Gets web publication date string {@link APIArticle#webPublicationDate}.
     *
     * @return the article publication date.
     */
    public String getWebPublicationDate(){
        return webPublicationDate;
    }

    /**
     * Gets web title string {@link APIArticle#webTitle}.
     *
     * @return the title of the article.
     */
    public String getWebTitle(){
        return webTitle;
    }

    /**
     * Gets hosted boolean {@link APIArticle#isHosted}.
     *
     * @return true if the article is hosted.
     */
    public boolean getHosted(){
        return isHosted;
    }

    /**
     * Gets pillar id string {@link APIArticle#pillarId}.
     *
     * @return the article column identifier.
     */
    public String getPillarId(){
        return pillarId;
    }

    /**
     * Gets pillar name string {@link APIArticle#pillarName}.
     *
     * @return the name of the column associated with the article.
     */
    public String getPillarName(){
        return pillarName;
    }

    /**
     * Gets head.
     *
     * @return head from the super class.
     */
    public String getHead() {
        return super.getHead();
    }

    /**
     * Gets body.
     *
     * @return body from the superclass.
     */
    public String getBody() {
        return super.getBody();
    }

    /**
     * Gets word count {@link APIArticle#wordcount}.
     *
     * @return word count in the articles .
     */
    public int getWordcount() {
        return wordcount;
    }
}
