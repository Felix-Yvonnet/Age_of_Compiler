package machine.go.printable.fixed.resource

import machine.scene.Point
import sfml.graphics._


import machine.go.printable.fixed.resource.Resource
class Tree(position: Point) extends Resource(position):
  this.name = "tree"
  // a simple resource : a tree giving basic resources
