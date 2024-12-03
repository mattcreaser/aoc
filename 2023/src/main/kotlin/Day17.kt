import Direction.*
import java.util.PriorityQueue

class Day17(input: String? = null) : AdventOfCodeDay(input) {

    private val grid = lines.map { it.map { char -> char.digitToInt() } }.toTypedArray()

    data class Node(val pos: Position, val dir: Direction, val cost: Int, val prev: Node? = null) {
        override fun equals(other: Any?) = other is Node && pos == other.pos && dir.vertical == other.dir.vertical
        override fun hashCode(): Int {
            var result = pos.hashCode()
            result = 31 * result + dir.vertical.toInt()
            return result
        }

        constructor(x: Int, y: Int, dir: Direction, cost: Int) : this(Position(x, y), dir, cost)
    }

    private val xRange = lines[0].indices
    private val yRange = lines.indices

    override fun part1() = run(1..3)
    override fun part2() = run(4..10)

    private fun run(searchRange: IntRange): Int {
        val queue = PriorityQueue<Node> { a, b -> a.cost - b.cost }.apply {
            add(Node(0, 0, Down, 0))
            add(Node(0, 0, Right, 0))
        }
        val complete = mutableSetOf<Node>()

        while (true) {
            val next = queue.remove()
            if (next.pos.x == xRange.last && next.pos.y == yRange.last) {
                return next.cost
            }
            complete.add(next)
            var nextA: Node? = next
            var nextB: Node? = next
            for (i in 1..searchRange.last) {
                if (nextA != null) {
                    nextA = getNext(nextA, next.dir.orthagonals[0])
                    if (i >= searchRange.first && nextA != null && !complete.contains(nextA)) {
                        addToQueueIfMin(nextA, queue)
                    }
                }
                if (nextB != null) {
                    nextB = getNext(nextB, next.dir.orthagonals[1])
                    if (i >= searchRange.first && nextB != null && !complete.contains(nextB)) {
                        addToQueueIfMin(nextB, queue)
                    }
                }
            }
        }
    }

    private fun getNext(node: Node, dir: Direction): Node? {
        val pos = node.pos + dir
        return grid.getOrNull(pos)?.let { Node(pos, dir, it + node.cost, node) }
    }

    private fun addToQueueIfMin(node: Node, queue: PriorityQueue<Node>) {
        val inQueue = queue.find { it == node }
        if (inQueue == null) { queue.add(node) } else if (inQueue.cost > node.cost) { queue.remove(inQueue); queue.add(node) }
    }
}

fun main() = Day17().run()
