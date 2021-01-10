package apgrison.codingtask.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public final class MapperUtil {

    private static ObjectMapper jsonMapper;
    private static XmlMapper xmlMapper;
    static {
        jsonMapper = new ObjectMapper();
        xmlMapper = new XmlMapper();
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> type) throws JsonProcessingException {
        return jsonMapper.readValue(json, type);
    }

    public static String toXml(Object obj) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(obj);
    }

    public static <T> T xmlToObject(String xml, Class<T> type) throws JsonProcessingException {
        return xmlMapper.readValue(xml, type);
    }
}
