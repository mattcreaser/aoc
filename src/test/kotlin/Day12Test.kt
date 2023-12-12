import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day12Test : StringSpec({
    val input = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
    """.trimIndent()

    "part 1" { Day12(input).part1 shouldBe 21 }
    "part 2" { Day12(input).part2 shouldBe 525152 }
})
