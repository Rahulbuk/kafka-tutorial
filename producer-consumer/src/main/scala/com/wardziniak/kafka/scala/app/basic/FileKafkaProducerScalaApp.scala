package com.wardziniak.kafka.scala.app.basic

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.utils._
import com.wardziniak.kafka.scala.config.ProducerConfigBuilder
import com.wardziniak.kafka.scala.app._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.io.Source

import scala.util.Random

object FileKafkaProducerScalaApp extends App with LazyLogging {


  val Topic = BasicTopic
  val rand = Random
  val producer = new KafkaProducer[String, String](ProducerConfigBuilder().buildConfig)


  Source.fromResource(FileWithData).getLines.
    zipWithIndex.map(lineWithIndex => createRecord(lineWithIndex._2, lineWithIndex._1))
    .map(producer.send).map(_.get).toList


  private def createRecord(index: Int, line: String): ProducerRecord[String, String] = {
    Thread.sleep(Math.abs(rand.nextLong()%100))
    val record = new ProducerRecord[String, String](Topic, s"$index", line)
    record
  }

}
