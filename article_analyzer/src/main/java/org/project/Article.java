package org.project;

import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * This abstract class is the general representation of an Article. It only contains the body and head field of it's
 * HTML page, allowing to set and get their values.
 */

// This annotation allow this abstract class to store the actual class of the object when serialized.
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class Article {
    /** This is the content of the article page head field in its HTML. */
    private String head = "";
    /** This is the content of the article page body field in its HTML. */
    private String body = "";

    /**
     * This constructor is just a dummy constructor needed by other classes.
     */
    public Article(){
    }
    /**
     * This constructor take as argument the values of the head and body to set the Article with,
     * also check if the article type is a valid article.
     *
     * @param head        which is the value the class variable head {@link Article#head} is set with.
     * @param body        which is the value the class variable body {@link Article#body} is set with.
     */
    public Article(String head, String body){
        this.body = body;
        this.head = head;
    }

    /* GETTERS */
    /**
     * Gets head {@link  Article#head}.
     *
     * @return the head field of the article page HTML.
     */
    public String getHead() {
        return head;
    }
    /**
     * Gets body {@link Article#body}.
     *
     * @return the body field of the article page HTML.
     */
    public String getBody() {
        return body;
    }

    /* SETTERS */

    /**
     * Sets head {@link Article#head}.
     *
     * @param head which is the head field of an article page HTML.
     */
    public void setHead(String head) {
        this.head = head;
    }
    /**
     * Sets body {@link Article#body}.
     *
     * @param body which is the body field af an Article page HTML .
     */
    public void setBody(String body) {
        this.body = body;
    }

}
