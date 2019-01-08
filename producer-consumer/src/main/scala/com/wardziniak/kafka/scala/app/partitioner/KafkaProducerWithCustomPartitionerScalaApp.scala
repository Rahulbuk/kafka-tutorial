package com.wardziniak.kafka.scala.app.partitioner

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.utils._
import com.wardziniak.kafka.scala.config.ProducerConfigBuilder
import com.wardziniak.kafka.scala.partitioner.CityPartitioner
import com.wardziniak.kafka.utils.model.Person
import com.wardziniak.kafka.utils.serialization.GenericSerializer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import scala.util.Random

object KafkaProducerWithCustomPartitionerScalaApp extends App with LazyLogging {

  val rand = Random
  val producer = new KafkaProducer[String, Person](ProducerConfigBuilder(
    partitioner = new CityPartitioner()
  ).buildConfig, new StringSerializer(), GenericSerializer[Person]())


  Stream.from(1).map(createPersonRecord).map(producer.send).map(_.get()).toList

  private def createPersonRecord(id: Int): ProducerRecord[String, Person] = {
    Thread.sleep(Math.abs(rand.nextLong()%100 * 10))
    val record = new ProducerRecord[String, Person](PeopleTopic, id.toString, Person(id))
    logger.info(s"$record")
    record
  }

}
