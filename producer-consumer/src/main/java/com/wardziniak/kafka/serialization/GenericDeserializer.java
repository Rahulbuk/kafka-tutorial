package com.wardziniak.kafka.serialization;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by wardziniak on 10.06.2018.
 */
public class GenericDeserializer<T> implements Deserializer<T> {
    private Gson gson = new Gson();
    private Class<T> classType;

    public GenericDeserializer(Class<T> classType) {
        this.classType = classType;
    }

    @Override
    public T deserialize(String s, byte[] data) {
        return gson.fromJson(new String(data), classType);
    }

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public void close() {

    }
}
