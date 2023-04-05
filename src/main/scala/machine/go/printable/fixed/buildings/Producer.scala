package machine.go.printable.fixed.buildings

import machine.scene.Point
import scala.collection.mutable.Queue
import machine.go.GameObject
import machine.scene.GameMap
import machine.scene.AStar.search
import sfml.graphics.*
import affichage.Resources
import machine.go.movable.characters.mathematiciens.Mathematician
import machine.go.printable.movable.characters.enemy.Centralien
import machine.go.invisible.Money

import machine.scene.AStar
class ProductionBuilding(position: Point, sprite_path: String) extends Building(sprite_path, position):

  var lastTimeBuilding: Long = 0
  var diffTimeBeforeNextBuild = Map.empty[String, Long]
  var priceForEntity = Map.empty[String, Int]
  var productionQueue: Queue[String] = Queue()

  def updateProduction(scene: GameMap): Unit =
    if !this.productionQueue.isEmpty then
      val toBuildUnit = this.productionQueue.find(_ => true).getOrElse("")
      var wasNotBuild = true
      if System.currentTimeMillis() - this.lastTimeBuilding > this.diffTimeBeforeNextBuild(toBuildUnit) then
        this.lastTimeBuilding = System.currentTimeMillis()
        wasNotBuild = produceUnits(toBuildUnit, scene)

  def produceUnits(toBuildUnit: String, scene: GameMap): Boolean =
    // Main logic for the production of units, match the name of the thing we have to build with known names and create them
    scene searchClosePlaceToPutUnits this.pos match
      case None =>
        println("No place for putting the char")
        false
      case Some(newPosition) =>
        this.productionQueue.dequeue()
        var toBuildUnitGameObject =
          toBuildUnit match
            case "mathematician" => Mathematician(newPosition)
            case "centralien"    => Centralien(newPosition)
            case _               => println("Unknown GO"); throw Exception("Unknown type")

        scene.place_sthg(toBuildUnitGameObject, newPosition)
        true

  override def action(scene: GameMap): Unit =
    updateProduction(scene)

  override def drawSelected(window: RenderWindow): Unit =
    // draw the selection tool to build troops and buildings
    var tmp = 0
    this.priceForEntity.foreach((element, price) => {
      Resources.drawText(element, window, (4 * 40 + 5 * 40 * (tmp % 4), (20 + 2 * (tmp / 4)) * 40))
      Resources.drawText(s"$price euros", 15, window, (4 * 40 + 5 * 40 * (tmp % 4) + 20, (20 + 2 * (tmp / 4)) * 40 + 30))
      tmp += 1
    })
    this.productionQueue.find(_ => true) match
      case None => ()
      case Some(nameElem) =>
        Resources.drawText(s"$nameElem in ${this.diffTimeBeforeNextBuild(nameElem) / 1000} seconds", window, (0, 23 * 40))

  override def prompted(place: Point, scene: GameMap): Unit =
    println("No cap")
    assert(place.y >= 20)
    if place.x >= 4 then
      var corrNum = (place.x - 4) / 5 + (place.y - 20) / 2 * 4
      this.priceForEntity.foreach((element, price) => {
        if corrNum == 0 then
          if scene.actors.gamer.inventory.removeResource(Money, price) then
            this.productionQueue += element
            this.lastTimeBuilding = System.currentTimeMillis()
            println(s"$element has been produced")
      })
      println("Well placed")

  /*
  def produceUnit(): Unit = {
      // TODO: Implement production of units
      if (productionQueue.nonEmpty) {
        val unit = productionQueue.dequeue()
        unit.position = position
        unit.addSelfToGameMap()
      }
    }*/
