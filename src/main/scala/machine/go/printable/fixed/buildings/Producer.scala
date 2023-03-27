package machine.go.printable.fixed.buildings

import machine.scene.Point
import scala.collection.mutable.Queue

class ProductionBuilding(name: String, position: Point, health: Int, val unitType: String, val productionTime: Int) extends Building(name, position, health) {
  private var productionProgress: Int = 0
  var productionQueue: Queue[Unit] = Queue()

  def update(): Unit = {
    productionProgress += 1
    if (productionProgress >= productionTime) {
      // produceUnit()
    }
  }
/*
  def produceUnit(): Unit = {
      // TODO: Implement production of units
      if (productionQueue.nonEmpty) {
        val unit = productionQueue.dequeue()
        unit.position = position
        unit.addSelfToGameMap()
      }
    }*/
}
