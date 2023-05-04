package org.example;

import javax.swing.text.BadLocationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test_parser
{
    public static void main(String Args[]) throws FileNotFoundException {
        File file = new File("http_url_connection\\http_url_connection\\src\\main\\java\\org\\example\\test.txt");
        Scanner console = new Scanner(file);
        String str = "";
        while (console.hasNextLine()){
            str += console.nextLine();
        }
        htmlParser parser = new htmlParser();
        try {
            String htmlParsed = parser.parse(str);
            System.out.println(htmlParsed);
        }catch(BadLocationException e){
            e.printStackTrace();
        }
    }
}
