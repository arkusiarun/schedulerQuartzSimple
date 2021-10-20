package com.airtel.scheduler.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Arun Singh
 */

public class CommonUtils {
    private static Gson gson = new Gson();

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Gson getGson() {
        return gson;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String getJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String getJsonFromMapper(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}