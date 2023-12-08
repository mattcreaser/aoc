import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

fun runAttempt(block: () -> Any) {
    val result: Any
    val time = measureTimeMillis {
        result = block()
    }
    println("Got result $result in ${time}ms")
}

fun readInputFile(dayNum: Int): String {
    return object {}.javaClass.getResource("day$dayNum.txt")?.readText() ?: error("Cannot read input file")
}

fun Regex.findAllWithOverlap(input: CharSequence, startIndex: Int = 0): Sequence<MatchResult> {
    if (startIndex < 0 || startIndex > input.length) {
        throw IndexOutOfBoundsException("Start index out of bounds: $startIndex, input length: ${input.length}")
    }
    return generateSequence({ find(input, startIndex) }, { find(input, it.range.first + 1) })
}

fun Boolean.toInt() = if (this) 1 else 0

val Regex.Companion.integer
    get() = "\\d+".toRegex()

fun quadraticRoots(a: Float, b: Float, c: Float): Pair<Float, Float> {
    val discriminant = b * b - 4 * a * c
    val sqrtDiscriminant = sqrt(discriminant)
    return (-b + sqrtDiscriminant) / (2 * a) to (-b - sqrtDiscriminant) / (2 * a)
}

fun <T> Sequence<T>.repeatForever() = sequence { while (true) { yieldAll(this@repeatForever) } }
inline fun <T> MutableList<T>.mapInPlace(func: (T) -> T) = forEachIndexed { index, i -> this[index] = func(i) }

fun Sequence<Long>.lcm(): Long = reduce(::lcm)
fun lcm(a: Long, b: Long) = (a * b) / gcd(a, b)
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
