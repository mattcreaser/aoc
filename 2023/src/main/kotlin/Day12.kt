import util.toInts

class Day12(input: String? = null) : AdventOfCodeDay(input) {
    private val positionCache = mutableMapOf<Pair<Int, Int>, List<IntRange>>()
    private val arrangementCache = mutableMapOf<Pair<Int, Int>, Long>()

    override fun part1() = lines.sumOf {
        positionCache.clear()
        arrangementCache.clear()
        val (springs, nums) = it.split(' ')
        val counts = nums.toInts().toList()
        countArrangements(springs, 0, counts, 0)
    }

    override fun part2() = lines.sumOf {
        positionCache.clear()
        arrangementCache.clear()
        val (springs, nums) = it.split(' ')
        countArrangements("$springs?".repeat(5).dropLast(1), 0, nums.toInts().repeat(5).toList(), 0)
    }

    private fun countArrangements(line: String, from: Int, counts: List<Int>, index: Int): Long {
        if (index > counts.indices.last) return 0
        val needed = counts[index]
        if (from + needed > line.length) return 0
        val pair = from to index
        return arrangementCache.getOrPut(pair) {
            val positions = getPositions(line, from, needed)
            if (index == counts.size - 1) {
                val lastBroken = line.lastIndexOf('#')
                return positions.count { it.last >= lastBroken }.toLong()
            }
            positions.sumOf { countArrangements(line, it.last + 2, counts, index + 1).toLong() }
        }
    }

    private fun getPositions(line: String, from: Int, needed: Int): List<IntRange> {
        val pair = from to needed
        return positionCache.getOrPut(pair) {
            var start = from
            var end = from + needed - 1
            var num = (start..end).count { line[it] != '.' }
            val positions = mutableListOf<IntRange>()
            var prev = line.getOrNull(start - 1)
            while (prev != '#' && end < line.length) {
                if (num == needed && line.getOrNull(end + 1) != '#') positions += start..end
                prev = line[start++]
                if (line.getOrNull(++end) != '.') num++
                if (prev != '.') num--
            }
            positions
        }
    }
}

fun main() = Day12().run()
