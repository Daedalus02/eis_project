package org.project;

import org.codehaus.jackson.map.DeserializationConfig;
import com.fasterxml.jackson.xml.XmlMapper;
import com.fasterxml.jackson.xml.ser.ToXmlGenerator;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * this class is used to deserialize the list of articles previously serialized in the file named with string "filename"
 */
public class Deserializer {
    private XmlMapper xmlMapper;
    public Deserializer(){
        xmlMapper = new XmlMapper();
        //this is used to avoid throwing an exception if an invalid XML character of different version from xml 1.1 happens to be in the xml
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1,true);
        xmlMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @param filename
     * @throws IOException
     */
    public List<Article> deserialize(String filename) throws IOException {
        InputStream data = new FileInputStream(new File(filename));
        StringBuilder builder = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(data));
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String xmlString = builder.toString();
        Articles articlesList = xmlMapper.readValue(xmlString,Articles.class);
        reader.close();
        return articlesList.getArticleList();
    }
}