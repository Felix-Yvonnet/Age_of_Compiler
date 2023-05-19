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
import machine.go.printable.movable.characters.friendly.units.mathematicians.Mathematician
import machine.go.printable.movable.characters.friendly.units.physiciens.Physicien
import machine.go.printable.movable.characters.enemy._
import machine.go.printable.fixed.buildings.friendly._
import machine.go.printable.fixed.buildings.friendly.towers.TeslaBuilding
import machine.go.printable.fixed.buildings.enemy.Centrale

object Serialization:

  def savePos(scene: GameMap, i: Int, j: Int): String =
    scene.getAtPos(i, j) match
      case t :: q =>
        s"-$i,$j:" + (t :: q).foldLeft("") { (acc, go) => acc + "+" + go.name + ":" + go.health }
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
      val scene = GameMap(Scalaseries.giveAGoodGridWithNoNullToThisManPlease(shapeX, shapeY), (ratioX, ratioY), (player, enemy))
      val newGOS = differentVar(1).split("-")
      for (k <- 0 until newGOS.length) do 
        val posAndGO = newGOS(k).split(":")
        val posIJ: Array[String] = posAndGO(0).split(",")
        val i = posIJ(0).toInt
        val j = posIJ(1).toInt
        val goAnds = posAndGO(1).split("+")
        goAnds foreach (goAnd => 
          val goAndHealth = goAnd split ":"
          val newgo = goAndHealth(0) match
            case "mathematician" => Mathematician(Point(i,j))
            case "physicien" => Physicien(Point(i,j))
            case "centralien" => Centralien(Point(i,j))
            case "xavier" => XavierMiel(Point(i,j))
            case "centrale" => Centrale(Point(i,j))
            case "ru" => RU(Point(i,j))
            case "lsv" => LSV(Point(i,j))
            case "georgessand" => GeorgesSand(Point(i,j))
            scene.placeSthg(newgo)
          )

      scene


    catch
      case _ =>
        println("Impossible to open path")
        null
