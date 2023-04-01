package machine.go.printable.movable.characters

import machine.go.GameObject
import machine.go.movable.Movable
import machine.scene.{GameMap, Point}

abstract class Fighters(position: Point, sprite_path: String) extends GameObject(position, sprite_path = sprite_path) with Movable :
  health = 500
  var damage: Int = 100
  var rangeAttack: Int = 1
  var targetEnnemy: Option[GameObject] = None
  var lastTimeAttacking: Long = 0
  var diffTimeBeforeNextAttack: Long = 2000

  def attack(other: GameObject, scene: GameMap): Unit =
    print(this); print(" is attacking "); print(other); print("\n")
    if other.isAttacked(damage, scene) then
      this.targetEnnemy = None

  def actionAttack(scene: GameMap): Unit =
    this.targetEnnemy match
      case None =>
      case Some(enemy) =>
        if (enemy.pos distanceTo this.pos) <= this.rangeAttack then 
          if System.currentTimeMillis() - this.lastTimeAttacking > this.diffTimeBeforeNextAttack then
            this.lastTimeAttacking = System.currentTimeMillis()
            attack(enemy, scene)

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
    this.goalMoving = Some(dest)
    this.lastTimeChanged = System.currentTimeMillis()

    move(scene)


