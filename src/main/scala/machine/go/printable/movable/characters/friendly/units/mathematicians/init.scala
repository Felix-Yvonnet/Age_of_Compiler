package machine.go.movable.characters.mathematiciens

import sfml.graphics.*
import sfml.system.Vector2
import machine.go.GameObject
import machine.go.movable.Movable
import machine.scene.{Point, GameMap, AStar}
import machine.go.printable.movable.characters.Fighters

class Mathematician(position: Point) extends Fighters(position, "moving_objects/characters/matheux_1.png"):

  waitTimeMove = 50
  waitTimeResources = 50
  health = 500
  damage = 100

  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.2, 0.2)
      sprite.position = Vector2[Float](pos.x * 40, pos.y * 40)
      window.draw(sprite)
      drawLifeBar(window)
