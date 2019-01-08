package com.wardziniak.kafka.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Optional;
import java.util.Properties;

/**
 * Created by wardziniak on 09.06.18.
 */
public class ProducerConfigBuilder extends ConfigBuilder {

    private static final String KEY_SERIALIZER_DEFAULT = StringSerializer.class.getCanonicalName();
    private static final String VALUE_SERIALIZER_DEFAULT = StringSerializer.class.getCanonicalName();
    private static final Partitioner PARTITONER_DEFAULT = new DefaultPartitioner();

    private Optional<String> keySerializer = Optional.empty();
    private Optional<String> valueSerializer = Optional.empty();
    private Optional<Partitioner> partitioner = Optional.empty();

    private ProducerConfigBuilder withKeySerializer(String keySerialize) {
        this.keySerializer = Optional.of(keySerialize);
        return this;
    }

    private ProducerConfigBuilder withValueSerializer(String valueSerializer) {
        this.valueSerializer = Optional.of(valueSerializer);
        return this;
    }

    public ProducerConfigBuilder withSerializers(String keySerialize, String valueSerializer) {
        return this.withKeySerializer(keySerialize).withValueSerializer(valueSerializer);
    }

    public <T extends Partitioner> ProducerConfigBuilder withPartitioner(T partitioner) {
        this.partitioner = Optional.of(partitioner);
        return this;
    }

    @Override
    public Properties buildConfig() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers.orElse(BOOTSTRAP_SERVERS_DEFAULT));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer.orElse(KEY_SERIALIZER_DEFAULT));
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer.orElse(VALUE_SERIALIZER_DEFAULT));
        properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, partitioner.orElse(PARTITONER_DEFAULT).getClass().getCanonicalName());
        return properties;
    }
}
