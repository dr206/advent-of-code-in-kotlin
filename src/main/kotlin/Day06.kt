fun main() {

    fun String.getEndIndexOfStartMarkerOfLength(length: Int): Int = this.indexOf(
        this.windowed(length, 1).first { it.toSet().size == length }
    ).plus(length)

    fun part1(input: List<String>): Int {
        return input.first().getEndIndexOfStartMarkerOfLength(4)
    }

    fun part2(input: List<String>): Int {
        return input.first().getEndIndexOfStartMarkerOfLength(14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println("Part 1 ${part1(input)}")
    println("Part 2 ${part2(input)}")
}
