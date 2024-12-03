import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day6Test : StringSpec({
    val input = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    "part 1" { Day6(input).part1 shouldBe 288 }
    "part 2" { Day6(input).part2 shouldBe 71503 }
})
