import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day3Test : StringSpec({
    val input = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent()

    "part 1" { Day3(input).part1 shouldBe 4361 }
    "part 2" { Day3(input).part2 shouldBe 467835 }
})
