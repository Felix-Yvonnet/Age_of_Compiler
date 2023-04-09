package machine.go.printable.fixed.buildings.enemy

import machine.go.printable.fixed.buildings.ProductionBuilding
import machine.scene.Point
import sfml.graphics._
import machine.scene.GameMap
import scala.util.Random

class Centrale(position: Point) extends ProductionBuilding(position, "fixed_objects/Tilemap/tilemap_packed.png"):
    isEnemy = true
    val diffTimeBeforeNextRandomProduction = 15000
    var lastTimeRandomProduction = 0


    diffTimeBeforeNextBuild = Map(
        "centralien" -> 15000
    )
    priceForEntity = Map(
        "centralien" -> 0
    )
    val probabilityAppearing = Map(
        "centralien" -> 1
    )
  

    def selectRandomElement(): String = 
      val rand = Random.nextDouble()
      var cumulativeProbability = 0.0
      for ((element, probability) <- this.probabilityAppearing) do
          cumulativeProbability += probability
          if (rand < cumulativeProbability) then
          return element
        
      // This should never happen unless the probabilities don't sum to 1.0
      throw new RuntimeException("Invalid probabilities")

    
    def ia() =
      if System.currentTimeMillis() - this.lastTimeRandomProduction + Random.between(-5000, 5000) > this.diffTimeBeforeNextRandomProduction then
        this.productionQueue += selectRandomElement()

    override def action(scene: GameMap): Unit = 
      ia()
      updateProduction(scene)
      

    override def draw(window: RenderWindow): Unit =
      if this.sprite_path != "" then
        val sprite = Sprite(this.texture)
        sprite.textureRect = (3 * 16, 8 * 16, 4 * 16, 4 * 16)
        sprite.scale(0.7, 0.7)
        sprite.position = (this.pos.x * 40, this.pos.y * 40)
        window.draw(sprite)
        drawLifeBar(window)

