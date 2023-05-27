package org.project;

import com.fasterxml.jackson.xml.XmlMapper;
import com.fasterxml.jackson.xml.ser.ToXmlGenerator;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * this class allow to store the information related to the articles read in a file in XML format for possible future usage of the stored data
 */
public class Serializer
{
    private XmlMapper xmlMapper;
    // XML 1.1 INVALID CHARACTERS
    private final String xml11pattern = "[\u0001-\u0008\u000B-\u000C\u000E-\u001F\u007F-\u0084\u0086-\u009F\uFDD0-\uFDDF]";

    public Serializer() throws IOException {
        xmlMapper = new XmlMapper();
        //this is used to avoid throwing an exception if an invalid XML character of different version from xml 1.1 happens to be in the xml
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1,true);
        //this is used to not serialize null fields
        xmlMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    /**
     * this method is used to format the content of the articles and store it int a file with XML format
     * @param articleList
     * @throws IOException
     */
    public void serialize(List<Article> articleList, String fileName) throws IOException {
        removeInvalid(articleList);
        Articles articles = new Articles(articleList);
        xmlMapper.writeValue(new File(fileName),articles);

    }
    private void removeInvalid(List<Article> articleList){
        Iterator<Article> iter = articleList.iterator();
        Article pair;
        while(iter.hasNext()){
            pair = iter.next();
            pair.setHead(pair.getHead().replaceAll(xml11pattern,""));
            pair.setBody(pair.getBody().replaceAll(xml11pattern,""));
        }
    }
}
