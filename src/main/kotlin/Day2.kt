import kotlin.math.max

private val lines = readInputFile(2).lines()

private val regex = """(\d+) (red|green|blue)""".toRegex()

private const val maxRed = 12
private const val maxGreen = 13
private const val maxBlue = 14

fun main() {
    runAttempt(::part1)
    runAttempt(::part2)
}

private fun part1() = lines.foldIndexed(0) { index, sum, line ->
    val matches = regex.findAll(line)
    val possible = matches.all { match ->
        val count = match.groupValues[1].toInt()
        count <= when (match.groupValues[2]) {
            "red" -> maxRed
            "green" -> maxGreen
            "blue" -> maxBlue
            else -> error("Unknown color")
        }
    }
    sum + if (possible) index + 1 else 0
}

private fun part2() = lines.sumOf { line ->
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
