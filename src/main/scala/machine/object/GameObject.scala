package machine.`object`

import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2

class GameObject(var sprite_path : String = "") :
  var typ = 0
  var pos = Vector2[Float](0,0)

  def draw(window: RenderWindow): Unit = 
    val txture = Texture()
    txture.loadFromFile(sprite_path)
    val sprite = Sprite(txture)
    sprite.position = pos
    window.draw(sprite)


  def toTexture(path : String) : Texture =
    val tmp = Texture()
    tmp.loadFromFile(path)
    tmp
  
  def toTexture() : Texture =
    val tmp = Texture()
    tmp.loadFromFile(sprite_path)
    tmp
  
    

  

