package org.example;

import java.util.StringTokenizer;
import edu.stanford.nlp.pipeline.*;
import java.util.*;
import edu.stanford.nlp.ling.*;

public class Tokenizer {
    private String str;
    private Properties properties;
    private CoreDocument document;
    private TreeMap<String,Integer> tokens;
    private StanfordCoreNLP pipeline;
    private Map.Entry<String, Integer> max;
    public Tokenizer(String str1){
        str = str1;
        // set up pipeline properties
        properties = new Properties();
        // set the list of annotators to run
        properties.setProperty("annotators", "tokenize");
        // coref annotator is being set to use the neural algorithm
        properties.setProperty("coref.algorithm", "neural");
        // build pipeline
        pipeline = new StanfordCoreNLP(properties);
        // create a document object
        switchDocument(str1);
        //save tokens in a map
        tokens = new TreeMap<String, Integer>();
        max = new AbstractMap.SimpleEntry<String,Integer>("",0);
        enterTokens();
    }
    public void printTokens(){
        /*for(Map.Entry<String,Integer> element : tokens.entrySet()){
            System.out.println(element.getKey()+" "+element.getValue());
        }*/
        System.out.println(tokens);
        Iterator<Map.Entry<String,Integer>> iter = tokens.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String,Integer> pair = iter.next();
            System.out.println(pair.getKey()+" "+pair.getValue());
        }
    }
    public void printFirst(){
        System.out.println(max.getKey()+" "+max.getValue());
    }
    public void switchDocument(String str1){
        str = str1;
        document = pipeline.processToCoreDocument(str1);
        removeTokens();
        enterTokens();
    }
    public void removeTokens(){
        tokens = new TreeMap<String,Integer>();
        max = new AbstractMap.SimpleEntry<String,Integer>("",0);
    }
    public void enterTokens() {
        List<CoreLabel> list = document.tokens();
        int value = 0;
        for (CoreLabel c :list){
            value = tokens.getOrDefault(c.toString(),0);
            tokens.put(c.toString(),value+1);
            if(value > max.getValue()){
                max = new AbstractMap.SimpleEntry<String,Integer>(c.toString(),value);
            }
        }
    }
    public void printFrequency(String str) throws NullPointerException{
        int frequency = 0;
        frequency = tokens.get(str);
        System.out.println(frequency);
    }
}
