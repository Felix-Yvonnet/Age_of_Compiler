package machine.event

import machine.`object`.GameObject
import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.`object`.movable.Movable

class Input(
    val keyboard: Map[Keyboard.Key, Int],
    var mousex: Int,
    var mousey: Int,
    var selected: Option[GameObject]
)


object Handler :

    def handleEvent(window : RenderWindow, status : Input, grid: Array[Array[List[GameObject]]]) =
        for event <- window.pollEvent() do
            event match {
                case _: Event.Closed                                     => window.close()
                case Event.KeyPressed(c, _, _, _, _): Event.KeyPressed   => status.keyboard.updated(c, 1)
                case Event.KeyReleased(c, _, _, _, _): Event.KeyReleased => status.keyboard.updated(c, 0)
                case Event.MouseMoved(x, y): Event.MouseMoved            => status.mousex = x; status.mousey = y
                case Event.MouseButtonPressed(Mouse.Button.Left, x, y)   => 
                    println(x/40)
                    println(y/40)
                    grid(x/40)(y/40) match
                        case gO::q => 
                            if gO.isMovable then
                                status.selected = Some(gO)
                                println("selected\n")
                            else status.selected = None
                        case _ => 
                            println("not selected\n")
                            status.selected = None
                    
                case Event.MouseButtonPressed(Mouse.Button.Right, x, y)  => 
                    status.selected match
                        case Some(gO) =>
                            gO.tp(grid,x/40,y/40)
                            // gO.addPath(grid, x, y)
                        case _ => ()

                    
                case _                                                   => ()
            }
    


    def handlePrint(window : RenderWindow, grid: Array[Array[Option[GameObject]]] ) : Unit = 
        for arr <- grid do 
            for someGO <- arr do
                someGO match
                    case Some(value) => value.draw(window)
                    case _ => ()