package machine.go.printable.fixed.buildings

import machine.scene.Point
import scala.collection.mutable.Queue
import machine.go.GameObject
import machine.scene.GameMap
import machine.scene.AStar.search

class ProductionBuilding(position: Point, sprite_path: String) extends Building(sprite_path, position) :

  var lastTimeBuilding: Long = 0
  var diffTimeBeforeNextBuild = scala.collection.mutable.HashMap.empty[GameObject,Long]
  val priceForEntity = scala.collection.mutable.HashMap.empty[GameObject,Int]
  var productionQueue: Queue[GameObject] = Queue()

  def updateProduction(scene: GameMap): Unit =
    if !this.productionQueue.isEmpty then
      val toBuildUnit = this.productionQueue.dequeue()
      var wasNotBuild = true
      if System.currentTimeMillis() - this.lastTimeBuilding > this.diffTimeBeforeNextBuild(toBuildUnit) then
            this.lastTimeBuilding = System.currentTimeMillis()
            wasNotBuild = produceUnits(toBuildUnit, scene)
    
  def produceUnits(toBuildUnit: GameObject, scene: GameMap): Boolean =
    scene searchClosePlaceToPutUnits this.pos match
      case None => false
      case Some(newPosition) =>
        this.productionQueue.dequeue()
        scene.place_sthg(toBuildUnit, newPosition)
        toBuildUnit.pos = newPosition
        true



  
  /*
  def produceUnit(): Unit = {
      // TODO: Implement production of units
      if (productionQueue.nonEmpty) {
        val unit = productionQueue.dequeue()
        unit.position = position
        unit.addSelfToGameMap()
      }
    }*/

