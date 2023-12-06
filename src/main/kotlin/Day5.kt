private val input = readInputFile(5).lineSequence()
private val regex = """(\d+) (\d+) (\d+)""".toRegex()
private val seeds = "\\d+".toRegex().findAll(input.first()).map { it.value.toLong() }
private val stages = getStages()

fun main() {
    runAttempt(::part1)
    runAttempt(::part2)
}

private fun part1() = seeds.toLocation(stages).min()
private fun part2() = seeds
    .chunked(2)
    .map { it[0]..<it[0] + it[1] }
    .rangeToLocation(stages)
    .minOf { it.first }

data class Stage(val mappings: MutableList<Mapping> = mutableListOf())
data class Mapping(val range: LongRange, val increment: Long)

private fun getStages() = input.drop(2).fold(mutableListOf(Stage())) { stages, line ->
    if (line.isEmpty()) {
        stages += Stage()
    } else {
        regex.matchEntire(line)?.let { match ->
            val (destination, source, size) = match.groupValues.drop(1).map { it.toLong() }
            stages.last().mappings.add(Mapping(source..<source + size, destination - source))
        }
    }
    stages.onEach { it.mappings.sortBy { mapping -> mapping.range.first } }
}

private fun Sequence<Long>.toLocation(stages: List<Stage>) = map { input ->
    stages.fold(input) { value, stage -> stage.mappings.firstOrNull { it.range.contains(value) }?.let { value + it.increment } ?: value }
}

private fun Sequence<LongRange>.rangeToLocation(stages: List<Stage>) = flatMap { range ->
    stages.fold(sequenceOf(range)) { ranges, stage -> ranges.flatMap { it.mapInto(stage.mappings) } }
}

private fun LongRange.mapInto(mappings: List<Mapping>) = sequence {
    var remaining = this@mapInto
    for (mapping in mappings) {
        val inc = mapping.increment
        if (remaining.last < mapping.range.first) {
            yield(remaining)
            return@sequence
        } else if (remaining.first > mapping.range.last) {
            continue
        }

        if (remaining.first < mapping.range.first) {
            yield(remaining.first..<mapping.range.first)
        }

        val range = maxOf(remaining.first, mapping.range.first) + inc..minOf(remaining.last, mapping.range.last) + inc
        yield(range)

        if (remaining.last > mapping.range.last) {
            remaining = mapping.range.last + 1..remaining.last
        } else {
            return@sequence
        }
    }
    yield(remaining)
}
