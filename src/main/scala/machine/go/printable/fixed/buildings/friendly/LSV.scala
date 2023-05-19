package machine.go.printable.fixed.buildings.friendly

import machine.scene.{Point, GameMap}
import scala.collection.mutable.Queue
import machine.go.invisible.Money
import affichage.Resources
import sfml.graphics.RenderWindow
import machine.go.printable.fixed.buildings.ProductionBuilding

sealed trait Technology
case object Sniper extends Technology
case object TP extends Technology

class LSV(position: Point) extends ProductionBuilding(position):
  name = "lsv"
  isFriendly = true

  diffTimeBeforeNextBuild = Map(
      "sniper" -> 50000,
      "teleportation" -> 100000
  )
  priceForEntity = Map(
      "sniper" -> 10,
      "teleportation" -> 10
  )
