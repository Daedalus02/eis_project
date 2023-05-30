package org.project;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class is used to deserialize an object previously serialized in a JSON format file.
 * NOTICE: it is a wrapper that contains another deserializer ObjectMapper.
 * */
public class Deserializer {
    /** This variable is the actual deserializer from the org.codehaus.jackson.map package. */
    private ObjectMapper JSONMapper;

    /**This constructor just sets the {@link Deserializer#JSONMapper} variable with proper visibility and configuration for reading the object it deserializes.*/
    public Deserializer(){
        JSONMapper = new ObjectMapper();
        //This allows to ignore unknown properties in the instance of the object to serialize.
        JSONMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //This is used to avoid serializing null fields.
        JSONMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        //This is used to set visibility of the serializer in relation to the object to serialize.
        JSONMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    /**
     * This method is used to deserialize an object from a file where it was previously serialized in XML format.
     *
     * @param filePath which is the path of the file that contains the serialized object.
     * @throws IOException which is thrown when the file path specified is unknown.
     */
    public Article[] deserialize(String filePath) throws IOException {
        //Reading the content of the file at filePath path.
        InputStream data = new FileInputStream(new File(filePath));
        StringBuilder builder = new StringBuilder();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(data));
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String JSONString = builder.toString();
        //Deserializing the object from the file content.
        Article[] articles = JSONMapper.readValue(JSONString,Article[].class);
        reader.close();
        return articles;
    }
}