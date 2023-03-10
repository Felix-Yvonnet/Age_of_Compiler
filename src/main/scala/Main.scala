import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2

import Scene.*
import machine.`object`.GameObject
import machine.event.{Input, Handler}
import machine.`object`.movable.characters.mathematiciens.Mathematician
import machine.`object`.movable.characters.Player
import machine.`object`.fixed.wall.Wall
import machine.`object`.fixed.resources.Tree

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
  val window = RenderWindow(VideoMode(1200, 800), "Age of Compilers")

  val scene = Scene(Array.ofDim[List[GameObject]](30, 20))
  val mat = Mathematician()
  scene.place_sthg(mat, mat.pos)
  val walle = Wall()
  walle.pos = Vector2[Int](11, 0)
  scene.place_sthg(walle, walle.pos)
  val walle2 = Wall()
  walle2.pos = Vector2[Int](12, 0)
  scene.place_sthg(walle2, walle2.pos)
  val walle3 = Wall()
  walle3.pos = Vector2[Int](13, 0)
  scene.place_sthg(walle3, walle3.pos)

  val tree = Tree()
  scene.place_sthg(tree, tree.pos)

  val player = Player("Héro")
  val status = Input(Map[Keyboard.Key, Int]().empty, 0, 0, None)
  while window.isOpen() do
    Handler.handleEvent(window, status, scene.grid, player)
    window.clear(Color.Black())

    Handler.handlePrint(window, scene.grid)
    // mat.draw(window)
    player.draw(window)
    window.display()
