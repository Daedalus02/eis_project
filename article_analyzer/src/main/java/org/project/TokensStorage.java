package org.project;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface is used to represent a generic storage for Tokens. Its only features are to order tokens
 * based on their frequency and inserting them.
 */
public interface TokensStorage {

    /**
     * This method is used to insert tokens
     * */
    void enterTokens(List<String> tokens);

    /**
     * This method is used to return Tokens ordered by their frequency in insertion operations.
     * Also associates how many insertion operations uses a token.
     *
     * @param maxSize which is the max dimension of the set of entries to return.
     * @return  a set of entries of integers (frequencies) as indexes and String
     *          List(tokens with same frequency) as values.
     */
    Set<Map.Entry<Integer, List<String>>> getOrderedTokens(int maxSize);
}
