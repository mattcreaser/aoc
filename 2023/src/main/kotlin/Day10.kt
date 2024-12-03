class Day10(input: String? = null) : AdventOfCodeDay(input) {

    private val grid = Array(lines.size) { lines[it].toCharArray() }

    override fun part1(): Int {
        var count = 1
        val (x, y) = findStart()
        val (a, b) = initPositions(x, y)
        while (a.x != b.x || a.y != b.y) {
            a.update(grid[a.y][a.x])
            b.update(grid[b.y][b.x])
            count++
        }
        return count
    }

    override fun part2(): Int {
        val (startX, startY) = findStart()
        val (a, b) = initPositions(startX, startY)

        val map = Array(grid.size) { CharArray(grid[0].size) { '.' } }
        map[startY][startX] = when {
            b.x == a.x -> '|'
            b.y == a.y -> '-'
            b.dx + b.dy + a.dx + a.dy == 2 -> 'F'
            b.dx + b.dy + a.dx + a.dy == -2 -> 'J'
            b.dx == -1 || a.dx == -1 -> '7'
            else -> 'L'
        }

        while (a.x != b.x || a.y != b.y) {
            map[a.y][a.x] = grid[a.y][a.x]
            map[b.y][b.x] = grid[b.y][b.x]
            a.update(grid[a.y][a.x])
            b.update(grid[b.y][b.x])
        }

        map[a.y][a.x] = grid[a.y][a.x]

        var count = 0
        map.forEach { row ->
            var inside = false
            row.forEach { char ->
                when (char) {
                    '.' -> if (inside) count++
                    '|', 'L', 'J' -> inside = !inside
                }
            }
        }

        return count
    }

    private fun initPositions(x: Int, y: Int) = buildList {
        if (x > 0) when (grid[y][x - 1]) { '-', 'L', 'F' -> add(Position(x - 1, y, -1, 0)) }
        if (x < grid[0].size - 1) when (grid[y][x + 1]) { '-', 'J', '7' -> add(Position(x + 1, y, 1, 0)) }
        if (y > 0) when (grid[y - 1][x]) { '|', 'F', '7' -> add(Position(x, y - 1, 0, -1)) }
        if (y < grid.size - 1) when (grid[y + 1][x]) { '|', 'J', 'L' -> add(Position(x, y + 1, 0, 1)) }
    }

    private fun findStart(): Pair<Int, Int> {
        grid.forEachIndexed { y, row -> row.forEachIndexed { x, char -> if (char == 'S') return@findStart x to y } }
        error("Start not found")
    }

    private data class Position(var x: Int, var y: Int, var dx: Int, var dy: Int) {
        fun update(char: Char) {
            when (char) {
                'F' -> { dx += 1; dy += 1 }
                'J' -> { dx -= 1; dy -= 1 }
                '7' -> { dx -= 1; dy += 1 }
                'L' -> { dx += 1; dy -= 1 }
            }
            x += dx
            y += dy
        }
    }
}

fun main() = Day10().run()
