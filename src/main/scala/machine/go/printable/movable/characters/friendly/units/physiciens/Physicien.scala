package machine.go.printable.movable.characters.friendly.units.physiciens

import machine.go.printable.movable.characters.friendly.units.Friendly
import machine.scene.Point
import sfml.graphics.*

class Physicien(position: Point) extends Friendly(position, "moving_objects/characters/physicien_1.png"):

  name = "Physicien"
  rangeAttack = 1
  waitTimeMove = 30
  waitTimeResources = 50
  maxLife = 1000
  health = maxLife
  damage = 200

  override def draw(window: RenderWindow, position: Point): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.3, 0.35)
      sprite.position = (position.x * 40, position.y * 40)
      window.draw(sprite)
      drawLifeBar(window)
