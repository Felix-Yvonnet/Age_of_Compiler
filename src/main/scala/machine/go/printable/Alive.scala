package machine.go.printable

import machine.scene.GameMap
import machine.go.GameObject
import sfml.graphics.RectangleShape
import sfml.graphics.Color
import sfml.graphics.RenderWindow
import machine.go.invisible.*

trait Alive extends GameObject:
  // Describe all living elements on the game : friendly or enemy characters and resources (eg tree)

  maxLife = 500
  health = this.maxLife

  // What does it brings when killed, default for basic enemy killed
  var bonusWhenKilled: List[(ResourceType, Int)] = List((Money, 10))
  override def isAttacked(damage: Int, scene: GameMap): Boolean =
    // Logic for loosing life, returns true if killed
    if this.health - damage <= 0 && this.existsInTheGame then
      scene.removeSthg(this, this.pos)
      this.destroy()
      this.bonusWhenKilled.foreach((typ, amount) => scene.actors.gamer.inventory.addResource(typ, amount))
      true
    else
      this.health -= damage
      false

  def drawLifeBar(window: RenderWindow): Unit =
    // Draw a lifebar over the character once it has lost life
    if this.health < this.maxLife then
      val outlineRectangle = RectangleShape((40, 10))
      outlineRectangle.fillColor = Color(0, 0, 0, 0)
      outlineRectangle.outlineThickness = 1
      outlineRectangle.outlineColor = Color(0, 0, 0, 200)
      outlineRectangle.position = (this.pos.x * 40, this.pos.y * 40)
      val inlineRectangle = RectangleShape((((this.health.toFloat / this.maxLife.toFloat) * 40).toInt + 1, 10))
      inlineRectangle.fillColor = Color(250, 10, 10)
      inlineRectangle.position = (this.pos.x * 40 + 1, this.pos.y * 40 + 1)
      window.draw(inlineRectangle)
      window.draw(outlineRectangle)
      inlineRectangle.close()
      outlineRectangle.close()
