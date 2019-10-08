

// PACKAGE/IMPORTS --------------------------------------------------
package com.symantec.exoplanets.utils;


import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


/**
 * <p>
 * JSON helper utility to parse JSON objects using mapper from jackson library,
 * or convert objects to JSON strings.</p>
 * 
 * <p>
 * This class is configured to accept single values as arrays, if required and
 * empty strings as null objects.</p>
 * 
 * @author Akhil_Bansal
 */
public class JSONHelper {

    // CONSTANTS ------------------------------------------------------
    private final static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    // CLASS VARIABLES ------------------------------------------------

    // INSTANCE VARIABLES ---------------------------------------------

    // CONSTRUCTORS ---------------------------------------------------

    // PUBLIC METHODS -------------------------------------------------
    /**
     * Convert object to json string and returns
     *
     * @param objectValue
     * @return
     */
    public static String convertToJson(final Object objectValue) {
        try {
            return mapper.writeValueAsString(objectValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert object to json string and returns, explicitly specifying the type
     * class of the object.
     *
     * @param objectValue
     * @return
     */
    public static <T> String convertToJson(final Object objectValue, Class<T> type) {
        try {
            ObjectWriter writer = mapper.writerFor(type);
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            writer.writeValue(baos, objectValue);
            final String results = new String(baos.toByteArray());
            baos.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Convert json string to object of the specified type.
     *
     * @param jsonText
     * @param type
     * @return
     */
    public static <T> T convertJsonToObject(String jsonText, Class<T> type) {
        try {
            return mapper.readValue(jsonText, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert json string to collection of objects of the specified type.
     *
     * @param jsonText
     * @param collectionType
     * @param objectType
     * @return
     */
    public static <E extends Collection<T>, T> E convertJsonToCollection(String jsonText, Class<E> collectionType, Class<T> objectType) {
        try {
            return mapper.readValue(jsonText, mapper.getTypeFactory().constructCollectionType(collectionType, objectType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert json string to map of objects of the specified types for keys and values
     *
     * @param jsonText
     * @param mapClass
     * @param keyType
     * @param valueType
     * @return
     */
    public static <E extends Map<K, V>, K, V> E convertJsonToMap(String jsonText, Class<E> mapClass, Class<K> keyType, Class<V> valueType) {
        try {
            return mapper.readValue(jsonText, mapper.getTypeFactory().constructMapType(mapClass, keyType, valueType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // PROTECTED METHODS ----------------------------------------------

    // PRIVATE METHODS ------------------------------------------------

    // ACCESSOR METHODS -----------------------------------------------

}

