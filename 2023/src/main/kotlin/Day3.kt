private const val numCols = 140

class Day3(input: String? = null) : AdventOfCodeDay(input) {
    override fun part1() = calcScore(ParsedLine::scoreParts)
    override fun part2() = calcScore(ParsedLine::scoreGears)

    private fun calcScore(lineScore: ParsedLine.(prevLine: ParsedLine?, nextLine: ParsedLine?) -> Int): Int {
        var prevLine: ParsedLine?
        var currentLine: ParsedLine? = null
        var nextLine: ParsedLine? = ParsedLine(lines[0])

        return lines.foldIndexed(0) { index, sum, _ ->
            prevLine = currentLine
            currentLine = nextLine
            nextLine = if (index < lines.size - 1) ParsedLine(lines[index + 1]) else null
            sum + (currentLine?.lineScore(prevLine, nextLine) ?: 0)
        }
    }
}

private class ParsedLine(line: String) {

    private class Part(var num: Int, val start: Int, var end: Int) {
        fun addDigit(digit: Int) {
            num = num * 10 + digit
            end++
        }
    }

    private val symbolAtPosition = Array(numCols) { false }
    private val partAtPosition = Array<Part?>(numCols) { null }
    private val gearPositions = mutableListOf<Int>()
    private val parts = mutableListOf<Part>()

    init {
        line.forEachIndexed { i, char ->
            when {
                char.isDigit() -> markPart(i, char.digitToInt())
                char != '.' -> markSymbol(i, char)
            }
        }
    }

    private fun markPart(index: Int, num: Int) {
        var part = partAtPosition.getOrNull(index - 1)
        if (part == null) {
            part = Part(num, index, index)
            parts.add(part)
        } else {
            part.addDigit(num)
        }
        partAtPosition[index] = part
    }

    private fun markSymbol(index: Int, char: Char) {
        symbolAtPosition[index] = true
        if (char == '*') gearPositions.add(index)
    }

    fun scoreParts(prevLine: ParsedLine?, nextLine: ParsedLine?) = parts.sumOf { part ->
        val hasAdjacentSymbol = (part.start - 1..part.end + 1).any { i ->
            this.symbolAt(i) || prevLine.symbolAt(i) || nextLine.symbolAt(i)
        }
        if (hasAdjacentSymbol) part.num else 0
    }

    fun scoreGears(prevLine: ParsedLine?, nextLine: ParsedLine?) = gearPositions.sumOf { position ->
        val adjacentParts = mutableSetOf<Part>()
        (position - 1..position + 1).forEach { i ->
            this.partAt(i)?.let { adjacentParts.add(it) }
            prevLine.partAt(i)?.let { adjacentParts.add(it) }
            nextLine.partAt(i)?.let { adjacentParts.add(it) }
        }
        if (adjacentParts.size == 2) adjacentParts.first().num * adjacentParts.last().num else 0
    }

    private fun ParsedLine?.partAt(i: Int) = this?.partAtPosition?.get(i)
    private fun ParsedLine?.symbolAt(i: Int) = this?.symbolAtPosition?.getOrNull(i) == true
}

fun main() = Day3().run()
