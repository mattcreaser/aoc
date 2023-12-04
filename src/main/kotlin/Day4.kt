import kotlin.math.pow

private val input = readInputFile(4).lines()

private val regex = """\d+""".toRegex()

fun main() {
    runAttempt(::part1)
    runAttempt(::part2)
}

private fun part1() = input.sumOf { line ->
    2f.pow(line.countWins() - 1).toInt()
}

private fun part2(): Int {
    val cardInstances = Array(input.size) { 1 }
    input.forEachIndexed { index, line ->
        for (i in 1..line.countWins()) {
            cardInstances[index + i] += cardInstances[index]
        }
    }
    return cardInstances.sum()
}

private fun String.countWins() = cardNums().count { winningNums().contains(it) }
private fun String.winningNums() = regex.findAll(substringBefore('|'), 9).map { it.value.toInt() }
private fun String.cardNums() = regex.findAll(substringAfter('|')).map { it.value.toInt() }
