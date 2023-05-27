package org.project;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * this class is a wrapper that contain another tokenizer ("core nlp") and allow to tokenize a give string of text into its words
 * giving the possibility to check punctuation and eventually getting a lexical/frequency ordered version to iterate through
 */
public class Tokenizer {
    private String str;
    private Properties properties;
    private CoreDocument document;
    private TreeMap<String,Integer> tokens;
    private StanfordCoreNLP pipeline;
    private boolean checks = false;
    private List<String> commonWords;
    private final String fileName = "res\\words\\words.txt";
    private final String regex = "[\\p{Punct}\\s.!?”“–—’‘'…+1234567890-]";
    /**
     * @param str1 initial pool of tokens
     * @param check1 which is used to check if there are common words or punctuation in the articles
     */
    public Tokenizer(String str1, boolean check1) {
        RedwoodConfiguration.current().clear().apply();
        str = str1;
        // set up pipeline properties
        properties = new Properties();

        // set the list of annotators to run
        properties.setProperty("annotators", "tokenize");

        // coref annotator is being set to use the neural algorithm
        properties.setProperty("coref.algorithm", "neural");

        //removing non recognized tokens
        properties.setProperty("tokenize.options", "untokenizable=noneDelete");
        // build pipeline
        pipeline = new StanfordCoreNLP(properties);

        // create a document object
        str = str1;
        document = pipeline.processToCoreDocument(str1);

        //save tokens in a map
        tokens = new TreeMap<String, Integer>();

        //setting checks
        checks = check1;
        commonWords = new ArrayList<String>();
        if(check1){
            try{
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String word = "";
                while((word = reader.readLine()) != null){
                    commonWords.add(word);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        enterTokens(str1);
    }

    /**
     * this method allow to print all the contained tokens by iterating through it
     */
    public void printTokens(){
        Iterator<Map.Entry<String,Integer>> iter = tokens.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String,Integer> pair = iter.next();
            System.out.println(pair.getKey()+" "+pair.getValue());
        }
    }

    /**
     * this method allow to switch the document this the object was initialized with
     * @param str1
     */
    public void switchDocument(String str1){
        str = str1;
        removeTokens();
        enterTokens(str1);
    }

    /**
     * this method allow to extends the content of the document the object was initialized with
     * @param str1
     */
    public void addDocument(String str1){
        str += str1;
        enterTokens(str1);
    }

    /**
     * this method remove the already contained tokens
     */
    public void removeTokens(){
        tokens = new TreeMap<String,Integer>();
    }

    /**
     * this method enables punctuation check
     */
    public void enableCheck(){
        checks = true;
    }

    /**
     * this method disables punctuation check
     */
    public void disableCheck(){
        checks = false;
    }

    /**
     * this method is used by this class to correctly enter tokens in the treemap
     * @param str1
     */
    public void enterTokens(String str1){
        //save string tokens in a list (without duplicates)
        document = pipeline.processToCoreDocument(str1);
        /*
        * This line combine multiple operation:
        * It first converts each element(corelabel) of the document produced by coreNLP tokenization into a string of lower case characters.
        * Then it split it using regex (see the definition in the finals fields definitions), this return an array of String.
        * At this point it convert the array into a list using "collect(Collectors.toList()". But in the end this would return an array.
        * So instead of considering an association between each corelabel to a list we consider multiple association between a corelabel and all
        * the tokens returned from the split operation (Strings).
        * But then in the end we would have an array of String, so, as we did before, we again convert the final big array into a list we the same method.
        *
        * */
        List<String> list = document.tokens().stream().map(coreLabel -> Arrays.stream(coreLabel.toString().toLowerCase().split(regex)).collect(Collectors.toList())).flatMap(List::stream).distinct().collect(Collectors.toList());
        //check document size
        if(list.size() == 0){
            return;
        }

        int value = 0;
        for (String c : list) {
            //splitting the string using common punctuation and numbers
                if (checks) {
                    //checks if the element in the list is not punctuation
                    if (!(Pattern.matches("\\p{Punct}", c) | Pattern.matches("[.!?”“–—’‘'…-]", c) | c.equals("") | commonWords.contains(c))) {
                        value = tokens.getOrDefault(c, 0);
                        tokens.put(c, value + 1);
                    }
                } else {
                    //return the value assciated with the string if present else it return 0
                    value = tokens.getOrDefault(c, 0);
                    //put a new string in the treemap else it add 1 to the actual value of the asscociated entry
                    tokens.put(c, value + 1);
                }
            }
        }



    /**
     * this method returns a Set of Ordered tokens building a reverse Treemap of list of String as values("words") and integers(frequency) as keys of the given size (if possible)
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
            //checks if the list is already contained
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
                        //System.out.println("here");
                        listPair.getValue().remove(index);
                    }
                    counter = maxSize;
                }
            }
        }

        //possible printing reverseMap calculated content
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
     * this method print the frequency of the given string (if it isn't contained it throws an exception)
     * @param str
     * @throws NullPointerException
     */
    public void printFrequency(String str) throws NullPointerException{
        int frequency = 0;
        frequency = tokens.get(str);
        System.out.println(frequency);
    }
}
