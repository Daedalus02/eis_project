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

    /** This is the pipeline which divide the text processing into phases(here only one is used). */
    private StanfordCoreNLP pipeline = null;
    /** This boolean enables class methods to take punctuation and current words into account.*/
    private boolean checks = false;
    /** This List of String allow the methods to know which words should be considered when checking for common words.*/
    private List<String> commonWords = null;
    /** This variable stores the tokens and is capable of returning them in an ordered set. */
    private TokensStorage storage;
    /** This String contains the file where {@link Tokenizer#commonWords} are stored.*/
    private final String FILENAME = "res" + File.separator + "words" + File.separator + "words.txt";
    /** This String contains the regex for common punctuation plus few more characters that
     *  are used to split the tokens (see {@link TreeStorage#getOrderedTokens(int)}).*/
    private final String REGEX = "[\\p{Punct}\\s.!?”“–—’‘'…+1234567890-]";

    /**
     * This constructor is used to set whether to do checks on the tokens or not ({@link Tokenizer#checks}),
     * eventually setting common word ({@link Tokenizer#commonWords}), the string to initialize the pipeline with ({@link Tokenizer#str}),
     * the actual pipeline ({@link Tokenizer#pipeline}), the document containing tokens ({@link Tokenizer#document}),
     * the storage class {@link Tokenizer#storage}.
     *
     * @param str is a text containing the initial pool of tokens.
     * @param checks which is used to check if there are common words or punctuation in the tokens.
     * @param storage  which is a structure capable of storing tokens of returning them in an ordered set.
     */
    public Tokenizer(String str, boolean checks, TokensStorage storage) {
        // This configures the internal tokenize core NLP so that it won't print warning messages.
        RedwoodConfiguration.current().clear().apply();

        // Setting the text string
        this.str = str;

        // Setting the tokens storage
        this.storage = storage;

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
        tokenize(this.str);
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
     * This method is used by this class to correctly enter tokens in the map {@link Tokenizer#storage}.
     *
     * @param str which is the string containing text to add to the class variable to obtain a bigger set of tokens.
     */
    public void tokenize(String str){
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
        // Checks if the element in the list is not punctuation, a common word or an empty string.
        if(checks) {
            Iterator<String> iter = list.iterator();
            String elem;
            while (iter.hasNext()) {
                elem = iter.next();
                if ((Pattern.matches(REGEX, elem) | elem.equals("") | commonWords.contains(elem))) {
                    iter.remove();
                }
            }
        }
        // Entering tokens in the tokens storage.
        storage.enterTokens(list);
    }
}
