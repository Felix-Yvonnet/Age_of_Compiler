package machine.event

import machine.go.GameObject
import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.go.printable.movable.Movable
import scala.collection.mutable.ListBuffer
import machine.scene.Point
import machine.scene.GameMap
import sfml.Immutable
import affichage.design.DrawCharacters
import machine.go.printable.movable.characters.Fighters
import affichage.design.DrawDecorations
import affichage.design.RandomForest
import affichage.*
import machine.event.Serialization

// Keep an eye on the past
class Input private (
    var selected: List[GameObject],
    var rectFst: Option[Point],
    var rectFstForDrawing: Point,
    var isOnEscaped: Boolean,
    var isOnSelect: Boolean,
    var isOnChoose: Boolean,
    var isOnWrite: Boolean
):
  def this() =
    this(List(), None, Point(0, 0), true, false, false, false)

class Handler(
    val window: RenderWindow,
    var scene: GameMap,
    val ratioX: Int,
    val ratioY: Int,
    val viewMainMap: View,
    val viewPrompter: View
):
  // Does all checks and prints I don't want to put in the Main loop
  val status = Input()
  val decorationDrawer = DrawDecorations(scene)

  def continueButton = Button(3, 5, scene.width - 3, 8, "play", () => status.isOnEscaped = false)
  def saveButton = Button(3, 9, scene.width - 3, 12, "save", () => status.isOnWrite = true)
  def loadButton = Button(3, 13, scene.width - 3, 16, "load", () => status.isOnChoose = true)
  def quitButton = Button(3, 17, scene.width - 3, 20, "quit", () => window.close())

  def handleEvent() =
    if status.isOnEscaped then handleEscapedEvent()
    else handleGameEvent()

  def handleEscapedEvent() =
    for event <- window.pollEvent() do
      event match {
        case _: Event.Closed => window.close()
        case Event.KeyPressed(Keyboard.Key.KeyEscape, _, _, _, _): Event.KeyPressed =>
          status.isOnEscaped = false
        case Event.MouseButtonReleased(Mouse.Button.Left, x, y) =>
          val mousePosition = getCoords()
          if loadButton.inPos(mousePosition) then loadButton.action()
          if saveButton.inPos(mousePosition) then saveButton.action()
          if quitButton.inPos(mousePosition) then quitButton.action()
          if continueButton.inPos(mousePosition) then continueButton.action()
        case _ => ()
      }

  def handleGameEvent() =
    if getCoords().y >= scene.height then handlePrompterThings()
    else
      // Check the interaction between the human and the world and react
      for event <- window.pollEvent() do
        event match {
          case _: Event.Closed => window.close()
          case Event.KeyPressed(Keyboard.Key.KeyEscape, _, _, _, _): Event.KeyPressed =>
            status.isOnEscaped = true
          case Event.KeyPressed(Keyboard.Key.KeyS, _, _, _, _): Event.KeyPressed   => status.isOnSelect = true
          case Event.KeyReleased(Keyboard.Key.KeyS, _, _, _, _): Event.KeyReleased => status.isOnSelect = false
          case Event.MouseButtonPressed(Mouse.Button.Left, x, y) =>
            val newPos = getCoords()

            status.selected = Nil
            status.rectFst = Some(newPos)
            status.rectFstForDrawing = getMousePos()

          case Event.MouseButtonReleased(Mouse.Button.Left, x, y) =>
            val mousePos = getCoords()
            val fst = status.rectFst.getOrElse(Point(0, 0))
            for i <- fst.x.min(mousePos.x) to fst.x.max(mousePos.x) do
              for j <- fst.y.min(mousePos.y) to fst.y.max(mousePos.y) do
                if Point(i, j) isWellFormedIn scene then addSelectedToStatus(scene.getAtPos(i, j))

            if (status.rectFst.getOrElse(Point(0, 0)) distanceTo getCoords()) < 1 then
              scene.actors.gamer.isLeftClickedWhileProbablyBuying(mousePos, scene)

            status.rectFst = None

          case Event.MouseButtonReleased(Mouse.Button.Right, x, y) => {
            val mousePos =
              if status.isOnSelect then
                scene findClosestEnemy getCoords() match
                  case None        => getCoords()
                  case Some(point) => point
              else getCoords()
            status.selected.filter(x => x.isFriendly && x.health > 0).foreach(_.rightClicked(scene, mousePos))
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
          status.selected.foreach(x => if x.health > 0 then x.prompted(mousePos, scene))
          scene.actors.gamer.findWhatToDoWhenSomeoneIsProbablyBuying(mousePos)

        case _ => ()

  def handlePrint(): Unit =
    if status.isOnEscaped then handleEscapedPrint()
    else handleClassicalPrint()

  def handleWrite(): Unit =
    Resources.drawText(
        "Give a name to save file",
        50,
        window,
        (scene.width * scene.ratio.x / 2 - 6 * scene.ratio.x, 1 * scene.ratio.y)
    )
    val mousePos = getCoords()
    var buttonToSave =
      Button(3, 5, scene.width - 3, 7, "Save without name", () => { Serialization.save(scene); status.isOnWrite = false })
    buttonToSave.draw(window, mousePos, scene)

  def handleChoose(): Unit =
    Resources.drawText("Choose a game", 50, window, (scene.width * scene.ratio.x / 2 - 3 * scene.ratio.x, 1 * scene.ratio.y))
    val mousePos = getCoords()

    var indice = 5
    Serialization.findAllPossiblePaths().foreach { path =>
      var buttonPath = Button(
          3,
          indice,
          scene.width - 3,
          indice + 3,
          path,
          () => { scene = Serialization.loadGameMap(path); status.isOnChoose = false }
      )
      indice += 4
      buttonPath.draw(window, mousePos, scene)
    }

    continueButton.draw(window, mousePos, scene)
    loadButton.draw(window, mousePos, scene)
    saveButton.draw(window, mousePos, scene)
    quitButton.draw(window, mousePos, scene)

  def handleEscapedPrint(): Unit =
    if status.isOnWrite then handleWrite()
    else if status.isOnChoose then handleChoose()
    else
      Resources.drawText("Menu", 50, window, (scene.width * scene.ratio.x / 2 - scene.ratio.x, 1 * scene.ratio.y))
      val mousePos = getCoords()
      continueButton.draw(window, mousePos, scene)
      loadButton.draw(window, mousePos, scene)
      saveButton.draw(window, mousePos, scene)
      quitButton.draw(window, mousePos, scene)

  def handleClassicalPrint(): Unit =
    decorationDrawer.drawBaseFloor(window)
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
    // draw what the selected character sees
    status.selected.filter(_.isInstanceOf[Fighters]) match
      case fighter :: rest =>
        fighter match
          case f: Fighters => f.drawVision(window, scene)
          case _           => ()
      case Nil => ()
    window.view = viewPrompter
    scene.actors.gamer.draw(window)
    status.selected.filter(_.isInstanceOf[Fighters]) match
      case fighter :: rest =>
        fighter.drawSelected(window)
      case Nil =>
        status.selected match
          case head :: next => head.drawSelected(window)
          case _            =>
    window.view = viewMainMap

  def handleAction(): Unit =
    // everybody does its action
    if !status.isOnEscaped then
      RandomForest.addMoreTrees(scene)
      for row <- scene.grid; elemL <- row do elemL.foreach(_.action(scene))
