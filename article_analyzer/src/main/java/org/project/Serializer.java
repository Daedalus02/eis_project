package org.project;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.io.File;
import java.io.IOException;

/**
 * This class allows to store the information, representing them in a JSON format file.
 * NOTE: it is a wrapper that contains another serializer ObjectMapper.
 */
public final class Serializer
{
    /* This variable is the serializer from the jackson.xml package. */
    private ObjectMapper JSONMapper;

    /**
     * This constructor sets the {@link Serializer#JSONMapper} variable with
     * proper visibility and configuration to read the object it has to serialize.
     * */
    public Serializer() throws IOException {
        JSONMapper = new ObjectMapper();
        //This allows to ignore unknown properties in the instance of the object to serialize.
        JSONMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //This is used to avoid serializing null fields.
        JSONMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        //This is used to set visibility of the serializer in relation to the object to serialize.
        JSONMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * This method is used to format the content of the articles in JSON and to store it into a file with JSON format.
     *
     * @param filePath which is the path of the file for storing the Article array articles
     * @param articles which is the Article array to serialize in the file at filePath path.
     * @throws IOException
     */
    public void serialize(Article[] articles, String filePath) throws IOException {
        //Formatting the object in XML and storing it in the file at filePath path.
        JSONMapper.writeValue(new File(filePath),articles);
    }

}
