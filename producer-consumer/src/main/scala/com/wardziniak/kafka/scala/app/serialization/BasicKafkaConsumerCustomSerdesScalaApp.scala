package com.wardziniak.kafka.scala.app.serialization

import java.util.Collections

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.utils._
import com.wardziniak.kafka.scala.config.ConsumerConfigBuilder
import com.wardziniak.kafka.utils.model.Person
import com.wardziniak.kafka.utils.serialization.GenericDeserializer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import scala.collection.JavaConverters._

object BasicKafkaConsumerCustomSerdesScalaApp extends App with LazyLogging {

  val consumerConfig = ConsumerConfigBuilder().buildConfig
  val consumer = new KafkaConsumer[String, Person](consumerConfig, new StringDeserializer(), GenericDeserializer[Person]())
  consumer.subscribe(Collections.singletonList(PeopleTopic))

  while (true) {
    val records = consumer.poll(Timeout)
    if (records.count() > 0) {
      logger.info(s"Poll records: ${records.count()}")
      records.asScala.map(record =>
        s"Received message topic = ${record.topic()}, partition = ${record.partition()}, offset = ${record.offset()}, key = ${record.key()}, value = ${record.value()}"
      ).foreach(msg => logger.info(msg))
    }
  }
}
