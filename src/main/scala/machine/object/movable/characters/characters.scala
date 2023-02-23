package machine.`object`.movable.characters

import sfml.system.Vector2
import machine.`object`.movable.Movable
import machine.`object`.GameObject
import Scene.Scene

<<<<<<< HEAD
class Player(sprite_path : String) extends Movable :
    private val waitPerMove = 0
    private var stateMove = 0
    private var nextPos : List[Vector2[Float]] = List()
    private var indice = 100
    def move(): Unit =
        next_pos match
            case List() => ()
            case t::q => pos += t
=======
class Pown(sprite_path : String) extends GameObject(sprite_path) with Movable :
    

>>>>>>> b52d27b36263fc1a003b8a86ddd77919dac1608f
    
    


    

        