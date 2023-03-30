package machine.go.movable.characters.mathematiciens


import sfml.graphics.*
import sfml.system.Vector2
import machine.go.GameObject
import machine.go.movable.Movable
import machine.scene.{Point, GameMap, AStar}
import machine.go.printable.movable.characters.Fighters

class Mathematician(position: Point) extends Fighters(position, "moving_objects/characters/matheux_1.png"):

  waitTimeMove = 50
  waitTimeResources = 50
  health = 500
  damage = 100

  override def draw(window: RenderWindow): Unit =
    println(pos)
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.scale(0.2, 0.2)
      sprite.position = Vector2[Float](pos.x * 40, pos.y * 40)
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

  def tp(scene: GameMap, dest: Point): Unit =
      val elemOnPos = scene.getAtPos(dest.x, dest.y)
      println("I got elemOnPos")
      elemOnPos match
        case t::q =>
          println("un autre truc")
          if t.isSuperposable then
            scene.place_sthg(this, dest)
            scene.removeSthg(this, this.pos)
            scene.getAtPos(this.pos.x, this.pos.y).foreach(println(_))
            this.pos = dest
        case _ =>
          scene.place_sthg(this, dest)
          scene.removeSthg(this, this.pos)
          scene.getAtPos(this.pos.x, this.pos.y).foreach(println(_))
          println(this.pos)
          this.pos = dest
          println(dest == this.pos) // false !!!!
          println(this.pos)
          scene.getAtPos(this.pos.x, this.pos.y).foreach(println(_))

  override def rightClicked(scene: GameMap, dest: Point): Unit =
    println("j'ai atteint tp")
    tp(scene, dest)
    println("j'ai fini tp")
