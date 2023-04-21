package machine.go.printable.fixed.buildings.friendly.towers

import machine.scene.GameMap
import machine.go.GameObject
import machine.go.printable.movable.characters.Fighters
import machine.scene.Point
import sfml.graphics.RenderWindow
import affichage.Resources

abstract class DefenseTower(position: Point) extends Fighters(position):
  // ugly definition yet the simplest

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

  override def action(scene: GameMap): Unit =
    ia(scene)
    this.actionAttack(scene)
    this.move(scene)

  override def drawSelected(window: RenderWindow): Unit =
    Resources.drawText(this.name, window, (0, 18 * 40))
    Resources.drawText("Damage: " + this.damage, window, (5 * 40, 16 * 40))
    Resources.drawText("Health: " + this.health + "/" + this.maxLife, window, (5 * 40, 17 * 40))
    Resources.drawText("Attack Speed: " + this.diffTimeBeforeNextAttack, window, (15 * 40, 16 * 40))
    Resources.drawText("Range: " + this.rangeAttack, window, (15 * 40, 17 * 40))
