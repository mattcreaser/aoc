import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day9Test : StringSpec({
    val input = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent()

    "part 1" { Day9(input).part1 shouldBe 114 }
    "part 2" { Day9(input).part2 shouldBe 2 }
})
