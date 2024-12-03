import Direction.*

class Day16(input: String? = null) : AdventOfCodeDay(input) {
    private val grid = lines.map { it.toCharArray() }.toTypedArray()
    private val xRange = grid[0].indices
    private val yRange = grid.indices

    override fun part1() = countEnergized(Beam(0, 0, Right))

    override fun part2() = maxOf(
        xRange.maxOf { countEnergized(Beam(it, 0, Down)) },
        yRange.maxOf { countEnergized(Beam(0, it, Right)) },
        xRange.maxOf { countEnergized(Beam(it, yRange.last, Up)) },
        yRange.maxOf { countEnergized(Beam(xRange.last, it, Left)) },
    )

    private fun countEnergized(start: Beam): Int {
        val visited = Array(grid.size) { IntArray(grid[0].size) }
        val beams = mutableListOf(start)
        while (beams.isNotEmpty()) {
            val iterator = beams.listIterator()
            while (iterator.hasNext()) {
                val beam = iterator.next()
                if (visited[beam.y][beam.x] and beam.direction.bitValue != 0) {
                    iterator.remove()
                    continue
                }
                visited[beam.y][beam.x] = visited[beam.y][beam.x] or beam.direction.bitValue
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

data class Beam(var x: Int, var y: Int, var direction: Direction) {
    fun update(char: Char): Beam? {
        var newBeam: Beam? = null
        when (char) {
            '/' -> direction = when(direction) {
                Up -> Right
                Down -> Left
                Left -> Down
                Right -> Up
            }
            '\\' -> direction = when(direction) {
                Up -> Left
                Down -> Right
                Left -> Up
                Right -> Down
            }
            '|' -> if (direction.horizontal) {
                direction = Up
                newBeam = Beam(x, y + 1, Down)
            }
            '-' -> if (direction.vertical) {
                direction = Left
                newBeam = Beam(x + 1, y, Right)
            }
        }
        x += direction.dx
        y += direction.dy
        return newBeam
    }
}

fun main() = Day16().run()
