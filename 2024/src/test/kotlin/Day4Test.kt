import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day4Test: StringSpec({
    val input = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent()

    "part 1" { Day4(input).part1 shouldBe 18 }
    "part 2" { Day4(input).part2 shouldBe 9 }
})