package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun part1(input: List<String>): Int {
        fun dfs(
            grid: List<String>,
            i: Int,
            j: Int,
            word: String,
            pos: Int,
            direction: Pair<Int, Int>
        ): Int {

            val pt = (i + direction.first) to (j + direction.second)
            if (
                i == -1 ||
                i == grid.size ||
                j == -1 ||
                j == grid.first().length
            ) return 0

            if (grid[i][j] != word[pos]) return 0
            if (pos == word.length - 1) {
                return 1
            }

            return dfs(grid, pt.first, pt.second, word, pos + 1, direction)
        }

        val word = "XMAS"
        val directions = mapOf(
            "N" to Pair(-1, 0),
            "NE" to Pair(-1, 1),
            "E" to Pair(0, 1),
            "SE" to Pair(1, 1),
            "S" to Pair(1, 0),
            "SW" to Pair(1, -1),
            "W" to Pair(0, -1),
            "NW" to Pair(-1, -1),
        )

        val rawData = input

        return rawData.indices.sumOf { i ->
            rawData[i].indices.sumOf { j ->
                if (rawData[i][j] == word[0]) {
                    directions.values
                        .sumOf {
                            dfs(rawData, i, j, word, 0, it)
                        }
                } else 0
            }
        }
            .also { println("Result: $it") }
    }

    fun part2(input: List<String>): Int {
        fun dfs(
            grid: List<String>,
            i: Int,
            j: Int,
            word: String,
            pos: Int,
            direction: Pair<Int, Int>
        ): Int {

            val pt = (i + direction.first) to (j + direction.second)
            if (
                i == -1 ||
                i == grid.size ||
                j == -1 ||
                j == grid.first().length
            ) return 0

            if (grid[i][j] != word[pos]) return 0
            if (pos == word.length - 1) {
                return -1
            }

            val ans = dfs(grid, pt.first, pt.second, word, pos + 1, direction)
            if (ans == -1) {
                val l = listOf(
                    (-1*direction.first) to direction.second,
                    direction.first to (-1*direction.second)
                )

                val letters = l.map {
                    grid[i+it.first][j+it.second].toString()
                }.toList().sorted()

                return if (letters == listOf("M", "S")) {
                    1
                } else {
                    0
                }
            }
            return ans
        }

        val word = "MAS"
        val directions = mapOf(
            "NE" to Pair(-1, 1),
            "SE" to Pair(1, 1),
            "SW" to Pair(1, -1),
            "NW" to Pair(-1, -1),
        )

        val rawData = input

        return rawData.indices.sumOf { i ->
            rawData[i].indices.sumOf { j ->
                if (rawData[i][j] == word[0]) {
                    directions.values
                        .sumOf {
                            dfs(rawData, i, j, word, 0, it)
                        }
                } else 0
            }
        }.let { it/2 }
            .also { println("Result: $it") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    check(part1(testInput) == 18)
    println("Part 1 ${part1(input)}")

    check(part2(testInput) == 9)
    println("Part 2 ${part2(input)}")
}
