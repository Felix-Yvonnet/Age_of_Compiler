package machine.go.movable

import machine.go.GameObject
import machine.scene.{GameMap, Point}

trait Movable extends GameObject:
  isSelectable = true
  isSuperposable = false
  var waitTimeMove : Int
  var waitTimeResources : Int

  
