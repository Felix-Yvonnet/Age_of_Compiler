import sfml.graphics.*
import sfml.window.*
import sfml.system.Vector2f

@main def main =
  val window = RenderWindow(VideoMode(1024, 768), "Hello world")

  val texture = Texture()
  texture.loadFromFile("src/resources/cat.png")
  texture.repeated = true
  texture.smooth = true

  val sprite = Sprite(texture)
  sprite.move(500, 400)
  // sprite.position = Vector2f(10, 320)
  // sprite.rotation = 90.0
  // sprite.scale(0.3,0.3)

  val sprite2 = Sprite(texture)
  sprite2.move(300, 400)

  while window.isOpen() do
    for event <- window.pollEvent() do
      event match {
        case _: Event.Closed => window.closeWindow()
        case _ => ()
      }

    window.clear(Color.Black())
    window.draw(sprite)
    window.draw(sprite2)
    window.display()
