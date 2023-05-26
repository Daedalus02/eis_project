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
    XStream xStream;
    public Serializer() throws IOException {
        //initializing serializer
        xStream = new XStream();
    }

    /**
     * this method is used to format the content of the articles and store it int a file with XML format
     * @param articleList
     * @throws IOException
     */
    public void serialize(List<Article> articleList, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        xStream.omitField(articleList.getClass(),"id");
        String xml = xStream.toXML(articleList);
        writer.write("");
        writer.append(xml);
        writer.newLine();
        writer.close();
    }
}
