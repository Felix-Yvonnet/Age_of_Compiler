package machine.go.printable.fixed.decoration

import sfml.graphics.*
import sfml.system.Vector2
import machine.go.GameObject
import machine.scene.Point

class Wall(pos: Point) extends GameObject(pos, "fixed_objects/mur_pierres.png"):

  isSuperposable = false
  override def draw(window: RenderWindow, position: Point): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.042, 0.07)
      sprite.position = (position.x * 40, position.y * 40)
      window.draw(sprite)
