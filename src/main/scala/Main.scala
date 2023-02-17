import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2

import machine.`object`.movable.characters.Player

/*
val map = scala.collection.mutable.HashMap.empty[Int,String]

val m2 = Map[String, Int]()
val () = println(m2("1"))
val () = println(m2 contains "1")
 */

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

@main def main =
    val window = RenderWindow(VideoMode(1024, 768), "Hello world")
    val texture = Texture()
    texture.loadFromFile("src/resources/cat.png")
    texture.repeated = true
    texture.smooth = true

    val sprite = Sprite(texture)
    sprite.textureRect = (0,0,600,600)
    sprite.move(500, 400)
    sprite.position = Vector2(100, 320)
    sprite.rotation = 90.0
    sprite.scale(0.3, 0.3)

    val txture = Texture()
    txture.loadFromFile("src/resources/randomChar1.png")
    val sprite3 = Sprite(txture)
    sprite3.position = Vector2[Float](100,100)

    sprite.move(600, 400)

    val sprite2 = Sprite(texture)
    sprite2.move(100, 400)

    val status = Input(Map[Keyboard.Key, Int]().empty, 0, 0, 0, 0, 0, 0, Array[Int](0, 0, 0, 0, 0, 0))
    while window.isOpen() do
        
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
        //println(Mouse.position)
        //println(System.currentTimeMillis())
        window.clear(Color.Black())
        window.draw(sprite)
        window.draw(sprite2)
        window.draw(sprite3)
        window.display()
