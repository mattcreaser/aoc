import util.toInts
import kotlin.math.abs

class Day2(
    input: String? = null,
) : AdventOfCodeDay(input) {
    override fun part1(): Int = lines.count { it.toInts().isSafe() }

    override fun part2(): Int =
        lines.count { line ->
            val ints = line.toInts().toList()
            ints.indices.any { i ->
                ints.asSequence().filterIndexed { index, _ -> index != i }.isSafe()
            }
        }

    private fun Sequence<Int>.isSafe(): Boolean {
        val diffs = zipWithNext().map { it.second - it.first }
        val increasing = diffs.first() > 0
        return diffs.all { it.isSafe(increasing) }
    }

    private fun Int.isSafe(increasing: Boolean): Boolean {
        val magnitude = abs(this)
        return this != 0 && (this > 0) == increasing && magnitude >= 1 && magnitude <= 3
    }
}

fun main() = Day2().run()
