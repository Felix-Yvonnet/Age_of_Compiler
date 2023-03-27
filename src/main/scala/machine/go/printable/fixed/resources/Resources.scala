package machine.go.printable.fixed.resources

import machine.scene.Point
import machine.go.GameObject
import machine.go.invisible.Player
import machine.go.movable.Movable

abstract class Resource(sprite_path: String, var position: Point) extends GameObject(sprite_path) {
  def collect(player: Player, char: Movable): Unit
}
