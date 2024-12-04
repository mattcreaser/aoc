import util.toInts
import kotlin.math.sqrt

fun Boolean.toInt() = if (this) 1 else 0

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

enum class Direction(val dx: Int, val dy: Int, val char: Char) {
    Up(0, -1, '^'),
    Down(0, 1, 'v'),
    Left(-1, 0, '<'),
    Right(1, 0, '>'),
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

typealias CharGrid = Array<CharArray>
typealias BooleanGrid = Array<BooleanArray>

val CharGrid.xRange: IntRange
    get() = first().indices
val CharGrid.yRange: IntRange
    get() = indices
operator fun CharGrid.get(pos: Position) = this.getOrNull(pos.y)?.getOrNull(pos.x)
operator fun CharGrid.set(pos: Position, char: Char) { this[pos.y][pos.x] = char }
inline fun CharGrid.positionOf(crossinline func: (Char) -> Boolean): Position? {
    this.forEachIndexed { y, row -> row.forEachIndexed { x, char -> if (func(char)) return Position(x, y) }}
    return null
}

fun BooleanGrid.deepCopy() = Array(size) { this[it].copyOf() }
operator fun BooleanGrid.set(pos: Position, value: Boolean) { this[pos.y][pos.x] = value }
operator fun BooleanGrid.get(pos: Position) = this.getOrNull(pos.y)?.getOrNull(pos.x)
