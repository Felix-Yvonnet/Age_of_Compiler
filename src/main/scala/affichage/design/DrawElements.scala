package affichage.design

import machine.scene.GameMap
import sfml.graphics.*
import machine.scene.Point
import machine.go.GameObject
import machine.go.GameObject
import machine.go.printable.Alive
import scala.reflect.ClassTag
import machine.go.printable.fixed.buildings.friendly.RU

object DrawCharacters:

  val charactersSpritePath = Map(
      "mathematician" -> "moving_objects/characters/matheux_1.png",
      "centralien" -> "moving_objects/characters/avg_centralien.png",
      "physicien" -> "moving_objects/characters/physicien_1.png",
      "wall" -> "fixed_objects/mur_pierres.png",
      "tesla" -> "fixed_objects/teslaTower.png",
      "xavier" -> "moving_objects/characters/Xavier_Miel.png",
      "tree" -> "",
      "georgessand" -> "",
      "centrale" -> "",
      "lsv" -> "",
      "ru" -> ""
  )
  val textureBigMap = Texture()
  if !textureBigMap.loadFromFile("src/resources/fixed_objects/Tilemap/tilemap_packed.png") then
    throw Exception("Impossible to load image")

  val charactersTextures: Map[String, Texture] = charactersSpritePath.map((x, d) =>
    x -> {
      x match
        case "georgessand" | "centrale" | "tree" | "lsv" | "ru" =>
          textureBigMap
        case _ =>
          val texture = Texture()
          if !texture.loadFromFile("src/resources/" + d) then throw Exception("Impossible to load image")
          texture
    }
  )

  def getSprite(charName: String, scale: Float, scene: GameMap) =
    val sprite = Sprite(charactersTextures(charName))
    charName match
      case "centralien" =>
        sprite.textureRect = (8, 16 + 2 * 64, 64, 64) // de dos
        sprite.scale = (0.9, 0.9)
      case "mathematician" =>
        sprite.scale = (0.19, 0.18)
      case "physicien" =>
        sprite.scale(0.3, 0.35)
      case "xavier" =>
        sprite.scale(0.045, 0.045)

      case "georgessand" =>
        sprite.textureRect = (0, 4 * 16, 4 * 16, 4 * 16)
        sprite.scale(0.6, 0.6)
      case "lsv" =>
        sprite.textureRect = (4 * 16, 4 * 16, 4 * 16, 4 * 16)
        sprite.scale(0.6, 0.6)
      case "centrale" =>
        sprite.textureRect = (3 * 16, 8 * 16, 4 * 16, 4 * 16)
        sprite.scale(0.7, 0.7)
      case "tesla" =>
        sprite.scale(0.22, 0.14)
      case "ru" =>
        sprite.textureRect = (9 * 16, 4 * 16, 16, 16)
        sprite.scale(3.5, 3.5)
        val color = scene.getAtPos(20, 0) filter (_.name == "ru") match
          case t :: q =>
            t match
              case _: RU => t.color
              case _     => throw Exception("Will never happen")
          case Nil => throw Exception("Where is the RU ?")

        sprite.color = color

      case "tree" =>
        sprite.textureRect = (4 * 16, 2 * 16, 16, 16)
        sprite.scale(2, 2.7)
      case "wall" =>
        sprite.scale(0.042, 0.07)

    sprite.scale((scale, scale))
    sprite

  def drawLifeBar(scene: GameMap, window: RenderWindow, character: GameObject): Unit =
    // Draw a lifebar over the character once it has lost life
    if character.health < character.maxLife then
      val outlineRectangle = RectangleShape((40, 10))
      outlineRectangle.fillColor = Color(0, 0, 0, 0)
      outlineRectangle.outlineThickness = 1
      outlineRectangle.outlineColor = Color(0, 0, 0, 200)
      outlineRectangle.position = (character.pos.x * scene.ratio.x, character.pos.y * scene.ratio.y)
      val inlineRectangle = RectangleShape((((character.health.toFloat / character.maxLife.toFloat) * scene.ratio.x).toInt + 1, 10))
      inlineRectangle.fillColor = Color(250, 10, 10)
      inlineRectangle.position = (character.pos.x * 40 + 1, character.pos.y * scene.ratio.y + 1)
      window.draw(inlineRectangle)
      window.draw(outlineRectangle)
      inlineRectangle.close()
      outlineRectangle.close()

  def draw(scene: GameMap, window: RenderWindow, char: GameObject): Unit =
    draw(scene, window, char, char.pos)

  def draw(scene: GameMap, window: RenderWindow, char: GameObject, position: Point, scale: Float = 1f): Unit =
    if char.name != "" then
      val sprite = getSprite(char.name, scale, scene)
      sprite.position = (position.x * scene.ratio.x, position.y * scene.ratio.y)
      window.draw(sprite)
      if char.maxLife > 0 then drawLifeBar(scene, window, char)
