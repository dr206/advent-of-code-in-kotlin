package com.dr206.adventOfCode.year22

import com.dr206.adventOfCode.readInput

fun main() {

    fun findValueOfIntersectingLetters(listOfLists: List<List<String>>) = listOfLists.sumOf {
        val letter = it.fold(it[0].toSet()) { acc, item ->
            acc.intersect(item.toSet())
        }.first()
        letter.code % 32 + if (letter.isUpperCase()) 26 else 0
    }

    fun part1(input: List<String>): Int {
        val rucksackCompartmentsList = input.map { it.chunked(it.length/2) }
        return findValueOfIntersectingLetters(rucksackCompartmentsList)
    }

    fun part2(input: List<String>): Int {
        val elfGroupList = input.chunked(3)
        return findValueOfIntersectingLetters(elfGroupList)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println("Part 1 ${part1(input)}")
    println("Part 2 ${part2(input)}")
}
