import sfml.graphics.*
import sfml.window.*
import scala.util.Using
import sfml.system.Vector2

import machine.event.{Scalaseries, Input, Handler}
import machine.go.printable.movable.characters.friendly.units.mathematicians.Mathematician
import machine.go.printable.fixed.decoration.Wall
import machine.go.printable.fixed.resources.Tree
import machine.scene.Point
import machine.scene.GameMap
import sfml.Immutable
import machine.go.invisible.Player
import machine.event.Scalaseries
import affichage.design.DrawDecorations
import machine.go.printable.movable.characters.enemy.Centralien
import machine.go.printable.fixed.buildings.friendly.GeorgesSand
import scala.collection.immutable.Queue
import machine.go.printable.movable.characters.friendly.units.physicians.Physician
import affichage.design.RandomForest
import machine.go.printable.fixed.buildings.enemy.Centrale

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
    val viewForTheCommentaries = View((0, 0, width, height))
    viewForTheCommentaries.viewport = (0.0, 0.80, 1.0, 0.20)

    // the window that will be shown
    val window = use(RenderWindow(VideoMode(width, height), "Age of Compilers"))
    window.verticalSync = true
    window.framerateLimit = 30

    // Define the playing characters
    val player = Player("Héro")
    val enemy = Player("Centralien")
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

    val mat = Physician(Point(3, 3))
    scene.place_sthg(mat, mat.pos)
    val walle = Wall(Point(11, 0))
    scene.place_sthg(walle, walle.pos)
    val walle2 = Wall(Point(12, 0))
    scene.place_sthg(walle2, walle2.pos)
    val walle3 = Wall(Point(13, 0))
    scene.place_sthg(walle3, walle3.pos)

    val tree = Tree(Point(10, 10))
    scene.place_sthg(tree, tree.pos)

    val gs = GeorgesSand(Point(0, 0))
    scene.place_sthg(gs, gs.pos)


    val centrale = Centrale(Point(29, 19))
    scene.place_sthg(centrale, centrale.pos)

    val méchant = Centralien(Point(3, 7))
    scene.place_sthg(méchant, méchant.pos)


    val méchant2 = Centralien(Point(9, 7))
    scene.place_sthg(méchant2, méchant2.pos)

    RandomForest.placeForests(scene)

    window.view = Immutable(viewForTheCommentaries)
    window.draw(Sprite())
    window.view = Immutable(viewForTheWorld)
    val decorationDrawer = DrawDecorations(scene)
    val handler = Handler(window, scene, ratioX, ratioY, viewForTheWorld, viewForTheCommentaries)
    while window.isOpen() do
      RandomForest.addMoreTrees(scene)
      handler.handleEvent()
      window.clear()
      decorationDrawer.drawBaseFloor(window)
      handler.handleAction()
      handler.handlePrint()
      window.display()
  }.get
