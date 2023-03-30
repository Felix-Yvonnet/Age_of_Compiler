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

class Input ( 
    val keyboard: Map[Keyboard.Key, Int],
    var selected: List[GameObject],
    var rectFst: Option[Point]
) :
  def this() =
    this(Map[Keyboard.Key, Int]().empty, List(), None)

class Handler(window: RenderWindow, scene: GameMap, ratioX : Int, ratioY : Int, view : View):
  val status = Input()

  def handleEvent() =
    for event <- window.pollEvent() do
      event match {
        case _: Event.Closed                                     => window.close()
        case Event.KeyPressed(c, _, _, _, _): Event.KeyPressed   => status.keyboard.updated(c, 1)
        case Event.KeyReleased(c, _, _, _, _): Event.KeyReleased => status.keyboard.updated(c, 0)
        case Event.MouseButtonPressed(Mouse.Button.Left, x, y) => {
          val newPos = getCoords()
          status.selected = Nil
          status.rectFst = Some(newPos)
        }

        case Event.MouseButtonReleased(Mouse.Button.Left, x, y) => {
          val mousePos = getCoords()
          val fst = status.rectFst.getOrElse(Point(0,0))
          for i <- fst.x.min(mousePos.x) to fst.x.max(mousePos.x) + 1 do
            for j <- fst.y.min(mousePos.y) to fst.y.max(mousePos.y) + 1 do
              addSelectedToStatus(scene.getAtPos(i, j))
          println("finish realesed")
          status.rectFst = None
        }

        case Event.MouseButtonReleased(Mouse.Button.Right, x, y) => {
          val mousePos = getCoords()
          status.selected.foreach(_.rightClicked(scene,mousePos))
        }

        case _ => ()
      }
  

  def getMousePos() : Point =
    Point(window.mapPixelToCoords(Mouse.position(window), view)) 

  def getCoords() : Point = 
    Point(window.mapPixelToCoords(Mouse.position(window), view)) / (ratioX,ratioY)

  def addSelectedToStatus(gOl : List[GameObject]) : Unit =
    gOl match 
      case gO :: q => status.selected = gO :: status.selected; addSelectedToStatus(q)
      case _ => ()
    println(status.selected)

  def handlePrint(): Unit =
    for arr <- scene.grid do
      for someGO <- arr do
        someGO match
          case value :: q => handlePrint(someGO) // someGO.foreach { println }; value.draw(window)
          case _          => ()

    status.rectFst match 
      case Some(point) => 
        val last = getCoords()
        val selectionRect = new RectangleShape(((last.x - point.x).abs * ratioX, (last.y - point.y).abs * ratioY))
        selectionRect.fillColor = Color(50, 50, 100, 100)
        selectionRect.outlineThickness = .1f
        selectionRect.outlineColor = Color(50, 50, 250, 200)
        selectionRect.position = ((point.x min last.x) * ratioX, (point.y min last.y) * ratioY)
        // println((point.x, point.y, last.x, last.y))
        // println((point.x min last.x, point.y min last.y))
        // println((((last.x - point.x).abs, (last.y - point.y).abs)))
        window.draw(selectionRect)
      case None => ()

  def handlePrint(listGO: List[GameObject]): Unit =
    listGO match {
      case gO :: q => handlePrint(q); gO.draw(window)
      case _       => ()
    }

  def handleAction(): Unit =
    for (row <- scene.grid; elemL <- row) do elemL.foreach(_.action(scene))


