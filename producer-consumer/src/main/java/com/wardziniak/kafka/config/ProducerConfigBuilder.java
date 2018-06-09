package com.wardziniak.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Optional;
import java.util.Properties;

/**
 * Created by wardziniak on 09.06.18.
 */
public class ProducerConfigBuilder implements ConfigBuilder {

    private static final String BOOTSTRAP_SERVERS_DEFAULT = "localhost:9092";
    private static final String KEY_SERIALIZER_DEFAULT = StringSerializer.class.getCanonicalName();
    private static final String VALUE_SERIALIZER_DEFAULT = StringSerializer.class.getCanonicalName();

    private Optional<String> bootstrapServer = Optional.empty();
    private Optional<String> keySerializer = Optional.empty();
    private Optional<String> valueSerializer = Optional.empty();

    public ProducerConfigBuilder withServer(String bootstrapServer) {
        this.bootstrapServer = Optional.ofNullable(bootstrapServer);
        return this;
    }

    private ProducerConfigBuilder withKeySerializer(String keySerialize) {
        this.keySerializer = Optional.ofNullable(keySerialize);
        return this;
    }

    private ProducerConfigBuilder withValueSerializer(String valueSerializer) {
        this.valueSerializer = Optional.ofNullable(valueSerializer);
        return this;
    }

    public ProducerConfigBuilder withSerializers(String keySerialize, String valueSerializer) {
        return this.withKeySerializer(keySerialize).withValueSerializer(valueSerializer);
    }

    @Override
    public Properties buildConfig() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer.orElse(BOOTSTRAP_SERVERS_DEFAULT));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer.orElse(KEY_SERIALIZER_DEFAULT));
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer.orElse(VALUE_SERIALIZER_DEFAULT));
        return properties;
    }
}
