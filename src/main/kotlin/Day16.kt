

class Day16(input: String? = null) : AdventOfCodeDay(input) {
    private val grid = lines.map { it.toCharArray() }.toTypedArray()
    private val xRange = grid[0].indices
    private val yRange = grid.indices

    override fun part1() = countEnergized(Beam(0, 0, 1, 0))

    override fun part2() = maxOf(
        xRange.maxOf { countEnergized(Beam(it, 0, 0, 1)) },
        yRange.maxOf { countEnergized(Beam(0, it, 1, 0)) },
        xRange.maxOf { countEnergized(Beam(it, yRange.last, 0, -1)) },
        yRange.maxOf { countEnergized(Beam(xRange.last, it, -1, 0)) },
    )

    private fun countEnergized(start: Beam): Int {
        val visited = Array(grid.size) { IntArray(grid[0].size) }
        val beams = mutableListOf(start)
        while (beams.isNotEmpty()) {
            val iterator = beams.listIterator()
            while (iterator.hasNext()) {
                val beam = iterator.next()
                if (visited[beam.y][beam.x] and beam.direction != 0) {
                    iterator.remove()
                    continue
                }
                visited[beam.y][beam.x] = visited[beam.y][beam.x] or beam.direction
                val newBeam = beam.update(grid[beam.y][beam.x])
                if (!beam.isOnGrid()) iterator.remove()
                if (newBeam != null && newBeam.isOnGrid()) {
                    iterator.add(newBeam)
                }
            }
        }
        return visited.sumOf { row -> row.count { it != 0 } }
    }

    private fun Beam.isOnGrid() = xRange.contains(x) && yRange.contains(y)
}

data class Beam(var x: Int, var y: Int, var dx: Int, var dy: Int) {
    val direction: Int
        get() = when {
            dx == 1 -> 1
            dy == 1 -> 2
            dx == -1 -> 4
            else -> 8
        }

    fun update(char: Char): Beam? {
        var newBeam: Beam? = null
        when (char) {
            '/' -> dy = -dx.also { dx = -dy }
            '\\' -> dy = dx.also { dx = dy }
            '|' -> if (dx != 0) {
                dy = -1; dx = 0
                newBeam = Beam(x, y + 1, 0, 1)
            }
            '-' -> if (dy != 0) {
                dy = 0; dx = -1
                newBeam = Beam(x + 1, y, 1, 0)
            }
        }
        x += dx
        y += dy
        return newBeam
    }
}

fun main() = Day16().run()
