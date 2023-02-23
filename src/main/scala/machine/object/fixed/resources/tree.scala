package machine.`object`.fixed.resources

import machine.`object`.GameObject
import sfml.system.Vector2
import machine.`object`.movable.characters.Player

class tree(sprite_path : String = "src/resources/fixed_objects/tree_hand_made.png") extends GameObject(sprite_path) :

    pos = Vector2[Int](10,5)
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