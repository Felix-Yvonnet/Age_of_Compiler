package affichage

import sfml.graphics.*
import sfml.system.Vector2

object Resources:

  val font = Font()

  if !(font.loadFromFile("src/resources/fixed_objects/arial.ttf")) then println("An error occured")

  val text = Text()

  // select the font
  text.font = font

  text.characterSize = 24
  text.fillColor = Color.White()

  def drawText(message: String, window: RenderWindow): Unit =

    text.string = message
    text.position = Vector2[Float](0, 0)
    window.draw(text)

  def drawText(message: String, window: RenderWindow, pos: Vector2[Float]): Unit =

    text.string = message
    text.position = pos
    window.draw(text)
