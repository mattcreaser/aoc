

class Day13(input: String? = null) : AdventOfCodeDay(input) {
    override fun part1() = run(::findReflectionPoint)
    override fun part2() = run(::findSmudgedReflectionPoint)

    private fun run(findIndex: IntArray.() -> Int?) = lines.splitBy { it.isEmpty() }.filterNot { it.isEmpty() }.sumOf { grid ->
        val rowScores = IntArray(grid.size)
        val colScores = IntArray(grid[0].length)
        grid.forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                if (char == '#') {
                    colScores[x] += (1 shl y)
                    rowScores[y] += (1 shl x)
                }
            }
        }
        colScores.findIndex() ?: (rowScores.findIndex()!! * 100)
    }

    private fun findReflectionPoint(scores: IntArray): Int? {
        search@ for (i in 0..<(scores.size - 1)) {
            val toCheck = minOf(i + 1, scores.size - i - 1)
            for (j in 0..<toCheck) if (scores[i - j] != scores[i + j + 1]) continue@search
            return i + 1
        }
        return null
    }

    private fun findSmudgedReflectionPoint(scores: IntArray): Int? {
        for (i in 0..<(scores.size - 1)) {
            val toCheck = minOf(i + 1, scores.size - i - 1)
            var numSmudged = 0
            var numReflected = 0
            for (j in 0..<toCheck) {
                val a = scores[i - j]
                val b = scores[i + j + 1]
                val x = a xor b
                if (a == b) numReflected++ else if (x and (x - 1) == 0) numSmudged++
            }

            if (numSmudged == 1 && numReflected == toCheck - 1) return i + 1
        }
        return null
    }
}

fun main() = Day13().run()
