import kotlin.math.abs

class Day11(
    input: String? = null,
    private val part2Multiplier: Int = 1_000_000,
) : AdventOfCodeDay(input) {

    data class Galaxy(var x: Int, var y: Int) {
        fun distanceTo(other: Galaxy) = abs(x - other.x) + abs(y - other.y)
    }

    private fun sumDistances(expandMultiplier: Int): Long {
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

        val expandY = emptyRows.runningFold(0) { count, empty -> count + empty.toInt() * (expandMultiplier - 1) }
        val expandX = emptyCols.runningFold(0) { count, empty -> count + empty.toInt() * (expandMultiplier - 1) }

        galaxies.forEach {
            it.x += expandX[it.x]
            it.y += expandY[it.y]
        }

        return galaxies.foldIndexed(0L) { i, sum, a -> sum + galaxies.drop(i).sumOf { b -> b.distanceTo(a).toLong() } }
    }

    override fun part1() = sumDistances(2)
    override fun part2() = sumDistances(part2Multiplier)
}

fun main() = Day11().run()
