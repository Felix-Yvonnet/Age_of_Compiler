package machine.go.printable.fixed.buildings.friendly.towers

import machine.scene.GameMap
import machine.go.GameObject
import machine.go.printable.movable.characters.Fighters
import machine.scene.Point
import sfml.graphics.RenderWindow
import affichage.Resources

abstract class DefenseTower(position: Point) extends Fighters(position):
  // ugly definition yet the simplest

  var isBuilding: Boolean = true
  var rateBuilding: Long = 15000
  health = 0

  override def move(scene: GameMap): Unit = ()

  def ia(scene: GameMap) =
    this.targetSelection match
      case None =>
        scene
          .allClotherThan(this.pos, this.rangeView)
          .map(scene.getAtPos(_))
          .flatten
          .filter(_.isEnemy) match
          case t :: q => targetSelection = Some(t)
          case Nil    =>
      case Some(enemy) =>
        if (enemy.pos distanceTo this.pos) > this.rangeView then targetSelection = None

  def build(): Unit =
    this.health = ((this.maxLife * System.currentTimeMillis()) / this.rateBuilding).toInt
    if health > this.maxLife then
      this.health = this.maxLife
      this.isBuilding = false

  override def action(scene: GameMap): Unit =
    if !this.isBuilding then
      ia(scene)
      this.actionAttack(scene)
    else this.build()

  override def drawSelected(window: RenderWindow): Unit =
    Resources.drawText(this.name, window, (0, 20 * 4))
    Resources.drawText("Damage: " + this.damage, window, (5 * 40, 0))
    Resources.drawText("Health: " + this.health + "/" + this.maxLife, window, (15 * 40, 0))
    Resources.drawText("Attack Speed: " + this.diffTimeBeforeNextAttack, window, (15 * 40, 10 * 4))
    Resources.drawText("Range: " + this.rangeAttack, window, (5 * 40, 10 * 4))
