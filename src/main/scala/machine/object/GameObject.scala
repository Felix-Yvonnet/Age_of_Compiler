package machine.`object`

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import Scene.Scene


class GameObject(var sprite_path : String = "") :
  var typ = 0
  var pos = Vector2[Int](0,0)
  var isMovable : Boolean = false
  var waitTime : Int = 0
  var speed : Int = 0


  val texture = Texture()
  texture.loadFromFile(sprite_path)

  private var nextPos : List[Vector2[Int]] = List()

  def move(grid : Array[Array[Option[GameObject]]]): Unit =
    nextPos match
        case List() => ()
        case t::q => 
          if waitTime == 0 then
            val tmp = grid(pos.x)(pos.y)
            grid(pos.x)(pos.y) = grid(t.x)(t.y)
            grid(t.x)(t.y) = tmp
            pos = t
            waitTime = speed
          waitTime -= 1
    
  def move(grid : Array[Array[Option[GameObject]]], destx : Int, desty : Int): Unit =
    if 0 <= destx && 0<= desty && 30>destx && 20>desty then
      grid(destx)(desty) match
        case Some(_) => 
        case _ => 
          grid(destx)(desty) = Some(this)
          grid(this.pos.x)(this.pos.y) = None
          this.pos = Vector2[Int](destx,desty)  
      

  def addPath(grid : Array[Array[Option[GameObject]]], destx : Int, desty : Int) : Unit =
    val tmp = Scene.bfs(grid, pos, Vector2[Int](destx,desty))
    tmp match
      case Some(listPath) => nextPos = listPath
      case _ => ()


  def draw(window: RenderWindow): Unit = 
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.position = Vector2[Float]((pos.x+1)*40,(pos.y+1)*40)
      window.draw(sprite)
  
  def toTexture() : Texture =
    val tmp = Texture()
    tmp.loadFromFile(sprite_path)
    tmp
  
    

  

