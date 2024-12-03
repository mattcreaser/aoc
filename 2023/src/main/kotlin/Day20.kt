import java.util.*

const val HI = true
const val LOW = false

class Day20(input: String? = null) : AdventOfCodeDay(input) {
    private val moduleRegex = """([%&]?)(\w+) -> (.*)""".toRegex()

    override fun part1(): Long {
        val modules = getModules()
        var numLow = 0L
        var numHigh = 0L
        for (i in 0..<1000) {
            val queue = LinkedList<Signal>().apply { add(Signal("broadcast", "Button", false)) }
            while (queue.isNotEmpty()) {
                val next = queue.poll()
                if (next.signal) numHigh++ else numLow++
                modules[next.to]?.let { module ->
                    val signal = module.process(next)
                    if (signal != null) {
                        queue.addAll(module.outputs.map { Signal(it, next.to, signal) })
                    }
                }
            }
        }
        return numLow * numHigh
    }

    override fun part2(): Long {
        val modules = getModules()
        val inputsToCheck = (modules.values.first { it.outputs.contains("rx") } as Conjunction).inputs
        val pressesRequired = inputsToCheck.associateWith { -1L }.toMutableMap()

        var presses = 0L
        while(pressesRequired.any { it.value== -1L }) {
            presses++
            val queue = LinkedList<Signal>().apply { add(Signal("broadcast", "Button", false)) }
            while (queue.isNotEmpty()) {
                val next = queue.poll()
                modules[next.to]?.let { module ->
                    val signal = module.process(next)
                    if (signal != null) {
                        if (signal == HI && inputsToCheck.contains(module.name) && pressesRequired[module.name] == -1L) {
                            pressesRequired[module.name] = presses
                        }
                        queue.addAll(module.outputs.map { Signal(it, next.to, signal) })
                    }
                }
            }
        }

        return pressesRequired.values.asSequence().lcm()
    }

    private fun getModules(): Map<String, Module> {
        val modules = lines.map {
            val (_, type, name, destinations) = moduleRegex.matchEntire(it)?.groupValues ?: error("Invalid line $it")
            when (type) {
                "&" -> Conjunction(name, destinations.split(", "))
                "%" -> FlipFlop(name, destinations.split(", "))
                else -> BroadCast(destinations.split(", "))
            }
        }.associateBy { it.name }

        modules.values.forEach { module ->
            module.outputs.forEach { output ->
                val target = modules[output]
                if (target is Conjunction) { target.addInput(module.name) }
            }
        }
        return modules
    }
}

fun main() = Day20().run()

abstract class Module(val outputs: List<String>) {
    abstract val name: String
    abstract fun process(signal: Signal): Boolean?
}

class FlipFlop(
    override val name: String,
    outputs: List<String>
) : Module(outputs) {
    private var state = false
    override fun process(signal: Signal) = if (signal.signal) null else !state.also { state = !state }
}

class Conjunction(
    override val name: String,
    outputs: List<String>
) : Module(outputs) {
    private val remembered = mutableMapOf<String, Boolean>()
    val inputs: Set<String>
        get() = remembered.keys
    override fun process(signal: Signal): Boolean {
        remembered[signal.from] = signal.signal
        return !remembered.all { it.value }
    }
    fun addInput(name: String) { remembered[name] = false }
}

class BroadCast(outputs: List<String>) : Module(outputs) {
    override val name = "broadcast"
    override fun process(signal: Signal) = signal.signal
}

data class Signal(val to: String, val from: String, val signal: Boolean)
