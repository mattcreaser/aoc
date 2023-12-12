import kotlin.math.sqrt

fun Regex.findAllWithOverlap(input: CharSequence) = generateSequence({ find(input) }, { find(input, it.range.first + 1) })

object Regexp {
    val integer = "-?\\d+".toRegex()
}

fun Boolean.toInt() = if (this) 1 else 0

fun String.toLongs() = Regexp.integer.findAll(this).map { it.value.toLong() }
fun String.toInts() = Regexp.integer.findAll(this).map { it.value.toInt() }

fun quadraticRoots(a: Float, b: Float, c: Float): Pair<Float, Float> {
    val discriminant = b * b - 4 * a * c
    val sqrtDiscriminant = sqrt(discriminant)
    return (-b + sqrtDiscriminant) / (2 * a) to (-b - sqrtDiscriminant) / (2 * a)
}

fun Sequence<String>.toInts() = map(String::toInts)
fun <T> Sequence<T>.repeatForever() = sequence { while (true) { yieldAll(this@repeatForever) } }
fun <T> Sequence<T>.repeat(n: Int) = sequence { for (i in 0..<n) { yieldAll(this@repeat) } }

fun Sequence<Long>.lcm(): Long = reduce(::lcm)
fun lcm(a: Long, b: Long) = (a * b) / gcd(a, b)
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun <T> List<T>.pairs(): List<Pair<T, T>> {
    val result = ArrayList<Pair<T, T>>(size * (size - 1) / 2)
    for (i in indices) for (j in i + 1..<size) result.add(this[i] to this[j])
    return result
}

inline fun BooleanArray.runningSum(func: (Boolean) -> Int) = runningFold(0) { sum, bool -> sum + func(bool) }
