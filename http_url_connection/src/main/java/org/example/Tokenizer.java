package org.example;

import edu.stanford.nlp.pipeline.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import edu.stanford.nlp.ling.*;

public class Tokenizer {
    private String str;
    private Properties properties;
    private CoreDocument document;
    private TreeMap<String,Integer> tokens;
    private StanfordCoreNLP pipeline;
    private boolean checkPunctuation = false;

    /**
     *
     * @param str1
     * @param check
     */
    public Tokenizer(String str1, boolean check) {
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
        str = str1;
        document = pipeline.processToCoreDocument(str1);

        //save tokens in a map
        tokens = new TreeMap<String, Integer>();
        checkPunctuation = check;
        enterTokens(str1);
    }

    /**
     *
     */
    public void printTokens(){
        System.out.println(tokens);
        Iterator<Map.Entry<String,Integer>> iter = tokens.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String,Integer> pair = iter.next();
            System.out.println(pair.getKey()+" "+pair.getValue());
        }
    }

    /**
     *
     * @param str1
     */
    public void switchDocument(String str1){
        str = str1;
        removeTokens();
        enterTokens(str1);
    }

    /**
     *
     * @param str1
     */
    public void addDocument(String str1){
        str += str1;
        enterTokens(str1);
    }

    /**
     *
     */
    public void removeTokens(){
        tokens = new TreeMap<String,Integer>();
    }

    /**
     *
     */

    public void enableCheck(){
        checkPunctuation = true;
    }

    /**
     *
     */
    public void disableCheck(){
        checkPunctuation = false;
    }

    /**
     *
     * @param str1
     */
    private void enterTokens(String str1){
        //save string tokens in a list (without duplicates)
        document = pipeline.processToCoreDocument(str1);
        List<String> list = document.tokens().stream().map(coreLabel -> coreLabel.toString().toLowerCase()).distinct().collect(Collectors.toList());

        if(list.size() == 0){
            return;
        }

        int value = 0;
        for (String c : list){
            if(checkPunctuation) {
                if (!Pattern.matches("\\p{Punct}", c)) {
                    value = tokens.getOrDefault(c, 0);
                    tokens.put(c, value+1);
                }
            }else{
                value = tokens.getOrDefault(c, 0);
                tokens.put(c, value + 1);
            }
        }
        //System.out.println(tokens.size());
    }

    /**
     *
     * @param maxSize
     * @return
     */
    public Set<Map.Entry<Integer, List<String>>> getOrderedTokens(int maxSize){

        SortedMap<Integer, List<String>> reverseMap = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        Iterator<Map.Entry<String,Integer>> iter = tokens.entrySet().iterator();
        Map.Entry<String,Integer> pair;

        //calculating reverseMap
        List<String> stringList;
        while(iter.hasNext()){
            pair = iter.next();

            stringList = reverseMap.get(pair.getValue());
            if(stringList == null){
                stringList = new ArrayList<String>();
                stringList.add(pair.getKey());
                reverseMap.put(pair.getValue(),stringList);
            }else{
                reverseMap.get(pair.getValue()).add(pair.getKey());
            }

        }

        //reducing size to maxSize words in total
        int counter = 0;
        Iterator<Map.Entry<Integer, List<String>>> listIter = reverseMap.entrySet().iterator();
        Map.Entry<Integer, List<String>> listPair;
        while(listIter.hasNext()){
            listPair = listIter.next();
            Collections.sort(listPair.getValue(),Collections.reverseOrder());
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

    /**
     *
     * @param str
     * @throws NullPointerException
     */
    public void printFrequency(String str) throws NullPointerException{
        int frequency = 0;
        frequency = tokens.get(str);
        System.out.println(frequency);
    }
}
