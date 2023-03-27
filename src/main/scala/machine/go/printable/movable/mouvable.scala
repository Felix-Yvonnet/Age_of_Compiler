package machine.go.movable

import machine.go.GameObject

trait Movable extends GameObject:
  isSelectable = true
  var waitTimeMove : Int
  var waitTimeResources : Int
  def tp(grid: Array[Array[List[GameObject]]], destx: Int, desty: Int): Unit
