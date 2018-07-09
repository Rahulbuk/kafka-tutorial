package com.wardziniak.kafka.utils.model

import scala.util.Random

case class Address(city: String, postalCode: String, street: String)

object Address {

  val random: Random = Random

  def apply(): Address = Address(
    city = Cites(Math.abs(random.nextInt()) % Cites.length),
    postalCode = PostalCodes(Math.abs(random.nextInt()) % PostalCodes.length),
    street = Streets(Math.abs(random.nextInt()) % Streets.length)
  )

  private val Streets = List("Al. Jerozolimskie", "Marszałkowska", "Wołoska", "Al. Niepodległości")
  private val PostalCodes = List("00-111", "00-222", "00-333", "11-222", "11-435")
  private val Cites = List("WARSZAWA", "ŻUROMIN", "RADZYN PODLASKI", "CIECHANÓW")
}
