package org.project;

import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.cli.*;
import org.json.JSONException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    /** Relative path of the old serialized research. */
    private static final String FILE_PATH = "resources" + File.separator + "backlog" + File.separator;
    /** Extension of the serialized file. */
    private static final String FILE_EXTENSION = ".json";
    /** Relative path to where the CSV files are stored. */
    private static final String CSV_PATH = "resources" + File.separator + "CSV_sources" + File.separator;
    /** Extension of CSV formatted file. */
    private static final String CSV_EXSTENSION = ".csv";
    /** Complete file address where the default API key can be found. */
    private static final String API_FILE = "resources" + File.separator + "private" + File.separator + "private.properties";

    public static void main(String Args[]) {

        // SETTING ALL THE "COMMON" VARIABLE NEEDED DURING EXECUTION.
        Scanner console = new Scanner(System.in);
        List<Article> articles = new ArrayList<Article>();      // Store the Articles read from different possible sources.
        String fileName = FILE_PATH + "test" + FILE_EXTENSION;      // Standard file name (only used when not specified).
        TokensStorage storage = new TreeStorage();      // Holds the tokens and is capable of returning them in an ordered set.
        Tokenizer tokenizer = new Tokenizer(true,storage);      //Used to tokenize articles in their different tokens checking them.
        Deserializer deserializer = new Deserializer();     // Used to deserialize Articles from the file they were previously serialized in.
        String pageText = "";       //Text contained in both the head and the body fields of an Article.
        String downloadAnswer = "n";        // y(yes) if the user decides to visualize the 50 more frequent tokens.
        String csvAnswer = "n";         // y(yes) if the user decides to read articles from a CSV file.
        String dataAnswer = "n";        // y(yes) if the user decides to read articles from previous researches.
        String csvName = "";        // Stores the name of the CSV file if the user want to read articles from a CSV file.
        String apiKey = "";         // API key of the user to be able to log to the "The Guardian" API endpoint.
        int maxArticle = 0;     // Max accepted number of articles to elaborate.
        List<String> queries = new ArrayList<String>();     // Queries to consider when doing research in the "The Guardian" APi endpoint response.
        List<String> tags = new ArrayList<String>();        // Tags to consider when doing research in the "The Guardian" APi endpoint response.
        ArticleSource source;       // General Article source (example: CSV file, API endpoint response).

        /*
         * Input structure from terminal:
         *
         * java -cp target/article_analyzer-1.0-jar-with-dependencies.jar org.project.Main -source ...
         * -If source is file:
         *      ... -file fileName -store storingFileName(optional)
         * -If source is The Guardian api:
         *      ... -api -queries query1 query2 queryN -tags(optional) tag1 tag2 tagN -max max_number_of_articles
         *          -apiKey(optional if entered in apikey properties file) your_api_Key -store storingFileName(optional)
         * -If the source is CSV:
         *      ... -csv filename -store storingFileName(optional)
         *
         * The implicit file path for csv is res/csv/ and for old research file is res/pages
         * (NOTE apiKey, store, tags, queries are optional.)
         * */

        //COMMAND LINE INPUT
        if(Args.length != 0){
            // Initializing new sets of option.
            Options options = new Options();

            // Setting a group of mutually exclusive options.
            OptionGroup actions = new OptionGroup();
            // Display help to use the command line input.
            Option hOption = new Option("h", "help", false, "Prints the help.");
            // Option for setting the API endpoint response analysis parameters.
            Option APIOption = new Option("api","read-api",false,"Reads article from API response.");
            // Option for setting the CSV file analysis.
            Option CSVOption = new Option("csv","read-csv",false,"Reads article from CSV file.");
            // Option for setting the previously stored articles analysis.
            Option fileOption = new Option("file", "read-file", false, "Reads article from previous research.");
            // Adding the options to the mutually exclusive group.
            actions.addOption(hOption);
            actions.addOption(APIOption);
            actions.addOption(CSVOption);
            actions.addOption(fileOption);

            // Other possible options for the different types of Article sources:
            Option maxOption = new Option("max", "max-articles",true, "The max number of articles to elaborate.");
            Option tagsOption = new Option("tags", "tags", true, "The tags to search for in the articles research.");
            tagsOption.setArgs(Option.UNLIMITED_VALUES);
            Option queriesOption = new Option("queries", "queries", true, "The queries to use for parsing the articles research.");
            queriesOption.setArgs(Option.UNLIMITED_VALUES);
            Option storeOption = new Option("store", "store",true,"The name of the file to store the articles in.");
            Option keyOption = new Option("apikey","api-key", true,"The value of the api key to use to connect to the \"The Guardian\" endpoint.");
            Option showOption = new Option("show","show",false,"Print the 50(or less) most frequent words in the read articles.");
            Option nameOption = new Option("name","csv-file-name",true,"The name of the CSV file.");
            Option jsonOption = new Option("json","json-file-name",true,"The name of the file where previously stored a research.");
            Option defaultOption = new Option("default","set-default",false,"This set the entered apikey as the default.");
            // Adding the parameters options to the possible set of options.
            options.addOptionGroup(actions);
            options.addOption(maxOption);
            options.addOption(tagsOption);
            options.addOption(queriesOption);
            options.addOption(keyOption);
            options.addOption(showOption);
            options.addOption(storeOption);
            options.addOption(nameOption);
            options.addOption(jsonOption);

            // Initializing the help message formatter.
            HelpFormatter helpFormatter = new HelpFormatter();
            // Initializing the options parser from the command line.
            CommandLineParser lineParser = new DefaultParser();
            // Holds the parsed command line.
            CommandLine line = null;

            // Trying to set the line.
            try{
                line = lineParser.parse(options,Args);
            }catch(org.apache.commons.cli.ParseException e){
                // Printing error and stopping execution.
                System.err.println("ERROR: not a valid input.");
                System.err.println(e.getMessage());
                helpFormatter.printHelp("Main -{h, api, csv, file} [options]", options);
                System.exit(1);
            }

            // Checking to see if the user requested a help message.
            if(line.hasOption(hOption)){
                helpFormatter.printHelp("Main -{h, api, csv, file} [options]", options);
            } else if(line.hasOption(APIOption)){       // Checking to see if the user chose the API endpoint response as articles source.
                /* Checking the presence of coherent parameters and setting their value if present (some of
                 * them are necessary so their absence cause the execution to stop).
                 * */
                if(line.hasOption(maxOption)) {
                    try {
                        maxArticle = Integer.parseInt(line.getOptionValue(maxOption));
                    }catch(NumberFormatException e){
                        // Printing error and stopping execution.
                        helpFormatter.printHelp("Main -{h, api, csv, file} [options]", options);
                        System.err.println("--> max field must be a number");
                        System.exit(1);
                    }
                }
                if(line.hasOption(tagsOption)) {
                    tags = Arrays.stream(line.getOptionValues(tagsOption)).collect(Collectors.toList());
                }
                if(line.hasOption(queriesOption)) {
                    queries = Arrays.stream(line.getOptionValues(queriesOption)).collect(Collectors.toList());
                }
                if(line.hasOption(keyOption)) {
                    apiKey = line.getOptionValue(keyOption);
                    if(line.hasOption(defaultOption)){
                        try{
                            FileOutputStream fos = new FileOutputStream(API_FILE);
                            Properties props = new Properties();
                            props.setProperty("apiKey",apiKey);
                            props.store(fos,null);
                        } catch (FileNotFoundException e) {
                            System.err.println("The system is not correctly configured, please check the documentation.");
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.err.println("The system was not able to read properties in properties file, ending.");
                            e.printStackTrace();
                        }
                    }
                }else{
                    // Reading the API key from a properties file.
                    try {
                        FileInputStream fis = new FileInputStream(API_FILE);
                        Properties props = new Properties();
                        props.load(fis);
                        apiKey = props.getProperty("apiKey");
                    } catch (FileNotFoundException e) {
                        System.err.println("The system is not correctly configured, please check the documentation.");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.err.println("The system was not able to read properties in properties file, ending.");
                        e.printStackTrace();
                    }
                    if(apiKey.equals("your_api_key")){      // Standard value when not set.
                        // Printing error and stopping execution.
                        helpFormatter.printHelp("Main -{h, api, csv, file} [options]", options);
                        System.err.println("--> apikey field must be specified if a default is not set in properties file.");
                        System.exit(1);
                    }
                }
                if(line.hasOption(showOption)) {
                    downloadAnswer = "y";
                }
                if(line.hasOption(storeOption)){
                    fileName = FILE_PATH + line.getOptionValue(storeOption) + FILE_EXTENSION;
                }
                // Trying to add the read articles from API endpoint response to the abstract article List.
                try {
                    source = new APISource(apiKey, tags.toArray(new String[]{}), queries.toArray(new String[]{}), maxArticle);
                    articles.addAll(source.getArticles());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if(line.hasOption(CSVOption)){       // Checking to see if the user chose the CSV formatted file as articles source.
                /* Checking the presence of coherent parameters and setting their value if present (some of
                 * them are necessary so their absence cause the execution to stop).
                 * */
                if(line.hasOption(nameOption)){
                    csvName = CSV_PATH + line.getOptionValue(nameOption) + CSV_EXSTENSION;
                    csvAnswer = "y";
                    // Trying to add the read articles from CSV formatted file to the abstract article List.
                    try {
                        source = new CSVSource(csvName);
                        articles.addAll(source.getArticles());
                    } catch (CsvValidationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    // Printing error and stopping execution.
                    helpFormatter.printHelp("Main -{h, api, csv, file} [options]", options);
                    System.err.println("The csv option must specify a file name ({-name}).");
                    System.exit(1);
                }
                if(line.hasOption(storeOption)){        // Checking to see if the user chose some old research file for tokens analysis.
                    fileName = FILE_PATH + line.getOptionValue(storeOption) + FILE_EXTENSION;
                }
            } else if(line.hasOption(fileOption)){
                /* Checking the presence of coherent parameters and setting their value if present (some of
                 * them are necessary so their absence cause the execution to stop).
                 * */
                if(line.hasOption(jsonOption)){
                    fileName = FILE_PATH + line.getOptionValue(jsonOption) + FILE_EXTENSION;
                    dataAnswer = "y";
                    System.out.println(fileName);
                }else{
                    // Printing error and stopping execution.
                    helpFormatter.printHelp("Main -{h, api, csv, file} [options]", options);
                    System.err.println("The file option must specify a file name ({-json}).");
                    System.exit(1);
                }
            } else {
                helpFormatter.printHelp("Main -{h, api, csv, file} [options]", options);
                System.exit(1);
            }
        }else {
            //USER INPUT (SETTINGS QUESTIONS PHASE)

            // Asking if the user want to use older data.
            System.out.println("Do you want to load data from file? (y/n) ");
            dataAnswer = console.nextLine().toLowerCase();
            while (!(dataAnswer.equals("y") || dataAnswer.equals("n"))) {
                System.out.println("Sorry I didn't understand your answer. Please enter a valid one (y/n): ");
                dataAnswer = console.nextLine().toLowerCase();
            }
            if (dataAnswer.equals("y")) {
                // Selecting file name.
                System.out.println("Enter the name of the file where you saved a previous search.");
                fileName = FILE_PATH + console.nextLine() + FILE_EXTENSION;
            } else {
                // Asking the user to set the name of the file where the research will be stored.
                System.out.println("Enter the name of the file where you want to save your research (enter \"test\" if not important): ");
                fileName = FILE_PATH + console.nextLine() + FILE_EXTENSION;

                // Asking if the user wants to read articles from the CSV file source.
                System.out.println("Do you want to load data from CSV file? (y/n) ");
                csvAnswer = console.nextLine().toLowerCase();
                while (!(csvAnswer.equals("y") || csvAnswer.equals("n"))) {
                    System.out.println("Sorry I didn't understand your answer. Please enter a valid one (y/n): ");
                    csvAnswer = console.nextLine().toLowerCase();
                }
                if (csvAnswer.equals("y")) {
                    // Asking for the user to enter the CSV file name.
                    System.out.println("Enter the name of CSV file (without extension): ");
                    csvName = CSV_PATH + console.nextLine().toLowerCase() + CSV_EXSTENSION;
                    // Elaborating articles from CSV source.
                    try {
                        source = new CSVSource(csvName);
                        articles.addAll(source.getArticles());
                    } catch (CsvValidationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                        // At this point the "The Guardian" API endpoint response is the only possible article source.
                        // Setting API key.
                        System.out.println("Do you want to use/set the default apiKey? (y/n) ");
                        String defaultAnswer = console.nextLine().toLowerCase();
                        while (!(defaultAnswer.equals("y") || defaultAnswer.equals("n"))) {
                            System.out.println("Sorry I didn't understand your answer. Please enter a valid one (y/n): ");
                            defaultAnswer = console.nextLine().toLowerCase();
                        }
                        if (defaultAnswer.equals("n")) {
                            System.out.println("Enter your API Key to access the \"The Guardian\" articles: ");
                            apiKey = console.nextLine();
                        } else {
                            // Reading API key from properties file.
                            try {
                                FileInputStream fis = new FileInputStream(API_FILE);
                                Properties props = new Properties();
                                props.load(fis);
                                apiKey = props.getProperty("apiKey");
                            } catch (FileNotFoundException e) {
                                System.err.println("The system is not correctly configured, please check the documentation.");
                                e.printStackTrace();
                            } catch (IOException e) {
                                System.err.println("The system was not able to read properties in properties file, ending.");
                                e.printStackTrace();
                            }
                            if(apiKey.equals("your_api_key")) {
                                System.out.println("Default value is not set for API key, enter your default API Key for the \"The Guardian\" endpoint: ");
                                apiKey = console.nextLine();
                                try{
                                    FileOutputStream fos = new FileOutputStream(API_FILE);
                                    Properties props = new Properties();
                                    props.setProperty("apiKey",apiKey);
                                    props.store(fos,null);
                                } catch (FileNotFoundException e) {
                                    System.err.println("The system is not correctly configured, please check the documentation.");
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    System.err.println("The system was not able to read properties in properties file, ending.");
                                    e.printStackTrace();
                                }
                            }
                        }

                        // Setting queries if needed.
                        System.out.println("Enter the number of queries to consider (0 if none): ");
                        int queriesSize = -1;
                        while(queriesSize < 0){
                            try {
                                queriesSize = Integer.parseInt(console.nextLine());
                            }catch(NumberFormatException e){
                                // Do nothing since we retry to set valid value.
                                System.out.println("Sorry, the queries number must be a number. Please retry: ");
                            }
                        }
                        for (int i = 0; i < queriesSize; i++) {
                            System.out.println("Enter the topic you want to investigate: ");
                            queries.add(console.nextLine());
                        }

                        // Setting tags if needed.
                        System.out.println("Enter the number of tags you want to consider (0 if none): ");
                        int tagNumber = -1;
                        while(tagNumber < 0){
                            try{
                                tagNumber = Integer.parseInt(console.nextLine());
                            }catch(NumberFormatException e){
                                // Do nothing since we retry to set valid value.
                                System.out.println("Sorry the tags number must be a number. Please retry: ");
                            }
                        }
                        for (int i = 0; i < tagNumber; i++) {
                            System.out.println("Enter the tag: ");
                            tags.add(console.nextLine());
                        }

                        // Setting max articles number.
                        System.out.println("Enter the max number of articles to elaborate: ");
                        maxArticle = -1;
                        while(maxArticle < 0){
                            try{
                                maxArticle = Integer.parseInt(console.nextLine());
                            }catch(NumberFormatException e){
                                // Do nothing since we retry to set valid value.
                                System.out.println("Sorry the max articles number must be a number. Please retry: ");
                            }
                        }

                        // Elaborating Articles from The Guardian API response content.
                        try{
                            source = new APISource(apiKey, tags.toArray(new String[]{}), queries.toArray(new String[]{}), maxArticle);
                            articles.addAll(source.getArticles());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    // Asking the user if it's required to print the 50 (or less) most frequent words.
                        System.out.println("Do you want to read the 50(or less) most frequent words in the downloaded articles? (y/n)");
                        downloadAnswer = console.next().toLowerCase();
                        while (!(downloadAnswer.equals("y") || downloadAnswer.equals("n"))) {
                            System.out.println("Sorry I didn't understand your answer. Please enter a valid one (y/n): ");
                            downloadAnswer = console.nextLine().toLowerCase();
                        }
                    }
                }
            }

            // SERIALIZING PHASE
            if (dataAnswer.equals("n")){
                // Checking if articles is empty.
                if(articles.size() == 0){
                    System.out.println("No articles were found.");
                    System.exit(0);
                }
                try {
                    Serializer serializer = new Serializer();
                    serializer.serialize(articles.toArray(new Article[]{}), fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Checking to see if deserialization is needed.
            if (csvAnswer.equals("y") || dataAnswer.equals("y") || downloadAnswer.equals("y")) {

                // DESERIALIZING PHASE
                try {
                    articles = Arrays.stream( deserializer.deserialize(fileName)).collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Printing the detected article Source.
                if(articles.size() != 0){
                    System.out.println(articles.get(0).getClass().getSimpleName() + " source detected, calculating frequent tokens:");
                }else{
                    System.out.println("No articles were found.");
                    System.exit(0);
                }

                // Inserts the Articles textual fields inside of Tokenizer.
                for (Article article : articles) {
                    pageText = article.getHead() + article.getBody();
                    tokenizer.tokenize(pageText,queries);
                }

                // PRINTING PHASE
                System.out.println("The 50(or less) most frequent words in the analyzed articles are: ");
                // This variable stores the entry with tokens list as values indexed with their frequency.
                Set<Map.Entry<Integer, List<String>>> set = storage.getOrderedTokens(50);
                int wordCounter = 0;
                Iterator<Map.Entry<Integer, List<String>>> iter = set.iterator();       // This variable is used to iterate through the set of ordered tokens Lists.
                Map.Entry<Integer, List<String>> pair;
                while (iter.hasNext()) {
                    pair = iter.next();
                    int index = pair.getValue().size();
                    while (index > 0) {
                        index--;
                        System.out.println("          " + (wordCounter + 1) + ": " + pair.getValue().get(index) + " " + pair.getKey());
                        wordCounter++;
                    }
                }
            }


    }
}
