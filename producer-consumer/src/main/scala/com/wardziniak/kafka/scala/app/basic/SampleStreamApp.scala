package com.wardziniak.kafka.scala.app.basic

import java.util.Properties

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsBuilder, StreamsConfig}
import org.apache.kafka.streams.kstream.Aggregator
import org.codehaus.jackson.map.ser.std.StringSerializer

object SampleStreamApp extends App {


  import org.apache.kafka.streams.kstream.KTable
  import org.apache.kafka.streams.kstream.TimeWindows
  import org.apache.kafka.streams.kstream.Windowed
  import java.time.Duration

  val builder = new StreamsBuilder()
//  builder.stream("input1").groupByKey.windowedBy(
//    TimeWindows.of(1000)).aggregate(() => "a", new Aggregator[String, String, String] {
//    override def apply(key: String, value: String, aggregate: String): String = aggregate.concat("a")
//  }).
//    //.toStream().map[String, String]((k, v) => new KeyValue[String, String](k.key(), v.toString))
//    .to("output1")

  val props = new Properties()
  props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "application1111")
  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass)
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass)
  //    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[BytesSerializer].getCanonicalName)
  //    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[BytesSerializer].getCanonicalName)
  props

  val streams = new KafkaStreams(builder.build(), props)

  streams.start()
}
