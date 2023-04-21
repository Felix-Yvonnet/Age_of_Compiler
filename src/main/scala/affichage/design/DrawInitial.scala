package affichage.design

import machine.scene.GameMap
import machine.go.printable.fixed.buildings.enemy.Centrale
import machine.scene.Point
import machine.go.printable.fixed.decoration.Wall
import machine.go.printable.fixed.buildings.friendly.GeorgesSand
import machine.go.printable.movable.characters.friendly.units.mathematicians.Mathematician
import machine.go.printable.fixed.buildings.friendly.towers.TeslaBuilding

object DrawInitial :
    def drawInit(scene: GameMap): Unit =
        buildCentrale(scene)
        buildFreindlySide(scene)

    def buildCentrale(scene: GameMap) =
        val centrale = Centrale(Point(scene.width-1, scene.height-1))
        scene.placeSthg(centrale)
        buildWallArroundCentrale(scene)

    def buildWallArroundCentrale(scene: GameMap) =
        for k <- 1 to 5 if k!= 3 do
            scene.placeSthg(Wall(Point(scene.width-k, scene.height-6)))
        for k <- 1 to 5 if k!= 3 do
            scene.placeSthg(Wall(Point(scene.width-6, scene.height-k)))

        for k <- 1 to 7 if k!= 5 do
            scene.placeSthg(Wall(Point(scene.width-k, scene.height-8)))
        for k <- 1 to 7 if k!= 5 do
            scene.placeSthg(Wall(Point(scene.width-8, scene.height-k)))

    def buildFreindlySide(scene: GameMap): Unit =
        scene.placeSthg(GeorgesSand(Point(0,0)))
        scene.placeSthg(Mathematician(Point(2,2)))
        scene.placeSthg(TeslaBuilding(Point(2,0)))
