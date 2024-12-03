class Day3(input: String? = null) : AdventOfCodeDay(input) {

    private val regex = """mul\((\d+),(\d+)\)""".toRegex()
    private val regex2 = """do\(\)|don't\(\)|mul\((\d+),(\d+)\)""".toRegex()

    override fun part1(): Int {
        return lines.sumOf { line ->
            regex.findAll(line).sumOf { match ->
                match.groupValues[1].toInt() * match.groupValues[2].toInt()
            }
        }
    }

    override fun part2(): Int {
        var enabled = true
        return lines.sumOf { line ->
            regex2.findAll(line).sumOf { match ->
                if (match.value == "do()") {
                    enabled = true
                    0
                } else if (match.value == "don't()") {
                    enabled = false
                    0
                } else if (enabled) {
                    match.groupValues[1].toInt() * match.groupValues[2].toInt()
                } else {
                    0
                }
            }
        }
    }
}

fun main() = Day3().run()