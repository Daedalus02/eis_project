package org.project;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class is used to represent an article with all properties af an article of the "The Guardian" media group.
 */
public final class APIArticle extends Article {
    /** This is the article identifier.*/
    private final String Id;
    /** This is the article type. */
    private final String type;
    /** This is the article section identifier. */
    private final String sectionId;
    /** This is the article section name. */
    private final String sectionName;
    /** This is the date of publication of the article. */
    private final String webPublicationDate;
    /** This is the name of the article. */
    private final String webTitle;
    /** This is the url of the article. */
    private final URL webUrl;
    /** This is the url of the API of the article. */
    private final URL apiUrl;
    /** This tells whether the article is hosted or not (always set to true if not specified). */
    private final boolean isHosted;
    /** This is the column identifier. */
    private final String pillarId;
    /** This is the name of the column the article is located at. */
    private final String pillarName;
    /** This is the number of words contained in the article. */
    private final int wordcount;


    /**
     * Initialises a new Api article setting its fields with the given parameters.
     *
     * @param head               the head parameter.
     * @param body               the body parameter.
     * @param Id                 the id parameter {@link APIArticle#Id}.
     * @param type               the type parameter {@link APIArticle#type}.
     * @param sectionId          the section id parameter {@link APIArticle#sectionId}.
     * @param sectionName        the section name parameter {@link APIArticle#sectionName}.
     * @param webPublicationDate the web publication date parameter {@link APIArticle#webPublicationDate}.
     * @param webTitle           the web title parameter {@link APIArticle#webTitle}.
     * @param webUrl             the web url parameter {@link APIArticle#webUrl} .
     * @param apiUrl             the api url parameter {@link APIArticle#apiUrl}.
     * @param isHosted           the is hosted parameter {@link APIArticle#isHosted}.
     * @param pillarId           the pillar id parameter {@link APIArticle#pillarId}.
     * @param pillarName         the pillar name parameter {@link APIArticle#pillarName}.
     * @param wordcount          the word count parameter {@link APIArticle#wordcount}.
     */
    @JsonCreator
    public APIArticle(@JsonProperty("head") String head,@JsonProperty("body") String body,@JsonProperty("Id") String Id,@JsonProperty("type") String type,
                      @JsonProperty("sectionId") String sectionId,@JsonProperty("sectionName") String sectionName,@JsonProperty("Date") String webPublicationDate,
                      @JsonProperty("webTitle") String webTitle,@JsonProperty("webUrl") URL webUrl,@JsonProperty("apiUrl") URL apiUrl,@JsonProperty("isHosted") boolean isHosted,
                      @JsonProperty("pillarId") String pillarId,@JsonProperty("pillarName") String pillarName,@JsonProperty("wordcount") Integer wordcount)  {
        super(head, body);
        this.Id = Id;
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
     * Gets word count {@link APIArticle#wordcount}.
     *
     * @return word count in the articles .
     */
    public int getWordcount() {
        return wordcount;
    }

    /**
     * Check if the parameter obj (which need to be an APIArticle) is a field by field copy of the class instance this method is invoked to.
     * NOTE: Since performance is not critical for this feature in this case a standard implementation is used.
     *
     * @param obj which should be an APIArticle object (checked).
     * @return true if the APIArticle obj has the same field values of the instance this method is invoked to, else it returns false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        APIArticle article = (APIArticle) obj;
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(getHead(), article.getHead());
        equalsBuilder.append(getBody(), article.getBody());
        equalsBuilder.append(isHosted, article.isHosted);
        equalsBuilder.append(wordcount, article.wordcount);
        equalsBuilder.append(Id, article.Id);
        equalsBuilder.append(type, article.type);
        equalsBuilder.append(sectionId, article.sectionId);
        equalsBuilder.append(sectionName, article.sectionName);
        equalsBuilder.append(webPublicationDate, article.webPublicationDate);
        equalsBuilder.append(webTitle, article.webTitle);
        equalsBuilder.append(webUrl, article.webUrl);
        equalsBuilder.append(apiUrl, article.apiUrl);
        equalsBuilder.append(pillarId, article.pillarId);
        equalsBuilder.append(pillarName, article.pillarName);
        return equalsBuilder.isEquals();
    }

    /**
     * This method return a hash code to eventually be able to enter APIArticles in a hash map.
     * NOTE: Since performance is not critical for this feature in this case a standard implementation is used.
     *
     * @return a hash code based on the private variables  ({@link APIArticle#Id}, {@link APIArticle#type},{@link APIArticle#sectionId},
     *         {@link APIArticle#sectionName}, {@link APIArticle#webPublicationDate}, {@link APIArticle#webTitle}, {@link APIArticle#webUrl},
     *         {@link APIArticle#apiUrl}, {@link APIArticle#isHosted}, {@link APIArticle#pillarId}, {@link APIArticle#pillarName}, {@link APIArticle#wordcount}).
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17,37);
        hashCodeBuilder.append(getBody());
        hashCodeBuilder.append(getHead());
        hashCodeBuilder.append(Id);
        hashCodeBuilder.append(type);
        hashCodeBuilder.append(sectionId);
        hashCodeBuilder.append(sectionName);
        hashCodeBuilder.append(webPublicationDate);
        hashCodeBuilder.append(webTitle);
        hashCodeBuilder.append(webUrl);
        hashCodeBuilder.append(apiUrl);
        hashCodeBuilder.append(isHosted);
        hashCodeBuilder.append(pillarId);
        hashCodeBuilder.append(pillarName);
        hashCodeBuilder.append(wordcount);
        return hashCodeBuilder.toHashCode();
    }
}
