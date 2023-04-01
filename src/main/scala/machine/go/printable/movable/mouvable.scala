package machine.go.movable

import machine.go.GameObject
import machine.scene.{GameMap, Point}
import machine.go.printable.Alive
import machine.scene.AStar

trait Movable extends Alive:
  isSelectable = true
  isSuperposable = false
  var waitTimeMove: Int
  var waitTimeResources: Int
  var goalMoving: Option[Point] = None
  var lastTimeChanged: Long = 0
  var diffTimeBeforeNextMove: Long = 500

  def move(scene: GameMap): Unit =
    this.goalMoving match
      case Some(place) =>
        if System.currentTimeMillis() - lastTimeChanged > diffTimeBeforeNextMove then
          searchMoveTo(scene, place)
          lastTimeChanged = System.currentTimeMillis()
      case _ => ()

  def searchMoveTo(scene: GameMap, goal: Point): Unit =
    this.pos.to(goal, scene) match
      case None => println("No path found"); this.goalMoving = None
      case Some(nextPoint) => tp(scene, nextPoint)
        

  def tp(scene: GameMap, dest: Point): Unit =
    val elemOnPos = scene.getAtPos(dest.x, dest.y)
    elemOnPos match
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
