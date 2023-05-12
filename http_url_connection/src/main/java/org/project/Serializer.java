package org.project;

import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * this class allow to store the information related to the articles read in a file in XML format for possible future usage of the stored data
 */
public class Serializer
{
    public Serializer(){}

    /**
     * this method is used to format the content of the articles and store it int a file with XML format
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
