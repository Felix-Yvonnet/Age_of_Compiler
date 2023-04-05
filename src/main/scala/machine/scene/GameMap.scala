package machine.scene

import scala.collection.mutable.Queue
import sfml.system.Vector2
import scala.collection.mutable.Set
import machine.go.printable.movable.Movable
import machine.go.GameObject
import machine.go.invisible.Player

final class GameMap(val grid: Array[Array[List[GameObject]]], val ratio: Vector2[Int], val vectActors: (Player, Player)):
  // The map of the world and every actors present in it

  case class Actors(gamer: Player, enemy: Player)
  val actors = Actors(vectActors._1, vectActors._2)

  def place_sthg(thing: GameObject, pos: Point): Unit =
    if !(grid(pos.x)(pos.y) contains thing) then
      grid(pos.x)(pos.y) match
        case t :: q => grid(pos.x)(pos.y) = thing :: grid(pos.x)(pos.y)
        case _      => grid(pos.x)(pos.y) = List(thing)

  def removeSthg(thing: GameObject, pos: Point): Unit =
    grid(pos.x)(pos.y) = grid(pos.x)(pos.y).filter(_ != thing)

  def getAtPos(i: Int, j: Int): List[GameObject] =
    if i < 0 || i >= grid.length then
      println((s"The ith coordinate is bad : i=$i"))
      throw new Exception(s"The ith coordinate is bad : i=$i")
    if j < 0 || j >= grid(0).length then
      println(s"The jth coordinate is bad : j=$j")
      throw new Exception(s"The jth coordinate is bad : j=$j")
    grid(i)(j) match
      case go :: q => grid(i)(j)
      case _       => Nil

  def getAtPos(point: Point): List[GameObject] =
    getAtPos(point.x, point.y)

  def isAccessible(position: Point): Boolean =
    getAtPos(position) match
      case t :: q => t.isSuperposable
      case _      => true

  def allClotherThan(point: Point, range: Int): List[Point] =
    allClotherThan(point, point, range, Set())

  def allClotherThan(currentPos: Point, initial: Point, range: Int, seen: Set[Point]): List[Point] =
    seen += currentPos
    val neighbours = (currentPos getAllNeighboursIn this).filter(pos => {
      !(seen contains pos) &&
      (pos.x >= 0) && (pos.x < this.grid.length) && (pos.y >= 0) && (pos.y < this.grid(0).length) &&
      ((pos distanceTo initial) <= range)
    })
    neighbours ++ neighbours.map(allClotherThan(_, initial, range, seen)).flatten

  def searchClosePlaceToPutUnits(originalPos: Point): Option[Point] =
    searchClosePlaceToPutUnits(originalPos, Set[Point]())

  def searchClosePlaceToPutUnits(oldPos: Point, seen: Set[Point]): Option[Point] =
    getAtPos(oldPos) match
      case Nil                                         => Some(oldPos)
      case t :: q if (t :: q).forall(_.isSuperposable) => Some(oldPos)
      case _ =>
        seen += oldPos
        if getAtPos(oldPos).filter(_.isAlive) != Nil then
          (oldPos getAllNeighboursIn this)
            .filter(pos => !(seen contains pos))
            .map(searchClosePlaceToPutUnits(_, seen).getOrElse(Point(-1, -1)))
            .filter(_.isPos()) match
            case Nil    => None
            case t :: q => Some(t)
        else None
