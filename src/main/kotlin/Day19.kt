

class Day19(input: String? = null) : AdventOfCodeDay(input) {

    companion object {
        private val workflowRegex = """([a-z]+)\{(.*)\}""".toRegex()
        private val stageRegex = """(x|m|a|s)(<|>)([0-9]+):(\w+)""".toRegex()
        private val partRegex = """\{x=([0-9]+),m=([0-9]+),a=([0-9]+),s=([0-9]+)\}""".toRegex()
        private val props = arrayOf("x", "m", "a", "s")
    }

    sealed interface Result {
        data class Workflow(val name: String) : Result
        data object Rejected : Result
        data object Accepted : Result
        data object Fallthrough: Result
    }

    data class Workflow(val name: String, val stages: List<Stage>)

    data class StageResult(val part: PartRange, val result: Result)

    interface Stage {
        fun process(part: Part): Result
        fun process(part: PartRange): List<StageResult>
    }

    data class UnconditionalStage(private val result: Result) : Stage {
        override fun process(part: Part) = result
        override fun process(part: PartRange) = listOf(StageResult(part, result))
    }

    data class ConditionalStage(
        private val property: String,
        private val operator: String,
        private val amount: Int,
        private val result: Result
    ) : Stage {
        override fun process(part: Part): Result {
            val value = part.get(property)
            val passes = when (operator) {">" -> value > amount; else -> value < amount }
            return if (passes) result else Result.Fallthrough
        }

        override fun process(part: PartRange): List<StageResult> {
            val results = mutableListOf<StageResult>()

            var accepted: PartRange? = null
            var rejected: PartRange? = null

            val range = part.components[property]!!
            if (operator == "<") {
                range.lt(amount)?.let { accepted = PartRange(part.components.toMutableMap().apply { set(property, it) }) }
                range.gte(amount)?.let { rejected = PartRange(part.components.toMutableMap().apply { set(property, it) }) }
            } else {
                range.gt(amount)?.let { accepted = PartRange(part.components.toMutableMap().apply { set(property, it) }) }
                range.lte(amount)?.let { rejected = PartRange(part.components.toMutableMap().apply { set(property, it) }) }
            }

            accepted?.let { results.add(StageResult(it, result)) }
            rejected?.let { results.add(StageResult(it, Result.Fallthrough)) }

            return results
        }
    }

    data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {
        val sum = x + m + a + s
        fun get(property: String) = when(property) { "x" -> x; "m" -> m; "a" -> a; else -> s}
    }

    data class PartRange(val components: Map<String, IntRange>) {
        val combinations: Long
            get() = components.values.fold(1L) { product, range -> product * range.size() }
    }

    override fun part1(): Int {
        val workflows = getWorkflows()
        val parts = lineSequence.dropWhile { it.isNotBlank() }.drop(1).map {
            val (_, x, m, a, s) = partRegex.matchEntire(it)?.groupValues ?: error("Invalid part $it")
            Part(x = x.toInt(), m = m.toInt(), a = a.toInt(), s = s.toInt())
        }
        return parts.sumOf { process(it, workflows, "in") }
    }

    override fun part2(): Long {
        val workflows = getWorkflows()
        val part = PartRange(props.associateWith { 1..4000 })
        return process(part, workflows, "in")
    }

    private fun process(part: Part, workflows: Map<String, Workflow>, flowName: String): Int {
        val stages = workflows[flowName]!!.stages
        for (stage in stages) {
            return when (val result = stage.process(part)) {
                Result.Rejected -> 0
                Result.Accepted -> part.sum
                Result.Fallthrough -> continue
                is Result.Workflow -> process(part, workflows, result.name)
            }
        }
        error("No steps returned result")
    }

    private fun process(part: PartRange, workflows: Map<String, Workflow>, flowName: String): Long {
        val stages = workflows[flowName]!!.stages
        var remaining: PartRange? = part
        var total = 0L
        for (stage in stages) {
            if (remaining == null) break
            val results = stage.process(remaining).also { remaining = null }
            for (result in results) {
                when(result.result) {
                    Result.Accepted -> total += result.part.combinations
                    Result.Fallthrough -> remaining = result.part
                    Result.Rejected -> continue
                    is Result.Workflow -> total += process(result.part, workflows, result.result.name)
                }
            }
        }
        return total
    }

    private fun String.toWorkflowResult() = when (this) {
        "R" -> Result.Rejected
        "A" -> Result.Accepted
        else -> Result.Workflow(this)
    }

    private fun getWorkflows() = lineSequence.takeWhile { it.isNotBlank() }.map {
        val (_, name, body) = workflowRegex.matchEntire(it)?.groupValues ?: error("Invalid workflow $it")
        val stages: List<Stage> = body.split(',').map { step ->
            val match = stageRegex.matchEntire(step)
            if (match == null) {
                UnconditionalStage(step.toWorkflowResult())
            } else {
                val (_, property, operator, amount, result) = match.groupValues
                ConditionalStage(property, operator, amount.toInt(), result.toWorkflowResult())
            }
        }
        Workflow(name, stages)
    }.associateBy { it.name }
}

fun main() = Day19().run()
