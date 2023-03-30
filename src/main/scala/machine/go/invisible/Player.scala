package machine.go.invisible

import machine.go.GameObject
import sfml.graphics.RenderWindow
import affichage.Resources
import sfml.system.Vector2


class Player(val name: String) extends GameObject():
  val inventory: Inventory = new Inventory

  override def draw(window: RenderWindow): Unit =
    Resources.drawText("Beton : " + inventory.getResourceAmount(Beton), window)
    Resources.drawText("Money : " + inventory.getResourceAmount(Money), window, Vector2[Float](0, 30))


