package machine.go.movable

import machine.go.GameObject
import machine.scene.{GameMap, Point}
import machine.go.printable.Alive
import machine.scene.AStar

trait Movable extends Alive:
  isSuperposable = false
  var waitTimeMove: Int = 2000
  var waitTimeResources: Int = 2000
  var goalMoving: Option[Point] = None
  var lastTimeChanged: Long = 0
  var diffTimeBeforeNextMove: Long = 200

  def move(scene: GameMap): Unit =
    // Logic of moving
    this.goalMoving match
      case Some(place) =>
        if System.currentTimeMillis() - lastTimeChanged > diffTimeBeforeNextMove then
          searchMoveTo(scene, place)
          lastTimeChanged = System.currentTimeMillis()
      case _ => ()

  def searchMoveTo(scene: GameMap, goal: Point): Unit =
    // Search a path and move to it
    this.pos.to(goal, scene) match
      case None => println("No path found"); this.goalMoving = None
      case Some(nextPoint) => tp(scene, nextPoint)
        

  def tp(scene: GameMap, dest: Point): Unit =
    // Basic teleportation 
    scene.getAtPos(dest) match
      case t :: q =>
        if t.isSuperposable then
          scene.removeSthg(this, this.pos)
          scene.place_sthg(this, dest)
          this.pos = dest
      case _ =>
        scene.removeSthg(this, this.pos)
        scene.place_sthg(this, dest)
        this.pos = dest

  override def rightClicked(scene: GameMap, dest: Point): Unit
