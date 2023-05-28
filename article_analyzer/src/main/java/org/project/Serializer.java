package org.project;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.io.File;
import java.io.IOException;

/**
 * This class allow to store the information represented in a JSON format file for possible future usage of the stored data
 * by different classes.
 * NOTICE: it is a wrapper that contains another serializer ObjectMapper.
 */
public class Serializer
{
    /* This variable is the actual serializer from the jackson.xml package. */
    private ObjectMapper jsonMapper;

    /**This constructor just sets the {@link Serializer#jsonMapper} variable with proper visibility and configuration for reading the object it deserializes.*/
    public Serializer() throws IOException {
        jsonMapper = new ObjectMapper();
        //This allows to ignore unknown properties in the instance of the object to serialize.
        jsonMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //This is used to avoid serializing null fields.
        jsonMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        //This is used to set visibility of the serializer in relation to the object to serialize.
        jsonMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * This method is used to format the content of the articles in JSON and store it into a file with JSON format.
     *
     * @param filePath which is the path of the file for storing the Article array articles
     * @param articles which is the Article array to serialize in the file at filePath path.
     * @throws IOException
     */
    public void serialize(Article[] articles, String filePath) throws IOException {
        //Formatting the object in XML and storing it in the file at filePath path.
        jsonMapper.writeValue(new File(filePath),articles);
    }

}
