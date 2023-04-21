package machine.go.printable.fixed.buildings.enemy

import machine.go.printable.fixed.buildings.ProductionBuilding
import machine.scene.Point
import sfml.graphics.*
import machine.scene.GameMap
import scala.util.Random

final class Centrale(position: Point) extends ProductionBuilding(position):
  this.name = "centrale"
  isEnemy = true
  val diffTimeBeforeNextRandomProduction = 15000
  var lastTimeRandomProduction = 0
  var randomDiff = 0

  diffTimeBeforeNextBuild = Map(
      "centralien" -> 15000
  )
  priceForEntity = Map(
      "centralien" -> 0
  )
  val probabilityAppearing = Map(
      "centralien" -> 1
  )

  def selectRandomElement(): String =
    val rand = Random.nextDouble()
    var cumulativeProbability = 0.0
    for (element, probability) <- this.probabilityAppearing do
      cumulativeProbability += probability
      if rand < cumulativeProbability then return element

    // This should never happen unless the probabilities don't sum to 1.0
    throw new RuntimeException("Invalid probabilities")

  def ia() =
    if System.currentTimeMillis() - this.lastTimeRandomProduction + this.randomDiff > this.diffTimeBeforeNextRandomProduction then 
      this.productionQueue += selectRandomElement()
      this.randomDiff = Random.between(-5000, 5000)

  override def action(scene: GameMap): Unit =
    ia()
    updateProduction(scene)

