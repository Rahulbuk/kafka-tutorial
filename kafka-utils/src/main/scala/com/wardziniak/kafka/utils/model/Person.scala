package com.wardziniak.kafka.utils.model

import scala.util.Random

case class Person(id: Int, name: String, address: Address)

object Person {

  val rand: Random = Random

  def apply(id: Int): Person = Person(id = id, name = Names(Math.abs(rand.nextInt())%Names.length), Address())

  private val Names = List("Michał", "Piotr", "Paweł", "Marcin", "Rafał")

}
