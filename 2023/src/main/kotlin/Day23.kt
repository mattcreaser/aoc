class Day23(input: String? = null) : AdventOfCodeDay(input) {
    data class Hike(val position: Position, var distance: Int, val visited: BooleanGrid) {
        fun move(direction: Direction) = apply {
            position += direction
            distance += 1
            visited[position] = true
        }
        fun split() = copy(position = position.copy(), visited = visited.deepCopy())
    }

    private val grid = inputAsCharGrid()

    override fun part1() = run(::canMove)
    override fun part2() = run(::canMoveIgnoreSlopes)

    private fun run(moveCheck: (Hike, Direction) -> Boolean): Int {
        val visited = Array(grid.size) { BooleanArray(grid.first().size) }
        visited[0][1] = true
        val hikes = mutableListOf(Hike(Position(1, 0), 0, visited))
        val end = Position(grid.xRange.last - 1, grid.yRange.last)
        val completed = mutableListOf<Hike>()
        while(hikes.isNotEmpty()) {
            val iterator = hikes.listIterator()
            while(iterator.hasNext()) {
                val hike = iterator.next()
                if (hike.position == end) {
                    completed.add(hike)
                    iterator.remove()
                    continue
                }
                val directions = getValidDirections(hike, moveCheck)
                if (directions.isEmpty()) { iterator.remove(); continue }
                for (i in 1..<directions.size) {
                    iterator.add(hike.split().move(directions[i]))
                }
                hike.move(directions.first())
            }
        }

        val longHike = completed.maxBy { it.distance }
        return longHike.distance
    }

    private fun getValidDirections(hike: Hike, moveCheck: (Hike, Direction) -> Boolean) = Direction.entries.filter { moveCheck(hike, it) }

    private fun canMove(hike: Hike, direction: Direction): Boolean {
        val updated = hike.position + direction
        val char = grid[updated]
        return (char == '.' || char == direction.char) && hike.visited[updated] == false
    }

    private fun canMoveIgnoreSlopes(hike: Hike, direction: Direction): Boolean {
        val updated = hike.position + direction
        val char = grid[updated]
        return char != null && char != '#' && hike.visited[updated] == false
    }
}

fun main() = Day23().run()