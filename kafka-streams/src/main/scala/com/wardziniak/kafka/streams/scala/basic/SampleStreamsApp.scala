package com.wardziniak.kafka.streams.scala.basic

import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.streams.scala.config.StreamsConfigBuilder
import com.wardziniak.kafka.utils._
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}

import scala.collection.JavaConverters._

object SampleStreamsApp extends App with LazyLogging {

  val Topic = BasicTopic

  val config = StreamsConfigBuilder(bootstrapServer = "192.168.1.17:9092").buildConfig()

  val builder: StreamsBuilder = new StreamsBuilder()

//  builder.stream[String, String](Topic)
//    .flatMapValues(value => value.split(" ").toList.asJava)
//    .peek((key, value) => logger.info(s"[Key=$key, Value=($value)]"))

  builder.stream("aviation-in").to("backup-aviation-in-2018-10-31")

  val streams: KafkaStreams = new KafkaStreams(builder.build(), config)
  streams.start()




  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    streams.close(10, TimeUnit.SECONDS)
  }))
}
