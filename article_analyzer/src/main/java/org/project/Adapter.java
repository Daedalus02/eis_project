package org.project;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class allow for any user of this project to elaborate the data in a different way to the one used by the Main class.
 */
public class Adapter {
    /** This variable is used to get the elaborated tokens. */
    private Tokenizer tokenizer;

    /**
     * This constructor just initialize the class variable tokenizer{@link Adapter#tokenizer} with the passed parameter.
     *
     * @param tokenizer which is a Tokenizer with all the stored tokens.
     */
    public Adapter(Tokenizer tokenizer){
        this.tokenizer = tokenizer;
    }

    /**
     * This returns a set of entry of String lists(words with same frequencies) as values and Integer(frequencies) as keys.
     *
     * @param MaxSize which specify the max accepted dimension of the ordered tokens in the returned set.
     * @return a set of String lists(words with same frequency) as values and Integer as keys(frequency).
     */
    public Set<Map.Entry<Integer, List<String>>> getOrderedTokens(int MaxSize){
        return tokenizer.getOrderedTokens(MaxSize);
    }
}