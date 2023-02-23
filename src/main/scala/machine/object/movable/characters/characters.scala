package machine.`object`.movable.characters

import sfml.system.Vector2
import machine.`object`.movable.Movable
import machine.`object`.GameObject

class Pown(sprite_path : String) extends GameObject(sprite_path) with Movable :
    var waitTime = 0
    private var next_pos : List[Vector2[Int]] = List()
    private var indice = 100

    def move(grid : Array[Array[Option[GameObject]]]): Unit =
        next_pos match
            case List() => ()
            case t::q => 
                val tmp = grid(pos.x)(pos.y)
                grid(pos.x)(pos.y) = grid(t.x)(t.y)
                grid(t.x)(t.y) = tmp
                pos = t

    
    


    

        