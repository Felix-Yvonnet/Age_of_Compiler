package machine.`object`.movable

import machine.`object`.GameObject
import machine.`object`.movable.characters.Player

trait Movable extends GameObject:
  isMovable = true
  def move(grid: Array[Array[Option[GameObject]]]): Unit
  def addPath(grid: Array[Array[Option[GameObject]]], destx: Int, desty: Int): Unit
  def tp(grid: Array[Array[List[GameObject]]], destx: Int, desty: Int, player: Player): Unit
