package Scene

import scala.collection.mutable.Queue
import sfml.system.Vector2
import scala.collection.mutable.Set
import machine.`object`.movable.Movable
import machine.`object`.GameObject

class Scene(val grid: Array[Array[Option[GameObject]]]):

    def place_sthg(thing : GameObject, pos : Vector2[Int]): Unit =
        grid(pos.x)(pos.y) = Some(thing)
    
    def remove_sthg(thing : Int, pos : Vector2[Int]): Unit =
        grid(pos.x)(pos.y) match
            case Some(x) => grid(pos.x)(pos.y) = None
            case _ => ()

object Scene :
    def bfs(grid: Array[Array[Option[GameObject]]], init: Vector2[Int], fin: Vector2[Int]): Option[List[Vector2[Int]]] =
        val queue = Queue(init)
        var seen = Set(init)
        bfs(grid, fin, seen, queue)

    private def bfs(grid: Array[Array[Option[GameObject]]], goal: Vector2[Int], seen: Set[Vector2[Int]], queue: Queue[Vector2[Int]]): Option[List[Vector2[Int]]] =
        val v = queue.dequeue()
        println(v)
        if v == goal then return Some(List(v))
        seen.concat(Set(v))

        var neighbours = List((v.x + 1, v.y), (v.x - 1, v.y), (v.x, v.y + 1), (v.x, v.y - 1))
        neighbours = neighbours.filter((x,y) => 0<= x && x < 30 && 0<=y && y<20 && (grid(x)(y) match
                case Some (_) => false
                case _ => true ))

        for neighbour <- neighbours do
            if !seen(neighbour) then
                queue.enqueue(neighbour)
                bfs(grid, goal, seen, queue) match
                    case Some(x) => return Some(v :: x)
                    case _    => ()

        None
