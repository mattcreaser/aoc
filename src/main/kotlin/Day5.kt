private val input = readInputFile(5).lineSequence()
private val regex = """(\d+) (\d+) (\d+)""".toRegex()
private val seeds = "\\d+".toRegex().findAll(input.first()).map { it.value.toLong() }
private val mappings = getMappings()

fun main() {
    runAttempt(::part1)
    runAttempt(::part2)
}

private fun part1() = seeds.toLocation(mappings).min()
private fun part2() = seeds
    .chunked(2)
    .flatMap { it[0]..<it[0] + it[1] }
    .toLocation(mappings)
    .min()

private fun getMappings() = input.drop(2).fold(mutableListOf<MutableMap<LongRange, Long>>(mutableMapOf())) { mappings, line ->
    if (line.isEmpty()) {
        mappings += mutableMapOf()
    } else {
        regex.matchEntire(line)?.let { match ->
            val (destination, source, size) = match.groupValues.drop(1).map { it.toLong() }
            mappings.last()[source..<source + size] = destination
        }
    }
    mappings
}.map { it.entries.map { entry -> entry.toPair() }.toTypedArray() }.toTypedArray()

private fun Sequence<Long>.toLocation(mappings: Array<Array<Pair<LongRange, Long>>>) = map { input ->
    mappings.fold(input) { value, mapping ->
        mapping.firstOrNull { it.first.contains(value) }?.let { (range, inc) -> value - range.first + inc } ?: value
    }
}
