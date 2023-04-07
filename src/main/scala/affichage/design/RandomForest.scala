package affichage.design

import machine.scene.Point
import machine.scene.GameMap
import machine.go.printable.fixed.resources.Tree
import scala.util.Random

object RandomForest :
  def randomForest(scene: GameMap, initial: Point, numTrees: Int) = 
    for iter <- 0 to numTrees do
      addTreeFromHere(initial, scene, Set())

  def addTreeFromHere(currentPoint: Point, scene: GameMap, seen: Set[Point]): Unit =
    if !(seen contains currentPoint) then
      scene.getAtPos(currentPoint) match
        case t::q if !t.isSuperposable =>
            val neighbours = (currentPoint getAllNeighboursIn scene)
            val random = new Random
            val newPos = neighbours(random.nextInt(neighbours.length))
            addTreeFromHere(newPos, scene, seen + currentPoint)
        case _ => 
            scene.place_sthg(Tree(currentPoint), currentPoint)




                                    


