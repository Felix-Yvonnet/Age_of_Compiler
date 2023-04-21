package machine.go.printable.movable.characters.friendly.units

import machine.scene.Point
import machine.go.printable.movable.characters.Fighters

abstract class Friendly(position: Point) extends Fighters(position):

  isFriendly = true
  rangeAttack = 2
  waitTimeMove = 50
  waitTimeResources = 50
  health = 500
  damage = 100
