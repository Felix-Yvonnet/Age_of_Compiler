package machine.`object`

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import Scene.Scene


class GameObject(var sprite_path : String = "") :
  var typ = 0
  var pos = Vector2[Int](0,0)
  var isMovable : Boolean = false


  private var nextPos : List[Vector2[Int]] = List()

  def move(grid : Array[Array[Option[GameObject]]]): Unit =
      nextPos match
          case List() => ()
          case t::q => 
              val tmp = grid(pos.x)(pos.y)
              grid(pos.x)(pos.y) = grid(t.x)(t.y)
              grid(t.x)(t.y) = tmp
              pos = t

  def addPath(grid : Array[Array[Option[GameObject]]], destx : Int, desty : Int) : Unit =
    val tmp = Scene.bfs(grid, pos, Vector2[Int](destx,desty))
    tmp match
      case None => ()
      case Some(listPath) => nextPos = listPath



  def draw(window: RenderWindow): Unit = 
    val txture = Texture()
    txture.loadFromFile(sprite_path)
    val sprite = Sprite(txture)
    sprite.position = Vector2[Float]((pos.x+1)*40,(pos.y+1)*40)
    window.draw(sprite)
    txture.close()


  def toTexture(path : String) : Texture =
    val tmp = Texture()
    tmp.loadFromFile(path)
    tmp
  
  def toTexture() : Texture =
    val tmp = Texture()
    tmp.loadFromFile(sprite_path)
    tmp
  
    

  

