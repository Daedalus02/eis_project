package org.project;

import java.util.List;

/**
 * This interface is used to represent a general Source of Articles in the system.
 */
public interface articleSource {
    /**
     * This method is used by whoever needs the articles contained in a general Article source.
     * @return List<Article> which represent the response of the source when asked to return its Articles.
     */
    List<Article> getArticles();
}
