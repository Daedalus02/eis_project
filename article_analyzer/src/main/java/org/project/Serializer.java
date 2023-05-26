package org.project;

import com.fasterxml.jackson.xml.XmlMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * this class allow to store the information related to the articles read in a file in XML format for possible future usage of the stored data
 */
public class Serializer
{
    XmlMapper xmlMapper;

    public Serializer() throws IOException {
        xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    /**
     * this method is used to format the content of the articles and store it int a file with XML format
     * @param articleList
     * @throws IOException
     */
    public void serialize(List<Article> articleList, String fileName) throws IOException {
        Articles articles = new Articles(articleList);
        xmlMapper.writeValue(new File(fileName),articles);

    }
}
