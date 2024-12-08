package com.dr206.adventOfCode.year24

fun main() {
    val fileInput = """
    """

    fun day2(input: String, evaluate: (List<Int>) -> Int): Int {
        val shape: (String) -> List<List<Int>> = { 
            input: String -> input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map {
                    it.split(" ").map { n -> n.toInt() }.toList()
                }
                .toList()
        }
        
        val data = shape(input)

        return data.sumOf { evaluate(it) }
    }
    
    val part1: (List<Int>) -> Int  =  { report: List<Int> -> 
        val pairs = report
            .zipWithNext()
            .map { it.second - it.first }
        val incr = pairs.all { it > 0 }
        val decr = pairs.all { it < 0 }
        val inRange = pairs
        	.map { Math.abs(it) }
            .all { 0 <=it && it <= 3 }
        if ((incr or decr) && inRange ) 1 else 0
    }

    val part2: (List<Int>) -> Int  =  { report: List<Int> -> 
        if (part1(report)==1) {
            1
        } else {
            report.indices
                .toList()
                .stream()
                .map { it -> 
                    val copy = report.toMutableList();
                    copy.removeAt(it)
                    part1(copy)
                }
                .toList()
                .firstOrNull({ it -> it == 1 })
                ?: 0
        }
    }

    fun part1(input: String): Int {
        return day2(input, part1);
    }

    fun part2(input: String): Int {
        return day2(input, part2);
    }
    
    
    // test if implementation meets criteria from the description, like:
    val testInput = """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
	"""
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    println("Part 1 ${part1(fileInput)}")
    println("Part 2 ${part2(fileInput)}")
}
