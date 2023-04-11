# Age_of_Compiler
## Introduction
Welcome to the Age of Compiler, a strategy game set in the universe of ENS Paris Saclay and LSV. This game is inspired by the classic game Age of Empires, but with a unique twist, it presents the history of the implentation of the ENS into saclaysian's land overwelmed by Centrale's school.

## Gameplay
In Age of Compiler, you will arise the ENS from the Centralian jungle in Saclay. You will gather resources, build structures, train units, and engage in battles with your rivals. Your goal is to develop yourself in order to take control over the map and spread the perfection of the ENS through the world.

## Features
- Strategy-based gameplay set in the world of the Saclaysian's land with all ENS departments represented
- Resource gathering and management
- Building construction and unit training
- Battles with rival teams
- Rich and immersive story set in the world of ENS Paris Saclay and LSV

## Requirements
Ubuntu and sbt to run efficiently
- 4 GHz CPU
- 8 GB RAM
- 7 GB available storage space
- A RTX 4000 or above
## Download
You can download the game from the directory above.

## Support
No support available. You know our e-mail so if necessary send us your concerns.


## Coding
### tree
```
scala

    ├── Main.scala

    ├── affichage

    │   ├── design

    │   │   └── DrawDecorations.scala

    │   └── resources.scala

    └── machine

        ├── event

        │   ├── Scalaseries.scala

        │   └── handling.scala

        ├── go

        │   ├── GameObject.scala

        │   ├── invisible

        │   │   ├── Inventory.scala

        │   │   └── Player.scala

        │   └── printable

        │       ├── Alive.scala

        │       ├── drawable.scala

        │       ├── fixed

        │       │   ├── buildings

        │       │   │   ├── Buildings.scala

        │       │   │   ├── GeorgesSand.scala

        │       │   │   └── Producer.scala

        │       │   ├── decoration

        │       │   │   └── wall.scala

        │       │   └── resources

        │       │       ├── Resources.scala

        │       │       └── tree.scala

        │       └── movable

        │           ├── characters

        │           │   ├── Fighters.scala

        │           │   ├── enemy

        │           │   │   └── Centralien.scala

        │           │   └── friendly

        │           │       └── units

        │           │           ├── Friendly.scala

        │           │           ├── mathematicians

        │           │           │   ├── highInter.scala

        │           │           │   ├── init.scala

        │           │           │   ├── lowInter.scala

        │           │           │   └── pm.scala

        │           │           └── physiciens

        │           │               ├── alainAspect.scala

        │           │               ├── highInter.scala

        │           │               ├── init.scala

        │           │               └── lowInter.scala

        │           └── mouvable.scala

        └── scene

            ├── Point.scala

            ├── aStar.scala

            └── gameMap.scala
```

### Implementation
- [ ] Put all textures together in an object in order to load them less
- [ ] Create many different characters with reasonable stats
- [x] Characters can attack, move and collect resources 
- [x] Characters cannot attack friends
- [x] Make buildings produce fighters
- [ ] Clean the code (At the very end)
- [x] Write the story and interactions
- [x] Add a prompter at the bottom to show specification about characters and buildings
- [ ] Add an introduction pannel
- [ ] Don't forgot to make centraliens attack by wave
- [ ] Add a remover for the units builder
- [ ] Add a place for building buildings (defense towers, money extractors...)
- [ ] Increase the size of the map
- [x] Add a tree placer
- [x] Code a better IA for the Centralien (but not too good)


## Conclusion
We hope you enjoy playing Age of Compiler! We hope that you will find the game to be both challenging and fun. Don't hesitate if you want to give us a five star on jvc.com or senscritique.com : the most reliable sites.





## Why I hate Scala
- it uses sbt which is slow, run for 2 minutes sometimes
- sbt may get killed at any moment
- sbt requires a script of a hundred for lines before actually coding
- sbt may :
    - fails and/or be killed for no reason
    - fail for a good reason
    - succeed even though the code is bad (no array overtaking verification)
    - succeed when it has to
  so no possibility to trust it...
- error messages are poop : no explanation just java saying it's dead with code error 138 or clang saying that it doesn't like me (and when I restart without changing nothing it works again...)
- no possibilities to `throw` an error message, it will disappear in the soul of sbt. You should first print it.
- if a bug happen not only you can't know where it is but also when it is
- sbt is killed again
- you google the error message and see dark github forums not answering the initial problem
- no array limits verification or anything like that, it just stops with no error message

Hence I hate scala :)




