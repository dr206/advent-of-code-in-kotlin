package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun part1(input: List<String>): Long {
        val antennaTypes = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        fun Pair<Int, Int>.plus(pt: Pair<Int, Int>) = (this.first + pt.first) to (this.second + pt.second)
        fun Pair<Int, Int>.rotate() = -this.first to -this.second

        for (i in input.indices) {
            for (j in 0..<input[i].length) {
                if (input[i][j] != '.') {
                    val list = antennaTypes[input[i][j]]
                    antennaTypes[input[i][j]] = list?.also { it.add(i to j) } ?: mutableListOf(i to j)
                }
            }
        }

        val sum: Long = antennaTypes.keys.flatMap {
            val arr = antennaTypes[it]!!.toList()
            val list = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

            arr.indices.forEach {
                i -> arr.indices.minus(0..i).forEach {
                    j -> list.add(arr[i] to arr[j])
                }
            }

            list.flatMap {
                val diff = (it.second.first - it.first.first) to (it.second.second - it.first.second)

                fun getNodes(pt: Pair<Int, Int>, diff: Pair<Int, Int>, nodes: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
                    val newPt = pt.plus(diff)
                    if (newPt.first !in input.indices || newPt.second !in input[0].indices) {
                        return nodes
                    }

                    val newNodes = nodes.toList() + newPt
                    return newNodes
                }

                val tooMany = getNodes(it.first, diff, emptyList()) +
                        getNodes(it.first, diff.rotate(), emptyList()) +
                        getNodes(it.second, diff, emptyList()) +
                        getNodes(it.second, diff.rotate(), emptyList())

                tooMany.minus(setOf(it.first, it.second))
            }
                .filter { it.first in input.indices && it.second in input[0].indices }
        }
            .distinct()
            .size
            .toLong()
        return sum
            .also { println("Result: $it") }
    }

    fun part2(input: List<String>): Long {
        val antennaTypes = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        fun Pair<Int, Int>.plus(pt: Pair<Int, Int>) = (this.first + pt.first) to (this.second + pt.second)
        fun Pair<Int, Int>.rotate() = -this.first to -this.second

        for (i in input.indices) {
            for (j in 0..<input[i].length) {
                if (input[i][j] != '.') {
                    val list = antennaTypes[input[i][j]]
                    antennaTypes[input[i][j]] = list?.also { it.add(i to j) } ?: mutableListOf(i to j)
                }
            }
        }

        val sum: Long = antennaTypes.keys.flatMap {
            val arr = antennaTypes[it]!!.toList()
            val list = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

            arr.indices.forEach {
                    i -> arr.indices.minus(0..i).forEach {
                    j -> list.add(arr[i] to arr[j])
                    }
            }
            list.flatMap {
                val diff = (it.second.first - it.first.first) to (it.second.second - it.first.second)

                fun getNodes(pt: Pair<Int, Int>, diff: Pair<Int, Int>, nodes: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
                    val newPt = pt.plus(diff)
                    if (newPt.first !in input.indices || newPt.second !in input[0].indices) {
                        return nodes
                    }

                    val newNodes = nodes.toList() + newPt
                    return getNodes(newPt, diff, newNodes)
                }

                val tooMany = getNodes(it.first, diff, emptyList()) +
                        getNodes(it.first, diff.rotate(), emptyList()) +
                        getNodes(it.second, diff, emptyList()) +
                        getNodes(it.second, diff.rotate(), emptyList())

                tooMany
            }
                .filter { it.first in input.indices && it.second in input[0].indices }
        }
            .distinct()
            .size
            .toLong()
        return sum
            .also { println("Result: $it") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

    check(part1(testInput) == 14L)
    println("Part 1 ${part1(input)}")

    check(part2(testInput) == 34L)
    println("Part 2 ${part2(input)}")
}
