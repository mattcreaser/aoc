import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day15Test : StringSpec({
    val input = """rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"""

    "part 1" { Day15(input).part1 shouldBe 1320 }
    "part 2" { Day15(input).part2 shouldBe 145 }
})