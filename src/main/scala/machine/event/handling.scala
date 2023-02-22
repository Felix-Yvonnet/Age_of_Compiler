package machine.event

import machine.`object`.GameObject
import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2

class Input(
    val keyboard: Map[Keyboard.Key, Int],
    var x: Int,
    var y: Int,
    var xrel: Int,
    var yrel: Int,
    var xwheel: Int,
    var ywheel: Int,
    var mouse: Array[Int]
)


object Handler :

    def handleEvent(window : Window, status : Input, grid: Array[Array[Option[GameObject]]]) =
        for event <- window.pollEvent() do
            event match {
                case _: Event.Closed                                     => window.close()
                case Event.KeyPressed(c, _, _, _, _): Event.KeyPressed   => status.keyboard.updated(c, 1)
                case Event.KeyReleased(c, _, _, _, _): Event.KeyReleased => status.keyboard.updated(c, 0)
                case Event.MouseMoved(x, y): Event.MouseMoved            => status.x = x; status.y = y
                case Event.MouseWheelScrolled(wheel, delta, x, y)        => status.xwheel = x; status.ywheel = y
                case Event.MouseButtonPressed(Mouse.Button.Left, x, y)   => status.mouse(0) = 1
                case Event.MouseButtonPressed(Mouse.Button.Right, x, y)  => status.mouse(0) = 1
                case _                                                   => ()
            }
