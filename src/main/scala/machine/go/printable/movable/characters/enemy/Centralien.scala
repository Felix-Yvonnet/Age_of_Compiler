package machine.go.printable.movable.characters.enemy

import machine.go.printable.movable.characters.Fighters
import machine.scene.Point
import sfml.graphics.{RenderWindow, Sprite}
import machine.scene.GameMap
import machine.go.GameObject
import scala.util.Random

class Centralien(position: Point) extends Fighters(position, "moving_objects/characters/avg_centralien.png"):
  // The main class for enemy characters
  isEnemy = true
  rangeAttack = 1
  damage = 25
  diffTimeBeforeNextMove = 600
  var diffTimeBeforeRandomMove: Long = 10000
  var lastTimeRandomMove: Long = 1000

  def ia(scene: GameMap) =
    // Choose the enemy to attack if it sees one
    this.targetSelection match
      case None =>
        scene
          .allClotherThan(this.pos, this.rangeView)
          .map(scene.getAtPos(_))
          .flatten
          .filter(_.isFriendly) match
        case gO :: q => 
          this.goalMoving = Some(gO.pos)
          this.targetSelection = Some(gO)
        case _       =>
          if System.currentTimeMillis() - this.lastTimeRandomMove > this.diffTimeBeforeRandomMove then
            val neighbours = this.pos getAllNeighboursIn scene
            if neighbours != Nil && Random.nextBoolean() then
              val random = new Random
              val newPos = neighbours(random.nextInt(neighbours.length))
              this.goalMoving = Some(newPos)
            this.lastTimeChanged = System.currentTimeMillis()
            this.lastTimeRandomMove = System.currentTimeMillis()

      case Some(gO) =>
        if (gO.pos distanceTo this.pos) > rangeView then
          this.targetSelection = None
        else 
          if (gO.pos distanceTo this.pos) > rangeAttack then
            this.goalMoving = Some(gO.pos)
            this.wasAttackingBefore = false
            this.lastTimeChanged = System.currentTimeMillis()

            
    
      

  override def action(scene: GameMap): Unit =
    // add searching enemies via `ia`
    ia(scene)
    this.actionAttack(scene)
    this.move(scene)

  override def draw(window: RenderWindow): Unit =
    if this.sprite_path != "" then
      val sprite = Sprite(this.texture)
      sprite.textureRect = (10, 10, 44, 64) // de dos
      sprite.scale = (0.9, 0.9)
      sprite.position = ((pos.x) * 40, (pos.y) * 40)
      window.draw(sprite)
      drawLifeBar(window)

