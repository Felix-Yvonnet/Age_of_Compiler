package machine.go.printable.movable.characters.friendly.units.physiciens

import machine.go.printable.movable.characters.friendly.units.Friendly
import machine.scene.Point
import sfml.graphics.*

final class Physicien(position: Point) extends Friendly(position):

  name = "physicien"
  rangeAttack = 1
  waitTimeMove = 30
  waitTimeResources = 50
  maxLife = 1000
  health = maxLife
  damage = 200
