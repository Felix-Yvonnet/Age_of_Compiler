package machine.go.printable.movable.characters

import machine.go.GameObject
import machine.go.movable.Movable
import machine.scene.{GameMap, Point}

class Fighters(position: Point, sprite_path: String) extends GameObject(position, sprite_path = sprite_path) with Movable {
  var waitTimeMove: Int = 50
  var waitTimeResources: Int = 50
  health = 500
  var damage: Int = 100
  var range: Int = 1
  var targetEnnemy: Option[GameObject] = None

  def attack(other: GameObject, scene: GameMap): Unit =
    other.isAttacked(damage, scene)

  def actionAttack(scene: GameMap): Unit =
    targetEnnemy match
      case None => 
      case Some(value) => 
        if (value.pos distanceTo this.pos) <= range then
          attack(value, scene)

  
  override def action(scene: GameMap): Unit = 
    actionAttack(scene)
  
}
