package machine.go.printable.movable.characters

import machine.go.GameObject
import machine.go.printable.movable.Movable
import machine.scene.{Point, GameMap}

import machine.go.printable.movable.Movable
import sfml.graphics.RenderWindow
import affichage.Resources
import sfml.graphics.RectangleShape
import sfml.graphics.Color
abstract class Fighters(position: Point) extends GameObject(position) with Movable:
  // Describes a character that can attack on another character or farm resources (all can do both)

  health = 500
  var rangeView: Int = 2
  var damage: Int = 100
  rangeAttack = 2
  // The character selected and that we want to attack
  var targetSelection: Option[GameObject] = None
  // Variables to know when to attack and when to wait
  var lastTimeAttacking: Long = 0
  var diffTimeBeforeNextAttack: Long = 1000
  // For stopping moving when we see an enemy
  var wasAttackingBefore = false
  // For capacities loading
  var lastTimeCapaciting: Long = 0
  var diffTimeBeforeNextCapacity: Long = 10000

  def attack(other: GameObject, scene: GameMap): Unit =
    // Returns to initial conditions if the enemy is killed by the attack
    if other.isAttacked(this.damage, scene) then this.targetSelection = None

  def doTheAttackCapacity(scene: GameMap): Unit = ()

  def actionAttack(scene: GameMap): Unit =
    // Logic for what to do if we have something in target
    this.targetSelection match
      case None =>
      case Some(enemy) =>
        if this.isFriendly == enemy.isFriendly then this.targetSelection = None
        else if this.capacity && System.currentTimeMillis() - this.lastTimeCapaciting > this.diffTimeBeforeNextCapacity then
          this.doTheAttackCapacity(scene)
          this.lastTimeCapaciting = System.currentTimeMillis()
        else if (enemy.pos distanceTo this.pos) <= this.rangeAttack then
          if !this.wasAttackingBefore then this.goalMoving = None
          this.wasAttackingBefore = true
          if System.currentTimeMillis() - this.lastTimeAttacking > this.diffTimeBeforeNextAttack then
            this.lastTimeAttacking = System.currentTimeMillis()
            attack(enemy, scene)
        else if this.isFriendly && !this.wasAttackingBefore then this.goalMoving = Some(enemy.pos)
        else if this.isEnemy then
          this.targetSelection = None
          if (enemy.pos distanceTo this.pos) <= this.rangeView then this.goalMoving = Some(enemy.pos)

  override def action(scene: GameMap): Unit =
    // add the attack action
    actionAttack(scene)
    move(scene)

  override def rightClicked(scene: GameMap, dest: Point): Unit =
    // add the selection of an enemy or resource
    scene.getAtPos(dest) match
      case Nil => this.targetSelection = None
      case _ =>
        scene.getAtPos(dest).filter(_.maxLife > 0) match
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

  override def drawSelected(window: RenderWindow): Unit =
    Resources.drawText(this.name, 20, window, (0, 20 * 4))
    Resources.drawText("Damage: " + this.damage, window, (5 * 40, 0))
    Resources.drawText("Health: " + this.health + "/" + this.maxLife, window, (15 * 40, 0))
    Resources.drawText("Attack Speed: " + this.diffTimeBeforeNextAttack, window, (15 * 40, 10 * 4))
    Resources.drawText("Range: " + this.rangeAttack, window, (5 * 40, 10 * 4))
    Resources.drawText("Speed: " + this.diffTimeBeforeNextMove, window, (15 * 40, 20 * 4))
    Resources.drawText("Eye Sight: " + this.rangeView, window, (5 * 40, 20 * 4))

  def drawVision(window: RenderWindow, scene: GameMap): Unit =
    (scene allClotherThan (this.pos, this.rangeAttack)) map { pos =>
      val selectionRect = new RectangleShape((scene.ratio.x, scene.ratio.y))
      selectionRect.fillColor = Color(150, 50, 50, 50)
      selectionRect.position = (pos.x * scene.ratio.x, pos.y * scene.ratio.y)
      window.draw(selectionRect)
    }
