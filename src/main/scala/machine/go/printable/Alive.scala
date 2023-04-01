package machine.go.printable

import machine.scene.GameMap
import machine.go.GameObject
import sfml.graphics.RectangleShape
import sfml.graphics.Color
import sfml.graphics.RenderWindow

trait Alive extends GameObject :

  var health = 500
  var maxLife = this.health
  isAlive = true
  override def isAttacked(damage: Int, scene: GameMap): Boolean =
    if this.health - damage <= 0 then
      scene.removeSthg(this, this.pos)
      this.destroy()
      true
    else
      this.health -= damage
      false


  def drawLifeBar(window: RenderWindow): Unit =
    if this.health < this.maxLife then
      val outlineRectangle = RectangleShape((40, 10))
      outlineRectangle.fillColor = Color(0, 0, 0, 0)
      outlineRectangle.outlineThickness = 1
      outlineRectangle.outlineColor = Color(0, 0, 0, 200)
      outlineRectangle.position = (this.pos.x * 40, this.pos.y * 40)
      val inlineRectangle = RectangleShape((((this.health.toFloat / this.maxLife.toFloat) * 40).toInt + 1, 10))
      inlineRectangle.fillColor = Color(250, 10, 10)
      inlineRectangle.position = (this.pos.x * 40 +1 , this.pos.y * 40 +1)
      window.draw(inlineRectangle)
      window.draw(outlineRectangle)
      inlineRectangle.close()
      outlineRectangle.close()
      

