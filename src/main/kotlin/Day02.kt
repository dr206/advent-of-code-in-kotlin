fun main() {

    fun part1(input: List<String>): Int {
        val strategyGuide = input
            .pairs<String>(" ")
            .map { Pair(it.first.toHand(), it.second.toHand()) }
        return strategyGuide.sumOf { getTotalPerHand(it) }
    }

    fun part2(input: List<String>): Int {
        val strategyGuide = input
            .pairs<String>(" ")
            .map { outcomeToHand(it) }
        return strategyGuide.sumOf { getTotalPerHand(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput).also { println("Test part 1: $it") } == 15)
    check(part2(testInput).also { println("Test part 2: $it") } == 12)

    val input = readInput("Day02")
    println("Part 1 ${part1(input)}")
    println("Part 2 ${part2(input)}")
}

enum class Hand(yours: String, val mine: String, val score: Int) {
    ROCK("X", "A", 1),
    PAPER("Y", "B", 2),
    SCISSORS("Z", "C", 3)
}

val beats = mapOf(
    Hand.ROCK to Hand.SCISSORS,
    Hand.PAPER to Hand.ROCK,
    Hand.SCISSORS to Hand.PAPER
)

fun matchOutcome(mine: Hand, yours: Hand) = when (yours) {
    mine -> 0
    beats[mine] -> 1
    else -> -1
}

fun getTotalPerHand(p: Pair<Hand, Hand>) = 3*(1 + matchOutcome(p.second, p.first)) + p.second.score

fun String.toHand() = when(this) {
    "X", "A" -> Hand.ROCK
    "Y", "B" -> Hand.PAPER
    else -> Hand.SCISSORS
}

fun outcomeToHand(p: Pair<String, String>): Pair<Hand, Hand> = when(p.second) {
    // Lose
    "X" -> Pair(p.first.toHand(), beats[p.first.toHand()]!!)
    // Draw
    "Y" -> Pair(p.first.toHand(), p.first.toHand())
    // Wim
    else -> Pair(p.first.toHand(), beats.entries.first { it.value == p.first.toHand() }.key)
}
