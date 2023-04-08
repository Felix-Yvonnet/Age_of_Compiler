package machine.go.printable.fixed.buildings.enemy

import machine.go.printable.fixed.buildings.ProductionBuilding
import machine.scene.Point
import sfml.graphics._

class Centrale(position: Point) extends ProductionBuilding(position, "fixed_objects/Tilemap/tilemap_packed.png"):
    isEnemy = true


    diffTimeBeforeNextBuild = Map(
        "centralien" -> 5000
    )
    priceForEntity = Map(
        "centralien" -> 10
    )
  


    override def draw(window: RenderWindow): Unit =
      if this.sprite_path != "" then
        val sprite = Sprite(this.texture)
        sprite.textureRect = (3 * 16, 8 * 16, 4 * 16, 4 * 16)
        sprite.scale(0.7, 0.7)
        sprite.position = (this.pos.x * 40, this.pos.y * 40)
        window.draw(sprite)
        drawLifeBar(window)

