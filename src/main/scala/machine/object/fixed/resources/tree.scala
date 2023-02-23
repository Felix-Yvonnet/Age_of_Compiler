package machine.`object`.fixed.resources

import machine.`object`.GameObject
import sfml.system.Vector2

class tree(sprite_path : String = "src/resources/fixed_objects/tree_hand_made.png") extends GameObject(sprite_path) :

    pos = Vector2[Int](10,5)