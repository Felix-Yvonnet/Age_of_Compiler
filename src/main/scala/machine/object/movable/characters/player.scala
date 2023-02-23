package machine.`object`.movable.characters


import affichage.Resources
import machine.`object`.GameObject
import sfml.graphics.RenderWindow
import sfml.system.Vector2

class Player(name : String) extends GameObject:
    var béton = 0
    var argent = 0

    override def draw(window : RenderWindow) : Unit =
        Resources.drawText("Beton : "+béton,window)
        Resources.drawText("Moula : "+argent, window, Vector2[Float](0,30))


  

