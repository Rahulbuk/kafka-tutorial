package com.wardziniak.kafka.scala.config

import java.util.Properties

import com.wardziniak.kafka.config.ConfigBuilder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer

case class ConsumerConfigBuilder(
  bootstrapServer: String = "localhost:9092",
  groupId: String = "adi1123410",
  autoOffsetReset: String = "earliest",
  keyDeserializer: String = classOf[StringDeserializer].getCanonicalName,
  valueDeserializer: String = classOf[StringDeserializer].getCanonicalName
  ) extends ConfigBuilder {

  override def buildConfig = {
    val consumerConfig = new Properties
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getCanonicalName)
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getCanonicalName)
    consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
    consumerConfig.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, int2Integer(60000))
    consumerConfig
  }
}
