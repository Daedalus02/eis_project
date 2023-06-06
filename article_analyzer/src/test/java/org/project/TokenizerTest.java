package org.project;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Tag("Backlog")
@Tag("CSV")
@Tag("API")
class TokenizerTest {
    private static final String RELATIVE_PATH = "test_resources" + File.separator + "articles" + File.separator;
    private static final String WORDS_PATH = "resources" + File.separator + "blacklist" + File.separator + "words.txt";
    private static String article1 = "";
    private static String article2 = "";
    private static String article3 = "";

    @BeforeAll
    static void readArticles() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(RELATIVE_PATH + "Article1.txt"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while((line = reader.readLine()) != null){
            buffer.append(line);
            buffer.append(" ");
        }
        article1 = buffer.toString();

        buffer = new StringBuffer();
        reader = new BufferedReader(new FileReader(RELATIVE_PATH + "Article2.txt"));
        while((line = reader.readLine()) != null){
            buffer.append(line);
            buffer.append(" ");
        }
        article2 = buffer.toString();

        buffer = new StringBuffer();
        reader = new BufferedReader(new FileReader(RELATIVE_PATH + "Article3.txt"));
        while((line = reader.readLine()) != null){
            buffer.append(line);
            buffer.append(" ");
        }
        article3 = buffer.toString();
    }
    @Test
    @DisplayName("Testing small string with checks.")
    void test1Tokenize()  {
        String string1 = "“We don’t know for sure if” side-effects from the leaks “will happen or when anything will" +
                         " happen but just the lack of transparency is very concerning said Jammes, a 36-year-old mother of four.";
        // Tokens : A-E {a,anything,but,concerning,do,effects,
        //          F-P  for,four,from,happen, if,is,jammes,just,know,lack,leaks,mother,n,of,old,or,
        //          R-Z  said,side,sure,t,the,transparency,very,we,when,will,year}
        String string2 = "In his budget speech, the chancellor announced a competition to co-fund small nuclear plants and hopes a new delivery body, " +
                         "great british nuclear, will ease the creation of nuclear projects.";
        // Tokens : A-E {a,and,announced,body,british,budget,chancellor,co,competition,creation,delivery,ease,
        //          F-P  fund,great,his,hopes, in,new, nuclear,of,plants,projects,
        //          R-Z  small,speech,the,to,will}
        String string3 = "At any rate, here again is the argument: nuclear energy provides the clean, climate-friendly energy we need." +
                         "Renewables such as wind and solar are important but progress on them is desperately slow and time is running out.";
        // Tokens : A-E {again,and,any,are,argument,as,at,but,clean,climate,desperately,energy,
        //          F-P  friendly,here, important,is,need,nuclear,on,out, progress,provides,
        //          R-Z  rate,renewables,running, slow,solar,such,the,them,time,we,wind}
        /* Analizing the three sorted list of tokens we notice that the common tokens are:
        *   *-* {A-E ; F-P ; R-Z}
        *   1-2 {a   ;    of   ; the,will}
        *   1-3 {but ;    is   ; the,we}
        *   2-3 {and ; nuclear ; the}
        *   *-*-* {A-E ; F-P ; R-Z}
        *   1-2-3 {  ;         ; the}
        *
        *  So terms with frequency one are all minus those with frequency 2 or 3. Terms with frequency 2 are those that appear in couples
        *  of tokens group minus those with a frequency 3.
        *
        *  1 : {year,wind,when,very,transparency,to,time,them,t,sure,such,speech,solar,small,slow,side,said,running,renewables,rate,
        *       provides,projects,progress,plants,out,or,on,old,new,need,n,mother,leaks,lack,know,just,jammes,in,important,if,hopes,
        *       his,here,happen,great,fund,from,friendly,four,for,energy,effects,ease,do,desperately,delivery,creation,concerning,competition,
        *       co,climate,clean,chancellor,budget,british,body,at,as,argument,are,anything,any,announced,again}
        *  2 : {will, we, nuclear, but, and, a}
        *  3 : {the}
        */
        List<String> list1 = Arrays.stream(new String[]{"year","wind","when","very","transparency","to","time","them","t","sure","such",
                "speech","solar","small","slow","side","said","running","renewables","rate","provides","projects","progress","plants","out",
                "or","on","old","new","need","n","mother","leaks","lack","know","just","jammes","in","important","if","hopes","his",
                "here","happen","great","fund","from","friendly","four","for","energy","effects","ease","do","desperately","delivery","creation",
                "concerning","competition","co","climate","clean","chancellor","budget","british","body","at","as","argument","are","anything","any",
                "announced","again"}).collect(Collectors.toList());
        List<String> list2 = Arrays.stream(new String[]{"will", "we", "of", "nuclear", "is", "but", "and", "a"}).collect(Collectors.toList());
        List<String> list3 = Arrays.stream(new String[]{"the"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(1,list1);
        expected.put(2,list2);
        expected.put(3,list3);
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(false,storage);
        tokenizer.tokenize(string1, new ArrayList<String>()) ;
        tokenizer.tokenize(string2, new ArrayList<String>());
        tokenizer.tokenize(string3, new ArrayList<String>());
        assertEquals(expected.entrySet(), storage.getOrderedTokens(83));
    }

    /**
     * This test consider three string as the tokenizer tokens source. The strings should be elaborated without checks
     * so the only operation out of the standard tokenization is the separation of the words that are composite with punctuation.
     * (example: "it's" --> "it" and "s"). After this operation the tokenizer should only count tokens (in lowercase format)
     * that happens to be in all the three strings.
     *
     * NOTE: If you want to follow the steps they are reported in  "res/res_test/steps.txt" file.
     */
    @Test
    @DisplayName("Tokenize small string without any checks.")
    void test2Tokenize() {
        TreeStorage storage = new TreeStorage();
        // NOTE: the tokenizer was set to not check punctuation, but it still split composite words(example: "it's" is treated like "it" and "s").
        Tokenizer tokenizer = new Tokenizer(false,storage);
        tokenizer.tokenize(article1, new ArrayList<String>());
        tokenizer.tokenize(article2, new ArrayList<String>());
        tokenizer.tokenize(article3, new ArrayList<String>());
        //assertEquals(expected.entrySet(), storage.getOrderedTokens(50));
    }
    @Test
    @DisplayName("Tokenize big strings with checks.")
    void test3Tokenize() {
        TreeStorage storage = new TreeStorage();
        // NOTE: the tokenizer was set to not check punctuation, but it still split composite words(example: "it's" is treated like "it" and "s").
        Tokenizer tokenizer = new Tokenizer(false,storage);
        tokenizer.tokenize(article1, new ArrayList<String>());
        tokenizer.tokenize(article2, new ArrayList<String>());
        tokenizer.tokenize(article3, new ArrayList<String>());
        List<String> list3 = Arrays.stream(new String[]{"will","which","way","was","very","up","to","this","they","the","that","such",
                "source","say","s","power","plants","out","one","on","of","nuclear","now","not","need","it","is","industry","in","have",
                "has","fukushima","from","fossil","for","first","energy","down","does","by","but","been","be","at","as","are","and","a",""}).collect(Collectors.toList());;
        List<String> list2 = Arrays.stream(new String[]{"across"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(3,list3);
        expected.put(2,list2);
        assertEquals(expected.entrySet(), storage.getOrderedTokens(50));
    }

    @Test
    @DisplayName("Tokenize big String without doing any checks.")
    void tokenizeWithoutChecks1(){
        TreeStorage storage = new TreeStorage();
        // NOTE: the tokenizer was set to not check punctuation, but it still split composite words(example: "it's" is treated like "it" and "s").
        Tokenizer tokenizer = new Tokenizer(true,storage);
        tokenizer.tokenize(article1, new ArrayList<String>());
        tokenizer.tokenize(article2, new ArrayList<String>());
        tokenizer.tokenize(article3, new ArrayList<String>());
        List<String> list3 = Arrays.stream(new String[]{"source","power","plants","nuclear","industry","fukushima","fossil","energy"}).collect(Collectors.toList());;
        List<String> list2 = Arrays.stream(new String[]{"safety","running","risk","remains","radioactive","questions","question","provide",
                "promise","prevent","potential","plant","north","mile","making","long","life","island","increasing","incidents","important","hope",
                "guardian","great","government","good","global","generations","general","fuels","fears","emissions","danger","crucial","country",
                "considered","commission","climate","clean","chornobyl","capacity","based"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(3,list3);
        expected.put(2,list2);
        assertEquals(expected.entrySet(), storage.getOrderedTokens(50));
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