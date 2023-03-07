package machine.`object`

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import Scene.Scene
import machine.`object`.movable.characters.Player
import machine.`object`.fixed.resources.Tree

class GameObject(var sprite_path: String = ""):
  var typ = 0
  var pos = Vector2[Int](0, 0)
  var isMovable: Boolean = false
  var waitTimeMove: Int = 0
  var speed: Int = 0
  var waitTimeResources: Int = 0
  var agility: Int = 0 // speed for extracting resources
  var isSuperposable: Boolean = true

  val texture = Texture()

  if sprite_path != "" then texture.loadFromFile(sprite_path)

  private var nextPos: List[Vector2[Int]] = List()

  def rightClicked(grid: Array[Array[List[GameObject]]], destx: Int, desty: Int, player: Player): Unit =
    ()
  /*
  def move(grid : Array[Array[List[GameObject]]]): Unit =
    nextPos match
        case List() => ()
        case t::q =>
          if waitTimeMove == 0 then
            val tmp = grid(pos.x)(pos.y)
            grid(pos.x)(pos.y) = grid(t.x)(t.y)
            grid(t.x)(t.y) = tmp
            pos = t
            waitTimeMove = speed
          waitTimeMove -= 1
   */


  def addPath(grid: Array[Array[List[GameObject]]], destx: Int, desty: Int): Unit =
    val tmp = Scene.bfs(grid, pos, Vector2[Int](destx, desty))
    tmp match
      case Some(listPath) => nextPos = listPath
      case _              => ()

  def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.position = Vector2[Float]((pos.x - 2) * 40, (pos.y - 2) * 40)
      window.draw(sprite)

  def toTexture(): Texture =
    val tmp = Texture()
    tmp.loadFromFile(sprite_path)
    tmp
