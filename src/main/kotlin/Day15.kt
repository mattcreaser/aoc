class Day15(input: String? = null) : AdventOfCodeDay(input) {
    private val regex = """(\w+)(=|-)([0-9]*)""".toRegex()

    override fun part1() = lines[0].split(',').sumOf { it.hash() }

    override fun part2(): Int {
        val boxes = Array(256) { linkedMapOf<String, Int>() }
        lines[0].split(',').forEach { value ->
            val (_, label, operator, amount) = regex.matchEntire(value)?.groupValues ?: error("Invalid value $value")
            val hash = label.hash()
            when (operator) {
                "-" -> boxes[hash].remove(label)
                "=" -> boxes[hash][label] = amount.toInt()
            }
        }
        return boxes.foldIndexed(0) { i, sum, box ->
            sum + box.values.foldIndexed(0) { j, boxSum, focal -> boxSum + (i + 1) * (j + 1) * focal }
        }
    }

    private fun String.hash() = fold(0) { hash, char -> ((hash + char.code) * 17) % 256 }
}

fun main() = Day15().run()
