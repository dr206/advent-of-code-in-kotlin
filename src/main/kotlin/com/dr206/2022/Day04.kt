fun main() {

    fun checkOverlapBasedOnPredicate(
        pairOfIdRanges: String,
        predicate: (Set<Int>, Set<Int>) -> Boolean
    ): Boolean {
        val (idRange1, idRange2) = pairOfIdRanges.split(',')

        val integers1 = idRange1.split('-').map { it.toInt() }
        val range1 = (integers1[0]..integers1[1]).toSet()

        val integers2 = idRange2.split('-').map { it.toInt() }
        val range2 = (integers2[0]..integers2[1]).toSet()

        return predicate(range1, range2)
    }

    fun part1(input: List<String>): Int {
        val predicate = { a: Set<Int>, b: Set<Int> -> a.containsAll(b) || b.containsAll(a) }
        return input.map {
            checkOverlapBasedOnPredicate(it, predicate)
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        val predicate = { a: Set<Int>, b: Set<Int> -> a.intersect(b).isNotEmpty() }
        return input.map {
            checkOverlapBasedOnPredicate(it, predicate)
        }.count { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println("Part 1 ${part1(input)}")
    println("Part 2 ${part2(input)}")
}
