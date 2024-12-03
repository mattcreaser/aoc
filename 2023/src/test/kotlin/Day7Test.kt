import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day7Test : StringSpec({
    val input = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    "part 1" { Day7(input).part1 shouldBe 6440 }
    "part 2" { Day7(input).part2 shouldBe 5905 }
})
