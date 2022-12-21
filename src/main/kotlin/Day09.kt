import kotlin.math.absoluteValue
import kotlin.math.sign

enum class Direction { U, D, L, R  }

fun main() {

    fun printPath(path: MutableSet<Pair<Int, Int>>) {
        val sorted = path.sortedWith(compareBy({ it.first }, { it.second }))
        val row: Int = sorted.maxOf { it.first }
        val col: Int = sorted.maxOf { it.second }
        for (i in row downTo  0){
            for (j in 0 .. col) {
                if (path.contains(Pair(i, j))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        var head: Pair<Int, Int> = Pair(0,0)
        var tail: Pair<Int, Int> = Pair(0,0)
        val headPath = mutableListOf(head)
        val tailPath =mutableListOf(tail)

        fun Pair<Int, Int>.follow(leader: Pair<Int, Int>) {
            val vertDelta = leader.first - this.first
            val horizDelta = leader.second - this.second

            if (vertDelta.absoluteValue > 0 && horizDelta.absoluteValue > 0 && maxOf(vertDelta.absoluteValue, horizDelta.absoluteValue) > 1) {
                tail = this.copy(first = first + vertDelta.sign, second = second + horizDelta.sign)
                return
            }
            if (vertDelta.absoluteValue == 2) {
                tail = this.copy(first = first + vertDelta.sign)
                return
            }
            if (horizDelta.absoluteValue == 2) {
                tail = this.copy(second = second + horizDelta.sign)
                return
            }
        }

        for (move in input) {
            val (direction, steps) = move.split(" ")
//                .also { println("$move") }
            for (i in (1 .. steps.toInt())) {
//                println("$direction")
                head = when (Direction.valueOf(direction)) {
                    Direction.U -> head.copy(first = head.first+1)
                    Direction.D -> head.copy(first = head.first-1)
                    Direction.L -> head.copy(second = head.second-1)
                    Direction.R -> head.copy(second = head.second+1)
                }
                headPath.add(head.copy())

                tail.follow(head)
                tailPath.add(tail.copy())
            }
        }
        printPath(tailPath.toMutableSet())

        return tailPath.toSet().size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
//    check(part2(testInput) == -1)

    val input = readInput("Day09")
    println("Part 1 ${part1(input)}")
    println("Part 2 ${part2(input)}")
}
