package machine.go.printable.fixed.resource

import machine.scene.Point
import sfml.graphics._


import machine.go.printable.fixed.resource.Resource
class Tree(position: Point) extends Resource(position, "fixed_objects/Tilemap/tilemap.png"):
  // a simple resource : a tree giving basic resources
  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.textureRect = (4 * 17, 2 * 17, 16, 17)
      sprite.scale(2, 2.7)
      sprite.position = (this.pos.x * 40, this.pos.y * 40)
      window.draw(sprite)
      drawLifeBar(window)
