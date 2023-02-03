trait Character:
    var hp : Int
    var satiety : Int
    var pos_x : Int
    var pos_y : Int
    var speed : Int
    var dmg : Int
    

    // Movements
    def move_right (dist : Int):
        pos_x += dist
    
    def move_down (dist : Int):
        pos_y += dist
    
    def move_left (dist : Int):
        pos_x -= dist
    
    def move_up (dist : Int):
        pos_y -= dist

    // How to lose life

    def hungry () =
        satiety -= 1

    def starve () =
        hp -= 1