package machine.go.printable.movable.characters.enemy

import machine.go.printable.movable.characters.Fighters
import machine.scene.Point
import sfml.graphics.{RenderWindow, Sprite}
import machine.scene.GameMap
import machine.go.GameObject

class Centralien(position: Point) extends Fighters(position, "moving_objects/characters/avg_centralien.png"):
  // The main class for enemy characters
  isEnemy = true
  rangeAttack = 1


  def ia(scene: GameMap) =
    // Choose the enemy to attack if it sees one
    this.targetSelection match
      case None =>
        scene.allClotherThan(this.pos, this.rangeAttack).map(scene.getAtPos(_))
                                                        .flatten
                                                        .filter(_.isAlive)
                                                        .filter(!_.isEnemy) match
                case gO :: q => this.targetSelection = Some(gO)
                case _ => 
      case Some(gO) => 


  override def action(scene: GameMap): Unit =
    // add searching enemies via `ia`
    ia(scene)
    actionAttack(scene)
    move(scene)

  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.textureRect = (10, 10, 44, 64) // de dos
      sprite.scale = (0.9, 0.9)
      sprite.position = ((pos.x) * 40, (pos.y) * 40)
      window.draw(sprite)
      drawLifeBar(window)

  override def move(scene: GameMap): Unit = 
    // don't
    