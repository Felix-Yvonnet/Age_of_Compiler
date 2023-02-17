package machine.`object`.movable

import machine.`object`.GameObject

trait Movable extends GameObject:
    var waitTime : Int
    def move(): Unit