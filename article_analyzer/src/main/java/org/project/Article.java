package org.project;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import java.lang.reflect.Field;
import java.util.Objects;


/**
 * This abstract class is the general representation of an Article. It only contains the body and head fields of its
 * HTML page, allowing to set and get their values.
 */

// This annotation allow this abstract class to store the actual class of the object when serialized.
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class Article {
    /** This is the content of the article page head field in its HTML. */
    private final String head;
    /** This is the content of the article page body field in its HTML. */
    private final String body;

    /**
     * This constructor takes as argument the values of the head and body using them to set the Article.
     * It also checks if the article type is a valid article.
     *
     * @param head        which is the value of the variable {@link Article#head}.
     * @param body        which is the value of the variable {@link Article#body}.
     */
    @JsonCreator
    public Article(@JsonProperty("head") String head,@JsonProperty("body") String body){
        this.body = body;
        this.head = head;
    }

    /* GETTERS */
    /**
     * Gets head {@link  Article#head}.
     *
     * @return the head field of the article.
     */
    public String getHead() {
        return head;
    }
    /**
     * Gets body {@link Article#body}.
     *
     * @return the body field of the article.
     */
    public String getBody() {
        return body;
    }
}
