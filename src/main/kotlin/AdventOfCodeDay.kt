import kotlin.system.measureNanoTime

abstract class AdventOfCodeDay(num: Int, input: String?) {
    abstract fun part1(): Any
    abstract fun part2(): Any

    protected val input: Sequence<String> =
        input?.lineSequence() ?: this.javaClass.getResource("day$num.txt")?.readText()?.lineSequence() ?: error("Cannot read input file")

    fun run() {
        runAttempt { part1() }
        runAttempt { part2() }
    }

    val part1: Any
        get() = part1()
    val part2: Any
        get() = part2()

    private fun runAttempt(block: () -> Any) {
        val result: Any
        val time = measureNanoTime { result = block() }
        println("Got result $result in ${time / 1_000_000f}ms")
    }
}