package com.wardziniak.kafka.streams.scala.basic

import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.streams.scala.config.StreamsConfigBuilder
import com.wardziniak.kafka.utils.BasicTopic
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}
import scala.collection.JavaConverters._

object AggregationApp extends App with LazyLogging {


  val Topic = BasicTopic

  val config = StreamsConfigBuilder().buildConfig()

  val builder: StreamsBuilder = new StreamsBuilder()

  builder.stream[String, String](Topic)
    .mapValues(value => value)
    .filter((key, value) => ???)
//    value.split(" ").toList.asJava)
//    .filter((key, value) => true)
//    .peek((key, value) => logger.info(s"[Key=$key, Value=($value)]"))

  val streams: KafkaStreams = new KafkaStreams(builder.build(), config)
  streams.start()


  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    streams.close(10, TimeUnit.SECONDS)
  }))
}
