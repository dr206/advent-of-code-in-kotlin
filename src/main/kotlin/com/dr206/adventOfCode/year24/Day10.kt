package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun solution(input: List<String>, part: (List<Pair<Int, Int>>) -> Int): Long {
        val heightLocations = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        fun Pair<Int, Int>.plus(pt: Pair<Int, Int>) = (this.first + pt.first) to (this.second + pt.second)
        fun Pair<Int, Int>.neighbours(): List<Pair<Int, Int>> = listOf(
            this.plus(0 to 1),
            this.plus(0 to -1),
            this.plus(1 to 0),
            this.plus(-1 to 0),
        ).filter { it.first in input.indices && it.second in input[0].indices }
            .toList()

        for (i in input.indices) {
            for (j in 0..<input[i].length) {
                val list = heightLocations[input[i][j]]
                heightLocations[input[i][j]] = list?.also { it.add(i to j) } ?: mutableListOf(i to j)
            }
        }

        fun search(startingNode: Pair<Int, Int>, currentHeight: Int): List<Pair<Int, Int>> {
            if (currentHeight == 9) {
                return listOf(startingNode)
            } else {
                val nextHeight = currentHeight + 1
                val a: MutableList<Pair<Int, Int>>? = heightLocations["$nextHeight".toCharArray().first()]
                val candidates: List<Pair<Int, Int>> = a!!
                val neighbours = startingNode.neighbours()
                val nextSteps = neighbours.intersect(candidates.toSet())

                return nextSteps.flatMap {
                    search(it, nextHeight)
                }.toList()
            }

        }
        return heightLocations['0']!!.sumOf { part(search(it, 0)) }
            .toLong()
            .also { println("Result: $it") }
    }

    fun part1(list: List<Pair<Int, Int>>) = list.distinct().size
    fun part2(list: List<Pair<Int, Int>>) = list.size


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val input = readInput("Day10")

    check(solution(testInput, ::part1) == 36L)
    println("Part 1 ${solution(input, ::part1)}")

    check(solution(testInput, ::part2) == 81L)
    println("Part 2 ${solution(input, ::part2)}")
}
