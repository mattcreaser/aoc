import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day16Test : StringSpec({
    val input = """
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent()

    "part 1" { Day16(input).part1 shouldBe 46 }
    "part 2" { Day16(input).part2 shouldBe 51 }
})
