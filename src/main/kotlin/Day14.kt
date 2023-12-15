typealias RockGrid = Array<CharArray>

class Day14(input: String? = null) : AdventOfCodeDay(input) {

    private val rows = lines.size
    private val cols = lines[0].length

    override fun part1() = lines.toRockGrid().shiftNorth().calculateWeight()

    override fun part2(): Int {
        val grids = mutableListOf(lines.toRockGrid())
        var cycle: IntRange? = null

        while (cycle == null) {
            val newGrid = grids.last().deepCopy().shiftNorth().shiftWest().shiftSouth().shiftEast()
            for (i in grids.size - 1 downTo 0) {
                if (newGrid contentDeepEquals grids[i]) cycle = i..<grids.size
            }
            grids.add(newGrid)
        }

        val cycleLength = cycle.last - cycle.first + 1
        val remaining = (1_000_000_000 - cycle.first) % cycleLength
        return grids[cycle.first + remaining].calculateWeight()
    }

    private fun RockGrid.shiftNorth() = apply {
        for (x in 0..<cols) {
            var next = 0
            for (y in 0..<rows) {
                when (this[y][x]) {
                    'O' -> { this[y][x] = '.'; this[next++][x] = 'O' }
                    '#' -> next = y + 1
                }
            }
        }
    }

    private fun RockGrid.shiftWest() = apply {
        for (y in 0..<rows) {
            var next = 0
            for (x in 0..<cols) {
                when (this[y][x]) {
                    'O' -> { this[y][x] = '.'; this[y][next++] = 'O' }
                    '#' -> next = x + 1
                }
            }
        }
    }

    private fun RockGrid.shiftSouth() = apply {
        for (x in 0..<cols) {
            var next = rows - 1
            for (y in rows - 1 downTo 0) {
                when (this[y][x]) {
                    'O' -> { this[y][x] = '.'; this[next--][x] = 'O' }
                    '#' -> next = y - 1
                }
            }
        }
    }

    private fun RockGrid.shiftEast() = apply {
        for (y in 0..<rows) {
            var next = cols - 1
            for (x in cols - 1 downTo 0) {
                when (this[y][x]) {
                    'O' -> { this[y][x] = '.'; this[y][next--] = 'O' }
                    '#' -> next = x - 1
                }
            }
        }
    }

    private fun RockGrid.calculateWeight() = this.foldIndexed(0) { i, sum, row -> sum + (this@calculateWeight.size - i) * row.count { it == 'O' } }
    private fun List<String>.toRockGrid() = map { it.toCharArray() }.toTypedArray()
    private fun RockGrid.deepCopy() = Array(size) { this[it].copyOf() }
}

fun main() = Day14().run()
