package machine.go.printable

import machine.scene.GameMap
import machine.go.GameObject

trait Alive extends GameObject {

  var health: Int = 500
  override def isAttacked(damage: Int, scene: GameMap) : Boolean =
    if health - damage <= 0 then
      scene.removeSthg(this, this.pos)
      this.destroy()
      true

    else 
        health = health - damage
        false
}
