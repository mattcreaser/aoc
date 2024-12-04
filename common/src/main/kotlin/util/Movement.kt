package util

import kotlin.math.E

open class Movement(val dx: Int, val dy: Int) {

    sealed class EightWay(dx: Int, dy: Int): Movement(dx = dx, dy = dy) {
        companion object {
            val entries: Set<EightWay> = FourWay.entries + Diagonal.entries
        }
    }

    sealed class Diagonal(dx: Int, dy: Int): EightWay(dx = dx, dy = dy) {
        companion object {
            val entries: Set<Diagonal> = setOf(NorthEast, NorthWest, SouthEast, SouthWest)
        }
    }

    sealed class FourWay(dx: Int, dy: Int) : EightWay(dx = dx, dy = dy) {
        companion object {
            val entries: Set<FourWay> = setOf(North, East, South, West)
        }
    }

    operator fun plus(other: Movement) = Movement(dx + other.dx, dy + other.dy)
    operator fun times(magnitude: Int) = Movement(dx * magnitude, dy * magnitude)
}


data object North: Movement.FourWay(0, -1)
data object East: Movement.FourWay(1, 0)
data object South: Movement.FourWay(0, 1)
data object West: Movement.FourWay(-1, 0)

data object NorthEast: Movement.Diagonal(1, -1)
data object NorthWest: Movement.Diagonal(-1, -1)
data object SouthEast: Movement.Diagonal(1, 1)
data object SouthWest: Movement.Diagonal(-1, 1)
