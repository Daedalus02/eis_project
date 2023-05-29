package org.project;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface is used to represent a general storage for Tokens. It's only features are to order tokens
 * basing on their frequency and entering them.
 */
public interface TokensStorage {

    /**
     * This method is used to enter tokens
     * */
    void enterTokens(List<String> tokens);

    /**
     * This method is used to return Tokens ordered by their frequency in entering operations.
     * Also associate how many entering operations contained a give tokens.
     *
     * @param maxSize which is the max dimension of the set of entries to return.
     * @return  a set of entries of integers(frequencies) as indexes and String
     *          List(tokens with same frequency) as values.
     */
    Set<Map.Entry<Integer, List<String>>> getOrderedTokens(int maxSize);
}
