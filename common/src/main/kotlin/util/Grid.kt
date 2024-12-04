package util

import AdventOfCodeDay

interface Grid<T> {
    operator fun get(
        x: Int,
        y: Int,
    ): T

    fun getOrNull(
        x: Int,
        y: Int,
    ): T?

    operator fun set(
        x: Int,
        y: Int,
        value: T,
    )

    val xRange: IntRange
    val yRange: IntRange

    val xSize: Int
    val ySize: Int
}

fun <T> Grid<T>.getOrNull(position: Position): T? = getOrNull(position.x, position.y)

operator fun <T> Grid<T>.get(position: Position): T = get(position.x, position.y)

operator fun <T> Grid<T>.set(
    position: Position,
    value: T,
) = set(x = position.x, y = position.y, value = value)

fun Grid<*>.positions(): Sequence<Position> =
    sequence {
        xRange.forEach { x -> yRange.forEach { y -> yield(Position(x = x, y = y)) } }
    }

inline fun <T> Grid<T>.first(crossinline func: (T) -> Boolean) = positions().first { position -> func(this[position]) }
inline fun <T> Grid<T>.firstOrNull(crossinline func: (T) -> Boolean) = positions().firstOrNull { position -> func(this[position]) }

class CharGrid(
    val data: Array<CharArray>,
) : Grid<Char> {
    constructor(xSize: Int, ySize: Int, initializer: (x: Int, y: Int) -> Char) : this(
        Array(ySize) { y -> CharArray(xSize) { x -> initializer(x, y) } },
    )

    override fun get(
        x: Int,
        y: Int,
    ) = data[y][x]

    override fun getOrNull(
        x: Int,
        y: Int,
    ) = data.getOrNull(y)?.getOrNull(x)

    override fun set(
        x: Int,
        y: Int,
        value: Char,
    ) {
        data[x][y] = value
    }

    override val xRange = data.first().indices
    override val yRange = data.indices
    override val xSize = data.first().size
    override val ySize = data.size
}

val AdventOfCodeDay.charGrid: CharGrid
    get() = CharGrid(lines.map { it.toCharArray() }.toTypedArray())

// Returns true if the given string exists in the grid starting from the position and with successive movement applied
fun CharGrid.matches(
    from: Position,
    movement: Movement,
    content: String,
): Boolean = content.indices.all { i -> this.getOrNull(from + movement * i) == content[i] }

inline fun <T> Grid<T>.findAll(crossinline func: (T) -> Boolean): Sequence<Position> =
    sequence {
        val grid = this@findAll
        xRange.forEach { x ->
            yRange.forEach { y ->
                if (func(grid[x, y])) {
                    yield(Position(x = x, y = y))
                }
            }
        }
    }
