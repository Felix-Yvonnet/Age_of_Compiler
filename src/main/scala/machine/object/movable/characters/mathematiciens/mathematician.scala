package machine.`object`.movable.characters.mathematiciens

import sfml.graphics.*
import sfml.system.Vector2
import machine.`object`.GameObject

class Mathematician(sprite_path : String = "src/resources/moving_objects/characters/matheux_1.png") extends GameObject(sprite_path) :
  isMovable = true
  waitTimeMove = 100
  speed = 100
  agility = 20
  waitTimeResources = 20

  pos = Vector2[Int](10,5)
  override def draw (window: RenderWindow): Unit = 
  if this.sprite_path != "" then
    val sprite = Sprite(this.texture)
    sprite.scale(0.2,0.2)
    sprite.position = Vector2[Float]((pos.x)*40,(pos.y)*40)
    window.draw(sprite)


