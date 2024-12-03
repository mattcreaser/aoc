import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day8Test : StringSpec({
    val input = """
        RL

        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    val input1b = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    val input2 = """
        LR

        AAA = (AAB, XXX)
        AAB = (XXX, AAZ)
        AAZ = (AAB, XXX)
        BBA = (BBB, XXX)
        BBB = (BBC, BBC)
        BBC = (BBZ, BBZ)
        BBZ = (BBB, BBB)
        XXX = (XXX, XXX)
    """.trimIndent()

    "part 1a" { Day8(input).part1 shouldBe 2 }
    "part 1b" { Day8(input1b).part1 shouldBe 6 }
    "part 2" { Day8(input2).part2 shouldBe 6 }
})