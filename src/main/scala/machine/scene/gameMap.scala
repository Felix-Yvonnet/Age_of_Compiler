package machine.scene

import scala.collection.mutable.Queue
import sfml.system.Vector2
import scala.collection.mutable.Set
import machine.go.movable.Movable
import machine.go.GameObject

class GameMap(val grid: Array[Array[List[GameObject]]]):

  def place_sthg(thing: GameObject, pos: Point): Unit =
    grid(pos.x)(pos.y) = thing :: grid(pos.x)(pos.y)

  def remove_sthg(thing: GameObject, pos: Point): Unit =
    grid(pos.x)(pos.y) = grid(pos.x)(pos.y).filter(_ != thing)

