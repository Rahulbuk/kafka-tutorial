package com.wardziniak.kafka.scala.app.basic

import java.util.Collections

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import com.wardziniak.kafka.utils._
import com.wardziniak.kafka.scala.config.ConsumerConfigBuilder
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._

object BasicKafkaConsumerScalaApp extends App with LazyLogging {

  val Topic = "test-in" //"nuke-pre-prod.configuration" //BasicTopic

  val consumerConfig = ConsumerConfigBuilder().buildConfig
    //bootstrapServer = "kafka-0-broker.kafka-macod-prod01.cdt.dcos:9612,kafka-1-broker.kafka-macod-prod01.cdt.dcos:9612,kafka-2-broker.kafka-macod-prod01.cdt.dcos:9612").buildConfig
  val consumer = new KafkaConsumer[String, String](consumerConfig)
  consumer.subscribe(Collections.singletonList(Topic))


//  consumer.poll(Timeout)
//  consumer.poll(Timeout)
//  consumer.commitSync()

  var counter = 1
  var numberOfThrows = 0
  while (true) {
    val records = consumer.poll(Timeout)
    if (records.count() > 0) {
      logger.info(s"Poll records: ${records.count()}")
      records.asScala.foreach(record => {
        s"Received message topic = ${record.topic()}, partition = ${record.partition()}, offset = ${record.offset()}, timestamp = ${record.timestamp()},key = ${record.key()}, value = ${record.value()}"
        logger.info(s"$record")
        try {
          process(counter, record)
          counter = counter + 1
          consumer.commitSync()
        }
        catch {
          case ex: Exception =>
            logger.error(s"Seek::${record.topic()}, ${record.partition()}, ${record.offset()}")
            consumer.seek(new TopicPartition(record.topic(), record.partition()), record.offset())
        }
      })
    }
  }

  def process(counter: Long, record: ConsumerRecord[String, String]): Unit = {
    logger.info(s"Processing: $counter, $numberOfThrows, ${record.partition()}, ${record.offset()}")
    Thread.sleep(3000)
    if (counter % 3 == 0){// && numberOfThrows < 3) {
      numberOfThrows = numberOfThrows + 1
      throw new IllegalStateException(s"$counter")
    }
  }
}
