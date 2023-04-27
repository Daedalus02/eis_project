package org.example;
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class test_json
{
    public static void main(String Args[]) throws JSONException, FileNotFoundException, MalformedURLException {
        File file = new File("src\\main\\java\\org\\example\\json.txt");
        Scanner console = new Scanner(file);
        String str = "";
        while (console.hasNextLine()){
            str += console.nextLine();
        }
        jsonParser json = new jsonParser(str);

    }
}
