import kotlin.math.abs

// https://adventofcode.com/2024/day/1
class Day1(input: String? = null) : AdventOfCodeDay(input) {

    private fun lists(): Pair<List<Int>, List<Int>> {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        lines.forEach {
            val (first, second) = it.split("   ")
            list1.add(first.toInt())
            list2.add(second.toInt())
        }
        return list1 to list2
    }

    override fun part1(): Int {
        val (list1, list2) = lists()
        val sorted1 = list1.sorted()
        val sorted2 = list2.sorted()
        return sorted1.foldIndexed(0) { i, sum, value -> sum + abs(value - sorted2[i]) }
    }

    override fun part2(): Int {
        val (list1, list2) = lists()
        val counted1 = list1.groupBy { it }.mapValues { it.value.size }
        val counted2 = list2.groupBy { it }.mapValues { it.value.size }

        return counted1.entries.sumOf { (num, count) ->
            val count2 = counted2[num] ?: 0
            num * count * count2
        }
    }
}

fun main() = Day1().run()