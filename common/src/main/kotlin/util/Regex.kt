package util

fun Regex.findAllWithOverlap(input: CharSequence) = generateSequence({ find(input) }, { find(input, it.range.first + 1) })

object Regexp {
    val integer = "-?\\d+".toRegex()
}

fun String.toLongs() = Regexp.integer.findAll(this).map { it.value.toLong() }
fun String.toInts() = Regexp.integer.findAll(this).map { it.value.toInt() }