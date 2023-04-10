package machine.scene

import sfml.system.Vector2
import machine.scene.AStar
import machine.scene.GameMap
import machine.go.printable.movable.Movable

case class Point(x: Int, y: Int):
  // Some useful functions to deal with position in the world

  def distanceTo(other: Point): Double =
    math.sqrt(math.pow(x - other.x, 2) + math.pow(y - other.y, 2))

  def /(other: Vector2[Int]): Point =
    Point(x / other.x, y / other.y)

  def getNeighboursIn(scene: GameMap): List[Point] =
    val (x, y) = (this.x, this.y)

    val neighbors = List((x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1))

    neighbors
      .filter { case (i, j) =>
        i >= 0 && j >= 0 && i < scene.width && j < scene.height && scene.grid(i)(j).forall(_.isSuperposable)
      }
      .map { case (i, j) => Point(i, j) }

  def getAllNeighboursIn(scene: GameMap): List[Point] =
    val (x, y) = (this.x, this.y)

    val neighbors = List((x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1))

    neighbors
      .map { case (i, j) => Point(i, j) }
      .filter(_ isWellFormedIn scene)

  def to(goal: Point, scene: GameMap, rangeAttack: Int): Option[Point] =
    AStar.search(this, goal, scene, rangeAttack) match
      case t :: q => Some(t)
      case _      => None

  def isWellFormedIn(scene: GameMap) =
    this.x >= 0 && this.y >= 0 && this.x < scene.width && this.y < scene.height

  def isPos() = this.x >= 0 && this.y >= 0

object Point:
  def apply(vect: Vector2[Float]): Point =
    Point(vect.x.toInt, vect.y.toInt)
