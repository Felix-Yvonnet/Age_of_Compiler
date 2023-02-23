package machine.`object`

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import Scene.Scene


class GameObject(var sprite_path : String = "") :
  var typ = 0
  var pos = Vector2[Int](0,0)
  var isMovable : Boolean = false
  var waitTimeMove : Int = 0
  var speed : Int = 0
  var waitTimeResources : Int = 0
  var agility : Int = 0 // speed for extracting resources
  var isSuperposable : Boolean = true


  val texture = Texture()
  texture.loadFromFile(sprite_path)

  private var nextPos : List[Vector2[Int]] = List()

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

  def tp(grid : Array[Array[List[GameObject]]], destx : Int, desty : Int): Unit =
    if 0 <= destx && 0<= desty && 30>destx && 20>desty then
      grid(destx)(desty) match {
        case gO::q => 
          if gO.isSuperposable then
            grid(destx)(desty) = this::gO::q
            grid(this.pos.x)(this.pos.y) = grid(this.pos.x)(this.pos.y).tail
          else println("Looks like there is something there")
        case _ => 
          grid(destx)(desty) = List(this)
          grid(this.pos.x)(this.pos.y) = grid(this.pos.x)(this.pos.y).tail
          this.pos = Vector2[Int](destx,desty)
          // println("everything looks right here")
      }
    else
      println("Revois ta taille Félix")   


  def addPath(grid : Array[Array[List[GameObject]]], destx : Int, desty : Int) : Unit =
    val tmp = Scene.bfs(grid, pos, Vector2[Int](destx,desty))
    tmp match
      case Some(listPath) => nextPos = listPath
      case _ => ()


  def draw(window: RenderWindow): Unit = 
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.position = Vector2[Float]((pos.x-2)*40,(pos.y-2)*40)
      window.draw(sprite)
  
  def toTexture() : Texture =
    val tmp = Texture()
    tmp.loadFromFile(sprite_path)
    tmp
  
    

  

