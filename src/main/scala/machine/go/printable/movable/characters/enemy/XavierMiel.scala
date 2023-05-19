package machine.go.printable.movable.characters.enemy

import machine.scene.Point

class XavierMiel(position: Point) extends Centralien(position):
  name = "xavier"

  isEnemy = true
  rangeAttack = 2
  rangeView = 5
  damage = 250
  maxLife = 3500
  health = maxLife
  diffTimeBeforeNextMove = 1000
  diffTimeBeforeRandomMove = 15000
  lastTimeRandomMove = 1000
