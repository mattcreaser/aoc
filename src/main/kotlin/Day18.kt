import Direction.*

class Day18(input: String? = null) : AdventOfCodeDay(input) {
    private val regex = """(R|D|L|U) ([0-9]+) \((#[0-9a-f]{6})\)""".toRegex()

    override fun part1() = calculateArea(parseInstructions())
    override fun part2() = calculateArea(parseInstructionsPart2())

    private fun parseInstructions() = lines.map {
        val matches = regex.matchEntire(it)?.groupValues ?: error("Invalid input")
        val direction = when (matches[1]) {
            "R" -> Right
            "U" -> Up
            "D" -> Down
            else -> Left
        }
        Instruction(direction, matches[2].toInt())
    }

    private fun parseInstructionsPart2() = lines.map {
        val matches = regex.matchEntire(it)?.groupValues ?: error("Invalid input")
        val direction = when (matches[3].last()) {
            '0' -> Right
            '1' -> Down
            '2' -> Left
            else -> Up
        }
        val amount = matches[3].drop(1).take(5).toInt(radix = 16)
        Instruction(direction, amount)
    }

    private fun calculateArea(instructions: List<Instruction>): Long {
        var x = 0
        var y = 0
        var outside = 0L
        val positions = instructions.map {
            val position = Position(x = x, y = y)
            x += it.direction.dx * it.amount
            y += it.direction.dy * it.amount
            if (it.direction == Down || it.direction == Left) outside += it.amount
            position
        }
        return positions.shoelace() + outside + 1
    }

    private fun List<Position>.shoelace(): Long {
        val pairs = zipWithNext() + Pair(last(), first())
        return pairs.sumOf { (a, b) -> a.x.toLong() * b.y - a.y.toLong() * b.x } / 2
    }
}

data class Instruction(val direction: Direction, val amount: Int)

fun main() = Day18().run()
