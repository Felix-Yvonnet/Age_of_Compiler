package machine.go.printable.movable.characters.friendly.units.physiciens

import machine.go.printable.movable.characters.friendly.units.Friendly
import machine.scene.Point
import sfml.graphics.*
import machine.scene.GameMap

final class Physicien(position: Point) extends Friendly(position):

  name = "physicien"
  rangeAttack = 1
  waitTimeMove = 30
  waitTimeResources = 50
  maxLife = 1000
  health = maxLife
  damage = 200

  override def doTheAttackCapacity(scene: GameMap): Unit =
    if (this.pos distanceManTo this.targetSelection.get.pos) > this.rangeAttack then
      scene.allClotherThan(this.targetSelection.get.pos, this.rangeAttack).filter(scene isAccessible _) match
        case Nil    => this.targetSelection = None
        case t :: q => this.tp(scene, t)
