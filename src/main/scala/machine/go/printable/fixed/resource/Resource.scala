package machine.go.printable.fixed.resource

import machine.scene.Point
import machine.go.GameObject
import machine.go.printable.Alive
import machine.go.invisible.Beton

class Resource(pos: Point) extends GameObject(pos) with Alive:
  // what it is to be a resource : nothing special but a different default value
  isSuperposable = false
  bonusWhenKilled = List((Beton, 10))
