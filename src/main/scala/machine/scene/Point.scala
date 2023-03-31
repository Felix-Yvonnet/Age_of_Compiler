package machine.scene

import sfml.system.Vector2

case class Point(x: Int, y: Int) {
  def distanceTo(other: Point): Double = {
    math.sqrt(math.pow(x - other.x, 2) + math.pow(y - other.y, 2))
  }

  def /(other : Vector2[Int]) : Point = 
    Point(x / other.x, y / other.y)
  
  def getNeighboursIn(scene: GameMap): List[Point] = 
    val (x, y) = (this.x, this.y)

    val neighbors = List((x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1))

    neighbors.filter { case (i, j) =>
      i >= 0 && j >= 0 && i < scene.grid.length && j < scene.grid(i).length && scene.grid(i)(j).forall(_.isSuperposable)
    }.map { case (i, j) => Point(i, j) }


  def to(goal: Point, scene: GameMap) : Option[Point] =
    AStar.search(this, goal, scene) match
      case t::q => Some(t)
      case _ => None
    
}


object Point :
  def apply(vect : Vector2[Float]) : Point = 
    Point(vect.x.toInt, vect.y.toInt)
