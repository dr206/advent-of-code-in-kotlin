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
        orderVariables: (Int, Int) -> Pair<Int, Int>
    ): String {
        val (moves, initialSate) = input.partition { it.contains("move") }
        val stacks: List<Stack<Char>> = createStacks(initialSate)

        fun List<Stack<Char>>.print() = this.forEach { println(it.toString()) }
        stacks.print()
        for (move in moves) {
            val (numberOfCratesToBeMoved, _, src, _, dest) = move.split(" ").subList(1, 6)

            fun crateMovingProcess(timesCranePerformsALift: Int, cratesCraneMovesPerLift: Int) {
                repeat(timesCranePerformsALift) {
                    (0 until cratesCraneMovesPerLift).fold(mutableListOf<Char>()) { acc, _ ->
                        acc.add(stacks[src.toInt() - 1].pop())
                        acc
                    }.reversed().onEach { stacks[dest.toInt() - 1].push(it) }
                }
            }
            val (timesCranePerformsALift, cratesCraneMovesPerLift) = orderVariables(numberOfCratesToBeMoved.toInt(), 1)
            crateMovingProcess(timesCranePerformsALift, cratesCraneMovesPerLift)

            println("$numberOfCratesToBeMoved, _, $src, _, $dest")
            stacks.print()
        }

        return String(stacks.map { it.pop() }.toCharArray())
            .also { println("Result is: $it") }
    }

    fun part1(input: List<String>): String {
        val identity = { a:Int, b:Int -> Pair(a, b) }
        return findTopOfStacks(input, identity)
    }

    fun part2(input: List<String>): String {
        val swap = { a:Int, b:Int -> Pair(b, a) }
        return findTopOfStacks(input, swap)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println("Part 1 ${part1(input)}")
    println("Part 2 ${part2(input)}")
}
