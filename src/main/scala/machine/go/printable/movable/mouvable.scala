package machine.go.movable

import machine.go.GameObject
import machine.scene.{GameMap, Point}
import machine.go.printable.Alive

trait Movable extends Alive:
  isSelectable = true
  isSuperposable = false
  var waitTimeMove : Int
  var waitTimeResources : Int

  def tp(scene: GameMap, dest: Point): Unit =
    val elemOnPos = scene.getAtPos(dest.x, dest.y)
    elemOnPos match
      case t::q =>
        if t.isSuperposable then
          scene.removeSthg(this, this.pos)
          scene.place_sthg(this, dest)
          this.pos = dest
      case _ =>
        scene.removeSthg(this, this.pos)
        scene.place_sthg(this, dest)
        this.pos = dest


