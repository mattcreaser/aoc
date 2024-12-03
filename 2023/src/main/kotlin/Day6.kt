import kotlin.math.ceil
import kotlin.math.floor

class Day6(input: String? = null) : AdventOfCodeDay(input) {
    private data class Race(val time: Long, val distance: Long)

    override fun part1(): Any {
        val (times, distances) = lineSequence.map { line -> line.toLongs().toList() }.toList()
        return times.zip(distances, ::Race).fold(1L) { product, race -> product * race.calculateWaysToWin() }
    }

    override fun part2(): Any {
        val (time, distance) = lineSequence.map { it.filter(Char::isDigit) }.map(String::toLong).toList()
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
        return (ceil(last - 1) - floor(first + 1)).toInt() + 1
    }
}

fun main() = Day6().run()
