trait Character:
    var hp : Int = 1
    var satiety : Int = 1
    var pos_x : Int = 0
    var pos_y : Int = 0
    var dist_max : Int = 1
    

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