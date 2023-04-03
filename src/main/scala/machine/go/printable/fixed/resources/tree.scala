package machine.go.printable.fixed.resources

import machine.scene.Point
import machine.go.GameObject
import machine.go.invisible.*
import sfml.graphics.*
import sfml.system.Vector2
import machine.go.movable.*

import machine.go.invisible.Player

class Tree(position: Point) extends Resource(position, "fixed_objects/Tilemap/tilemap.png") :
  // a simple resource : a tree giving basic resources
  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.textureRect = (4 * 17, 2 * 17, 16, 17)
      sprite.scale(2, 2.7)
      sprite.position = Vector2[Float](this.pos.x * 40, this.pos.y * 40)
      window.draw(sprite)
      drawLifeBar(window)

