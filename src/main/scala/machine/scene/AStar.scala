package machine.scene

import machine.go.GameObject
import scala.collection.mutable.PriorityQueue

object AStar:
  type Cost = Double

  case class Node(point: Point, var parent: Option[Node], var h: Cost, var cost: Cost)

  def search(start: Point, goal: Point, scene: GameMap, rangeAttack: Int): List[Point] =

    val openQueue = PriorityQueue.empty[Node](Ordering.by(-_.h))
    val closedSet = scala.collection.mutable.Set[Point]()
    openQueue += Node(start, None, 0, 0)

    closedSet add start

    while openQueue.nonEmpty do
      val current = openQueue.dequeue()

      val currentDistance = current.point distanceTo goal
      if currentDistance == 0 || (currentDistance <= rangeAttack && !(scene isAccessible goal)) then return getPath(current, start)

      val neighbors = current.point getNeighboursIn scene

      neighbors.foreach { neighbor =>
        if !(closedSet contains neighbor) then
          closedSet add neighbor

          if openQueue.forall(x => { x.point != neighbor || x.cost >= current.cost }) then
            openQueue += Node(neighbor, Some(current), current.cost + 1 + neighbor.distanceTo(goal), current.cost + 1)

      }

    Nil

  def getPath(node: Node, start: Point): List[Point] = {
    node.parent match {
      case Some(parent) =>
        if parent.point == start then List(node.point)
        else getPath(parent, start) :+ node.point
      case None => List(node.point)
    }
  }
