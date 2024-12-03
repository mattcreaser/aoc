import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day14Test : StringSpec({
    val input = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent()

    "part 1" { Day14(input).part1 shouldBe 136 }
    "part 2" { Day14(input).part2 shouldBe 64 }
})
