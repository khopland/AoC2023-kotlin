plugins {
    kotlin("jvm") version "1.9.20"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

task("generateNextDay") {
    doLast {
        val prevDayNum = fileTree("$projectDir/src/main/kotlin").matching {
            include("Day*.kt")
        }.maxOf {
            val (prevDayNum) = Regex("Day(\\d\\d)").find(it.name)!!.destructured
            prevDayNum.toInt()
        }
        val newDayNum = String.format("%02d", prevDayNum + 1)
        File("$projectDir/src/main/kotlin", "Day$newDayNum.kt").writeText(
                """
fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }
    fun part2(input: List<String>): Int {
        return 0
    }
    
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("main/resources/Day${newDayNum}_test")
    check(part1(testInput) == 0)
    
    val testInput2 = readInput("main/resources/Day01_test2")
    check(part2(testInput2) == 0)
    
    val input = readInput("main/resources/Day$newDayNum")
    println(part1(input))
    println(part2(input))
}
"""
        )
    }
}