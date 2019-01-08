package com.wardziniak.kafka.streams.apps.missing_records;

import com.wardziniak.kafka.streams.apps.missing_records.serdes.JsonPOJODeserializer;
import com.wardziniak.kafka.streams.apps.missing_records.serdes.JsonPOJOSerdes;
import com.wardziniak.kafka.streams.apps.missing_records.serdes.JsonPOJOSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import java.time.Duration;
import java.util.Properties;

public class DetectMissingRecordApp {

    public final static long SESSION_TIMEOUT = 10000;
    public final static Duration PUNCTUATION_INTERVAL = Duration.ofMillis(1000);

    public static String inputTopic = "sessionTopic";
    public static String sessionStateStoreName = "sessionStateStore";

    public static void main(String[] args) {


        Properties properties = new Properties();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "app1");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        StreamsBuilder builder = new StreamsBuilder();

        Predicate<String, SessionModel> isDeadSession = (sessionId,sessionModel) -> CachedSessionModel.RUNNING.equals(sessionModel.sesssionStatus);
        Predicate<String, SessionModel> isDoneSession = (sessionId,sessionModel) -> CachedSessionModel.DONE.equals(sessionModel.sesssionStatus);

        StoreBuilder<KeyValueStore<String, CachedSessionModel>> sessionStateStore =
                Stores.keyValueStoreBuilder(
                        Stores.persistentKeyValueStore(sessionStateStoreName),
                        Serdes.String(),
                        new JsonPOJOSerdes<CachedSessionModel>(new JsonPOJOSerializer<CachedSessionModel>(), new JsonPOJODeserializer<CachedSessionModel>(CachedSessionModel.class)));
        builder.addStateStore(sessionStateStore);
        KStream<String, SessionModel> oldSession = builder.stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()))
                .transform(() -> new MissingRecordTransformer(sessionStateStoreName), sessionStateStoreName);

        oldSession.filter(isDeadSession).peek(((key, value) -> System.out.println("Dead session " + key + " status " + value)));
        oldSession.filter(isDoneSession).peek(((key, value) -> System.out.println("Done session " + key + " status " + value)));

        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));


    }
}
