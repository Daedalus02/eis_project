package org.project;

import java.util.List;

/**
 * This interface is used to represent a general source of articles.
 */
public interface ArticleSource {
    /**
     * This method is used by classes that needs the articles contained in a generic source.
     *
     * @return Article List which is the List of Articles elaborated by the generic source.
     */
    List<? extends Article> getArticles();
}
