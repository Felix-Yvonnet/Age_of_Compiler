package machine.go.printable.movable.characters.friendly.units.mathematicians

import machine.go.printable.movable.characters.friendly.units.Friendly
import machine.scene.Point
import sfml.graphics.*

class Mathematician(position: Point) extends Friendly(position, "moving_objects/characters/matheux_1.png"):
  name = "Mathematician"
  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.19, 0.18)
      sprite.position = (pos.x * 40, pos.y * 40)
      window.draw(sprite)
      drawLifeBar(window)
