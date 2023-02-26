package com.bryant.traffic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class JsonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final static ObjectMapper objectMapperWithDateFormat = new ObjectMapper();

    private final static ObjectMapper objectMapperWithUnderline = new ObjectMapper();

    static {
        init(objectMapper);

        init(objectMapperWithUnderline);
        initDateProperty(objectMapperWithUnderline);
        objectMapperWithUnderline.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        init(objectMapperWithDateFormat);
        initDateProperty(objectMapperWithDateFormat);
    }

    private static void initDateProperty(ObjectMapper mapper) {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat(DateUtils.DEFAULT_FORMAT);
        mapper.setConfig(mapper.getSerializationConfig().with(dateFormat));
        mapper.setConfig(mapper.getDeserializationConfig().with(dateFormat));
    }

    private static void init(ObjectMapper mapper) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }


    /**
     * 将对象转换为json字符串
     * @param obj
     * @return
     */
    public static String object2Json(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException ex) {
            log.error("objectMapper writeValueAsString is error. obj is {}.", obj, ex);
            return null;
        }
    }

    /**
     * 将对象转换为json字符串(含日期格式转换)
     * @param obj
     * @return
     */
    public static String object2JsonWithDateFormat(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }

        try {
            return objectMapperWithDateFormat.writeValueAsString(obj);
        } catch (IOException ex) {
            log.error("objectMapperWithDateFormat writeValueAsString is error. obj is {}.", obj, ex);
            return null;
        }
    }

    /**
     * 把json字符串转为指定对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }

        if(clazz.equals(String.class)){
            return (T)json;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException ex) {
            log.error("objectMapper readValue is error. json is {}.", json, ex);
            return null;
        }
    }

    /**
     * 把json字符串转为指定对象(含日期格式转换)
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T json2ObjectWithDateFormat(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }

        if(clazz.equals(String.class)){
            return (T)json;
        }

        try {
            return objectMapperWithDateFormat.readValue(json, clazz);
        } catch (IOException ex) {
            log.error("objectMapperWithDateFormat readValue is error. json is {}.", json, ex);
            return null;
        }
    }

    /**
     * 校验字符串是否可以转换为入参(clazz)所定义的对象
     * 可以转换则返回转换后的对象实例，否则返回为空
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T checkStringIsObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }

        if (clazz.equals(String.class)) {
            return (T) json;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException ex) {
            log.info("the string can't transfer the clazz's object");
            return null;
        }
    }

    /**
     * 校验字符串是否符合json格式
     * @param string
     * @return
     */
    public static boolean checkStringIsJSON(String string){
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        try {
            objectMapper.readTree(string);
        } catch (Exception ex) {
            log.warn("the string can't transfer to json. string is {}.", string);
            return false;
        }

        return true;
    }

    /**
     *
     * @param jsonArray
     * @param clazz -> List<T>
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String jsonArray, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonArray) || clazz == null) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonArray, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            log.error("objectMapper readValue to list is error. string is {}.", jsonArray, e);
            return null;
        }
    }

    public static <T> T json2ObjectWithUnderLine(String json, Class<T> clazz) {

        try {
            return objectMapperWithUnderline.readValue(json, clazz);
        }catch (Exception e) {
            log.error("objectMapperWithUnderline readValue error. json {} ", json, e);
        }

        return null;
    }

    public static <T> List<T> json2ListWithUnderLine(String jsonArray, Class<T> clazz) {

        try {
            return objectMapperWithUnderline.readValue(jsonArray, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        }catch (Exception e) {
            log.error("objectMapperWithUnderline readValue error. json {} ", jsonArray, e);
        }

        return null;
    }

}
