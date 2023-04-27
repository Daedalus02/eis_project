package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {
    public static void main(String Args[]){

        try{
            Tokenizer tokenizer = new Tokenizer("", false);
             Scanner reader = new Scanner(new File("src\\main\\java\\org\\example\\test.txt"));
            String fileContent = "";
            while(reader.hasNextLine()) {
                fileContent = reader.nextLine();
            }
            tokenizer.switchDocument(fileContent);
            //tokenizer.printFrequency("}");
            tokenizer.printFirst();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }
}
