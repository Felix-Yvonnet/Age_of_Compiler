package machine.go.printable.fixed.resources

import machine.scene.Point
import machine.go.GameObject
import machine.go.invisible.Player
import machine.go.movable.Movable

abstract class Resource(var position: Point, sprite_path: String) extends GameObject(position, sprite_path) {
  def collect(player: Player, char: Movable): Unit
}
