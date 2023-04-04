package machine.go.printable.fixed.buildings

import machine.scene.Point
import scala.collection.mutable.Queue
import machine.go.GameObject
import machine.scene.GameMap
import machine.scene.AStar.search
import sfml.graphics._
import affichage.Resources
import machine.go.movable.characters.mathematiciens.Mathematician
import machine.go.printable.movable.characters.enemy.Centralien

class ProductionBuilding(position: Point, sprite_path: String) extends Building(sprite_path, position) :

  var lastTimeBuilding: Long = 0
  var diffTimeBeforeNextBuild = Map.empty[String,Long]
  var priceForEntity = Map.empty[String,Int]
  var productionQueue: Queue[String] = Queue()

  def updateProduction(scene: GameMap): Unit =
    if !this.productionQueue.isEmpty then
      val toBuildUnit = this.productionQueue.dequeue()
      var wasNotBuild = true
      if System.currentTimeMillis() - this.lastTimeBuilding > this.diffTimeBeforeNextBuild(toBuildUnit) then
            this.lastTimeBuilding = System.currentTimeMillis()
            wasNotBuild = produceUnits(toBuildUnit, scene)
    
  def produceUnits(toBuildUnit: String, scene: GameMap): Boolean =
    scene searchClosePlaceToPutUnits this.pos match
      case None => false
      case Some(newPosition) =>
        this.productionQueue.dequeue()
        var toBuildUnitGameObject = GameObject()
        toBuildUnit match
          case "mathematician" => val toBuildUnitGameObject = Mathematician(newPosition)
          case "centralien" => val toBuildUnitGameObject = Centralien(newPosition)
          case _ => println("Unknown GO"); throw Exception("Unknown type")
        
        scene.place_sthg(toBuildUnitGameObject, newPosition)
        true

  override def action(scene: GameMap): Unit = 
    updateProduction(scene)

  override def drawSelected(window: RenderWindow): Unit = 
    val tailleListe = priceForEntity.size / 2
    var tmp = 0
    priceForEntity.foreach(
      (element, price) => {
        Resources.drawText(element, window, (4 * 40 + 5 * 40 * (tmp % 4) , (20 + tmp / 4) * 40))
        Resources.drawText(s"$price euros", 15, window, (4 * 40 + 5 * 40 * (tmp % 4) + 20, (20 + tmp / 4) * 40 + 30))
        tmp += 1
    })

  override def prompted(place: Point): Unit = ()
    
    

  
  /*
  def produceUnit(): Unit = {
      // TODO: Implement production of units
      if (productionQueue.nonEmpty) {
        val unit = productionQueue.dequeue()
        unit.position = position
        unit.addSelfToGameMap()
      }
    }*/

