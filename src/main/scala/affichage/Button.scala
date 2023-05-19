package affichage

import sfml.graphics.*
import machine.scene.*

class Button(
    val topCornerx: Int,
    val topCornery: Int,
    val bottomCornerx: Int,
    val bottomCornery: Int,
    val message: String,
    val action: () => Unit
):

  def inPos(mousePos: Point) =
    mousePos.x >= topCornerx && mousePos.y >= topCornery && mousePos.x <= bottomCornerx && mousePos.y <= bottomCornery

  def draw(window: RenderWindow, mousePos: Point, scene: GameMap): Unit =
    val selectionRect = RectangleShape(((bottomCornerx - topCornerx) * scene.ratio.x, (bottomCornery - topCornery) * scene.ratio.y))
    if this inPos mousePos then
      selectionRect.outlineThickness = .5f
      selectionRect.fillColor = Color(50, 150, 200)
    else
      selectionRect.outlineThickness = 0.1f
      selectionRect.fillColor = Color(50, 150, 250, 50)

    selectionRect.position = (topCornerx * scene.ratio.x, topCornery * scene.ratio.y)
    window.draw(selectionRect)
    Resources.drawText(
        message,
        30,
        window,
        (
            topCornerx * scene.ratio.x,
            topCornery * scene.ratio.y + (bottomCornery - topCornery) * scene.ratio.y / 2 - scene.ratio.y / 2
        )
    )
