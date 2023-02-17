package Scene

import scala.collection.mutable.Queue
import sfml.system.Vector2
import scala.collection.mutable.Set
import machine.`object`.movable.Movable
import machine.`object`.GameObject

class Scene(val grid: Array[Array[Option[GameObject]]]):

    private val gameObjectMovable: List[Movable] = List()

    def doMove() =
        gameObjectMovable.map(_.move())

    def bfs(init: Vector2[Int], fin: Vector2[Int]): Option[List[Vector2[Int]]] =
        val queue = Queue(init)
        var seen = Set(init)
        bfs(fin, seen, queue)

    private def bfs(goal: Vector2[Int], seen: Set[Vector2[Int]], queue: Queue[Vector2[Int]]): Option[List[Vector2[Int]]] =
        val v = queue.dequeue()
        if v == goal then return Some(List(v))
        seen.concat(Set(v))

        val neighbours = List((v.x + 1, v.y), (v.x - 1, v.y), (v.x, v.y + 1), (v.x, v.y - 1))
        neighbours.filter((x,y) => grid(x)(y) match
                case None => true
                case Some (_) => false)

        for neighbour <- neighbours do
            if !seen(neighbour) then
                queue.enqueue(neighbour)
                bfs(goal, seen, queue) match
                    case Some(x) => return Some(v :: x)
                    case None    => ()

        None

    def place_sthg(thing : Int, pos : Vector2[Int]): Unit =
        grid(pos.x)(pos.y) match
            case None => val tmp = GameObject(); tmp.typ = thing; grid(pos.x)(pos.y) = Some(tmp)
            case Some(x) => x.typ = thing
    def remove_sthg(thing : Int, pos : Vector2[Int]): Unit =
        grid(pos.x)(pos.y) match
            case None => ()
            case Some(x) => grid(pos.x)(pos.y) = None
