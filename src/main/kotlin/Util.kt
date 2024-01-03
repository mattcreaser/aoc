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

fun <T> List<T>.splitBy(func: (T) -> Boolean): Sequence<List<T>> {
    var index = 0
    val source = this
    return generateSequence {
        if (index >= source.size) return@generateSequence null
        buildList {
            var next = source.getOrNull(index++)
            while (next != null && !func(next)) {
                add(next)
                next = source.getOrNull(index++)
            }
        }
    }
}

inline fun BooleanArray.runningSum(func: (Boolean) -> Int) = runningFold(0) { sum, bool -> sum + func(bool) }

enum class Direction(val dx: Int, val dy: Int) {
    Up(0, -1),
    Down(0, 1),
    Left(-1, 0),
    Right(1, 0),
    ;

    val vertical = dx == 0
    val horizontal = dy == 0
    val bitValue = 1 shl ordinal
    val orthagonals by lazy { if (vertical) arrayOf(Left, Right) else arrayOf(Up, Down) }
}

data class Position(var x: Int, var y: Int) {
    operator fun plus(direction: Direction) = Position(x + direction.dx, y + direction.dy)
    operator fun plusAssign(direction: Direction) {
        x += direction.dx
        y += direction.dy
    }
}

fun Array<List<Int>>.getOrNull(position: Position) = getOrNull(position.y)?.getOrNull(position.x)

fun IntRange.size() = last - first + 1
fun IntRange.gt(x: Int) = if (x >= last) null else maxOf(x+1, first)..last
fun IntRange.lt(x: Int) = if (x <= first) null else first..minOf(last, x-1)
fun IntRange.gte(x: Int) = if (x > last) null else maxOf(x, first)..last
fun IntRange.lte(x: Int) = if (x < first) null else first..minOf(last, x)
