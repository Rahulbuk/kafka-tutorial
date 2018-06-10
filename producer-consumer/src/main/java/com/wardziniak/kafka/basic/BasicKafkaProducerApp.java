package com.wardziniak.kafka.basic;

import com.wardziniak.kafka.Constants;
import com.wardziniak.kafka.config.ProducerConfigBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by wardziniak on 09.06.18.
 */
public class BasicKafkaProducerApp {

    private static final Logger LOGGER = Logger.getLogger(BasicKafkaProducerApp.class);

    private static final String outputTopic = Constants.BASIC_TOPIC;

    public static void main(String[] args) {
        Properties producerConfig = new ProducerConfigBuilder().buildConfig();
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerConfig);


        LOGGER.info("start");

        try {

            for (int i = 0; ; i++) {
                String key = "" + i % 3;
                String value = "someValue" + i;
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(outputTopic, key, value);
                producer.send(record);
                if (i % 11 == 0) {
                    LOGGER.info("Sent 11 messages");
                    Thread.sleep(1500);
                }
            }

        }
        catch (Exception e) {
            LOGGER.error("Some error occurred during sending", e);
            producer.flush();
        }



    }
}
