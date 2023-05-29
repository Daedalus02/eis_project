package org.project;

import java.util.*;

/**
 * This class is used to keep the tokens associated with their frequency in entering operations.
 * It's capable of entering tokens and ordering them basing on their frequencies plus it has few more feature
 * like removing all tokens, printing them, printing a given token's frequency.
 */
public class TreeStorage implements TokensStorage{
    /** This map is used to store the tokens using them as indexes and using the associated values to store their frequency.*/
    private HashMap<String, Integer> tokens = null;

    /**
     * This constructor initialize an empty hash map {@link TreeStorage#tokens} capable of storing the tokens with {@link TreeStorage#enterTokens(List)}.
     */
    public TreeStorage() {
        // This initializes the maps containing the entries of tokens(String) as indexes and their frequency(int) as values.
        tokens = new HashMap<String, Integer>();
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
     * This method remove the tokens.
     */
    public void removeTokens(){
        tokens = new HashMap<String, Integer>();
    }

    /**
     * This method enter a new list of tokens in the hashmap used to store them {@link TreeStorage#tokens}.
     *
     * @param list which is a list of tokens to enter the storage.
     */
    @Override
    public void enterTokens(List<String> list) {
        // Check document size.
        if(list.size() == 0){
            return;
        }
        int value = 0;      // This variable store the frequency of the tokens to put inside of map
        for (String c : list) {
            // This line either get the previous frequency of the token (if it was present) or return 0.
            value = tokens.getOrDefault(c, 0);
            // Entering the token incrementing its frequency because it was present inside the new text entered.
            tokens.put(c, value + 1);
        }
    }

    @Override
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
