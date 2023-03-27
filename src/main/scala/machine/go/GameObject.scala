package machine.go

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.scene.Point

class GameObject(var sprite_path: String = ""):
  var pos = Point(0, 0)
  var isSelectable: Boolean = false
  var isSuperposable: Boolean = true

  val texture = Texture()

  if sprite_path != "" then texture.loadFromFile(sprite_path)

  def rightClicked(grid: Array[Array[List[GameObject]]], destx: Int, desty: Int): Unit = ()

  def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.position = Vector2[Float]((pos.x - 2) * 40, (pos.y - 2) * 40)
      window.draw(sprite)

  def toTexture(): Texture =
    val tmp = Texture()
    tmp.loadFromFile(sprite_path)
    tmp

  def destroy(): Unit =
    texture.close()
