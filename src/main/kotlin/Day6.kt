import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

private val input = readInputFile(6).lines()
private val regex = "\\d+".toRegex()
private val times = regex.findAll(input[0]).map { it.value.toLong() }.toList()
private val distances = regex.findAll(input[1]).map { it.value.toLong() }.toList()

private data class Race(val time: Long, val distance: Long)

fun main() {
    runAttempt(::part1)
    runAttempt(::part2)
}

private fun part1() = times.zip(distances) { time, distance -> Race(time, distance) }
    .fold(1L) { product, race -> product * race.calculateWaysToWin() }

private fun part2(): Int {
    val time = times.joinToString("").toLong()
    val distance = distances.joinToString("").toLong()
    return Race(time, distance).calculateWaysToWin()
}

// First attempt - search from both ends
private fun Race.countWaysToWin(): Int {
    val range = (1..<time)
    val first = range.first { (time - it) * it > distance }
    val last = range.reversed().first { (time - it) * it > distance }
    return (last - first).toInt() + 1
}

// Second attempt - calculate zeroes
private fun Race.calculateWaysToWin(): Int {
    val (first, last) = quadraticRoots(-1f, time.toFloat(), -distance.toFloat())
    return (floor(last) - ceil(first)).toInt() + 1
}
