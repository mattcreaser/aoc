import kotlin.math.max

class Day2(input: String? = null) : AdventOfCodeDay(input) {
    private val regex = """(\d+) (red|green|blue)""".toRegex()

    override fun part1() = lineSequence.foldIndexed(0) { index, sum, line ->
        val matches = regex.findAll(line)
        val possible = matches.all { match ->
            match.groupValues[1].toInt() <= when (match.groupValues[2]) {
                "red" -> 12
                "green" -> 13
                else -> 14
            }
        }
        sum + if (possible) index + 1 else 0
    }

    override fun part2() = lineSequence.sumOf { line ->
        var redNeeded = 0
        var greenNeeded = 0
        var blueNeeded = 0

        val matches = regex.findAll(line)
        matches.forEach { match ->
            val count = match.groupValues[1].toInt()
            when (match.groupValues[2]) {
                "red" -> redNeeded = max(redNeeded, count)
                "green" -> greenNeeded = max(greenNeeded, count)
                "blue" -> blueNeeded = max(blueNeeded, count)
                else -> error("Unknown color")
            }
        }

        redNeeded * greenNeeded * blueNeeded
    }
}

fun main() = Day2().run()
