import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day21Test : StringSpec({
    val input = """
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
    """.trimIndent()

    "part 1" { Day21(input, part1Steps = 6).part1 shouldBe 16 }
})