package machine.`object`.movable

import machine.`object`.GameObject

trait Movable extends GameObject :
    isMovable = true
    def move(grid : Array[Array[Option[GameObject]]]): Unit
    def addPath(grid : Array[Array[Option[GameObject]]], destx : Int, desty : Int) : Unit
