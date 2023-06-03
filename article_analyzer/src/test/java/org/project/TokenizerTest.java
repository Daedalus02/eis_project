package org.project;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class TokenizerTest {
    private static final String RELATIVE_PATH = "res" + File.separator + "test_res" + File.separator + "pages" + File.separator;
    private static final String WORDS_PATH = "res" + File.separator + "words" + File.separator + "words.txt";
    private static String article1 = "";
    private static String article2 = "";
    private static String article3 = "";

    @BeforeAll
    static void readArticles() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(RELATIVE_PATH + "Article1Test.txt"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while((line = reader.readLine()) != null){
            buffer.append(line);
        }
        article1 = buffer.toString();

        buffer = new StringBuffer();
        reader = new BufferedReader(new FileReader(RELATIVE_PATH + "Article2Test.txt"));
        while((line = reader.readLine()) != null){
            buffer.append(line);
        }
        article2 = buffer.toString();

        buffer = new StringBuffer();
        reader = new BufferedReader(new FileReader(RELATIVE_PATH + "Article3Test.txt"));
        while((line = reader.readLine()) != null){
            buffer.append(line);
        }
        article2 = buffer.toString();
    }
    @Test
    @DisplayName("Testing to see if the tokenizer actually fill the storage with tokens.")
    void test1Tokenize()  {
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer("",true,storage);
        tokenizer.tokenize(article1);
        tokenizer.tokenize(article2);
        tokenizer.tokenize(article3);
        assertFalse(storage.isEmpty());
    }

    /**
     * This test consider three string as the tokenizer tokens source. The strings should be elaborated without checks
     * so the only operation out of the standard tokenization is the separation of the words that are composite with punctuation.
     * (example: "it's" --> "it" and "s"). After this operation the tokenizer should only count tokens (in lowercase format)
     * that happens to be in all the three strings.
     *
     * NOTE: If you want to follow the steps they are reported in  "res/res_test/steps.txt" file.
     */
    @Test
    @DisplayName("Tokenize big string without any check.")
    void test2Tokenize() {
        TreeStorage storage = new TreeStorage();
        // NOTE: the tokenizer was set to not check punctuation, but it still split composite words(example: "it's" is treated like "it" and "s").
        Tokenizer tokenizer = new Tokenizer("",false,storage);
        tokenizer.tokenize(article1);
        tokenizer.tokenize(article2);
        tokenizer.tokenize(article3);
        List<String> list1 = Arrays.stream(new String[]{"was","to","the","that","said","on","of","in","him","had","could","but","and","a"}).collect(Collectors.toList());
        List<String> list2 = Arrays.stream(new String[]{"you","would","who","when","way","upon","up","took","time","this","them","their",
                                                        "t","so","s","off","n","it","i","his","he","have","good","fox","for","fast","by",
                                                        "away","at","as","annoyed",""}).collect(Collectors.toList());;
        List<String> list3 = Arrays.stream(new String[]{"again","after","afford","about"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(3,list1);
        expected.put(2,list2);
        expected.put(1,list3);
        System.out.println(storage.getOrderedTokens(50));
        //assertEquals(expected.entrySet(), storage.getOrderedTokens(50));
    }
    @Test
    @DisplayName("Tokenize big strings with checks.")
    void test3Tokenize() {
        TreeStorage storage = new TreeStorage();
        // NOTE: the tokenizer was set to not check punctuation, but it still split composite words(example: "it's" is treated like "it" and "s").
        Tokenizer tokenizer = new Tokenizer("",true,storage);
        tokenizer.tokenize(article1);
        tokenizer.tokenize(article2);
        tokenizer.tokenize(article3);
        List<String> list2 = Arrays.stream(new String[]{"time","good","fox","fast","annoyed"}).collect(Collectors.toList());;
        List<String> list1 = Arrays.stream(new String[]{"finish", "fill", "fell", "feet", "feel", "feasting",
                                            "falling", "eventually", "entangled", "enjoyed", "end", "eat", "driving", "drive", "discontented", "dignity", "despising",
                "deputation", "deepest", "decided", "death", "crossing", "continued", "contempt", "challenged", "challenge", "catch", "cast", "carry", "bush", "bounded", "bolder",
                "blood", "bleed", "bet", "begged", "began", "bad", "asleep", "asked", "arrived", "appetite", "ahead", "agreed", "afford"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(1,list1);
        expected.put(2,list2);
        System.out.println(storage.getOrderedTokens(50));
        //assertEquals(expected.entrySet(), storage.getOrderedTokens(50));
    }

    @Test
    @DisplayName("Tokenize small String without doing any checks.")
    void tokenizeWithoutChecks1(){
        String phrase1 = "It was impossible to with all that ";
    }

    @Test
    @DisplayName("")
    void enableCheck() {
    }

    @Test
    @DisplayName("")
    void disableCheck() {
    }
}