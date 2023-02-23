import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2

import machine.`object`.movable.characters.Pown
import Scene.*
import machine.`object`.GameObject
import machine.event.{Input, Handler}
import machine.`object`.movable.characters.mathematiciens.Mathematician

/*
val map = scala.collection.mutable.HashMap.empty[Int,String]

val m2 = Map[String, Int]()
val () = println(m2("1"))
val () = println(m2 contains "1")


//println(Mouse.position)
//println(System.currentTimeMillis())

//
//    val sprite2 = Sprite(texture)
//    sprite2.move(100, 400)
//

//    val sprite = Sprite(texture)
//    sprite.textureRect = (0,0,600,600)
//    sprite.move(500, 400)
//    sprite.position = Vector2(100, 10)
//    sprite.rotation = 90.0
//    sprite.scale(0.3, 0.3)


val texture = Texture()
texture.loadFromFile("src/resources/fixed_objects/cat.png")
texture.repeated = true
texture.smooth = true

val char = Pown("src/resources/fixed_objects/cat.png")
char.pos = Vector2[Int](100,10)

 */



@main def main =
    val window = RenderWindow(VideoMode(1200, 800), "Hello world")

    val scene = Scene(Array.ofDim[Option[GameObject]](30,20))
    val mat = Mathematician()
    scene.place_sthg(mat,mat.pos)


    val status = Input(Map[Keyboard.Key, Int]().empty, 0, 0, None)
    while window.isOpen() do
        Handler.handleEvent(window, status, scene.grid)
        window.clear(Color.Black())

        Handler.handlePrint(window, scene.grid)
        //mat.draw(window)
        window.display()