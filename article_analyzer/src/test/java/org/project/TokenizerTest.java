package org.project;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

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