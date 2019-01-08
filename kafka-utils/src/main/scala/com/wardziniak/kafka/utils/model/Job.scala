package com.wardziniak.kafka.utils.model

import scala.util.Random

case class Job(position: String, salary: Int)

object Job {

  private val MaxSalary = 50000
  private val random: Random = Random
  private val Positions = List("Junior Developer", "Developer", "Senior Developer", "Project Manager", "Tester", "Director")

  def apply(): Job = Job(
    position = Positions(Math.abs(random.nextInt() % Positions.length)),
    salary = Math.abs(random.nextInt() % MaxSalary)
  )
}