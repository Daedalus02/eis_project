package org.project;

import java.util.List;

/**
 * This interface is used to represent a general Source of Articles.
 */
public interface ArticleSource {
    /**
     * This method is used by classes that needs the articles contained in a general Article source.
     *
     * @return List<Article> which is the List of Articles elaborated by the general Article source class.
     */
    List<? extends Article> getArticles();
}
