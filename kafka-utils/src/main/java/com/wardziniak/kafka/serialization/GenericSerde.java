package com.wardziniak.kafka.serialization;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class GenericSerde<T> implements Serde<T> {

    private Class<T> classType;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public GenericSerde(Class<T> classType) {
        this.classType = classType;
    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<T> serializer() {
        return new GenericSerializer<>();
    }

    @Override
    public Deserializer<T> deserializer() {
        return new GenericDeserializer<>(classType);
    }
}
