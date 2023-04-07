package affichage.design

import machine.scene.Point
import machine.scene.GameMap
import machine.go.printable.fixed.resources.Tree
import scala.util.Random

object RandomForest :
  def randomForest(scene: GameMap, initial: Point, numTrees: Int) = 
    findNew(initial, scene, Set(), numTrees)

  def findNew(currentPoint: Point, scene: GameMap, seen: Set[Point], nIter: Int) =
    if !(seen contains currentPoint) && nIter > 0 then
      scene.getAtPos(currentPoint) match
        case t::q if !t.isSuperposable =>
            val neighbours = (currentPoint getAllNeighboursIn scene)
            val random = new Random
            val newPos = neighbours(random.nextInt(neighbours.length))
            
            
        case _ => 
            scene.place_sthg(Tree(currentPoint), currentPoint)




                                    


