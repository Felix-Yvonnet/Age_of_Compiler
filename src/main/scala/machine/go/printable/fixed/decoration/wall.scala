package machine.go.printable.fixed.decoration

import sfml.graphics.*
import sfml.system.Vector2
import machine.go.GameObject
import machine.scene.Point

class Wall(pos: Point) extends GameObject(pos, "fixed_objects/mur_pierres.png"):

  isSuperposable = false
  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.042, 0.07)
      sprite.position = Vector2[Float]((pos.x) * 40, (pos.y) * 40)
      window.draw(sprite)
