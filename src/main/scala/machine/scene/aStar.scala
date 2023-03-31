package machine.scene

import machine.go.GameObject
import scala.collection.mutable.PriorityQueue

object AStar {
  type Cost = Double

  case class Node(point: Point, var parent: Option[Node], var f: Cost, var g: Cost)

  val openSet = PriorityQueue.empty[Node](Ordering.by(_.f))
  val closedSet = scala.collection.mutable.Set[Point]()

  def search(start: Point, goal: Point, scene: GameMap): List[Point] = {
    openSet += Node(start, None, 0, 0)

    while (openSet.nonEmpty) {
      val current = openSet.dequeue()

      val currentDistance = current.point distanceTo goal
      if (currentDistance == 0 || (currentDistance == 1 && !(scene isAccessible goal)) ) then
        return getPath(current)
      
      closedSet.add(current.point)

      val neighbors = current.point getNeighboursIn scene

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

    Nil
  }


  def getPath(node: Node): List[Point] = {
    node.parent match {
      case Some(parent) => getPath(parent) :+ node.point
      case None => List(node.point)
    }
  }
}


