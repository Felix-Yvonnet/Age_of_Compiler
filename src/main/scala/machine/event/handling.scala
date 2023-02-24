package machine.event

import machine.`object`.GameObject
import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.`object`.movable.Movable
import machine.`object`.movable.characters.Player

class Input(
    val keyboard: Map[Keyboard.Key, Int],
    var mousex: Int,
    var mousey: Int,
    var selected: Option[GameObject]
)


object Handler :

    def handleEvent(window : RenderWindow, status : Input, grid: Array[Array[List[GameObject]]], player : Player) =
        for event <- window.pollEvent() do
            event match {
                case _: Event.Closed                                     => window.close()
                case Event.KeyPressed(c, _, _, _, _): Event.KeyPressed   => status.keyboard.updated(c, 1)
                case Event.KeyReleased(c, _, _, _, _): Event.KeyReleased => status.keyboard.updated(c, 0)
                case Event.MouseMoved(x, y): Event.MouseMoved            => status.mousex = x; status.mousey = y
                case Event.MouseButtonPressed(Mouse.Button.Left, x, y)   => {
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
                }
                    
                case Event.MouseButtonPressed(Mouse.Button.Right, x, y)  => {
                    status.selected match
                        case Some(gO) =>
                            gO.tp(grid,x/40,y/40,player)
                            // gO.addPath(grid, x, y)
                        case _ => ()
                }

                    
                case _                                                   => ()
            }
    


    def handlePrint(window : RenderWindow, grid: Array[Array[List[GameObject]]] ) : Unit = 
        for i <- grid.length-1 to 0 by -1 do 
            for j <- grid(i).length-1 to 0 by -1 do
                val someGO = grid(i)(j)
                someGO match
                    case value::q => handlePrint(window, someGO) //someGO.foreach { println }; value.draw(window)
                    case _ => ()
    def handlePrint(window : RenderWindow, listGO : List[GameObject]) : Unit =
        listGO match {
            case List() => ()
            case List(gO) => gO.draw(window)
            case gO :: q => handlePrint(window, q); gO.draw(window)
            case null => ()
        } 