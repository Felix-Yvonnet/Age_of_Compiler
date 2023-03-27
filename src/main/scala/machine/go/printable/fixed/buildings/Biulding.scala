package machine.go.printable.fixed.buildings

import machine.scene.Point
import machine.go.GameObject

abstract class Building(name: String, var position: Point, var health: Int) extends GameObject(name) {
  def takeDamage(damage: Int): Unit = {
    health -= damage
    if (health <= 0) {
      remove()
    }
  }

  def remove(): Unit = {
    println(s"$name has been destroyed!")
    // TODO: remove building from game map and player's list of buildings
  }
}
