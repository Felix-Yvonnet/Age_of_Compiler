package machine.go.printable.fixed.buildings.friendly.towers

import machine.scene.Point
import sfml.graphics.*

final class TeslaBuilding(position: Point) extends DefenseTower(position):
  name = "tesla"
  damage = 250
  rangeAttack = 5
  isFriendly = true
