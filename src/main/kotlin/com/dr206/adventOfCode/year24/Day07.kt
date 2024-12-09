package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun part1(input: List<String>): Long {
        val raw = input
            .map { it.split(":").toList() }
            .map { it[0].trim() to it[1].trim() }
            .map { Pair(it.first, it.second.split(" ").toList()) }

        var finalAnswer = 0.0
        for (equation in raw) {
            val numbers = equation.second
            val levels = numbers.size

            var result = listOf<String>()
            for (i in 0..<levels) {
                val letter = numbers[i]

                result = if (result.isEmpty()) {
                    listOf(letter)
                } else {
                    result.map { listOf("$it + $letter", "$it * $letter") }.flatten().toList()
                }
            }

            val ans = result.map {
                it.split(" ").toList()
            }.map {
                val startingValue = it.take(1).first().toLong()
                it.drop(1)
                    .chunked(2)
                    .fold(startingValue) { acc, item ->
                        when (item[0]) {
                            "+" -> acc.plus(item[1].toLong())
                            "*" -> acc.times(item[1].toLong())
                            else -> throw Exception()
                        }
                    }
            }
                .filter { it == equation.first.toLong() }
                .distinct()
                .toList()

            finalAnswer += ans.firstOrNull() ?: 0
        }

        return finalAnswer.toLong()
            .also { println("Result: $it") }
    }

    fun part2(input: List<String>): Long {
        val raw = input
            .map { it.split(":").toList() }
            .map { it[0].trim() to it[1].trim() }
            .map { Pair(it.first, it.second.split(" ").toList()) }

        fun findTestValueForCalculations(
            calculations: List<List<String>>,
            testValue: String
        ): List<Long> {
            val ans = calculations
                .map {
                    val startingValue = it.take(1).first().toLong()
                    it.drop(1)
                        .chunked(2)
                        .fold(startingValue) { acc, item ->
                            when (item[0]) {
                                "+" -> acc.plus(item[1].toLong())
                                "*" -> acc.times(item[1].toLong())
                                "||" -> "${acc}${item[1]}".toLong()
                                else -> throw Exception()
                            }
                        }
                }
                .filter { it == testValue.toLong() }
                .distinct()
                .toList()
            return ans
        }

        fun findTestValueForValidNumber(
            testValue: String,
            numbers: List<String>
        ): List<Long> {
            var result = listOf<String>()
            for (element in numbers) {

                result = if (result.isEmpty()) {
                    listOf(element)
                } else {
                    result.map { listOf("$it + $element", "$it * $element", "$it || $element") }.flatten().toList()
                }
            }

            val calculations = result.map {
                it.split(" ").toMutableList()
            }

            var isEmpty = true
            var replacementCount = 0
            val maxReplacements = (calculations.first().size-1)/2
            var answer: List<Long> = emptyList()

            while (isEmpty && replacementCount <= maxReplacements) {
                // TODO: Bad assumption: '||' can only appear once per equation
                if (replacementCount == 0 ){
                    answer = findTestValueForCalculations(calculations, testValue)
                    isEmpty = answer.isEmpty()
                } else {
                    val newCalculations = calculations.toList()
                    val newInput = newCalculations.map {
                        it.toMutableList()
                    }.map {
                        val idx = replacementCount*2 - 1
                        it.removeAt(idx)
                        it.add(idx, "||")
                        it
                    }.distinct()

                    answer = findTestValueForCalculations(newInput, testValue)
                    isEmpty = answer.isEmpty()
                }
                replacementCount++
            }

            return answer
        }

        var finalAnswer = 0L
        for (equation in raw) {
            val testValue = equation.first
            val numbers = equation.second
            val ans = findTestValueForValidNumber(testValue, numbers)

            finalAnswer += ans.firstOrNull() ?: 0
        }

        return finalAnswer
            .also { println("Result: $it") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    check(part1(testInput) == 3749L)
    println("Part 1 ${part1(input)}")


    check(part2(testInput) == 11387L)
    println("Part 2 ${part2(input)}")

}
