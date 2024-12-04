import util.*

class Day4(
    input: String? = null,
) : AdventOfCodeDay(input) {
    private val grid = charGrid

    override fun part1(): Int =
        grid.positions().sumOf { position ->
            Movement.EightWay.entries.count { move ->
                grid.matches(position, move, "XMAS")
            }
        }

    override fun part2(): Int = grid.positions().count { position ->
        if (grid[position] == 'A') {
            val diag1 = grid.getOrNull(position + NorthEast) == 'M' && grid.getOrNull(position + SouthWest) == 'S' ||
                    grid.getOrNull(position + NorthEast) == 'S' && grid.getOrNull(position + SouthWest) == 'M'
            val diag2 = grid.getOrNull(position + NorthWest) == 'M' && grid.getOrNull(position + SouthEast) == 'S' ||
                    grid.getOrNull(position + NorthWest) == 'S' && grid.getOrNull(position + SouthEast) == 'M'
            diag1 && diag2
        } else false
    }
}

fun main() = Day4().run()
