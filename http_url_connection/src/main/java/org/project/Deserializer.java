package org.project;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.*;
import java.util.List;

/**
 * this class is used to get back the list of articles previously written in the file named with string filename
 */
public class Deserializer {
    public Deserializer(){}

    /**
     *
     * @param filename
     * @throws IOException
     */
    public List<Article> deserialize(String filename) throws IOException {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        List<Article> articlesList = (List<Article>) xStream.fromXML(new FileInputStream(filename));
        return articlesList;
    }
}
