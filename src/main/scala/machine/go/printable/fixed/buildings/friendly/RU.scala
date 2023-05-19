package machine.go.printable.fixed.buildings.friendly

import machine.go.printable.fixed.buildings.ProductionBuilding
import machine.scene.Point
import machine.scene.GameMap
import machine.go.invisible.Money
import sfml.graphics.Color

class RU(position: Point) extends ProductionBuilding(position):
  this.name = "ru"
  isFriendly = false
  isEnemy = true
  val colorFriendly = Color(50, 150, 150, 150)
  val colorEnemy = Color(150, 50, 50, 150)
  this.color = colorEnemy

  var lastTimeGave: Long = 0

  diffTimeBeforeNextBuild = Map(
      "money" -> 15000
  )
  priceForEntity = Map(
      "money" -> 10
  )

  override def isAttacked(damage: Int, scene: GameMap): Boolean =
    // Logic for loosing life, returns true if killed
    if this.health - damage <= 0 && this.existsInTheGame then
      if this.isFriendly then this.color = this.colorEnemy
      else this.color = this.colorFriendly
      this.isFriendly = !this.isFriendly
      this.isEnemy = !this.isEnemy
      this.health = this.maxLife
      true
    else
      this.health -= damage
      false

  override def action(scene: GameMap): Unit =
    if this.isFriendly then
      if System.currentTimeMillis() - this.lastTimeGave > this.diffTimeBeforeNextBuild("money") then
        scene.actors.gamer.inventory.addResource(Money, this.priceForEntity("money"))
        this.lastTimeGave = System.currentTimeMillis()

  override def prompted(place: Point, scene: GameMap): Unit = ()
