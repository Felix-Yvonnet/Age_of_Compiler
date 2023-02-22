package machine.`object`.movable.characters

import sfml.system.Vector2
import machine.`object`.movable.Movable
import machine.`object`.GameObject

class Pown(sprite_path : String) extends GameObject(sprite_path) with Movable :
    var waitTime = 0
    private var next_pos : List[Vector2[Float]] = List()
    private var indice = 100

    def move(): Unit =
        next_pos match
            case List() => ()
            case t::q => pos += t
    
    


    

        