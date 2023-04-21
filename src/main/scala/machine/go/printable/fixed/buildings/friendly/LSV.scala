package machine.go.printable.fixed.buildings.friendly

import machine.go.GameObject
import machine.go.printable.Alive
import machine.scene.Point

class LSV(position: Point) extends GameObject(position) with Alive:
  var x = true
