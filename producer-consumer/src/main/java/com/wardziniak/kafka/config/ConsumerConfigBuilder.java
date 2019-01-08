package com.wardziniak.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Optional;
import java.util.Properties;

/**
 * Created by wardziniak on 10.06.18.
 */
public class ConsumerConfigBuilder extends ConfigBuilder {

    private static final String KEY_DESSERIALIZER_DEFAULT = StringDeserializer.class.getCanonicalName();
    private static final String VALUE_DESSERIALIZER_DEFAULT = StringDeserializer.class.getCanonicalName();
    private static final String GROUP_ID_DEFAULT = "group1";
    private static final String AUTO_OFFSET_REST = "earliest";

    private Optional<String> keyDeserializer = Optional.empty();
    private Optional<String> valueDeserializer = Optional.empty();
    private Optional<String> groupId = Optional.empty();


    private ConsumerConfigBuilder withKeyDeserializer(String keySerialize) {
        this.keyDeserializer = Optional.ofNullable(keySerialize);
        return this;
    }

    private ConsumerConfigBuilder withValueDeserializer(String valueSerializer) {
        this.valueDeserializer = Optional.ofNullable(valueSerializer);
        return this;
    }

    public ConsumerConfigBuilder withDeserializers(String keySerialize, String valueSerializer) {
        return this.withKeyDeserializer(keySerialize).withValueDeserializer(valueSerializer);
    }

    public ConsumerConfigBuilder withGroupId(String groupId) {
        this.groupId = Optional.ofNullable(groupId);
        return this;
    }

    @Override
    public Properties buildConfig() {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers.orElse(BOOTSTRAP_SERVERS_DEFAULT));
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId.orElse(GROUP_ID_DEFAULT));
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_REST);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        return consumerConfig;
    }
}
