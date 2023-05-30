package org.project;

/**
 * This class represent the fields that are used in the CSV source to represent an Article.
 */
public class CSVArticle extends Article{
    /** This is used to identify the article.*/
    private String identifier;
    /** This is the URL to the article online page.*/
    private String URL;
    /** This is the date of publication of the article. */
    private String Date;
    /** This is the group that published the article. */
    private String sourceSet;
    /** This is used to store the article type (example: opinion, business, environment, ...).*/
    private String source;

    /**
     * This is a dummy constructor needed by other classes to just instantiate
     * a new instance of this class.
     */
    public CSVArticle(){
        super();
    }

    /**
     * This construct is used to set all the fields when they are known to the
     * class that try to initialize an instance of CSVArticle.
     *
     * @param head       which is the head field of the article HTML page.
     * @param body       which is the body field of the article HTML page.
     * @param identifier which is the article identifier {@link CSVArticle#identifier}.
     * @param URL        which is the article HTML page URL {@link CSVArticle#URL}.
     * @param Date       which is the article web publication date {@link CSVArticle#Date}.
     * @param sourceSet  which is the group the published the article {@link CSVArticle#sourceSet}.
     * @param source     which is the article type {@link CSVArticle#source}.
     */
    public CSVArticle(String head, String body, String identifier, String URL, String Date, String sourceSet, String source) {
        super(head, body);
        this.identifier = identifier;
        this.URL = URL;
        this.Date = Date;
        this.sourceSet = sourceSet;
        this.source = source;
    }

    /* SETTERS */
    /**
     * Sets identifier {@link CSVArticle#identifier}.
     *
     * @param identifier which is the article identifier.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * Sets URL {@link CSVArticle#URL}.
     *
     * @param URL which is the article web page URL.
     */
    public void setURL(String URL) {
        this.URL = URL;
    }
    /**
     * Sets Date {@link CSVArticle#Date}.
     *
     * @param date which is the web publication date of the article.
     */
    public void setDate(String date) {
        Date = date;
    }
    /**
     * Sets source set {@link CSVArticle#sourceSet}.
     *
     * @param sourceSet which is the group the published the article.
     */
    public void setSourceSet(String sourceSet) {
        this.sourceSet = sourceSet;
    }
    /**
     * Sets source {@link CSVArticle#source}.
     *
     * @param source which is the article type.
     */
    public void setSource(String source) {
        this.source = source;
    }
    /**
     * Sets the body field of the article page HTML.
     *
     * @param body which is the body field af an Article page HTML.
     */
    public void setBody(String body){
        super.setBody(body);
    }
    /**
     * Sets the head field of the article page HTML.
     *
     * @param head which is the head field of an article page HTML.
     */
    public void setHead(String head){
        super.setHead(head);
    }

    /* GETTERS */
    /**
     * Gets source set {@link CSVArticle#sourceSet}.
     *
     * @return the source set.
     */
    public String getSourceSet() {
        return sourceSet;
    }
    /**
     * gets the body field of the article page HTML.
     *
     * @return body field of the article page HTML.
     */
    public String getBody(){
        return super.getBody();
    }
    /**
     * Gets URL {@link CSVArticle#URL}.
     *
     * @return the article web page URL.
     */
    public String getURL() {
        return URL;
    }
    /**
     * Gets the head field of the article page HTML.
     *
     * @return the head field of the article page HTML.
     */
    public String getHead(){
        return super.getHead();
    }
    /**
     * Gets source {@link CSVArticle#source}.
     *
     * @return the article type.
     */
    public String getSource() {
        return source;
    }
    /**
     * Gets date {@link CSVArticle#Date}.
     *
     * @return the article web publication date.
     */
    public String getDate() {
        return Date;
    }
    /**
     * Gets identifier {@link CSVArticle#identifier}.
     *
     * @return the article identifier.
     */
    public String getIdentifier() {
        return identifier;
    }
}
