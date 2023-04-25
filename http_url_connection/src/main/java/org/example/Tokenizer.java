package org.example;

import java.util.StringTokenizer;

public class Tokenizer {
    private String str;
    public StringTokenizer tokenizer;
    public Tokenizer(String str1){
        str = str1;
        tokenizer = new StringTokenizer(str);
    }
}
