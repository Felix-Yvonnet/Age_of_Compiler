import sfml.graphics.*
import sfml.window.*
import scala.util.Using
import sfml.system.Vector2

import machine.go.GameObject
import machine.event.{Input, Handler}
import machine.go.movable.characters.mathematiciens.Mathematician
import machine.go.printable.fixed.decoration.Wall
import machine.go.printable.fixed.resources.Tree
import machine.scene.Point
import machine.scene.GameMap
import sfml.Immutable
import machine.go.invisible.Player
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
  Using.Manager { use =>
    val width = 1200
    val height = 800
    val shapeX = 30
    val shapeY = 20
    val ratioX = width / shapeX
    val ratioY = height / shapeY
    val view = View((0, 0, width, height))

    val window = use(RenderWindow(VideoMode(width, height), "Age of Compilers"))

    val scene = GameMap(Array.ofDim[List[GameObject]](shapeX, shapeY))
    val mat = Mathematician()
    scene.place_sthg(mat, mat.pos)
    val walle = Wall()
    walle.pos = Point(11, 0)
    scene.place_sthg(walle, walle.pos)
    val walle2 = Wall()
    walle2.pos = Point(12, 0)
    scene.place_sthg(walle2, walle2.pos)
    val walle3 = Wall()
    walle3.pos = Point(13, 0)
    scene.place_sthg(walle3, walle3.pos)

    val tree = Tree(Point(10,10))
    scene.place_sthg(tree, tree.pos)

    val player = Player("Héro")
    val handler = Handler(window, scene.grid, ratioX, ratioY, view)
    while window.isOpen() do
      // window.view = Immutable(view)
      handler.handleEvent()
      window.clear()


      player.draw(window)
      handler.handlePrint()
      window.display()
  }