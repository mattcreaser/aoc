class Day9(input: String? = null) : AdventOfCodeDay(input) {
    override fun part1() = lineSequence.toInts()
        .map { it.toList().calculateNext { next -> last() + next } }
        .sum()

    override fun part2() = lineSequence.toInts()
        .map { it.toList().calculateNext { next -> first() - next } }
        .sum()

    private fun List<Int>.calculateNext(func: List<Int>.(Int) -> Int): Int {
        val diffs = mapAdjacentPairs { a, b -> b - a }
        return if (diffs.all { it == 0 }) first() else func(diffs.calculateNext(func))
    }
}

fun main() = Day9().run()
