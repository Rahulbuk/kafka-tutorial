package com.wardziniak.kafka.utils.model

import scala.util.Random

case class Person(id: Int, job: Job, name: String, address: Address)

object Person {

  val rand: Random = Random

  def apply(id: Int): Person = Person(
    id = id,
    job = Job(),
    name = Names(Math.abs(rand.nextInt()) % Names.length),
    address = Address()
  )

  private val Names = List("Michał", "Piotr", "Paweł", "Marcin", "Rafał")

}
