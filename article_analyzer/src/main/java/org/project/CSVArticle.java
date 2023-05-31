package org.project;

import java.net.URL;

/**
 * This class represents the fields that are used in the CSV source to represent an Article.
 */
public class CSVArticle extends Article{
    /** This is used to identify the article.*/
    private String identifier;
    /** This is the URL to the article online page.*/
    private URL URL;
    /** This is the date of publication of the article. */
    private String Date;
    /** This is the group that published the article. */
    private String sourceSet;
    /** This is used to store the article type (example: opinion, business, environment, ...).*/
    private String source;

    /**
     * This is a dummy constructor needed by other classes to just initialise
     * a new instance of this class.
     */
    public CSVArticle(){
        super();
    }

    /**
     * This constructor is used to set all the fields.
     *
     * @param head       which is the head field of the article.
     * @param body       which is the body field of the article.
     * @param identifier which is the article {@link CSVArticle#identifier}.
     * @param URL        which is the article HTML page {@link CSVArticle#URL}.
     * @param Date       which is the article web publication {@link CSVArticle#Date}.
     * @param sourceSet  which is the group the published the article.
     * @param source     which is the article type.
     */
    public CSVArticle(String head, String body, String identifier, URL URL, String Date, String sourceSet, String source) {
        super(head, body);
        this.identifier = identifier;
        this.URL = URL;
        this.Date = Date;
        this.sourceSet = sourceSet;
        this.source = source;
    }

    /* SETTERS */

    /**
     * Sets {@link CSVArticle#identifier}.
     *
     * @param identifier which is the article identifier.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * Sets {@link CSVArticle#URL}.
     *
     * @param URL which is the article web page URL.
     */
    public void setURL(URL URL) {
        this.URL = URL;
    }
    /**
     * Sets {@link CSVArticle#Date}.
     *
     * @param date which is the web publication date of the article.
     */
    public void setDate(String date) {
        Date = date;
    }
    /**
     * Sets {@link CSVArticle#sourceSet}.
     *
     * @param sourceSet which is the group the published the article.
     */
    public void setSourceSet(String sourceSet) {
        this.sourceSet = sourceSet;
    }
    /**
     * Sets {@link CSVArticle#source}.
     *
     * @param source which is the article type.
     */
    public void setSource(String source) {
        this.source = source;
    }
    /**
     * Sets the body field of the article.
     *
     * @param body which is the body field af an Article.
     */
    public void setBody(String body){
        super.setBody(body);
    }
    /**
     * Sets the head field of the article.
     *
     * @param head which is the head field of an article.
     */
    public void setHead(String head){
        super.setHead(head);
    }


    /* GETTERS */
    /**
     * Gets {@link CSVArticle#sourceSet}.
     *
     * @return the source set.
     */
    public String getSourceSet() {
        return sourceSet;
    }
    /**
     * Gets the body field of the article.
     *
     * @return body field of the article.
     */
    public String getBody(){
        return super.getBody();
    }
    /**
     * Gets {@link CSVArticle#URL}.
     *
     * @return the article web page URL.
     */
    public URL getURL() {
        return URL;
    }
    /**
     * Gets the head field of the article.
     *
     * @return the head field of the article.
     */
    public String getHead(){
        return super.getHead();
    }
    /**
     * Gets {@link CSVArticle#source}.
     *
     * @return the article type.
     */
    public String getSource() {
        return source;
    }
    /**
     * Gets {@link CSVArticle#Date}.
     *
     * @return the article web publication date.
     */
    public String getDate() {
        return Date;
    }
    /**
     * Gets {@link CSVArticle#identifier}.
     *
     * @return the article identifier.
     */
    public String getIdentifier() {
        return identifier;
    }
}
