package affichage.design

import machine.scene.Point
import machine.scene.GameMap
import machine.go.printable.fixed.resources.Tree
import scala.util.Random

object RandomForest :
  // Define the probability of a tile being a tree
  val treeProbability = 0.1

  // Define the minimum and maximum sizes of a forest
  val minForestSize = 2
  val maxForestSize = 4

  // Define the minimum and maximum distances between forests
  val minForestDistance = 5
  val maxForestDistance = 8

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


  def generateForest(x: Int, y: Int, scene: GameMap): Unit =
      val forestSize = Random.nextInt(maxForestSize - minForestSize + 1) + minForestSize
      for (i <- 0 until forestSize; j <- 0 until forestSize) {
        if (x+i >= 0 && x+i < scene.width && y+j >= 0 && y+j < scene.height && scene.isAccessible(Point(x+i,y+j))) {
          scene.place_sthg(Tree(Point(x+i,y+j)), Point(x+i,y+j))
        }
      }
  
  def placeForests(scene: GameMap): Unit =
    // Generate random forests on the map
    var x = 0
    while (x < scene.width) do
      var y = 0
      while (y < scene.height) do
        if scene.isAccessible(x,y) && Random.nextDouble() < treeProbability then
          RandomForest.generateForest(x, y, scene)
        y += 1
      
      x += 1
    



                                    


