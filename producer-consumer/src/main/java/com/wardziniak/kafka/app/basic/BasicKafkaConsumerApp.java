package com.wardziniak.kafka.app.basic;

import com.wardziniak.kafka.Constants;
import com.wardziniak.kafka.config.ConsumerConfigBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Properties;

import static com.wardziniak.kafka.Constants.TIMEOUT;

/**
 * Created by wardziniak on 09.06.18.
 */
public class BasicKafkaConsumerApp {

    private static final Logger LOGGER = Logger.getLogger(BasicKafkaProducerApp.class);

    public static void main(String[] args) {

        Properties consumerConfig = new ConsumerConfigBuilder().buildConfig();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerConfig);
        consumer.subscribe(Collections.singletonList(Constants.BASIC_TOPIC));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(TIMEOUT);
                if (records.count() > 0) {
                    LOGGER.info("Poll records: " + records.count());

                    for (ConsumerRecord<String, String> record : records) {
                        String line = String.format("Received Message topic = %s, partition = %s, offset = %d, key = %s, value = %s\n",
                                record.topic(), record.partition(), record.offset(), record.key(), record.value());
                        LOGGER.info(line);
                    }
                }
                //consumer.commitAsync();
            }
        } catch (Exception e) {
            LOGGER.error("Some error...", e);
        } finally {
            //consumer.commitSync();
            consumer.close();
        }
    }
}
