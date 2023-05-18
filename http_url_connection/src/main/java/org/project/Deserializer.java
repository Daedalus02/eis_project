package org.project;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.*;
import java.util.List;

/**
 * this class is used to deserialize the list of articles previously serialized in the file named with string "filename"
 */
public class Deserializer {
    private XStream xStream;
    public Deserializer(){
        xStream = new XStream();
    }

    /**
     * @param filename
     * @throws IOException
     */
    public List<Article> deserialize(String filename) throws IOException {
        xStream.addPermission(AnyTypePermission.ANY);
        List<Article> articlesList = (List<Article>) xStream.fromXML(new FileInputStream(filename));
        return articlesList;
    }
}
