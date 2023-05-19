package machine.scene

import scala.collection.mutable.Queue
import sfml.system.Vector2
import scala.collection.mutable.Set
import machine.go.printable.movable.Movable
import machine.go.GameObject
import machine.go.invisible.Player
import machine.go.printable.fixed.buildings.friendly.Technology
import machine.scene.Point
import machine.go.printable.movable.characters.enemy.Centralien

final class GameMap(val grid: Array[Array[List[GameObject]]], val ratio: Vector2[Int], val vectActors: (Player, Player)):
  // The map of the world and every actors present in it

  var name = ""

  val width = grid.length
  val height = grid(0).length
  case class Actors(gamer: Player, enemy: Player)
  val actors = Actors(vectActors._1, vectActors._2)

  def placeSthg(thing: GameObject, pos: Point): Unit =
    if !(grid(pos.x)(pos.y) contains thing) then
      grid(pos.x)(pos.y) match
        case t :: q => grid(pos.x)(pos.y) = thing :: grid(pos.x)(pos.y)
        case _      => grid(pos.x)(pos.y) = List(thing)

  def placeSthg(thing: GameObject): Unit =
    placeSthg(thing, thing.pos)

  def removeSthg(thing: GameObject, pos: Point): Unit =
    grid(pos.x)(pos.y) = grid(pos.x)(pos.y).filter(_ != thing)

  def removeSthg(thing: GameObject): Unit =
    removeSthg(thing, thing.pos)

  def getAtPos(i: Int, j: Int): List[GameObject] =
    if i < 0 || i >= width then
      println((s"The ith coordinate is bad : i=$i"))
      throw new Exception(s"The ith coordinate is bad : i=$i")
    if j < 0 || j >= height then
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
  def isAccessible(positionx: Int, positiony: Int): Boolean =
    isAccessible(Point(positionx, positiony))

  def allClotherThan(point: Point, range: Int): List[Point] =
    allClotherThan(point, point, range, Set(point))

  def allClotherThan(currentPos: Point, initial: Point, range: Int, seen: Set[Point]): List[Point] =
    if (initial distanceManTo currentPos) >= range then Nil
    else
      val neighbours = (currentPos getAllNeighboursIn this).filter(pos => {
        !(seen contains pos) &&
        (pos isWellFormedIn this)
      })
      neighbours foreach (seen += _)
      neighbours ++ neighbours.map(allClotherThan(_, initial, range, seen)).flatten

  def searchClosePlaceToPutUnits(originalPos: Point): Option[Point] =
    // val queue = scala.collection.mutable.Queue(originalPos)
    // val seen = Set[Point]()
//
    // while !queue.isEmpty do
    //   val current = queue.dequeue()
    //   this getAtPos current match
    //     case head :: next => if head.isSuperposable then return Some(current)
    //     case Nil          => return Some(current)
//
    //   val neighbours = (current getAllNeighboursIn this).filter(position => {
    //     this getAtPos position match
    //       case t :: q => t.isSuperposable || t.isInstanceOf[Centralien]
    //       case Nil    => true
    //   })
//
    //   neighbours.foreach(pos => {
    //     seen += pos
    //     queue.enqueue(pos)
    //   })

    searchClosePlaceToPutUnits(originalPos, Set[Point]())

  def searchClosePlaceToPutUnits(oldPos: Point, seen: Set[Point]): Option[Point] =
    getAtPos(oldPos) match
      case Nil                                         => Some(oldPos)
      case t :: q if (t :: q).forall(_.isSuperposable) => Some(oldPos)
      case _ =>
        seen += oldPos
        if getAtPos(oldPos).filter(_.maxLife > 0) != Nil then
          (oldPos getAllNeighboursIn this)
            .filter(pos => !(seen contains pos))
            .map(searchClosePlaceToPutUnits(_, seen).getOrElse(Point(-1, -1)))
            .filter(_.isPos()) match
            case Nil    => None
            case t :: q => Some(t)
        else None

  def findClosestEnemy(initialPos: Point): Option[Point] =
    val queue = scala.collection.mutable.Queue(initialPos)
    val seen = Set[Point]()

    while !queue.isEmpty do
      val current = queue.dequeue()
      if (this getAtPos current).filter(_.isInstanceOf[Centralien]) != Nil then return Some(current)

      val neighbours = (current getAllNeighboursIn this).filter(position => {
        this getAtPos position match
          case t :: q => t.isSuperposable
          case Nil    => true
      })

      neighbours.foreach(pos => {
        seen += pos
        queue.enqueue(pos)
      })
    None

  def getTechLevel(tech: Technology): Boolean =
    this.actors.gamer.hasUnlockedTech.getOrElse(tech, false)
  def unlockTech(tech: Technology): Unit =
    this.actors.gamer.hasUnlockedTech(tech) = true
