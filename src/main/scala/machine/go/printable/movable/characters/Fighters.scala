package machine.go.printable.movable.characters

import machine.go.GameObject
import machine.go.printable.movable.Movable
import machine.scene.{Point, GameMap}

import machine.go.printable.movable.Movable
abstract class Fighters(position: Point, sprite_path: String) extends GameObject(position, sprite_path = sprite_path) with Movable:
  // Describes a character that can attack on another character or farm resources (all can do both)

  health = 500
  var rangeView: Int = 3
  var damage: Int = 100
  var rangeAttack: Int = 2
  // The character selected and that we want to attack
  var targetSelection: Option[GameObject] = None
  // Variables to know when to attack and when to wait
  var lastTimeAttacking: Long = 0
  var diffTimeBeforeNextAttack: Long = 1000
  // For stopping moving when we see an enemy
  var wasAttackingBefore = false

  def attack(other: GameObject, scene: GameMap): Unit =
    // Returns to initial conditions if the enemy is killed by the attack
    if other.isAttacked(this.damage, scene) then this.targetSelection = None

  def actionAttack(scene: GameMap): Unit =
    // Logic for what to do if we have something in target
    this.targetSelection match
      case None =>
      case Some(enemy) =>
        if (enemy.pos distanceTo this.pos) <= this.rangeAttack then
          if !this.wasAttackingBefore then this.goalMoving = None
          this.wasAttackingBefore = true
          if System.currentTimeMillis() - this.lastTimeAttacking > this.diffTimeBeforeNextAttack then
            this.lastTimeAttacking = System.currentTimeMillis()
            attack(enemy, scene)
        else
          if this.isEnemy then
            this.targetSelection = None
            if (enemy.pos distanceTo this.pos) <= this.rangeView then
              this.goalMoving = Some(enemy.pos)


  override def action(scene: GameMap): Unit =
    // add the attack action
    actionAttack(scene)
    move(scene)

  override def rightClicked(scene: GameMap, dest: Point): Unit =
    // add the selection of an enemy or resource
    scene.getAtPos(dest) match
      case Nil => this.targetSelection = None
      case _ =>
        scene.getAtPos(dest).filter(_.isAlive) match
          case Nil    => this.targetSelection = None
          case t :: q =>
            // Does not attack its friends
            if this.isEnemy then this.targetSelection = Some(t)
            if this.isFriendly then
              (t :: q).filter(x => !(x.isFriendly)) match
                case Nil    => this.targetSelection = None
                case t :: q => this.targetSelection = Some(t)
    this.wasAttackingBefore = false
    this.goalMoving = Some(dest)
    this.lastTimeChanged = System.currentTimeMillis()
    move(scene)
