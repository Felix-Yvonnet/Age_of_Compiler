package machine.go.printable.movable.characters.enemy

import machine.go.printable.movable.characters.Fighters
import machine.scene.Point
import sfml.graphics.{RenderWindow, Sprite}
import machine.scene.GameMap
import machine.go.GameObject

class Centralien(position: Point) extends Fighters(position, "moving_objects/characters/avg_centralien.png"):
  belongsToThePlayer = false
  var rangeView: Int = 2

  def ia(scene: GameMap) =
    this.targetEnnemy match
      case None => println("Im no enemy")
        scene.allClotherThan(this.pos, rangeView).map( point => scene.getAtPos(point.x, point.y))
                                                .flatten
                                                .filter(_.isAlive)
                                                .filter(_.belongsToThePlayer) match
          case gO :: q => println("enemy selected"); this.targetEnnemy = Some(gO)
          case _ => 
      case Some(gO) => 
                                             

    



  override def action(scene: GameMap): Unit =
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
