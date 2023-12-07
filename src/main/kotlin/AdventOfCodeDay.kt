import kotlin.system.measureNanoTime

abstract class AdventOfCodeDay(private val num: Int) {
    abstract fun part1(): Any
    abstract fun part2(): Any

    protected val input: Sequence<String> = this.javaClass.getResource("day$num.txt")?.readText()?.lineSequence() ?: error("Cannot read input file")

    fun run() {
        runAttempt(::part1)
        runAttempt(::part2)
    }

    private fun runAttempt(block: () -> Any) {
        val result: Any
        val time = measureNanoTime { result = block() }
        println("Got result $result in ${time / 1_000_000}ms")
    }
}
