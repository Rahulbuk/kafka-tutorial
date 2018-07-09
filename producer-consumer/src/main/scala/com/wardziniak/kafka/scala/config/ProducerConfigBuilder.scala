package com.wardziniak.kafka.scala.config

import java.util.Properties

import org.apache.kafka.clients.producer.internals.DefaultPartitioner
import org.apache.kafka.clients.producer.{Partitioner, ProducerConfig}
import org.apache.kafka.common.serialization.StringSerializer

case class ProducerConfigBuilder(
  bootstrapServer: String = "localhost:9092",
  keySerializer: String = classOf[StringSerializer].getCanonicalName,
  valueSerializer: String = classOf[StringSerializer].getCanonicalName,
  partitioner: Partitioner = new DefaultPartitioner()
) extends ConfigBuilder {


  override def buildConfig = {
    val properties = new Properties
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer)
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer)
    properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, partitioner.getClass.getCanonicalName)
    properties
  }
}
