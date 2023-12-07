class Day1(input: String? = null) : AdventOfCodeDay(input) {
    private val numbers = listOf("1", "one", "2", "two", "3", "three", "4", "four", "5", "five", "6", "six", "7", "seven", "8", "eight", "9", "nine")
    private val regex = numbers.joinToString("|").toRegex()

    override fun part1() = input.sumOf { line ->
        val firstDigit = line.first { it.isDigit() }.digitToInt()
        val secondDigit = line.last { it.isDigit() }.digitToInt()
        firstDigit * 10 + secondDigit
    }

    override fun part2() = input.sumOf { line ->
        val matches = regex.findAllWithOverlap(line)
        val firstNum = matches.first().value
        val lastNum = matches.last().value

        val firstDigit = numbers.indexOf(firstNum) / 2 + 1
        val secondDigit = numbers.indexOf(lastNum) / 2 + 1
        firstDigit * 10 + secondDigit
    }
}

fun main() = Day1().run()
