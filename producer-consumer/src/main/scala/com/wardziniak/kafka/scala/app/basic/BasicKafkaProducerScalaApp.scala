package com.wardziniak.kafka.scala.app.basic

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.scala.app._
import com.wardziniak.kafka.scala.config.ProducerConfigBuilder
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

object BasicKafkaProducerScalaApp extends App with LazyLogging {

  val rand = Random
  val producer = new KafkaProducer[String, String](ProducerConfigBuilder().buildConfig)

  Stream.from(1).map(createRecord).map(producer.send).map(_.get()).toList

  private def createRecord(i: Int): ProducerRecord[String, String] = {
    Thread.sleep(Math.abs(rand.nextLong()%100 * 10))
    val value = "someValue" + i
    val record = new ProducerRecord[String, String](BasicTopic, s"$i", value)
    record
  }
}
