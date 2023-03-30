package machine.go

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.scene.Point
import machine.scene.GameMap

class GameObject(var pos: Point = Point(0,0), var sprite_path: String = "") :
  var isSelectable: Boolean = false
  var isSuperposable: Boolean = true
  val pathToTextures = "src/resources/"
  var health: Int = 0

  val texture = Texture()

  if sprite_path != "" then texture.loadFromFile(pathToTextures + sprite_path)

  def rightClicked(scene: GameMap, dest: Point): Unit = ()

  def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.position = Vector2[Float]((pos.x - 2) * 40, (pos.y - 2) * 40)
      window.draw(sprite)

  def toTexture(): Texture =
    val tmp = Texture()
    tmp.loadFromFile(sprite_path)
    tmp

  def action(scene: GameMap) : Unit =
    ()
  def isAttacked(damage: Int, scene: GameMap) : Unit =
    if health - damage <= 0 then
      scene.removeSthg(this, this.pos)
      this.destroy()
    else health = health - damage

  def destroy(): Unit =
    texture.close()
