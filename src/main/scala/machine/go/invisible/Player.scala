package machine.go.invisible

import machine.go.GameObject
import sfml.graphics.RenderWindow
import affichage.Resources
import scala.collection.mutable.Queue
import machine.scene.Point
import machine.scene.GameMap
import sfml.graphics.RectangleShape
import sfml.graphics.Color
import sfml.window.Event
import machine.go.printable.fixed.buildings.friendly.towers.TeslaBuilding

class Player(name: String):

  // Define the building creation constants
  var lastTimeBuilding: Long = 0
  var priceForEntity: Map[String, Int] = Map(
        "tesla" -> 0
    )
  var selected: Option[GameObject] = None

  // For error coloration
  var anErrorHasHappend: Option[Point] = None
  var lastTimeColored: Long = 0
  var diffTimeColoration: Long = 500

  // To keep track on what the user have
  val inventory: Inventory = new Inventory

  def draw(window: RenderWindow): Unit =
    Resources.drawText("Beton : " + inventory.getResourceAmount(Beton), window, (0, 16 * 40))
    Resources.drawText("Money : " + inventory.getResourceAmount(Money), window, (0, 16 * 40 + 30))
    Resources.drawText("Tesla Tower" , window, (5 * 5 * 40, 16 * 40))
    Resources.drawText("500 beton" , 15, window, (5 * 5 * 40, 16 * 40 + 30))
    
  def isLeftClickedWhileProbablyBuying(mousePos: Point, scene: GameMap): Unit =
    // What happen if we try to put the building somewhere
    this.selected match
      case Some(newThing) =>
        if mousePos isWellFormedIn scene then
          if (scene getAtPos mousePos).forall(_.isSuperposable) then
            newThing.pos = mousePos 
            scene.place_sthg(newThing, mousePos)
            this.inventory.removeResource(Beton, 10)
            this.selected = None
          else
            this.anErrorHasHappend = Some(mousePos)
            this.lastTimeColored = System.currentTimeMillis()
      case None => ()

  def isRightClickedWhileProbablyBuying() =
    this.selected = None

  def drawBuying(window: RenderWindow, mousePos: Point, scene: GameMap): Unit =
    this.selected match
      case Some(elem) =>
        if mousePos isWellFormedIn scene then
          elem.draw(window, mousePos)
      case _ => ()

    if System.currentTimeMillis() - this.lastTimeColored > this.diffTimeColoration then
      this.anErrorHasHappend = None

    this.anErrorHasHappend match
      case Some(point) =>
        println(point)
        val colorationRect = new RectangleShape((40,40))
        colorationRect.fillColor = Color(150, 50, 50, 100)
        colorationRect.outlineThickness = .1f
        colorationRect.outlineColor = Color(250, 50, 50, 200)
        colorationRect.position = (point.x * 40, point.y * 40)
        window.draw(colorationRect)
      case None => ()

  def findWhatToDoWhenSomeoneIsProbablyBuying(mousePos: Point) =
    assert(mousePos.y >= 20)
    if mousePos.x >= 25 && mousePos.y <= 22 then
      if this.inventory.getResourceAmount(Beton) >= 10 then
        this.selected = Some(TeslaBuilding(Point(0,0)))