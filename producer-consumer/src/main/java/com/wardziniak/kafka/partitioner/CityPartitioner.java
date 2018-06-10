package com.wardziniak.kafka.partitioner;

import com.wardziniak.kafka.model.Person;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Created by wardziniak on 10.06.2018.
 */
public class CityPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (value instanceof Person)
        {
            Person person = (Person)value;
            return person.getAddress().getCity().hashCode() % cluster.partitionCountForTopic(topic);
        }
        else
            return key.hashCode() % cluster.partitionCountForTopic(topic);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
