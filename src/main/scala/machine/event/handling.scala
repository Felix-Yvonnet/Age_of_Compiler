package machine.event

import machine.go.GameObject
import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.go.movable.Movable
import sfml.graphics.View
import scala.collection.mutable.ListBuffer
import machine.scene.Point
import machine.scene.GameMap
import sfml.Immutable

// Keep an eye on the past
class Input private (
    val keyboard: Map[Keyboard.Key, Int],
    var selected: List[GameObject],
    var rectFst: Option[Point]
):
  def this() =
    this(Map[Keyboard.Key, Int]().empty, List(), None)

class Handler(window: RenderWindow, scene: GameMap, ratioX: Int, ratioY: Int, viewMainMap: View, viewPrompter: View):
  // Does all checks and prints I don't want to put in the Main loop
  val status = Input()

  def handleEvent() =
    if getCoords().y >= scene.grid(0).length then handlePrompterThings()
    else 
      // Check the interaction between the human and the world and react 
      for event <- window.pollEvent() do
        event match {
          case _: Event.Closed                                     => window.close()
          case Event.KeyPressed(c, _, _, _, _): Event.KeyPressed   => status.keyboard.updated(c, 1)
          case Event.KeyReleased(c, _, _, _, _): Event.KeyReleased => status.keyboard.updated(c, 0)
          case Event.MouseButtonPressed(Mouse.Button.Left, x, y)   => {

            val newPos = getCoords()
            
            status.selected = Nil
            status.rectFst = Some(newPos)
          }

          case Event.MouseButtonReleased(Mouse.Button.Left, x, y)  => {
            val mousePos = getCoords()
            val fst = status.rectFst.getOrElse(Point(0, 0))
            for i <- fst.x.min(mousePos.x) to fst.x.max(mousePos.x) do
              for j <- fst.y.min(mousePos.y) to fst.y.max(mousePos.y) do addSelectedToStatus(scene.getAtPos(i, j))
            status.rectFst = None
          }

          case Event.MouseButtonReleased(Mouse.Button.Right, x, y) => {
            val mousePos = getCoords()
            status.selected.foreach(_.rightClicked(scene, mousePos))
          }

          case _ => ()
        }

  def getMousePos(): Point =
    Point(window.mapPixelToCoords(Mouse.position(window), viewMainMap))

  def getCoords(): Point =
    Point(window.mapPixelToCoords(Mouse.position(window), viewMainMap)) / (ratioX, ratioY)

  def addSelectedToStatus(gOl: List[GameObject]): Unit =
    // Add all the selectable objects in gOl to status.selected
    gOl match
      case gO :: q => status.selected = gO :: status.selected; addSelectedToStatus(q)
      case _       => ()

  def handlePrompterThings() =
    for event <- window.pollEvent() do
      // Not coded yet : interaction between human and prompter at the bottom of the screen
      event match
        case Event.MouseButtonReleased(Mouse.Button.Left, x, y) => ()
        case _                                                  => ()

  def handlePrint(): Unit =
    // Print everything needed
    for arr <- 1 to scene.grid.length; someGO <- scene.grid(scene.grid.length - arr) do someGO.reverse.foreach(_.draw(window))

    status.rectFst match
      case Some(point) =>
        val last = getCoords()
        val selectionRect = new RectangleShape(((last.x - point.x +1).abs * ratioX, (last.y - point.y+1).abs * ratioY))
        selectionRect.fillColor = Color(50, 50, 100, 100)
        selectionRect.outlineThickness = .1f
        selectionRect.outlineColor = Color(50, 50, 250, 200)
        selectionRect.position = ((point.x min last.x) * ratioX, (point.y min last.y) * ratioY)
        window.draw(selectionRect)
      case None => ()
    

    scene.actors.gamer.draw(window)
    window.view = Immutable(viewPrompter)
    status.selected.foreach(_.drawSelected(window))
    window.view = Immutable(viewMainMap)
    

  def handleAction(): Unit =
    // everybody does its action
    for row <- scene.grid; elemL <- row do elemL.foreach(_.action(scene))
