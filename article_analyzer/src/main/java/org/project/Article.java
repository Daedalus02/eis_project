package org.project;

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
    private String head = "";
    /** This is the content of the article page body field in its HTML. */
    private String body = "";

    /**
     * This constructor is just a dummy constructor needed by other classes.
     */
    public Article(){
    }
    /**
     * This constructor takes as argument the values of the head and body using them to set the Article.
     * It also checks if the article type is a valid article.
     *
     * @param head        which is the value of the variable {@link Article#head}.
     * @param body        which is the value of the variable {@link Article#body}.
     */
    public Article(String head, String body){
        this.body = body;
        this.head = head;
    }

    /**
     * This method return a hash code to be able to eventually enter Articles in a hash map.
     * NOTE: Since performance is not critical for this feature in this case a standard implementation is used.
     *
     * @return a hash code based one the private variables head and body ({@link Article#body}, {@link Article#head}).
     */
    @Override
    public int hashCode() {
        final int primeNumber = 31;
        int hash = primeNumber;
        hash = hash * primeNumber + (head != null ? head.hashCode() : 0 );
        hash = hash * primeNumber + (body != null ? body.hashCode() : 0 );
        return hash;
    }
    /**
     * Check if the parameter obj (which need to be an Article) is a field by field copy of the class instance this method is invoked to.
     *
     * @param obj which should be an Article object (checked).
     * @return  true if the Article obj has the same field values of the instance this method is invoked to, else it returns false.
     */
    @Override
    public boolean equals(Object obj)  {
        if (obj == this) return true;
        if (obj.getClass() == getClass() && (obj != null)) {
            Article article = (Article) obj;
            Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (!field.get(this).equals(field.get(article))) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
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

    /* SETTERS */

    /**
     * Sets {@link Article#head}.
     *
     * @param head which is the head field of an article.
     */
    public void setHead(String head) {
        this.head = head;
    }
    /**
     * Sets {@link Article#body}.
     *
     * @param body which is the body field af an article.
     */
    public void setBody(String body) {
        this.body = body;
    }

}
