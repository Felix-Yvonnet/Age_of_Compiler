package machine.go.printable.fixed.buildings.friendly.towers

import machine.scene.Point
import sfml.graphics.*

class TeslaBuilding(position: Point) extends DefenseTower(position, "fixed_objects/teslaTower.png"):
  name = "Tesla Tower"
  damage = 250
  rangeAttack = 5
  isFriendly = true

  override def draw(window: RenderWindow, position: Point): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.22, 0.14)
      sprite.position = (position.x * 40, position.y * 40)
      window.draw(sprite)
      drawLifeBar(window)
