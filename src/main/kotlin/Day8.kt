

class Day8(input: String? = null) : AdventOfCodeDay(input) {

    data class Node(val string: String, val left: String, val right: String)

    private val regex = """([A-Z]{3}) = \(([A-Z]{3}), ([A-Z]{3})\)""".toRegex()
    private val directions = this.lines.first().asSequence().repeatForever()
    private val nodes = this.lines.drop(2).mapNotNull { regex.matchEntire(it)?.groupValues }.associate {
        it[1] to Node(it[1], it[2], it[3])
    }

    override fun part1() = run(isStart = { it.string == "AAA" }, isEnd = { it.string == "ZZZ" })
    override fun part2() = run(isStart = { it.string.last() == 'A' }, isEnd = { it.string.last() == 'Z' })
    private fun run(isStart: (Node) -> Boolean, isEnd: (Node) -> Boolean) = nodes.values.asSequence().filter(isStart).map { distance(it, isEnd) }.lcm()

    private fun distance(node: Node, isEnd: (Node) -> Boolean): Long {
        var location = node
        return directions.takeWhile { dir ->
            location = nodes[if (dir == 'L') location.left else location.right]!!
            !isEnd(location)
        }.count().toLong() + 1
    }
}

fun main() = Day8().run()
