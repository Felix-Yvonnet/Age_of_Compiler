package machine.go.printable.fixed.buildings

import machine.scene.Point
import machine.go.GameObject
import machine.go.printable.Alive
import sfml.graphics.RenderWindow

class Building(sprite_path: String, position: Point) extends GameObject(position, sprite_path) with Alive:
  // destroying a building does not gives resources
  bonusWhenKilled = List()
  isSuperposable = false
