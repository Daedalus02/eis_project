package org.example;

import edu.stanford.nlp.pipeline.*;
import java.util.*;
import java.util.regex.Pattern;
import edu.stanford.nlp.ling.*;

public class Tokenizer {
    private String str;
    private Properties properties;
    private CoreDocument document;
    private TreeMap<String,Integer> tokens;
    private StanfordCoreNLP pipeline;
    private Map.Entry<String, Integer> max;
    private boolean checkPunctuation = false;

    public Tokenizer(String str1, boolean check){
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
        checkPunctuation = check;
        enterTokens();
    }
    public void printTokens(){
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
    public void enableCheck(){
        checkPunctuation = true;
    }
    public void disableCheck(){
        checkPunctuation = false;
    }
    public void enterTokens() {
        List<CoreLabel> list = document.tokens();
        int value = 0;
        for (CoreLabel c :list){
            if(checkPunctuation) {
                if (!Pattern.matches("\\p{Punct}", c.toString())) {
                    value = tokens.getOrDefault(c.toString(), 0);
                    tokens.put(c.toString(), value + 1);
                    if (value > max.getValue()) {
                        max = new AbstractMap.SimpleEntry<String, Integer>(c.toString(), value+1);
                    }
                }
            }else{
                value = tokens.getOrDefault(c.toString(), 0);
                tokens.put(c.toString(), value + 1);
                if (value > max.getValue()) {
                    max = new AbstractMap.SimpleEntry<String, Integer>(c.toString(), value);
                }
            }
        }
    }
    public Set<Map.Entry<Integer, List<String>>> getOrderedTokens(int maxSize){

        SortedMap<Integer, List<String>> reverseMap = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        Iterator<Map.Entry<String,Integer>> iter = tokens.entrySet().iterator();
        Map.Entry<String,Integer> pair;

        //calculating reverseMap
        List<String> list;
        while(iter.hasNext()){
            pair = iter.next();

            list = reverseMap.get(pair.getValue());
            if(list == null){
                list = new ArrayList<String>();
                list.add(pair.getKey());
                reverseMap.put(pair.getValue(),list);
            }else{
                reverseMap.get(pair.getValue()).add(pair.getKey());
            }

        }

        //reducing size to 50 words in total
        int counter = 0;
        Iterator<Map.Entry<Integer, List<String>>> listIter = reverseMap.entrySet().iterator();
        Map.Entry<Integer, List<String>> listPair;
        while(listIter.hasNext()){
            listPair = listIter.next();
            if(counter > maxSize){
                listIter.remove();
            }else {
                counter += listPair.getValue().size();
                if(counter > maxSize){
                    int index = counter - maxSize;
                    while(index > 0) {
                        index--;
                        listPair.getValue().remove(index);
                    }
                    counter = maxSize;
                }
            }
        }

        //printing reverseMap calculated content
        /*int currentFreq = 0;
        int currentSize = 0;
        while(!reverseMap.isEmpty()){
            currentFreq = reverseMap.lastKey();
            currentSize = reverseMap.get(reverseMap.lastKey()).size();
            for(int i = (currentSize-1); i >= 0; i--){
                System.out.println(reverseMap.get(reverseMap.lastKey()).remove(i)+" "+currentFreq);
            }
            reverseMap.remove(currentFreq);
        }*/
        return reverseMap.entrySet();
    }
    public void printFrequency(String str) throws NullPointerException{
        int frequency = 0;
        frequency = tokens.get(str);
        System.out.println(frequency);
    }
}
