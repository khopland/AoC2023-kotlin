enum class Digits(val s: String) {
    ONE("one"), TWO("two"), THREE("three"), FOUR("four"), FIVE("five"), SIX("six"), SEVEN("seven"), EIGHT("eight"), NINE("nine")
}

fun Digits.toInt(): Int = when (this) {
    Digits.ONE -> 1
    Digits.TWO -> 2
    Digits.THREE -> 3
    Digits.FOUR -> 4
    Digits.FIVE -> 5
    Digits.SIX -> 6
    Digits.SEVEN -> 7
    Digits.EIGHT -> 8
    Digits.NINE -> 9
}

fun String.toDigit(): Int = try {
    this.toInt()
} catch (_: Exception) {
    Digits.valueOf(this.uppercase()).toInt()
}

fun String.getFirstAndLast(): Pair<String, String> =
        ("(\\d|${Digits.entries.joinToString(separator = "|") { it.s }})"
                .toRegex().find(this)?.value ?: "") to
                ("(\\d|${Digits.entries.joinToString(separator = "|") { it.s.reversed() }})"
                        .toRegex().find(this.reversed())?.value?.reversed() ?: "")



fun main() {
    fun part1(input: List<String>): Int = input.sumOf { line ->
        "${line.first { it.isDigit() }.digitToInt()}${line.last { it.isDigit() }.digitToInt()}".toInt()
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->
        val (first, last) = line.getFirstAndLast()
        "${first.toDigit()}${last.toDigit()}".toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("main/resources/Day01_test")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("main/resources/Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("main/resources/Day01")
    part1(input).println()
    part2(input).println()
}
