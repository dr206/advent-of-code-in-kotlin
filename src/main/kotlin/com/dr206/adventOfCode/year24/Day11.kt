package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun part1(input: List<String>): Long {
        fun rec(numbers: List<String>, repetition: Int): List<String> {
            if (repetition == 0) {
                return numbers
            }
            return rec(
                numbers.flatMap {
                    when {
                        it == "0" -> listOf("1")
                        it.length % 2 == 0 -> listOf("${it.take(it.length/2).toLong()}", "${it.takeLast(it.length/2).toLong()}")
                        else -> listOf("${2024L * it.toLong()}")
                    }
                }.toList()
                , repetition-1
            )
        }

        return input.map { it.split(" ").toList() }
            .flatMap { rec(it, 25) }
            .size
            .toLong()
            .also { println("Result: $it") }
    }

    fun foldingSolution(input: List<String>, num: Int): Long {
        val stoneFrequenciesPerLine: List<Map<Long, Long>> = input.map { line ->
            line.split(" ")
                .map { it.toLong() }
                .groupingBy { it }
                .eachCount()
                .map { it.key to it.value.toLong() }
                .toMap()
        }

        return stoneFrequenciesPerLine.sumOf { stoneFrequencies ->
            (1..num).fold(stoneFrequencies) { results: Map<Long, Long>, _: Int ->
                results.entries.fold(mutableMapOf()) { iterativeResults, stoneFrequency ->
                    when {
                        stoneFrequency.key == 0L -> iterativeResults.also { it.merge(1, stoneFrequency.value, Long::plus) }
                        "${stoneFrequency.key}".length % 2 == 0 -> {
                            iterativeResults.also {
                                it.merge("${stoneFrequency.key}".take("${stoneFrequency.key}".length / 2).toLong(), stoneFrequency.value, Long::plus)
                                it.merge("${stoneFrequency.key}".takeLast("${stoneFrequency.key}".length / 2).toLong(), stoneFrequency.value, Long::plus)
                            }
                        }
                        else -> iterativeResults.also { it.merge(2024L * stoneFrequency.key, stoneFrequency.value, Long::plus) }
                    }
                }
            }.values.sum()
        }
            .also { println("Result: $it") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val input = readInput("Day11")

    check(part1(testInput) == 55312L)
    println("Part 1 ${part1(input).also { check(it == 193269L) }}")

    val iterationCountPart1 = 25
    val iterationCountPart2 = 75
    println("Part 1 using part 2 solution: ${foldingSolution(input, iterationCountPart1).also { check(it == 193269L) }}")
    println("Part 2 ${foldingSolution(input, iterationCountPart2).also { check(it == 228449040027793) }}")
}
