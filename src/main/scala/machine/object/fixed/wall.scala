package machine.`object`.fixed.wall

import sfml.graphics.*
import sfml.system.Vector2
import machine.`object`.GameObject

class Wall(sprite_path : String = "src/resources/fixed_objects/mur_pierres.png") extends GameObject(sprite_path) :
    pos = Vector2[Int](3,19)
    override def draw (window: RenderWindow): Unit = 
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.042,0.2)
      sprite.position = Vector2[Float]((pos.x)*40,(pos.y)*40)
      window.draw(sprite)