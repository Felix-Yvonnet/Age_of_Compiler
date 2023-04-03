package affichage.design

import machine.scene.GameMap
import sfml.graphics.{RenderWindow, Texture, Sprite}
import sfml.system.Vector2

sealed trait TilesDecoration
case object GreenFloor extends TilesDecoration
case object GreenFloorWithFlowers extends TilesDecoration

class DrawDecorations(scene: GameMap):

  // Build a random map to fix a decoration
  val randomGen = scala.util.Random
  val texturePositionMap = Array.ofDim[Int](scene.grid.length, scene.grid(0).length)
  for row <- texturePositionMap; elem <- 0 to scene.grid(0).length - 1 do row(elem) = randomGen.nextInt

  // Get the texture for the floor
  val tileMapTextureGreenFloor = Texture()
  tileMapTextureGreenFloor.loadFromFile("src/resources/fixed_objects/Tilemap/tilemap_packed.png")

  def drawBaseFloor(window: RenderWindow) =
    // Draw the floor with flowers
    for i <- 0 to scene.grid.length - 1; j <- 0 to scene.grid(0).length - 1 do

      val spriteForGreenFloor = getRandomBaseFloorPattern(i, j)
      spriteForGreenFloor.position = Vector2[Float](i * 40, j * 40)
      window.draw(spriteForGreenFloor)

  def getRandomBaseFloorPattern(i: Int, j: Int) =
    // Return the sprite according to the random distribution 
    val spriteForGreenFloor = Sprite(tileMapTextureGreenFloor)

    if texturePositionMap(i)(j) < 0 then
      spriteForGreenFloor.textureRect = (0 * 16, 0, 1 * 16, 1 * 16)
      spriteForGreenFloor.scale(2.5, 2.5)
    else if texturePositionMap(i)(j) > 94561451 then
      spriteForGreenFloor.textureRect = (1 * 16, 0, 1 * 16, 1 * 16)
      spriteForGreenFloor.scale(2.5, 2.5)
    else
      spriteForGreenFloor.textureRect = (2 * 16, 0, 1 * 16, 1 * 16)
      spriteForGreenFloor.scale(2.5, 2.5)

    spriteForGreenFloor
