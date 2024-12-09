package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun part1(input: List<String>): Int {
        val rules = input.filter { it.contains("|") }.toList()
        val rulesGrouped = rules
            .map {
                it.split("|")
                    .let { it[0].toLong() to it[1].toLong() }
            }
            .groupingBy { it.first }
            .fold(listOf<Long>()) { acc, el -> acc + el.second }

        val updates = input.filter { it.contains(",") }

        val goodUpdates = mutableListOf<List<Long>>()
        for (update in updates.map { it.split(",").map { it.toLong() } }) {
            var truth = false
            for (i in 1..< update.size) {
                val key = update[i-1]
                val subList = update.subList(i, update.size)
                truth = rulesGrouped[key]?.containsAll(subList) ?: false
                if (!truth) {
                    break
                }
            }
            if (truth) {
                goodUpdates.add(update)
            }
        }

        return goodUpdates.sumOf { it[it.size / 2] }
            .toInt()
            .also { println("Result: $it") }
    }

    fun part2(input: List<String>): Int {
        val rules = input.filter { it.contains("|") }.toList()
        val rulesGrouped = rules
            .map {
                it.split("|")
                    .let { it[0].toLong() to it[1].toLong() }
            }
            .groupingBy { it.first }
            .fold(listOf<Long>()) { acc, el -> acc + el.second }

        val updates = input.filter { it.contains(",") }

        val badUpdates = mutableListOf<List<Long>>()
        for (update in updates.map { it.split(",").map { it.toLong() } }) {
            var truth = false
            for (i in 1..< update.size) {
                val key = update[i-1]
                val subList = update.subList(i, update.size)
                truth = rulesGrouped[key]?.containsAll(subList) ?: false
                if (!truth) {
                    break
                }
            }
            if (!truth) {
                badUpdates.add(update)
            }
        }

        fun createOrdering(rules: Map<Long, List<Long>>, orderedList: List<Long>, element: Long, candidates: List<Long>): List<Long> {
            if (candidates.isEmpty()) {
                return orderedList.toList() + listOf(element)
            }

            val nextSteps = rules[element]?.intersect(candidates) ?: emptyList()

            val out: List<Long> = nextSteps
                .map {
                    val newCandidates = candidates.toMutableList()
                    newCandidates.remove(it)
                    createOrdering(rules, orderedList.toList() + listOf(element), it, newCandidates.toList())
                }.firstOrNull { it.isNotEmpty() }
                ?: emptyList()

            return out
        }

        val out: List<List<Long>> = badUpdates.map { list ->
            list.map {
                val candidates = list.toMutableList()
                candidates.remove(it)

                createOrdering(rulesGrouped, emptyList(), it, candidates)
            }.firstOrNull { it.isNotEmpty() }
                ?: emptyList()
        }

        return out
            .sumOf { it[it.size / 2] }
            .toInt()
            .also { println("Result: $it") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    check(part1(testInput) == 143)
    println("Part 1 ${part1(input)}")

    check(part2(testInput) == 123)
    println("Part 2 ${part2(input)}")
}
