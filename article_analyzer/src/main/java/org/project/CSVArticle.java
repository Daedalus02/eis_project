package org.project;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import java.lang.reflect.Field;
import java.net.URL;

/**
 * This class represents the fields that are used in the CSV source to represent an Article.
 */
public final class CSVArticle extends Article{
    /** This is used to identify the article.*/
    private final String identifier;
    /** This is the URL to the article online page.*/
    private final URL URL;
    /** This is the date of publication of the article. */
    private final String Date;
    /** This is the group that published the article. */
    private final String sourceSet;
    /** This is used to store the article type (example: opinion, business, environment, ...).*/
    private final String source;

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
    @JsonCreator
    public CSVArticle(@JsonProperty("head") String head,@JsonProperty("body") String body,@JsonProperty("identifier") String identifier,
                      @JsonProperty("URL") URL URL,@JsonProperty("Date") String Date,@JsonProperty("sourceSet") String sourceSet,@JsonProperty("source") String source) {
        super(head, body);
        this.identifier = identifier;
        this.URL = URL;
        this.Date = Date;
        this.sourceSet = sourceSet;
        this.source = source;
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
     * Gets {@link CSVArticle#URL}.
     *
     * @return the article web page URL.
     */
    public URL getURL() {
        return URL;
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

    /**
     * Check if the parameter obj (which need to be an CSVArticle) is a field by field copy of the class instance this method is invoked to.
     *
     * @param obj which should be an CSVArticle object (checked).
     * @return  true if the CSVArticle obj has the same field values of the instance this method is invoked to, else it returns false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CSVArticle article = (CSVArticle) obj;
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(getBody(), article.getBody());
        equalsBuilder.append(getHead(), article.getHead());
        equalsBuilder.append(identifier, article.identifier);
        equalsBuilder.append(URL, article.URL);
        equalsBuilder.append(Date, article.Date);
        equalsBuilder.append(sourceSet, article.sourceSet);
        equalsBuilder.append(source, article.source);
        return equalsBuilder.isEquals();
    }

    /**
     * This method return a hash code to eventually be able to enter CSVArticles in a hash map.
     * NOTE: Since performance is not critical for this feature in this case a standard implementation is used.
     *
     * @return a hash code based one the private variables  ({@link CSVArticle#identifier}, {@link CSVArticle#URL},{@link CSVArticle#Date},
     *         {@link CSVArticle#sourceSet}, {@link CSVArticle#source}).
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
        hashCodeBuilder.append(getBody());
        hashCodeBuilder.append(getHead());
        hashCodeBuilder.append(identifier);
        hashCodeBuilder.append(URL);
        hashCodeBuilder.append(Date);
        hashCodeBuilder.append(sourceSet);
        hashCodeBuilder.append(source);
        return hashCodeBuilder.toHashCode();
    }
}
