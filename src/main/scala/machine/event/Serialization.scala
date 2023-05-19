package machine.event

import java.io.*
import machine.scene.GameMap
import java.nio.file.*
import machine.go.invisible.*
import scala.io.Source
import sfml.graphics.RenderWindow
import affichage.Resources
import machine.scene.Point
import affichage.Button

// add @SerialiseUID(101)
// add @transient before we don'tr want

object Serialization:

  def savePos(scene: GameMap, i: Int, j: Int): String =
    scene.getAtPos(i, j) match
      case t :: q =>
        s"-$i,$j:" + (t :: q).foldLeft("") { (acc, go) => acc + "+" + go.name }
      case _ => ""

  def saveInventory(scene: GameMap): String =
    scene.actors.gamer.inventory.getResourceAmount(Money).toString() + "+" + scene.actors.gamer.inventory
      .getResourceAmount(Beton)
      .toString()

  def saveShape(scene: GameMap): String =
    scene.height.toString() + "-" + scene.width.toString() + "#" +
      scene.ratio.x.toString() + "-" + scene.ratio.y.toString()

  def stringOfScene(scene: GameMap): String =
    var rezString = saveInventory(scene) + "#"
    for row <- 0 until scene.width; col <- 0 until scene.height do rezString += savePos(scene, row, col)
    rezString += "#"
    rezString += saveShape(scene)
    rezString

  def save(scene: GameMap, name: String = "") =
    val pPath = "./savedFiles/"
    val ext = ".save"
    val path = Paths.get("./savedFiles/")
    if !(Files.exists(path) && Files.isDirectory(path)) then Files.createDirectory(path)
    val newName =
      if name != "" then name
      else
        var k = 0
        while Files.exists(Paths.get(pPath + s"unnamedFile$k$ext")) do k += 1
        s"unnamedFile$k"

    val pw = new PrintWriter(new File(pPath + newName + ext))
    pw.write(stringOfScene(scene))
    pw.close

  def findAllPossiblePaths(): List[String] =
    val path = Paths.get("./savedFiles/")
    if !(Files.exists(path) && Files.isDirectory(path)) then Files.createDirectory(path)
    var compt = 0
    File("./savedFiles/").listFiles.filter(_.isFile).toList.map(_.getName).filter { x =>
      compt += 1
      compt < 4
    }

  def chooseTheSave(scene: GameMap, window: RenderWindow, mousePos: Point) = ()

  def loadGameMap(path: String): GameMap =
    try
      val fileContents = Source.fromFile(path).getLines.mkString
      // [0] -> inventory [1] -> elements on map [2] -> shape [3] -> ratio
      val differentVar = fileContents.split("#")
      val shapes = differentVar(2).split("-")
      val (shapeX, shapeY) = (shapes(0).toInt, shapes(1).toInt)
      val ratios = differentVar(3).split("-")
      val (ratioX, ratioY) = (ratios(0).toInt, ratios(1).toInt)
      val player = Player("Héro")
      val amountOfResources = differentVar(1).split("-")
      val (amountMoney, amountBeton) = (amountOfResources(0).toInt, amountOfResources(1).toInt)
      player.inventory.addResource(Money, amountMoney)
      player.inventory.addResource(Beton, amountBeton)
      val enemy = Player("Çontralien")
      GameMap(Scalaseries.giveAGoodGridWithNoNullToThisManPlease(shapeX, shapeY), (ratioX, ratioY), (player, enemy))

    catch
      case _ =>
        println("Impossible to open path")
        null
