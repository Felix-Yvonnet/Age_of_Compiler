package machine.go.printable.movable.characters.friendly.units.physicians

import machine.go.printable.movable.characters.friendly.units.Friendly
import machine.scene.Point
import sfml.graphics.*

class Physician(position: Point) extends Friendly(position, "moving_objects/characters/physicien_1.png"):

  rangeAttack = 1
  waitTimeMove = 30
  waitTimeResources = 50
  health = 1000
  damage = 200

  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.3, 0.35)
      sprite.position = (pos.x * 40, pos.y * 40)
      window.draw(sprite)
      drawLifeBar(window)
