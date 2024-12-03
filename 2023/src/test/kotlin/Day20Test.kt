import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day20Test : StringSpec({
    val input1 = """
        broadcaster -> a, b, c
        %a -> b
        %b -> c
        %c -> inv
        &inv -> a
    """.trimIndent()

    val input2 = """
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """.trimIndent()

    "part 1a" { Day20(input1).part1 shouldBe 32000000 }
    "part 1b" { Day20(input2).part1 shouldBe 11687500 }
})
