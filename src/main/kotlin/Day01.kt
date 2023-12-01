enum class Digits(val s: String) {
    ONE("one"),
    TWO("two"),
    THREE("three"),
    FOUR("four"),
    FIVE("five"),
    SIX("six"),
    SEVEN("seven"),
    EIGHT("eight"),
    NINE("nine")
}

fun Digits.toInt(): Int {
    return when (this) {
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
}

fun String.toDigit(): Int {
    return try {
        this.toInt()
    } catch (_: Exception) {
        Digits.valueOf(this.uppercase()).toInt()
    }
}

fun String.getFirstAndLast(): Pair<String, String> {
    val regex = Regex("(\\d|${Digits.entries.joinToString(separator = "|") { it.s }})")
    val regexr = Regex("(\\d|${Digits.entries.joinToString(separator = "|") { it.s.reversed() }})")
    return (regex.find(this)?.value ?: "") to (regexr.find(this.reversed())?.value?.reversed() ?: "")

}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val list = line.filter { it.isDigit() }
            "${list.first()}${list.last()}".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (first, last) = line.getFirstAndLast()
            "${first.toDigit()}${last.toDigit()}".toInt()
        }
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
