package machine.go.printable.movable.characters

import machine.go.GameObject
import machine.go.movable.Movable
import machine.scene.{GameMap, Point}

class Fighters(position: Point, sprite_path: String) extends GameObject(position, sprite_path = sprite_path) with Movable {
  var waitTimeMove: Int = 2
  var waitTimeResources: Int = 1
  health = 500
  var damage: Int = 100
  var rangeAttack: Int = 1
  var targetEnnemy: Option[GameObject] = None

  def attack(other: GameObject, scene: GameMap): Unit =
    other.isAttacked(damage, scene)

  def actionAttack(scene: GameMap): Unit =
    this.targetEnnemy match
      case None =>
      case Some(value) =>
        if (value.pos distanceTo this.pos) <= this.rangeAttack then attack(value, scene)

  override def action(scene: GameMap): Unit =
    actionAttack(scene)
    move(scene)

  override def rightClicked(scene: GameMap, dest: Point): Unit =
    scene.getAtPos(dest.x, dest.y) match
      case Nil => ()
      case _ =>
        scene.getAtPos(dest.x, dest.y).filter(_.isAlive) match
          case Nil    => ()
          case t :: q => this.targetEnnemy = Some(t)
    goalMoving = Some(dest)
    lastTimeChanged = System.currentTimeMillis()

    move(scene)

}
