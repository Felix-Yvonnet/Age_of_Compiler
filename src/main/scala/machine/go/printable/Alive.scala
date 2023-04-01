package machine.go.printable

import machine.scene.GameMap
import machine.go.GameObject

trait Alive extends GameObject {

  var health = 500
  isAlive = true
  override def isAttacked(damage: Int, scene: GameMap): Boolean =
    println("is attacked")
    if this.health - damage <= 0 then
      scene.removeSthg(this, this.pos)
      this.destroy()
      true
    else
      this.health = this.health - damage
      false
}
