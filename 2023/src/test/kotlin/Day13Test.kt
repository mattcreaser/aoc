import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day13Test : StringSpec({
    val input = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.

        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent()

    "part 1" { Day13(input).part1 shouldBe 405 }
    "part 2" { Day13(input).part2 shouldBe 400 }
})
