package org.project;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    @DisplayName("")
    void test2Tokenize() {
        String fable = " ‘The Hare and the Tortoise’. A hare was making fun of a tortoise for moving so slowly." +
                " The tortoise, tiring of the hare’s gibes about how slow he was on his feet, eventually challenged the hare to a race." +
                " ‘I’ll race you, hare,’ he said; ‘and I bet I’ll win the race.’ The hare agreed to this challenge, " +
                "and a fox was found who set the course of the race and to judge who had won at the end. When the race started, " +
                "the hare bounded off in front, making good progress. He was so far ahead of the tortoise that he decided he could " +
                "afford to stop and have a rest. The tortoise was so far behind that a little rest wouldn’t hurt!" +
                "However, the hare fell fast asleep, and as he lay sleeping, the tortoise continued to plod along at his slow pace. " +
                "In time, he reached the finish-line and won the race. When the hare woke up, he was annoyed at himself for falling asleep." +
                " So he ran off towards the finish-line as fast as his legs would carry him, but it was too late, as the tortoise had already won.";
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(fable,true,storage);
        List<String> list3 = new ArrayList<>();
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>();
        expected.add(new AbstractMap.SimpleEntry(1000,list1));
        assertEquals(storage.getOrderedTokens(50));
    }
    @Test
    @DisplayName("")
    void test3Tokenize() {
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