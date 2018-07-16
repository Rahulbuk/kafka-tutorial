package com.wardziniak.kafka.scala.app.basic

import java.util.Collections

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import com.wardziniak.kafka.utils._
import com.wardziniak.kafka.scala.config.ConsumerConfigBuilder

import scala.collection.JavaConverters._

object BasicKafkaConsumerScalaApp extends App with LazyLogging {

  val Topic = BasicTopic

  val consumerConfig = ConsumerConfigBuilder().buildConfig
  val consumer = new KafkaConsumer[String, String](consumerConfig)
  consumer.subscribe(Collections.singletonList(Topic))

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
