package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun day3(input: List<String>, evaluate: (String) -> List<String>): Int {
       return evaluate(input.joinToString(" ") { it })
           .asSequence()
           .map { it.substringAfter("(") }
           .map { it.substringBefore(")") }
           .map { it.split(",") }
           .map { it[0].toInt() * it[1].toInt() }
           .sum()
    }

    val part1: (String) -> List<String>  =  { memory: String ->
        """(mul\(\d{1,3},\d{1,3}\)|don't\(\)|do\(\))""".toRegex()
            .findAll(memory)
            .map { it.value }
            .filter { !listOf("do()", "don't()").contains(it) }
            .toList()
    }

    val part2: (String) -> List<String>  =  { memory: String ->
        val o = """(mul\(\d{1,3},\d{1,3}\)|don't\(\)|do\(\))""".toRegex()
            .findAll(memory)
            .map { it.value }
            .toList()

        var multiply = true
        val result = mutableListOf<String>()
        for (s in o) {
            if (s == "do()") {
                multiply = true
            } else if (s == "don't()") {
                multiply = false
            } else if (multiply) {
                result.add(s)
            }
        }

        result
    }

    fun part1(input: List<String>): Int {
        return day3(input, part1)
            .also { println("Result: $it") }
    }

    fun part2(input: List<String>): Int {
        return day3(input, part2)
            .also { println("Result: $it") }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val testInput2 = readInput("Day03_test2")
    val input = readInput("Day03")

    check(part1(testInput) == 161)
    println("Part 1 ${part1(input)}")

    check(part2(testInput2) == 48)
    println("Part 2 ${part2(input)}")
}
