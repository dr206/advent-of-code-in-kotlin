package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun part1(map: List<String>): Int {
        val directions = mapOf(
            "^" to ((0 to -1) to ">"),
            ">" to ((1 to 0) to "v"),
            "v" to ((0 to 1) to "<"),
            "<" to ((-1 to 0) to "^"),
        )

        val startLocation = map.indexOfFirst { it.contains("^") }
            .let {
                map[it].indexOfFirst { it == '^' } to it
            }

        fun Pair<Int, Int>.add(step: Pair<Int, Int>): Pair<Int, Int> = (this.first + step.first) to (this.second + step.second)
        fun List<String>.isBlocked(position: Pair<Int, Int>): Boolean = this[position.second][position.first] == '#'
        fun List<String>.isBoundary(position: Pair<Int, Int>): Boolean = (listOf(0, this.size-1).contains(position.second) or listOf(0, this.first().length-1).contains(position.first))

        tailrec fun move(map: List<String>, position: Pair<Int, Int>, direction: String, visited: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
            val delta = directions[direction]!!.first
            val newPosition = position.add(delta)

            if(map.isBoundary(position) and !map.isBlocked(position)) {
                return visited + setOf(position)
            }

            if (map.isBlocked(newPosition)) {
                return move(map, position, directions[direction]!!.second, visited)
            }

            return move(map, newPosition, direction, visited+ setOf(position))
        }

        val visited = move(map, startLocation, "^", emptySet())
        return visited.size
            .also { println("Result ${it}") }
    }

    fun part2(map: List<String>): Int {
        val directions = mapOf(
            "^" to ((0 to -1) to ">"),
            ">" to ((1 to 0) to "v"),
            "v" to ((0 to 1) to "<"),
            "<" to ((-1 to 0) to "^"),
        )

        val startLocation = map.indexOfFirst { it.contains("^") }
            .let {
                map[it].indexOfFirst { it == '^' } to it
            }


        fun Pair<Int, Int>.add(step: Pair<Int, Int>): Pair<Int, Int> = (this.first + step.first) to (this.second + step.second)
        fun List<String>.isBlocked(position: Pair<Int, Int>): Boolean = listOf('#', 'O').contains(this[position.second][position.first])
        fun List<String>.isBoundary(position: Pair<Int, Int>): Boolean = (listOf(0, this.size-1).contains(position.second) or listOf(0, this.first().length-1).contains(position.first))
        fun List<String>.update(position: Pair<Int, Int>, symbol: String = "X"): List<String> {
            val list = this.toMutableList()
            list[position.second] = (list[position.second] as CharSequence).replaceRange(position.first..position.first, symbol).toString()
            return list
        }

        var m: MutableMap<Pair<Int, Int>, Set<String>> = mutableMapOf()

        tailrec fun move(map: List<String>, position: Pair<Int, Int>, direction: String): Boolean {
            val cache: Set<String> = m[position] ?: emptySet()
            if (cache.contains(direction)) {
                return true
            }

            val delta = directions[direction]!!.first
            val newPosition = position.add(delta)

            if(map.isBoundary(position) and !map.isBlocked(position)) {

                val set = m[position] ?: emptySet()
                val popSet: Set<String> = if(set.isEmpty()) {
                    setOf(direction)
                } else {
                    set + setOf(direction)
                }
                m[position] = popSet

                return false
            }

            if (map.isBlocked(newPosition)) {
                return move(map, position, directions[direction]!!.second)
            }


            val set = m[position] ?: emptySet()
            val popSet: Set<String> = if(set.isEmpty()) {
                setOf(direction)
            } else {
                set + setOf(direction)
            }
            m[position] = popSet

            return move(map, newPosition, direction)
        }

        move(map, startLocation, "^")

        val objPosCandidates = m.keys.filter { it != startLocation }.toList()
        val finalCount = objPosCandidates
            .map { map.update(it, "O") }
            .count {
                m = mutableMapOf()
                move(it, startLocation, "^")
            }

        return finalCount
            .also { println("Result ${it}") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    check(part1(testInput) == 41)
    println("Part 1 ${part1(input)}")

    check(part2(testInput) == 6)
    println("Part 2 ${part2(input)}")


}
