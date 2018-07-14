package com.wardziniak.kafka.scala.app.serialization

import java.util.{Collections, Properties}

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.scala.app._
import com.wardziniak.kafka.config.ConsumerConfigBuilder
import com.wardziniak.kafka.scala.app.basic.BasicKafkaConsumerScalaApp.logger
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object BasicKafkaConsumerCustomSerdesScalaApp extends App with LazyLogging {


  Stream.from(1).map(p => "test").foreach(println)


  val consumerConfig = new ConsumerConfigBuilder().buildConfig
  val consumer = new KafkaConsumer[String, String](consumerConfig)
  consumer.subscribe(Collections.singletonList(BasicTopic))

//  while (true) {
//    val records = consumer.poll(Timeout)
//    if (records.count() > 0) {
//      logger.info(s"Poll records: ${records.count()}")
//      records.asScala.map(record =>
//        s"Received message topic = ${record.topic()}, partition = ${record.partition()}, offset = ${record.offset()}, key = ${record.key()}, value = ${record.value()}"
//      ).foreach(msg => logger.info(msg))
//    }
//  }
}
