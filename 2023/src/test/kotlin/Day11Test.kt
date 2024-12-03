import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day11Test : StringSpec({
    val input = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent()

    "part 1" { Day11(input).part1 shouldBe 374 }
    "part 2a" { Day11(input, 10).part2 shouldBe 1030 }
    "part 2b" { Day11(input, 100).part2 shouldBe 8410 }
})