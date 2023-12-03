fun main() {
    val directions = listOf(-1, 0, 1)

    fun resize(list: List<String>): List<String> = buildList {
        add(list.first().map { '.' }.joinToString(""))
        addAll(list.map { it })
        add(list.first().map { '.' }.joinToString(""))
    }

    fun check(list: List<String>, column: Int, row: Int, filter: (Char, Int, Int) -> Unit) = directions.forEach { dx ->
        directions.filter { !(dx == 0 && it == 0) && it + column in 0 until list.first().length }
            .forEach { filter(list[dx + row][it + column], dx + row, it + column) }
    }

    fun check(list: List<String>, column: Int, row: Int, filter: (Char) -> Unit) =
        check(list, column, row) { adj, _, _ -> filter(adj) }

    fun part1(input: List<String>): Long {
        val list = resize(input)

        return list.mapIndexed { row, string ->
            var hasSymbolNear = false
            var currentNumber = ""
            string.mapIndexedNotNull { index, c ->
                if (c.isDigit()) {
                    currentNumber += c
                    check(list, index, row) { adj ->
                        if (!adj.isDigit() && adj != '.') hasSymbolNear = true
                    }
                    0L
                } else if (currentNumber.isNotEmpty()) {
                    var number = 0L
                    if (hasSymbolNear) number = currentNumber.toLong()
                    currentNumber = ""
                    hasSymbolNear = false
                    number
                } else 0L
            }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val list = resize(input)

        return list.mapIndexed { row, line ->
            var currentNumber = ""
            val parts = mutableSetOf<Pair<Int, Int>>()

            line.mapIndexedNotNull { column, cell ->
                if (cell.isDigit()) {
                    currentNumber += cell
                    check(list, column, row) { adj, x, y ->
                        if (adj == '*') parts.add(x to y)
                    }
                    null
                } else if (currentNumber.isNotEmpty()) {
                    val res = mutableMapOf<Pair<Int, Int>, MutableList<Long>>()
                    if (parts.isNotEmpty()) {
                        parts.forEach { res.getOrPut(it) { mutableListOf() }.add(currentNumber.toLong()) }
                    }
                    currentNumber = ""
                    parts.clear()
                    res
                } else null
            }
        }.flatten().reduce { acc, mutableMap ->
            mutableMap.forEach { (pos, list) ->
                acc.getOrPut(pos) { mutableListOf() }.addAll(list)
            }
            acc
        }.values.filter { it.size == 2 }.sumOf {
            it.reduce { acc, l -> acc * l }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("main/resources/Day03_test")
    check(part1(testInput) == 4361L)

    val testInput2 = readInput("main/resources/Day03_test")
    check(part2(testInput2) == 467835L)

    val input = readInput("main/resources/Day03")
    println(part1(input))
    println(part2(input))
}
