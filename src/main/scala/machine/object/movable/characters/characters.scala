package machine.`object`.movable.characters

import sfml.system.Vector2
import machine.`object`.movable.Movable

class Player(sprite_path : String) extends Movable :
    var waitTime = 0
    private var next_pos : List[Vector2[Float]] = List()
    private var indice = 100

    def move(): Unit =
        next_pos match
            case List() => ()
            case t::q => pos += t
    
    


    

        