package com.wardziniak.kafka.app.partitioner;

import com.wardziniak.kafka.Constants;
import com.wardziniak.kafka.config.ProducerConfigBuilder;
import com.wardziniak.kafka.model.Person;
import com.wardziniak.kafka.model.PersonFactory;
import com.wardziniak.kafka.partitioner.CityPartitioner;
import com.wardziniak.kafka.serialization.GenericSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by wardziniak on 09.06.18.
 */
public class KafkaProducerWithCustomPartitionerApp {

    private static final Logger LOGGER = Logger.getLogger(KafkaProducerWithCustomPartitionerApp.class);

    private static final String outputTopic = Constants.PEOPLE_TOPIC;

    public static void main(String[] args) {
        Properties producerConfig = new ProducerConfigBuilder().withPartitioner(new CityPartitioner()).buildConfig();
        KafkaProducer<String, Person> producer = new KafkaProducer<>(
                producerConfig,
                new StringSerializer(),
                new GenericSerializer<Person>());
        try {
            for (int i = 0; ; i++) {
                Person person = PersonFactory.getPerson(i);
                ProducerRecord<String, Person> record = new ProducerRecord<>(outputTopic, Integer.valueOf(i).toString(), person);
                producer.send(record);
                if (i % 5 == 0) {
                    LOGGER.info("Sent 5 messages");
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
