package machine.scene

import scala.collection.mutable.Queue
import sfml.system.Vector2
import scala.collection.mutable.Set
import machine.go.movable.Movable
import machine.go.GameObject

class GameMap (val grid: Array[Array[List[GameObject]]], val ratio: Vector2[Int]):
        
  def listMem(liste : List[GameObject], elem: GameObject) : Boolean =
    liste match
      case t :: q => t == elem || listMem(q, elem)
      case _ => false

  def place_sthg(thing: GameObject, pos: Point): Unit =
    if !listMem(grid(pos.x)(pos.y), thing) then
      grid(pos.x)(pos.y) match
        case t :: q => grid(pos.x)(pos.y) = thing :: grid(pos.x)(pos.y)
        case _ => grid(pos.x)(pos.y) = List(thing)

  def removeSthg(thing: GameObject, pos: Point): Unit =
    grid(pos.x)(pos.y) = grid(pos.x)(pos.y).filter(_ != thing)

  def getAtPos(i: Int, j: Int) : List[GameObject] =
    if i <0 || i >= grid.length then
      println((s"The ith coordinate is bad : i=$i"))
      throw new Exception(s"The ith coordinate is bad : i=$i")
    if j <0 || j >= grid(0).length then
      println(s"The jth coordinate is bad : j=$j")
      throw new Exception(s"The jth coordinate is bad : j=$j")
    grid(i)(j) match
          case go :: q => grid(i)(j)
          case _ => Nil

