package machine.scene

import machine.go.GameObject
import scala.collection.mutable.PriorityQueue

class AStar(start: Point, goal: Point, grid: Array[Array[List[GameObject]]]) {
  type Cost = Double

  case class Node(point: Point, var parent: Option[Node], var f: Cost, var g: Cost)

  val openSet = PriorityQueue.empty[Node](Ordering.by(_.f))
  val closedSet = scala.collection.mutable.Set[Point]()

  def search(): Option[List[Point]] = {
    openSet += Node(start, None, 0, 0)

    while (openSet.nonEmpty) {
      val current = openSet.dequeue()

      if (current.point == goal) {
        return Some(getPath(current))
      }

      closedSet.add(current.point)

      val neighbors = getNeighbors(current.point)

      neighbors.foreach { neighbor =>
        if (!closedSet.contains(neighbor)) {
          val tentativeG = current.g + current.point.distanceTo(neighbor)

          val neighborNode = openSet.find(_.point == neighbor).getOrElse {
            val n = Node(neighbor, Some(current), 0, 0)
            openSet += n
            n
          }

          if (tentativeG < neighborNode.g) {
            neighborNode.parent = Some(current)
            neighborNode.g = tentativeG
            neighborNode.f = neighborNode.g + neighborNode.point.distanceTo(goal)
          }
        }
      }
    }

    None
  }

  def getNeighbors(point: Point): List[Point] = {
    val (x, y) = (point.x, point.y)

    val neighbors = List((x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1))

    neighbors.filter { case (i, j) =>
      i >= 0 && j >= 0 && i < grid.length && j < grid(i).length && grid(i)(j).forall(_.isSuperposable)
    }.map { case (i, j) => Point(i, j) }
  }

  def getPath(node: Node): List[Point] = {
    node.parent match {
      case Some(parent) => getPath(parent) :+ node.point
      case None => List(node.point)
    }
  }
}


