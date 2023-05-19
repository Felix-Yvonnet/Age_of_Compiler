package machine.go.printable.fixed.buildings.friendly

import machine.scene.Point
import sfml.graphics.*
import machine.go.printable.movable.characters.friendly.units.Friendly
import machine.go.GameObject
import machine.go.printable.fixed.buildings.ProductionBuilding

class GeorgesSand(position: Point) extends ProductionBuilding(position):
  this.name = "georgessand"
  isFriendly = true

  diffTimeBeforeNextBuild = Map(
      "mathematician" -> 5000,
      "physicien" -> 10000,
      "design" -> 15000
  )
  priceForEntity = Map(
      "mathematician" -> 10,
      "physicien" -> 10,
      "design" -> 15
  )
