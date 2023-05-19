import sfml.graphics.*
import sfml.window.*
import scala.util.Using
import sfml.system.Vector2

import machine.event.{Scalaseries, Input, Handler}
import machine.go.printable.movable.characters.friendly.units.mathematicians.Mathematician
import machine.go.printable.fixed.decoration.Wall
import machine.go.printable.fixed.resource.Tree
import machine.scene.Point
import machine.scene.GameMap
import sfml.Immutable
import machine.go.invisible.Player
import machine.event.Scalaseries
import machine.go.printable.movable.characters.enemy.Centralien
import machine.go.printable.fixed.buildings.friendly.GeorgesSand
import scala.collection.immutable.Queue
import machine.go.printable.movable.characters.friendly.units.physiciens.Physicien
import affichage.design.RandomForest
import machine.go.printable.fixed.buildings.enemy.Centrale
import machine.go.printable.fixed.buildings.friendly.towers.TeslaBuilding
import affichage.design.DrawInitial
import affichage.design.DrawCharacters
import affichage.Resources
import machine.go.printable.fixed.buildings.friendly.*

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
    // size of the window
    val width = 1200
    val height = 800
    // size of the map
    val shapeX = 30
    val shapeY = 20
    // ratio to convert from one to another
    val ratioX = width / shapeX
    val ratioY = height / shapeY
    // views
    val viewForTheWorld = View((0, 0, width, height))
    viewForTheWorld.viewport = (0.0, 0.0, 1.0, 0.80)
    val viewForTheCommentaries = View((0, 0, width, height * 2 / 10))
    viewForTheCommentaries.viewport = (0.0, 0.80, 1.0, 0.20)

    // the window that will be shown
    val window = use(RenderWindow(VideoMode(width, height), "Age of Compilers"))
    window.verticalSync = true
    window.framerateLimit = 30

    // Define the playing characters
    val player = Player("Héro")
    val enemy = Player("Çontralien")
    // define the game map
    val scene = GameMap(Scalaseries.giveAGoodGridWithNoNullToThisManPlease(shapeX, shapeY), (ratioX, ratioY), (player, enemy))

    /* one texture to rule them all
    val tileMapTexture = Texture()
    tileMapTexture.loadFromFile("src/resources/fixed_objects/Tilemap/tilemap.png")
    val trucATester = Sprite(tileMapTexture)
    trucATester.textureRect = (5 * 16 + 5, 0, 16, 16 + 2)
    trucATester.scale(2, 2)
    trucATester.position = (11 * 40, 11 * 40)
     */

    val mat = LSV(Point(3, 3))
    scene.placeSthg(mat)
    val ru = RU(Point(20, 0))
    scene.placeSthg(ru)
    val fstCont = Centralien(Point(22, 0))
    scene.placeSthg(fstCont)
    DrawInitial.drawInit(scene)
    RandomForest.placeForests(scene)

    window.view = Immutable(viewForTheWorld)
    val handler = Handler(window, scene, ratioX, ratioY, viewForTheWorld, viewForTheCommentaries)
    while window.isOpen() do
      handler.handleEvent()
      window.clear()

      handler.handleAction()
      handler.handlePrint()
      window.display()
  }.get
