package com.wardziniak.kafka.config;

import java.util.Optional;
import java.util.Properties;

/**
 * Created by wardziniak on 09.06.18.
 */
public abstract class ConfigBuilder {

    static final String BOOTSTRAP_SERVERS_DEFAULT = "localhost:9092";
    protected Optional<String> bootstrapServers = Optional.empty();

    public ConfigBuilder withServer(String bootstrapServer) {
        this.bootstrapServers = Optional.ofNullable(bootstrapServer);
        return this;
    }

    public abstract Properties buildConfig();
}
