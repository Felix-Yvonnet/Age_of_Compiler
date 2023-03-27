package machine.scene

import sfml.system.Vector2

case class Point(x: Int, y: Int) {
  def distanceTo(other: Point): Double = {
    math.sqrt(math.pow(x - other.x, 2) + math.pow(y - other.y, 2))
  }

  def /(other : Vector2[Int]) : Point = {
    Point(x / other.x, y / other.y)
  }
}


object Point :
  def apply(vect : Vector2[Float]) : Point = 
    Point(vect.x.toInt, vect.y.toInt)
