package machine.go.printable.fixed.buildings

import machine.scene.Point
import scala.collection.mutable.Queue
import machine.go.GameObject
import machine.scene.GameMap
import sfml.graphics.*
import affichage.Resources
import machine.go.printable.movable.characters.friendly.units.mathematicians.Mathematician
import machine.go.printable.movable.characters.friendly.units.physiciens.Physicien
import machine.go.printable.movable.characters.enemy.*
import machine.go.invisible.Money
import machine.go.printable.fixed.buildings.friendly.{TP, Sniper}
import machine.go.printable.fixed.buildings.friendly.LSV
import scala.util.Random

class ProductionBuilding(position: Point) extends Building(position):

  var lastTimeBuilding: Long = 0
  var diffTimeBeforeNextBuild = Map.empty[String, Long]
  var priceForEntity = Map.empty[String, Int]
  var productionQueue: Queue[String] = Queue()
  val rand = Random()

  def updateProduction(scene: GameMap): Unit =
    if !this.productionQueue.isEmpty then
      val toBuildUnit = this.productionQueue.find(_ => true).getOrElse("")
      if System.currentTimeMillis() - this.lastTimeBuilding > this.diffTimeBeforeNextBuild(toBuildUnit) then
        this.lastTimeBuilding = System.currentTimeMillis()
        if this.isInstanceOf[LSV] then produceTechnology(toBuildUnit, scene)
        else produceUnits(toBuildUnit, scene)

  def produceTechnology(toBeBuildTech: String, scene: GameMap): Unit =
    toBeBuildTech match
      case "teleportation" => scene unlockTech TP
      case "sniper"        => scene unlockTech Sniper
      case _               => throw Exception("Unknown tech")
    this.productionQueue.dequeue()

  def produceUnits(toBuildUnit: String, scene: GameMap): Unit =
    // Main logic for the production of units, match the name of the thing we have to build with known names and create them
    scene searchClosePlaceToPutUnits this.pos match
      case None =>
        println("No place for putting the char")
      case Some(newPosition) =>
        this.productionQueue.dequeue()
        if toBuildUnit != "design" then
          val toBuildUnitGameObject =
            toBuildUnit match
              case "mathematician" => Mathematician(newPosition)
              case "centralien"    => Centralien(newPosition)
              case "physicien"     => Physicien(newPosition)
              case "xavier"        => XavierMiel(newPosition)
              case _               => println("Unknown GO"); throw Exception("Unknown type")

          if toBuildUnit == "centralien" && rand.nextInt() >= 0 then
            toBuildUnitGameObject.goalMoving = scene.searchClosePlaceToPutUnits(Point(20, 0))
          if toBuildUnit == "mathematician" && scene.getTechLevel(Sniper) then
            toBuildUnitGameObject.rangeView = 3
            toBuildUnitGameObject.rangeAttack = 3
          if toBuildUnit == "physicien" && scene.getTechLevel(TP) then toBuildUnitGameObject.capacity = true
          scene.placeSthg(toBuildUnitGameObject, newPosition)

  override def action(scene: GameMap): Unit =
    updateProduction(scene)

  override def drawSelected(window: RenderWindow): Unit =
    // draw the selection tool to build troops and buildings
    if this.isFriendly then
      var tmp = 0
      this.priceForEntity.foreach((element, price) => {
        Resources.drawText(element, window, (4 * 40 + 5 * 40 * (tmp % 4), (16 + 5 * (tmp / 4)) * 2 / 10))
        Resources.drawText(s"$price euros", 15, window, (4 * 40 + 5 * 40 * (tmp % 4) + 20, ((16 + 2 * (tmp / 4)) * 2) / 10 + 30))
        tmp += 1
      })
      this.productionQueue.headOption match
        case None => ()
        case Some(nameElem) =>
          Resources.drawText(
              s"$nameElem \nin ${(this.diffTimeBeforeNextBuild(nameElem) - System.currentTimeMillis() + this.lastTimeBuilding) / 1000} seconds",
              15,
              window,
              (0, (18 * 40) / 10)
          )

  override def prompted(place: Point, scene: GameMap): Unit =
    // Handle the problems when somehing is clicked on the menue
    if this.isFriendly then
      assert(place.y >= 20)
      if place.x >= 4 then
        var corrNum = (place.x - 4) / 5 + (place.y - 20) / 2 * 4
        this.priceForEntity.foreach((element, price) => {
          if corrNum == 0 then
            if scene.actors.gamer.inventory.removeResource(Money, price) then
              this.productionQueue += element
              this.lastTimeBuilding = System.currentTimeMillis()
              println(s"$element has been produced")
          corrNum -= 1
        })

      if place.x <= 1 then
        if place.y == 22 then
          if !this.productionQueue.isEmpty then
            scene.actors.gamer.inventory.addResource(Money, this.priceForEntity(this.productionQueue.dequeue()))
