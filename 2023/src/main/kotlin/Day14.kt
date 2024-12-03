typealias RockGrid = Array<CharArray>

class Day14(input: String? = null) : AdventOfCodeDay(input) {

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

    private fun RockGrid.shiftNorth() = shiftVertical(indices, 1)
    private fun RockGrid.shiftSouth() = shiftVertical(indices.reversed(), -1)
    private fun RockGrid.shiftWest() = shiftHorizontal(indices, 1)
    private fun RockGrid.shiftEast() = shiftHorizontal(indices.reversed(), -1)

    private fun RockGrid.shiftHorizontal(xRange: IntProgression, dX: Int) = apply {
        for (y in indices) {
            var next = xRange.first
            for (x in xRange) {
                when (this[y][x]) {
                    'O' -> { this[y][x] = '.'; this[y][next] = 'O'; next += dX }
                    '#' -> next = x + dX
                }
            }
        }
    }

    private fun RockGrid.shiftVertical(yRange: IntProgression, dY: Int) = apply {
        for (x in indices) {
            var next = yRange.first
            for (y in yRange) {
                when (this[y][x]) {
                    'O' -> { this[y][x] = '.'; this[next][x] = 'O'; next += dY }
                    '#' -> next = y + dY
                }
            }
        }
    }

    private fun RockGrid.calculateWeight() = this.foldIndexed(0) { i, sum, row -> sum + (size - i) * row.count { it == 'O' } }
    private fun List<String>.toRockGrid() = map { it.toCharArray() }.toTypedArray()
    private fun RockGrid.deepCopy() = Array(size) { this[it].copyOf() }
}

fun main() = Day14().run()
