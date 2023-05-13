package org.project;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * this class allow for any user of this project to elaborate the data in a different way to the one used by the internal tokenizer
 */
public class Adapter {
    private Tokenizer tokenizer;

    //setting the internal tokenizer
    public Adapter(Tokenizer tokenizer1){
        tokenizer = tokenizer1;
    }

    //this returns a set of entry of String lists(words with same frequencies) as values and Integer(frequencies) as keys
    public Set<Map.Entry<Integer, List<String>>> getOrderedTokens(int MaxSize){
        return tokenizer.getOrderedTokens(MaxSize);
    }
}