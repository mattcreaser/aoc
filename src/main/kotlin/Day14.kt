import java.util.*

typealias RockGrid = Array<CharArray>

class Day14(input: String? = null) : AdventOfCodeDay(input) {

    private val rows = lines.size
    private val cols = lines[0].length

    override fun part1() = lines.toRockGrid().shiftedNorth().calculateWeight()

    override fun part2(): Int {
        val grids = mutableListOf(lines.toRockGrid())
        var cycle: IntRange? = null

        while (cycle == null) {
            val newGrid = grids.last().shiftedNorth().shiftedWest().shiftedSouth().shiftedEast()
            for (i in grids.size - 1 downTo 0) {
                if (newGrid contentDeepEquals grids[i]) cycle = i..<grids.size
            }
            grids.add(newGrid)
        }

        val cycleLength = cycle.last - cycle.first + 1
        val remaining = (1_000_000_000 - cycle.first) % cycleLength
        return grids[cycle.first + remaining].calculateWeight()
    }

    private fun RockGrid.shiftedNorth() = newGrid().also {
        for (x in 0..<cols) {
            var next = 0
            for (y in 0..<rows) {
                when (this[y][x]) {
                    'O' -> it[next++][x] = 'O'
                    '#' -> { it[y][x] = '#'; next = y + 1 }
                }
            }
        }
    }

    private fun RockGrid.shiftedWest() = newGrid().also {
        for (y in 0..<rows) {
            var next = 0
            for (x in 0..<cols) {
                when (this[y][x]) {
                    'O' -> it[y][next++] = 'O'
                    '#' -> { it[y][x] = '#'; next = x + 1 }
                }
            }
        }
    }

    private fun RockGrid.shiftedSouth() = newGrid().also {
        for (x in 0..<cols) {
            var next = rows - 1
            for (y in rows - 1 downTo 0) {
                when (this[y][x]) {
                    'O' -> it[next--][x] = 'O'
                    '#' -> { it[y][x] = '#'; next = y - 1 }
                }
            }
        }
    }

    private fun RockGrid.shiftedEast() = newGrid().also {
        for (y in 0..<rows) {
            var next = cols - 1
            for (x in cols - 1 downTo 0) {
                when (this[y][x]) {
                    'O' -> it[y][next--] = 'O'
                    '#' -> { it[y][x] = '#'; next = x - 1 }
                }
            }
        }
    }

    private fun RockGrid.calculateWeight() = this.foldIndexed(0) { i, sum, row -> sum + (this@calculateWeight.size - i) * row.count { it == 'O' } }
    private fun List<String>.toRockGrid() = map { it.toCharArray() }.toTypedArray()
    private fun newGrid() = Array(rows) { CharArray(cols) { '.' } }
}

fun main() = Day14().run()
