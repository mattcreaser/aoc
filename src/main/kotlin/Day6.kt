

private val input = readInputFile(6).lines()
private val regex = "\\d+".toRegex()
private val times = regex.findAll(input[0]).map { it.value.toLong() }
private val distances = regex.findAll(input[1]).map { it.value.toLong() }

private data class Race(val time: Long, val distance: Long)

fun main() {
    runAttempt(::part1)
    runAttempt(::part2)
}

private fun part1() = times.zip(distances) { time, distance -> Race(time, distance) }
    .fold(1L) { product, race -> product * race.countWaysToWin() }

private fun part2(): Long {
    val time = times.joinToString("").toLong()
    val distance = distances.joinToString("").toLong()
    return Race(time, distance).countWaysToWin()
}

private fun Race.countWaysToWin(): Long {
    val range = (1..<time)
    val first = range.first { (time - it) * it > distance }
    val last = range.reversed().first { (time - it) * it > distance }
    return last - first + 1
}
