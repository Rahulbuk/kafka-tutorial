package com.wardziniak.kafka.scala.partitioner

import java.util

import com.wardziniak.kafka.utils.model.Person
import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.Cluster

class CityPartitioner extends Partitioner {
  override def partition(topic: String, key: scala.Any, keyBytes: Array[Byte], value: scala.Any, valueBytes: Array[Byte], cluster: Cluster): Int =
    value match {
      case person: Person => person.address.city.hashCode % cluster.partitionCountForTopic(topic)
      case _ => key.hashCode % cluster.partitionCountForTopic(topic);
    }

  override def close(): Unit = {}

  override def configure(configs: util.Map[String, _]): Unit = {}
}
