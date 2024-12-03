import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day2Test : StringSpec({

    val input1 = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()

    "part1" { Day2(input1).part1() shouldBe 2 }
    "part2" { Day2(input1).part2() shouldBe 4 }
})