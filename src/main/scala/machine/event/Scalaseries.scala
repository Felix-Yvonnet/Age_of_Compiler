package machine.event

import machine.go.GameObject

object Scalaseries {
  def giveAGoodGridWithNoNullToThisManPlease(shapeX: Int, shapeY: Int) =
    val grid = Array.ofDim[List[GameObject]](shapeX, shapeY)
    for row <- 0 until grid.length; col <- 0 until grid(0).length do grid(row)(col) = List()
    grid
}
