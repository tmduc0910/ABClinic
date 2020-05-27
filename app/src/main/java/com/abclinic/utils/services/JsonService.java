package com.abclinic.utils.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonService {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static <T> T toObject(String s, Class<T> c) {
        try {
            return objectMapper.readValue(s, c);
        } catch (Exception e) {
            return null;
        }
    }
}
