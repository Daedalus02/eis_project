package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {
    public static void main(String Args[]){

        try{
            Tokenizer tokenizer = new Tokenizer("");
             Scanner reader = new Scanner(new File("C:\\Users\\kriptos\\OneDrive - Università degli Studi di Padova\\università\\anno-2\\Secondo_semestre\\elementi_di_ingegneria_del_software\\materiale\\esercizi\\java\\http_url_connection\\http_url_connection\\src\\main\\java\\org\\example\\test.txt"));
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
