package machine.go

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2
import machine.scene.Point
import machine.scene.GameMap
import machine.go.invisible.ResourceType

class GameObject(var pos: Point = Point(0, 0)):
  // The general class giving basic properties for all its child
  var isSuperposable: Boolean = true
  var isEnemy: Boolean = false
  var isFriendly: Boolean = false
  var maxLife: Int = 0
  var health: Int = 0
  var existsInTheGame: Boolean = true
  var name: String = ""

  // What happens when we right click while selected
  def rightClicked(scene: GameMap, dest: Point): Unit = ()

  def drawSelected(window: RenderWindow): Unit = ()
  def prompted(place: Point, scene: GameMap): Unit = ()

  // All things to do without a human action
  def action(scene: GameMap): Unit = ()

  // What happens when attacked (couldn't put it in the Alive trait only)
  def isAttacked(damage: Int, scene: GameMap): Boolean = false

  // destroy the texture to avoid memory problems
  def destroy(): Unit =
    existsInTheGame = false
