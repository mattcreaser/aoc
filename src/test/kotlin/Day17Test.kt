import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day17Test : StringSpec({
    val input = """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent()

    val input2 = """
        111111111111
        999999999991
        999999999991
        999999999991
        999999999991
    """.trimIndent()

    "part 1" { Day17(input).part1 shouldBe 102 }
    "part 2" { Day17(input).part2 shouldBe 94 }
    "part 2b" { Day17(input2).part2 shouldBe 71 }
})
