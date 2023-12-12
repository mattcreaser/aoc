import kotlin.math.abs

class Day11(
    input: String? = null,
    private val part2Expansion: Int = 1_000_000,
) : AdventOfCodeDay(input) {

    data class Galaxy(var x: Int, var y: Int) {
        fun distanceTo(other: Galaxy) = abs(x - other.x) + abs(y - other.y)
    }

    private fun sumDistances(expansion: Int): Long {
        val emptyRows = BooleanArray(lines.size) { true }
        val emptyCols = BooleanArray(lines[0].length) { true }
        val galaxies = mutableListOf<Galaxy>()

        lines.forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                if (char == '#') {
                    emptyCols[x] = false
                    emptyRows[y] = false
                    galaxies += Galaxy(x, y)
                }
            }
        }

        val expandY = emptyRows.runningSum { empty -> empty.toInt() * (expansion - 1) }
        val expandX = emptyCols.runningSum { empty -> empty.toInt() * (expansion - 1) }

        galaxies.forEach {
            it.x += expandX[it.x]
            it.y += expandY[it.y]
        }

        return galaxies.pairs().sumOf { (a, b) -> a.distanceTo(b).toLong() }
    }

    override fun part1() = sumDistances(2)
    override fun part2() = sumDistances(part2Expansion)
}

fun main() = Day11().run()
