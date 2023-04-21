package machine.event

import machine.go.GameObject
import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.go.printable.movable.Movable
import sfml.graphics.View
import scala.collection.mutable.ListBuffer
import machine.scene.Point
import machine.scene.GameMap
import sfml.Immutable
import affichage.design.DrawCharacters

// Keep an eye on the past
class Input private (
    val keyboard: Map[Keyboard.Key, Int],
    var selected: List[GameObject],
    var rectFst: Option[Point],
    var rectFstForDrawing: Point
):
  def this() =
    this(Map[Keyboard.Key, Int]().empty, List(), None, Point(0, 0))

class Handler(window: RenderWindow, scene: GameMap, ratioX: Int, ratioY: Int, viewMainMap: View, viewPrompter: View):
  // Does all checks and prints I don't want to put in the Main loop
  val status = Input()

  def handleEvent() =
    if getCoords().y >= scene.height then handlePrompterThings()
    else
      // Check the interaction between the human and the world and react
      for event <- window.pollEvent() do
        event match {
          case _: Event.Closed                                     => window.close()
          case Event.KeyPressed(c, _, _, _, _): Event.KeyPressed   => status.keyboard.updated(c, 1)
          case Event.KeyReleased(c, _, _, _, _): Event.KeyReleased => status.keyboard.updated(c, 0)
          case Event.MouseButtonPressed(Mouse.Button.Left, x, y) => {

            val newPos = getCoords()

            status.selected = Nil
            status.rectFst = Some(newPos)
            status.rectFstForDrawing = getMousePos()
          }

          case Event.MouseButtonReleased(Mouse.Button.Left, x, y) => {
            val mousePos = getCoords()
            val fst = status.rectFst.getOrElse(Point(0, 0))
            for i <- fst.x.min(mousePos.x) to fst.x.max(mousePos.x) do
              for j <- fst.y.min(mousePos.y) to fst.y.max(mousePos.y) do addSelectedToStatus(scene.getAtPos(i, j))

            if (status.rectFst.getOrElse(Point(0, 0)) distanceTo getCoords()) < 1 then
              scene.actors.gamer.isLeftClickedWhileProbablyBuying(mousePos, scene)

            status.rectFst = None
          }

          case Event.MouseButtonReleased(Mouse.Button.Right, x, y) => {
            val mousePos = getCoords()
            status.selected.foreach(_.rightClicked(scene, mousePos))
            scene.actors.gamer.isRightClickedWhileProbablyBuying()
          }

          case _ => ()
        }

  def getMousePos(): Point =
    getMousePos(viewMainMap)

  def getCoords(): Point =
    getCoords(viewMainMap)

  def getMousePos(view: View): Point =
    Point(window.mapPixelToCoords(Mouse.position(window), view))

  def getCoords(view: View): Point =
    Point(window.mapPixelToCoords(Mouse.position(window), view)) / (ratioX, ratioY)

  def addSelectedToStatus(gOl: List[GameObject]): Unit =
    // Add all the selectable objects in gOl to status.selected
    gOl match
      case gO :: q => status.selected = gO :: status.selected; addSelectedToStatus(q)
      case _       => ()

  def handlePrompterThings() =
    for event <- window.pollEvent() do
      event match
        case Event.MouseButtonReleased(Mouse.Button.Left, x, y) =>
          val mousePos = getCoords()
          status.selected.foreach(_.prompted(mousePos, scene))
          scene.actors.gamer.findWhatToDoWhenSomeoneIsProbablyBuying(mousePos)

        case _ => ()

  def handlePrint(): Unit =
    // Print everything needed
    for arr <- 1 to scene.width; someGO <- scene.grid(scene.width - arr) do
      someGO.reverse.foreach(DrawCharacters.draw(scene, window, _))

    status.rectFst match
      case Some(_) =>
        val point = status.rectFstForDrawing
        val last = getMousePos()
        val selectionRect = new RectangleShape(((last.x - point.x).abs, (last.y - point.y).abs))
        selectionRect.fillColor = Color(50, 50, 100, 100)
        selectionRect.outlineThickness = .1f
        selectionRect.outlineColor = Color(50, 50, 250, 200)
        selectionRect.position = ((point.x min last.x), (point.y min last.y))
        window.draw(selectionRect)
      case None => ()

    val mousePos = getCoords()
    scene.actors.gamer.drawBuying(window, mousePos, scene)
    window.view = window.defaultView
    scene.actors.gamer.draw(window)
    status.selected match
      case head :: next => head.drawSelected(window)
      case _            =>
    window.view = viewMainMap

  def handleAction(): Unit =
    // everybody does its action
    for row <- scene.grid; elemL <- row do elemL.foreach(_.action(scene))
