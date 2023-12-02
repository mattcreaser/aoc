// Input file: resources/day1.txt

private val input = readInputFile(1)

private val numbers = listOf("1", "one", "2", "two", "3", "three", "4", "four", "5", "five", "6", "six", "7", "seven", "8", "eight", "9", "nine")
private val regex = numbers.joinToString("|").toRegex()

fun main() = runAttempt {
    input.lineSequence().sumOf {
        val matches = regex.findAllWithOverlap(it)
        val firstNum = matches.first().value
        val lastNum = matches.last().value

        val firstDigit = numbers.indexOf(firstNum) / 2 + 1
        val secondDigit = numbers.indexOf(lastNum) / 2 + 1
        firstDigit * 10 + secondDigit
    }
}
