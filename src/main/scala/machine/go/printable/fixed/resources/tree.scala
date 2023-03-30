package machine.go.printable.fixed.resources


import machine.scene.Point
import machine.go.GameObject
import machine.go.invisible.*
import sfml.graphics.*
import sfml.system.Vector2
import machine.go.movable._

import machine.go.invisible.Player

class Tree(position: Point) extends Resource(position, "fixed_objects/tree_hand_made.png") {

  val apportBéton = 10
  val apportMoula = 0
  private val waitTime = 10
  private var lastCollectedTime: Long = 0

  override def collect(player: Player, char: Movable): Unit = {
    val currentTime = System.currentTimeMillis() / 1000
    if (currentTime - lastCollectedTime >= char.waitTimeResources) {
      println(s"Collecting wood")
      player.inventory.addResource(Beton, apportBéton)
      lastCollectedTime = currentTime
    }
  }

  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.017, 0.0225)
      sprite.position = Vector2[Float](pos.x * 40, pos.y * 40)
      window.draw(sprite)
}



