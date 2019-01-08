package com.wardziniak.kafka.scala.app.basic

import java.time.LocalDateTime
import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig

object TestConsumer1 extends App {

  import org.apache.kafka.clients.consumer.KafkaConsumer



  import java.util.concurrent.Executors
  import java.util.concurrent.ScheduledExecutorService
  import java.util.concurrent.TimeUnit

  val scheduler = Executors.newScheduledThreadPool(1)
  val task = new Task
  task.init()
  scheduler.scheduleAtFixedRate(() => task.doWork(), 5, 1, TimeUnit.SECONDS)


  class Task {


    private var consumer: KafkaConsumer[String, String] = _

    import org.apache.kafka.clients.consumer.ConsumerRecords
    import java.time.Duration

    def doWork(): Unit = {
      try { //            System.out.println(LocalDateTime.now() + " Poll triggered");
        val consumerRecords = consumer.poll(0)
        //Thread.sleep(100)
        if (!consumerRecords.isEmpty) {
                          System.out.println(LocalDateTime.now() + " polled " + consumerRecords.count() + "s")
        }
        else {
          //                System.out.println(LocalDateTime.now() + " Zero batch polled");
        }
        consumer.commitSync()
      } catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }



    def init() {
      val properties = new Properties()
      properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[org.apache.kafka.common.serialization.StringDeserializer])
      properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[org.apache.kafka.common.serialization.StringDeserializer])
      properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
      properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
      properties.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "5000")
      properties.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "8192")
      properties.put("max.poll.interval.ms", "300000")

      properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, new Integer(50))
      properties.put(ConsumerConfig.GROUP_ID_CONFIG, "local")
      this.consumer = new KafkaConsumer[String, String](properties)
      consumer.subscribe(util.Arrays.asList("test1"))
    }
  }
}
