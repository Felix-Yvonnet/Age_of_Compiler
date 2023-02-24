package machine.`object`.fixed.resources

import machine.`object`.GameObject
import sfml.system.Vector2
import machine.`object`.movable.characters.Player
import sfml.graphics.*

class Tree(sprite_path : String = "src/resources/fixed_objects/tree_hand_made.png") extends GameObject(sprite_path) :

    pos = Vector2[Int](15,10)
    val apportBéton = 10
    val apportMoula = 0
    waitTimeResources = 10
    agility  = 10

    def giveResources(player : Player) =
        if waitTimeResources == 0 then
            player.béton+=apportBéton
            player.argent+=apportMoula
            waitTimeResources = agility
        else waitTimeResources -= 1

    override def draw (window: RenderWindow): Unit = 
      if this.sprite_path != "" then
        val sprite = Sprite(this.texture)
        sprite.scale(0.02,0.03)
        sprite.position = Vector2[Float]((pos.x)*40,(pos.y-2)*40)
        window.draw(sprite)