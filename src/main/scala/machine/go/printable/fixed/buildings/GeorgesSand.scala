package machine.go.printable.fixed.buildings

import machine.scene.Point
import sfml.graphics.*
import machine.go.printable.movable.characters.friendly.units.Friendly
import machine.go.GameObject

class GeorgesSand(position: Point) extends ProductionBuilding(position, "fixed_objects/Tilemap/tilemap_packed.png") with Friendly:

  diffTimeBeforeNextBuild = Map(
      "mathematician" -> 10000
  )
  priceForEntity = Map(
      "mathematician" -> 10000
  )

  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.textureRect = (0, 4 * 17, 4 * 16, 4 * 16)
      sprite.scale(0.7, 0.7)
      sprite.position = (this.pos.x * 40, this.pos.y * 40)
      window.draw(sprite)
      drawLifeBar(window)
