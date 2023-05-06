package org.example;

import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class Serializer
{
    public Serializer(){

    }

    /**
     *
     * @param articleList
     * @param fileName
     * @throws IOException
     */
    public void serialize(List<Article> articleList, String fileName) throws IOException {
        XStream xStream = new XStream();
        String xml = xStream.toXML(articleList);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("");
        writer.append(xml);
        writer.newLine();
        writer.close();
    }
}
