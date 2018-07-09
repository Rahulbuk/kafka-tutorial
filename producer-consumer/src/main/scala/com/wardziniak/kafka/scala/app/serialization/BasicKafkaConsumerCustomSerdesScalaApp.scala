package com.wardziniak.kafka.scala.app.serialization

import java.util.{Collections, Properties}

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.scala.app._
import com.wardziniak.kafka.config.ConsumerConfigBuilder
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object BasicKafkaConsumerCustomSerdesScalaApp extends App with LazyLogging {


  Stream.from(1).map(p => "test").foreach(println)


//  val consumerConfig = new ConsumerConfigBuilder().buildConfig
//  val consumer = new KafkaConsumer[String, String](consumerConfig)
//  consumer.subscribe(Collections.singletonList(OutputTopic))
//
//  try
//      while ( {
//        true
//      }) {
//        val records = consumer.poll(Timeout)
//        if (records.count > 0) {
//          LOGGER.info("Poll records: " + records.count)
//          import scala.collection.JavaConversions._
//          for (record <- records) {
//            val line = String.format("Received Message topic = %s, partition = %s, offset = %d, key = %s, value = %s\n", record.topic, record.partition, record.offset, record.key, record.value)
//            LOGGER.info(line)
//          }
//        }
//      }
//  catch {
//    case e: Exception =>
//      LOGGER.error("Some error...", e)
//  } finally {
//    consumer.commitSync()
//    consumer.close()
//  }
}
