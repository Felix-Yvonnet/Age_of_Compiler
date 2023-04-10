package affichage.design

import machine.scene.Point
import machine.scene.GameMap
import machine.go.printable.fixed.resource.Tree
import scala.util.Random
import machine.scene.GameMap

object RandomForest:
  // Define the probability of a tile being a tree
  val treeProbability = 0.1

  // Define the minimum and maximum sizes of a forest
  val minForestSize = 1
  val maxForestSize = 3

  // Define the minimum and maximum distances between forests
  val minForestDistance = 5
  val maxForestDistance = 8

  // Define the reaparition rate of trees
  val diffTimeBeforeNextAppearance: Long = 30000
  var lastTimeAppearing: Long = 0

  def generateForest(x: Int, y: Int, scene: GameMap): Unit =
    val forestSize = Random.nextInt(maxForestSize - minForestSize + 1) + minForestSize
    for i <- 0 until forestSize; j <- 0 until forestSize do {
      if x + i >= 0 && x + i < scene.width && y + j >= 0 && y + j < scene.height && scene.isAccessible(Point(x + i, y + j)) then {
        scene.place_sthg(Tree(Point(x + i, y + j)), Point(x + i, y + j))
      }
    }

  def placeForests(scene: GameMap): Unit =
    // Generate random forests on the map
    var x = 0
    while x < scene.width do
      var y = 0
      while y < scene.height do
        if scene.isAccessible(x, y) && Random.nextDouble() < treeProbability && (Point(x, y) distanceTo Point(0, 0)) >= 5 then
          RandomForest.generateForest(x, y, scene)
        y += 1

      x += 1

  def treePos(scene: GameMap): List[Point] =
    var treePosList: List[Point] = Nil
    for row <- 0 until scene.width; col <- 0 until scene.height do
      scene.getAtPos(row, col).filter(_.isInstanceOf[Tree]) match
        case Nil    =>
        case t :: q => treePosList = Point(row, col) :: treePosList
    treePosList

  def addMoreTrees(scene: GameMap) =
    if System.currentTimeMillis() - this.lastTimeAppearing > this.diffTimeBeforeNextAppearance then
      var treePosition = treePos(scene)
      if treePosition.length == 0 then
        var isThereAPlaceForATreeInThisCrualWorld: Boolean = false
        for row <- 0 until scene.width; col <- 0 until scene.height do {
          if scene.getAtPos(row, col).filter(!_.isSuperposable).isEmpty then { isThereAPlaceForATreeInThisCrualWorld = true }
        }
        if isThereAPlaceForATreeInThisCrualWorld then

          var newRandomPosForTree = Point(Random.nextInt(scene.width), Random.nextInt(scene.height))
          while !(scene.getAtPos(newRandomPosForTree).filter(!_.isSuperposable).isEmpty) do
            newRandomPosForTree = Point(Random.nextInt(scene.width), Random.nextInt(scene.height))
          scene.place_sthg(Tree(newRandomPosForTree), newRandomPosForTree)
          treePosition = newRandomPosForTree :: treePosition
        else println("No place found")

      treePosition(Random.nextInt(treePosition.length)) getNeighboursIn scene match
        case t :: q =>
          val newPlace = ((t :: q)(Random.nextInt((t :: q).length)))
          scene.place_sthg(Tree(newPlace), newPlace)
        case Nil => ()

      lastTimeAppearing = System.currentTimeMillis()
