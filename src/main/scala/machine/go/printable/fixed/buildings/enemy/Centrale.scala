package machine.go.printable.fixed.buildings.enemy

import machine.go.printable.fixed.buildings.ProductionBuilding
import machine.scene.Point
import sfml.graphics.*
import machine.scene.GameMap
import scala.util.Random

final class Centrale(position: Point) extends ProductionBuilding(position):
  this.name = "centrale"
  isEnemy = true
  val diffTimeBeforeNextRandomProduction: Long = 15000
  var lastTimeRandomProduction: Long = 0
  var randomDiff: Long = 0
  var limitAppearXavier = 7
  var presentXavier = false

  diffTimeBeforeNextBuild = Map(
      "centralien" -> 15000,
      "xavier" -> 0
  )
  priceForEntity = Map(
      "centralien" -> 0,
      "xavier" -> 0
  )
  val probabilityAppearing = Map(
      "centralien" -> 1,
      "xavier" -> 0
  )

  def selectRandomElement(): String =
    val rand = Random.nextDouble()
    var cumulativeProbability = 0.0
    for (element, probability) <- this.probabilityAppearing do
      cumulativeProbability += probability
      if rand < cumulativeProbability then return element

    // This should never happen unless the probabilities don't sum to 1.0
    throw new RuntimeException("Invalid probabilities")

  def ia(scene: GameMap) =
    var totNumAlly = 0
    scene.grid.foreach(
        _.foreach(
            _ match
              case t :: q => totNumAlly += (if t.isFriendly then 1 else 0)
              case _      => ()
        )
    )
    if totNumAlly > limitAppearXavier && !presentXavier then
      presentXavier = true
      this.productionQueue += "xavier"
    else if System.currentTimeMillis() - this.lastTimeRandomProduction + this.randomDiff > this.diffTimeBeforeNextRandomProduction
    then
      this.productionQueue += selectRandomElement()
      this.randomDiff = Random.between(-5000, 5000)
      this.lastTimeRandomProduction = System.currentTimeMillis()

  override def action(scene: GameMap): Unit =
    ia(scene)
    updateProduction(scene)
