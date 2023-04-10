package machine.go

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.scene.Point
import machine.scene.GameMap
import machine.go.invisible.ResourceType

class GameObject(var pos: Point = Point(0, 0), var sprite_path: String = ""):
  // The general class giving basic properties for all its child
  var isSuperposable: Boolean = true
  val pathToTextures = "src/resources/"
  var isEnemy: Boolean = false
  var isFriendly: Boolean = false
  var isAlive: Boolean = false
  var existsInTheGame: Boolean = true
  var name: String = ""

  // Get the texture of the object (will be moved later in order to have one texture for all characters of a specific type)
  val texture = Texture()
  if sprite_path != "" then texture.loadFromFile(pathToTextures + sprite_path)

  // What happens when we right click while selected
  def rightClicked(scene: GameMap, dest: Point): Unit = ()

  // How to draw basically
  def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.position = ((pos.x) * 40, (pos.y) * 40)
      window.draw(sprite)

  def drawSelected(window: RenderWindow): Unit = ()
  def prompted(place: Point, scene: GameMap): Unit = ()

  // All things to do without a human action
  def action(scene: GameMap): Unit = ()

  // What happens when attacked (couldn't put it in the Alive trait only)
  def isAttacked(damage: Int, scene: GameMap): Boolean = false

  // destroy the texture to avoid memory problems
  def destroy(): Unit =
    existsInTheGame = false
    this.texture.close()
