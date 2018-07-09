package com.wardziniak.kafka.scala.config

import java.util.{Optional, Properties}

import com.wardziniak.kafka.config.ConfigBuilder

trait ConfigBuilder {

  private[config] val BOOTSTRAP_SERVERS_DEFAULT = "localhost:9092"
  protected var bootstrapServers: Option[String] = Option.empty

  def withServer(bootstrapServer: String): ConfigBuilder = {
    this.bootstrapServers = Option(bootstrapServer)
    this
  }

  def buildConfig: Properties
}
