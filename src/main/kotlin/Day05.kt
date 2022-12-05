import java.util.Stack

fun main() {

    fun createStacks(initialSate: List<String>): List<Stack<Char>> {
        val stackShape = initialSate.filter { it.contains("[") }.reversed()
        val numberOfStacks = (stackShape.first().length + 1) / 4

        val stacks: List<Stack<Char>> = generateSequence { Stack<Char>() }.take(numberOfStacks).toMutableList()
        val paddedShape = stackShape.map { it.padEnd(4 * numberOfStacks, ' ') }
        for (idx in paddedShape.indices) {
            for (i in 1..4 * numberOfStacks step 4) {
                val char = paddedShape[idx][i]
                if (char != ' ') stacks[i / 4].add(char)
            }
        }
        return stacks
    }

    fun findTopOfStacks(
        input: List<String>,
        crateMovingProcess: (List<Stack<Char>>, String, String, String) -> Unit
    ): String {
        val (moves, initialSate) = input.partition { it.contains("move") }
        val stacks: List<Stack<Char>> = createStacks(initialSate)

        fun List<Stack<Char>>.print() = this.forEach { println(it.toString()) }
        stacks.print()
        for (move in moves) {
            val (amount, _, src, _, dest) = move.split(" ").subList(1, 6)
            crateMovingProcess(stacks, amount, src, dest)
            println("$amount, _, $src, _, $dest")
            stacks.print()
        }

        return String(stacks.map { it.pop() }.toCharArray())
            .also { println("Result is: $it") }
    }

    fun part1(input: List<String>): String {
        val moveCreatesOneByOne = { stacks: List<Stack<Char>>, amount: String, src: String, dest: String ->
            (0 until amount.toInt()).forEach { _ ->
                stacks[dest.toInt() - 1].push(stacks[src.toInt() - 1].pop())
            }
        }
        return findTopOfStacks(input, moveCreatesOneByOne)
    }

    fun part2(input: List<String>): String {
        val moveMultipleCratesAtATime = { stacks: List<Stack<Char>>, amount: String, src: String, dest: String ->
            val removed = (0 until amount.toInt()).fold(mutableListOf<Char>()) { acc, _ ->
                acc.add(stacks[src.toInt() - 1].pop())
                acc
            }
            removed.reversed().forEach { stacks[dest.toInt() - 1].push(it) }
        }
        return findTopOfStacks(input, moveMultipleCratesAtATime)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println("Part 1 ${part1(input)}")
    println("Part 2 ${part2(input)}")
}
