package machine.go.printable.fixed.buildings

import machine.scene.Point
import machine.go.GameObject

abstract class Building(sprite_path: String, position: Point) extends GameObject(position, sprite_path) {}
