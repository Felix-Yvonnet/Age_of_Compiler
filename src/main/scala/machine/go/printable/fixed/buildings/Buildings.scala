package machine.go.printable.fixed.buildings

import machine.scene.Point
import machine.go.GameObject
import machine.go.printable.Alive
import sfml.graphics.RenderWindow

class Building(position: Point) extends GameObject(position) with Alive:
  // destroying a building does not gives resources
  bonusWhenKilled = List()
  isSuperposable = false
