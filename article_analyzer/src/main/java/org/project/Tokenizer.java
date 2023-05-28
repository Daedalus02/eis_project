package org.project;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class is a wrapper that contain another tokenizer ("core NLP") from the package edu.stanford.mlp.pipeline
 * and allow to tokenize a given string of text into its words giving the possibility to check the presence of punctuation
 * and common words, eventually returning a lexically/frequency ordered version to iterate through.
 */
public class Tokenizer {
    /** This is the String that initially contains the entire text to process using this class.*/
    private String str = "";
    /** This is a wrapper built around an annotation representing a document.*/
    private CoreDocument document = null;
    /** This map is used to store the tokens using them as indexes and using the associated values to store their frequency.*/
    private HashMap<String, Integer> tokens = null;
    /** This is the pipeline which divide the text processing into phases(here only one is used). */
    private StanfordCoreNLP pipeline = null;
    /** This boolean enables class methods to take punctuation and current words into account.*/
    private boolean checks = false;
    /** This List of String allow the methods to know which words should be considered when checking for common words.*/
    private List<String> commonWords = null;
    /** This String contains the file where {@link Tokenizer#commonWords} are stored.*/
    private final String FILENAME = "res" + File.separator + "words" + File.separator + "words.txt";
    /** This String contains the regex for common punctuation plus few more characters that
     *  are used to split the tokens (see {@link Tokenizer#getOrderedTokens(int)}).*/
    private final String REGEX = "[\\p{Punct}\\s.!?”“–—’‘'…+1234567890-]";

    /**
     * This constructor is used to set whether to do checks on the tokens or not ({@link Tokenizer#checks}),
     * eventually setting common word ({@link Tokenizer#commonWords}), the string to initialize the pipeline with ({@link Tokenizer#str}),
     * the actual pipeline ({@link Tokenizer#pipeline}), the document containing tokens ({@link Tokenizer#document}).
     *
     * @param str is a text containing the initial pool of tokens.
     * @param checks which is used to check if there are common words or punctuation in the tokens.
     */
    public Tokenizer(String str, boolean checks) {
        RedwoodConfiguration.current().clear().apply();
        this.str = str;
        //SETTING PIPELINE PROPERTIES
        // This is a variables that store the properties of the core NLP pipeline.
         Properties properties = new Properties();

        // This set the pipeline to only tokenize the processed string.
        properties.setProperty("annotators", "tokenize");

        // This sets the pipeline algorithm to neural algorithm.
        properties.setProperty("coref.algorithm", "neural");

        // This remove the unrecognized tokens (some Unicodes are not processable).
        properties.setProperty("tokenize.options", "untokenizable=noneDelete");
        // This initializes the pipeline.
        pipeline = new StanfordCoreNLP(properties);

        // This process the string containing tokens.
        document = pipeline.processToCoreDocument(str);

        // This initializes the maps containing the entries of tokens(String) as indexes and their frequency(int) as values.
        tokens = new HashMap<String, Integer>();

        // This sets the possibility to do checks on the processed document.
        this.checks = checks;
        // Assigning values to the variable that store common words.
        if(this.checks){
            // This initializes the commonWords String List.
            commonWords = new ArrayList<String>();
            try{
                // Reading the common words from the file at FILENAME address.
                BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
                String word = "";
                while((word = reader.readLine()) != null){
                    commonWords.add(word);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        // Finally entering tokens inside the Maps.
        enterTokens(this.str);
    }

    /**
     * This method allow to print all the tokens with their "frequency" iterating through the map that contains them.
     */
    public void printTokens(){
        Iterator<Map.Entry<String,Integer>> iter = tokens.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String,Integer> pair = iter.next();
            System.out.println(pair.getKey()+" "+pair.getValue());
        }
    }

    /**
     * This method print the frequency of the given string (if it isn't contained it returns the value 0).
     *
     * @param str which is the String we want to search inside the tokens map.
     */
    public void printFrequency(String str) {
        int frequency = 0;
        frequency = tokens.getOrDefault(str,0);
        System.out.println(frequency);
    }

    /**
     * This method allow to switch the document the instance of the class was initialized with.
     * @param str
     */
    public void switchDocument(String str){
        this.str = str;
        removeTokens();
        enterTokens(this.str);
    }

    /**
     * This method remove the tokens.
     */
    public void removeTokens(){
        tokens = new HashMap<String, Integer>();
    }

    /**
     * This method enables punctuation and common words check for the future entered tokens by setting the check variable{@link Tokenizer#checks}.
     */
    public void enableCheck(){
        if(!checks){
            // This initializes the commonWords String List.
            commonWords = new ArrayList<String>();
            try{
                // Reading the common words from the file at FILENAME address.
                BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
                String word = "";
                while((word = reader.readLine()) != null){
                    commonWords.add(word);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        checks = true;
    }

    /**
     * This method disables punctuation and common words checks for the future entered tokens by setting the check variable{@link Tokenizer#checks}.
     */
    public void disableCheck(){
        checks = false;
    }

    /**
     * This method is used by this class to correctly enter tokens in the map {@link Tokenizer#tokens}.
     *
     * @param str which is the string containing text to add to the class variable to obtain a bigger set of tokens.
     */
    public void enterTokens(String str){
        //save string tokens in a list (without duplicates)
        document = pipeline.processToCoreDocument(str);
        //updating the content of the text containing tokens
        this.str += str;

        /*
        * The next lines operation combine multiple operation:
        * It first converts each element(coreLabel) of the document produced by coreNLP tokenization into a string of lower case characters.
        *
        * Then it split it using regex (see the definition in the finals fields definitions), this return an array of String.
        *
        * At this point it convert this array into a list using "collect(Collectors.toList()". But in the end this would return an array of Lists.
        *
        * So instead of considering only one association between each coreLabel and the list of its inner split tokens, we consider multiple association
        * between a coreLabel and all the inner tokens returned from the split operation (Strings).
        *
        * Again, in the end we would have an array of String, so, as we did before, we convert the final big array containing all the inner tokens of coreLabels
        * into a list with the same method.
        *
         */
        List<String> list = document.tokens().stream().map(coreLabel -> Arrays.stream(coreLabel.toString().toLowerCase().split(REGEX))
                .collect(Collectors.toList())).flatMap(List::stream).distinct().collect(Collectors.toList());

        // Check document size.
        if(list.size() == 0){
            return;
        }


        int value = 0;      // This variable store the frequency of the tokens to put inside of map
        for (String c : list) {
            // Splitting the string using common punctuation and numbers.
                if (checks) {
                    // Checks if the element in the list is not punctuation, a common word or an empty string.
                    if (!(Pattern.matches("\\p{Punct}", c) | Pattern.matches("[.!?”“–—’‘'…-]", c) | c.equals("") | commonWords.contains(c))) {
                        value = tokens.getOrDefault(c, 0);      // This line either get the previous frequency of the token (if it was present) or return 0.
                        tokens.put(c, value + 1);       // Entering the token incrementing its frequency because it was present inside the new text entered.
                    }
                } else {
                    // This line either get the previous frequency of the token (if it was present) or return 0.
                    value = tokens.getOrDefault(c, 0);
                    // Entering the token incrementing its frequency because it was present inside the new text entered.
                    tokens.put(c, value + 1);
                }
            }
        }



    /**
     * This method returns a Set of Ordered entries of Integers and String Lists by building a Treemap (with reverse ordering pf entries)
     * of list of String as values("words") and integers(frequency) as keys with the given size (if enough tokens are present).
     *
     * @param maxSize which is the max quantity of tokens present in the returned map.
     * @return a TreeMap which contains the entries of tokens values indexed with their frequency.
     */
    public Set<Map.Entry<Integer, List<String>>> getOrderedTokens(int maxSize){

        // Initializing the new Map with reverse order (having the bigger integer keys at the top of the balanced Three of ThreeMap).
        SortedMap<Integer, List<String>> reverseMap = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        Iterator<Map.Entry<String,Integer>> iter = tokens.entrySet().iterator();
        Map.Entry<String,Integer> pair;

        /* Building the reverseMap iterating through the HashMap used in this class to store frequency-tokens entries. */
        List<String> stringList;
        while(iter.hasNext()){
            pair = iter.next();
            stringList = reverseMap.get(pair.getValue());       // This checks if the list is already contained.
            // If there was no List with that specific frequency a new one is created and initialized with the current token.
            if(stringList == null){
                stringList = new ArrayList<String>();
                stringList.add(pair.getKey());
                reverseMap.put(pair.getValue(),stringList);
            }else{
                // If a List with that specific frequency was present then we only add the current toke to it.
                reverseMap.get(pair.getValue()).add(pair.getKey());
            }
        }

        /* Reducing size to maxSize tokens or less the number of tokens entered inside the reverse map.
        *  Done by iterating through the map and removing all the eventual exceeding tokens. */
        int counter = 0;        // This variable is used to keep track of the number of tokens encountered.
        Iterator<Map.Entry<Integer, List<String>>> listIter = reverseMap.entrySet().iterator();
        Map.Entry<Integer, List<String>> listPair;
        boolean max = false;        // This check if we already reached the max possible number of tokens.
        while(listIter.hasNext()){
            listPair = listIter.next();
            if(!max) {
                // Ordering the list of string contained in each entry with lexicographical order.
                Collections.sort(listPair.getValue(), Collections.reverseOrder());
                counter += listPair.getValue().size();      // Add the dimension of the current String List to the counter.
                if (counter > maxSize) {
                    int index = counter - maxSize;
                    while (index > 0) {      // Removing eventual exceeding tokens from the List.
                        index--;
                        listPair.getValue().remove(index);
                    }
                    max = true;
                    counter = maxSize;
                }
            }else { // If we already got the max number of tokens we start removing the exceeding Lists.
                listIter.remove();
            }
        }

        // Finally returning the correct size set of ordered entries.
        return reverseMap.entrySet();
    }

}
