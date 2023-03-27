package machine.go.movable.characters.mathematiciens


import sfml.graphics.*
import sfml.system.Vector2
import machine.go.GameObject
import machine.go.movable.Movable
import machine.scene.Point
import machine.scene.AStar

class Mathematician(sprite_path: String = "src/resources/moving_objects/characters/matheux_1.png") extends GameObject(sprite_path) with Movable:

  var waitTimeMove : Int = 100
  var waitTimeResources : Int = 20
  pos = Point(10, 5)

  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.2, 0.2)
      sprite.position = Vector2[Float](pos.x, pos.y)
      window.draw(sprite)


  def move(to: Point, grid: Array[Array[List[GameObject]]]): Unit = {
      val pathFinder = new AStar(pos, to, grid)

      pathFinder.search() match {
        case Some(path) => {
          path.tail.foreach { point =>
            pos = point
            Thread.sleep(500) // simulate movement delay
          }
        }
        case None => println("No path found")
      }
    }


  def tp(grid: Array[Array[List[GameObject]]], destx: Int, desty: Int): Unit =
    println(destx); println(desty); println(grid(destx)(desty))
    grid(destx)(desty) match
      case t::q =>
        println("un autre truc")
        if t.isSuperposable then
          grid(destx)(desty) = List(this)
          grid(this.pos.x)(this.pos.y) = grid(this.pos.x)(this.pos.y).filter(_ != this)
          this.pos = Point(destx, desty)
      case _ => 
        grid(destx)(desty) = List(this)
        println("truc")
        println(this.pos.x); println(this.pos.y)
        println(grid(this.pos.x)(this.pos.y).head)
        
        grid(this.pos.x)(this.pos.y) = grid(this.pos.x)(this.pos.y).filter(_ != this)
        this.pos = Point(destx, desty)

  override def rightClicked(grid: Array[Array[List[GameObject]]], destx: Int, desty: Int): Unit =
    println("j'ai atteint tp")
    tp(grid, destx, desty)
    println("j'ai fini tp")
