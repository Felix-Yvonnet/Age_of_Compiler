package machine.go.printable.fixed.decoration

import sfml.graphics.*
import sfml.system.Vector2
import machine.go.GameObject
import machine.scene.Point
import machine.go.printable.Alive

class Wall(pos: Point) extends  GameObject(pos) with Alive:
  this.name = "wall"

  isSuperposable = false
