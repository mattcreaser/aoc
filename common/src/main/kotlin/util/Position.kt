package util

sealed interface Position {
    val x: Int
    val y: Int

    operator fun plus(move: Movement): Position
}

fun Position(x: Int, y: Int) = StaticPosition(x = x, y = y)

data class StaticPosition(override var x: Int, override var y: Int): Position {
    override fun plus(move: Movement) = StaticPosition(x = x + move.dx, y = y + move.dy)

}

data class MutablePosition(override var x: Int, override var y: Int): Position {
    operator fun plusAssign(move: Movement) {
        x += move.dx
        y += move.dy
    }

    override fun plus(move: Movement) = MutablePosition(x = x + move.dx, y = y + move.dy)
}