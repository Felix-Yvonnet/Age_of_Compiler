package machine.go.printable.movable.characters.enemy

import machine.go.printable.movable.characters.Fighters
import machine.scene.Point
import sfml.graphics.{RenderWindow, Sprite}
import machine.scene.GameMap
import machine.go.GameObject
import scala.util.Random

class Centralien(position: Point) extends Fighters(position):
  // The main class for enemy characters
  name = "centralien"
  isEnemy = true
  rangeAttack = 1
  damage = 100
  maxLife = 500
  health = maxLife
  diffTimeBeforeNextMove = 600
  var diffTimeBeforeRandomMove: Long = 10000
  var lastTimeRandomMove: Long = 1000
  var isOnAttackMode: Boolean = false

  def changeToNewSelection(scene: GameMap): Unit =
    this.targetSelection match
      case None =>
        // find new enemies
        scene
          .allClotherThan(this.pos, this.rangeView)
          .map(scene.getAtPos(_))
          .flatten
          .filter(_.isFriendly) match
          case gO :: q =>
            this.goalMoving = Some(gO.pos)
            this.targetSelection = Some(gO)
          case _ =>
            if this.goalMoving == None && System.currentTimeMillis() - this.lastTimeRandomMove > this.diffTimeBeforeRandomMove then
              val neighbours = this.pos getAllNeighboursIn scene
              if neighbours != Nil && Random.nextBoolean() then
                val newPos = neighbours(Random.nextInt(neighbours.length))
                this.goalMoving = Some(newPos)
              this.lastTimeChanged = System.currentTimeMillis()
              this.lastTimeRandomMove = System.currentTimeMillis()

      case Some(gO) =>
        if gO.isEnemy then this.targetSelection = None
        else if (gO.pos distanceTo this.pos) > rangeView then this.targetSelection = None
        else if (gO.pos distanceTo this.pos) > rangeAttack then
          this.goalMoving = Some(gO.pos)
          this.wasAttackingBefore = false
          this.lastTimeChanged = System.currentTimeMillis()

//  def searchEnemies(scene: GameMap): Unit =
//    val neighbours = (scene.allClotherThan(this.pos, this.rangeView)).map(scene.getAtPos(_))
//                                                        .flatten
//                                                        .filter( elem => elem match
//                                                          case c: Centralien => c.isOnAttackMode
//                                                          case _ => false
//                                                        ) match
//          case t :: q => t match
//            case c: Centralien =>
//              this.targetSelection = c.targetSelection
//              this.pos to

    ()
  def ia(scene: GameMap) =
    // Choose the enemy to attack if it sees one
    changeToNewSelection(scene)
    // Change position to attack mode
    // searchEnemies(scene)
  override def action(scene: GameMap): Unit =
    // add searching enemies via `ia`
    ia(scene)
    this.actionAttack(scene)
    this.move(scene)
