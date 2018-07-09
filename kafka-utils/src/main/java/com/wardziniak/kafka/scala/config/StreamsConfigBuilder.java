package com.wardziniak.kafka.scala.config;

import org.apache.kafka.streams.StreamsConfig;

import java.util.Optional;
import java.util.Properties;

public class StreamsConfigBuilder extends ConfigBuilder {

    private static final String DEFAULT_APPLICATION_ID = "applicationId1";

    private Optional<String> applicationId = Optional.empty();

    public StreamsConfigBuilder withApplicationId(String applicationId) {
        this.applicationId = Optional.of(applicationId);
        return this;
    }

    @Override
    public Properties buildConfig() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId.orElse(DEFAULT_APPLICATION_ID));
        return properties;
    }
}
