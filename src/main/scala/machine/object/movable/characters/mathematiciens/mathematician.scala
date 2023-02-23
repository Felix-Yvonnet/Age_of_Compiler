package machine.`object`.movable.characters.mathematiciens

import machine.`object`.movable.characters.Pown
import sfml.system.Vector2
import machine.`object`.GameObject

class Mathematician(sprite_path : String = "src/resources/fixed_objects/cat.png") extends GameObject(sprite_path) :
    isMovable = true
    waitTime = 100
    speed = 100
    pos = Vector2[Int](10,5)


