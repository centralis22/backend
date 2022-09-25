package edu.usc.marshall.centralis22.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.util.Map;

public class JSONToMap {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final MapType type = mapper
            .getTypeFactory()
            .constructMapType(
                    Map.class, String.class, Object.class
            );

    /**
     * Parses a JSON String into a Java Map.
     */
    // https://stackoverflow.com/questions/13916086/jackson-recursive-parsing-into-mapstring-object
    public static Map<String, Object> toMap(String message) throws JsonProcessingException {
        return mapper.readValue(message, type);
    }
}
