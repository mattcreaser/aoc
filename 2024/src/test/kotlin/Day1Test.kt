import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day1Test : StringSpec ({

    val input1 = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent()


    "part 1" { Day1(input1).part1() shouldBe 11 }
    "part 2" { Day1(input1).part2() shouldBe 31 }
})