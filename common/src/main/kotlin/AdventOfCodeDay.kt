import kotlin.system.measureNanoTime

abstract class AdventOfCodeDay(input: String?) {
    abstract fun part1(): Any
    abstract fun part2(): Any

    protected val lines = input?.lines() ?: this.javaClass.getResource("day$dayNum.txt")?.readText()?.lines() ?: error("Cannot read input file")
    protected val lineSequence: Sequence<String>
        get() = lines.asSequence()
    protected fun inputAsCharGrid(): CharGrid = lines.map { it.toCharArray() }.toTypedArray()

    fun run() {
        runAttempt { part1() }
        runAttempt { part2() }
    }

    val part1: Any
        get() = part1()
    val part2: Any
        get() = part2()

    private val dayNum: Int
        get() = Regexp.integer.find(this::class.simpleName!!)?.value?.toInt() ?: error("Invalid class name")

    private fun runAttempt(block: () -> Any) {
        val result: Any
        val time = measureNanoTime { result = block() }
        println("Got result $result in ${time / 1_000_000f}ms")
    }
}
