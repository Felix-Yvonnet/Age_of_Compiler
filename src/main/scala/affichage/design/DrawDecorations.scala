package affichage.design

import machine.scene.GameMap
import sfml.graphics.{RenderWindow, Texture, Sprite}
import machine.go.printable.fixed.buildings.enemy.Centrale
import machine.scene.Point
import machine.go.printable.fixed.decoration.Wall

class DrawDecorations(scene: GameMap):

  // Build a random map to fix a decoration
  val randomGen = scala.util.Random
  val texturePositionMap = Array.ofDim[Int](scene.width, scene.height)
  for row <- texturePositionMap; elem <- 0 until scene.height do row(elem) = randomGen.nextInt

  // Get the texture for the floor
  val tileMapTextureGreenFloor = Texture()
  tileMapTextureGreenFloor.loadFromFile("src/resources/fixed_objects/Tilemap/tilemap_packed.png")

  def drawBaseFloor(window: RenderWindow) =
    // Draw the floor with flowers
    for i <- 0 until scene.width; j <- 0 until scene.height do

      val spriteForGreenFloor = getRandomBaseFloorPattern(i, j)
      spriteForGreenFloor.position = (i * scene.ratio.x, j * scene.ratio.y)
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
