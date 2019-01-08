package com.wardziniak.kafka.serialization;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by wardziniak on 10.06.2018.
 */
public class GenericSerializer<T> implements Serializer<T> {

    private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String s, T t) {
        return gson.toJson(t).getBytes();
    }

    @Override
    public void close() {

    }
}
