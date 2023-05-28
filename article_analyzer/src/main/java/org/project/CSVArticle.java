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
    /** This is used to store.*/
    private String source;

    /**
     *
     * */
    public CSVArticle(){
        super();
    }
    public CSVArticle(String head, String body, String identifier, String URL, String Date, String sourceSet, String source) throws ClassNotFoundException {
        super(head, body);
        this.identifier = identifier;
        this.URL = URL;
        this.Date = Date;
        this.sourceSet = sourceSet;
        this.source = source;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getURL() {
        return URL;
    }

    /**
     * Sets url.
     *
     * @param URL the url
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return Date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        Date = date;
    }

    /**
     * Gets source set.
     *
     * @return the source set
     */
    public String getSourceSet() {
        return sourceSet;
    }

    /**
     * Sets source set.
     *
     * @param sourceSet the ource set
     */
    public void setSourceSet(String sourceSet) {
        this.sourceSet = sourceSet;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets source.
     *
     * @param source the source
     */
    public void setSource(String source) {
        this.source = source;
    }

    public void setBody(String body){
        super.setBody(body);
    }
    public String getBody(){
        return super.getBody();
    }
    public String getHead(){
        return super.getHead();
    }
    public void setHead(String head){
        super.setHead(head);
    }
}
