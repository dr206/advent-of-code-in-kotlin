package com.dr206.adventOfCode.year22

import com.dr206.adventOfCode.readInput
import com.dr206.adventOfCode.split

fun main() {

    fun getCounts(input: List<String>): List<Int> = input
        .split { calorie -> calorie.isBlank() }
        .map { elfCalorieList -> elfCalorieList.sumOf { calorie -> calorie.toInt() } }

    fun part1(input: List<String>): Int {
        return getCounts(input).max()
    }

    fun part2(input: List<String>): Int {
        return getCounts(input)
            .sortedDescending()
            .also { println(it) }
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println("Part 1 ${part1(input)}")
    println("Part 2 ${part2(input)}")
}
