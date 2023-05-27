package org.project;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.util.List;

/**
 * This is a dummy class used to make Jackson capable to serialize a List of articles
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Articles {
    private List<Article> articleList = null;

    //setting the internal List of articles
    public Articles(List<Article> articles){
        articleList = articles;
    }

    //dummy constructor
    public Articles(){

    }

    //return the List of article it was set with
    public List<Article> getArticleList() {
        return articleList;
    }
}
