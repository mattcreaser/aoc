class Day21(
    input: String? = null,
    private val part1Steps: Int = 64,
    private val part2Steps: Int = 26501365,
) : AdventOfCodeDay(input) {
    override fun part1(): Int {
        val grid = inputAsCharGrid()
        val positions = mutableSetOf(findStart(grid))
        val reachable = updatePositions(grid, 0, positions)
        return reachable.count()
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }

    private tailrec fun updatePositions(grid: CharGrid, count: Int, positions: Set<Position>): Set<Position> {
        if (count == part1Steps) return positions
        val next = mutableSetOf<Position>()
        for (position in positions) {
            Direction.entries.forEach { dir ->
                val newPosition = position + dir
                if (grid[newPosition] == '.') next += newPosition
            }
        }
        return updatePositions(grid, count + 1, next)
    }

    private fun findStart(grid: CharGrid) = grid.positionOf { it == 'S' }?.also { grid[it] = '.' } ?: error("Start position not found")
}

fun main() = Day21().run()
