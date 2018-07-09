package com.wardziniak.kafka.app.serializiation;

import com.wardziniak.kafka.Constants;
import com.wardziniak.kafka.config.ConsumerConfigBuilder;
import com.wardziniak.kafka.model.Person;
import com.wardziniak.kafka.serialization.GenericDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Properties;

import static com.wardziniak.kafka.Constants.TIMEOUT;

/**
 * Created by wardziniak on 09.06.18.
 */
public class KafkaConsumerCustomSerdesApp {

    private static final Logger LOGGER = Logger.getLogger(KafkaProducerWithCustomSerdesApp.class);

    public static void main(String[] args) {

        Properties consumerConfig = new ConsumerConfigBuilder().buildConfig();
        KafkaConsumer<String, Person> consumer = new KafkaConsumer<>(
                consumerConfig,
                new StringDeserializer(),
                new GenericDeserializer<>(Person.class));
        consumer.subscribe(Collections.singletonList(Constants.PEOPLE_TOPIC));

        try {
            while (true) {
                ConsumerRecords<String, Person> records = consumer.poll(TIMEOUT);
                if (records.count() > 0) {
                    LOGGER.info("Poll records: " + records.count());

                    for (ConsumerRecord<String, Person> record : records) {
                        String line = String.format("Received Message topic = %s, partition = %s, offset = %d, key = %s, value = %s\n",
                                record.topic(), record.partition(), record.offset(), record.key(), record.value());
                        LOGGER.info(line);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Some error during retrieving messages", e);
        } finally {
            consumer.close();
        }
    }
}
