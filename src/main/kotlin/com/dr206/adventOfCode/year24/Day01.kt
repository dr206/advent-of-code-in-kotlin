package com.dr206.adventOfCode.year24

fun main() {
    val fileInput = """

"""
    
    fun day1(input: String, evaluate: (List<Int>, List<Int>) -> Int): Int {
        val shape: (String) -> Pair<List<Int>, List<Int>> = { 
            input: String -> input.split("\n")
                .map { it.trim() }
                .filter { it != "" }
                .map { it.split("\\s+".toRegex()) }
            	.map { Pair(it[0].toInt(), it[1].toInt()) }
                .fold(Pair(emptyList<Int>(), emptyList<Int>())) { 
                    acc, next -> Pair(acc.first + next.first, acc.second + next.second) 
                }
        };
        
        val data = shape(input);

        return evaluate(data.first, data.second);
    }
    
    val part1: (List<Int>, List<Int>) -> Int  =  { l1: List<Int>, l2: List<Int> 
        -> (l1.sorted()).zip((l2.sorted())) { f, l -> Math.abs(l-f) }.sum()
    };

    val part2: (List<Int>, List<Int>) -> Int  =  { l1: List<Int>, l2: List<Int> 
        -> l1.map {
            el -> l2.count { it==el } * el
        }.sum()
    };

    fun part1(input: String): Int {
        return day1(input, part1);
    }

    fun part2(input: String): Int {
        return day1(input, part2);
    }
    
    
    // test if implementation meets criteria from the description, like:
    val testInput1 = """
3   4
4   3
2   5
1   3
3   9
3   3
    """
    val testInput2 = testInput1
    check(part1(testInput1) == 11)
    check(part2(testInput2) == 31)

    


    val input1 = fileInput
    val input2 = fileInput
    println("Part 1 ${part1(input1)}")
    println("Part 2 ${part2(input2)}")
}
