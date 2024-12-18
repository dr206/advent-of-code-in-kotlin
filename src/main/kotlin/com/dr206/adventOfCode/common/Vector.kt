package com.dr206.adventOfCode.common

data class Vector(val x: Long, val y: Long) {
    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())
}

enum class DIRECTIONS4(val vector: Vector) {
    NORTH(Vector(1, 0)),
    EAST(Vector(0, 1)),
    SOUTH(Vector(-1, 0)),
    WEST(Vector(0, -1)),
}

enum class DIRECTIONS8(val vector: Vector) {
    NORTH(Vector(1, 0)),
    NORTH_EAST(Vector(1, 1)),
    EAST(Vector(0, 1)),
    SOUTH_EAST(Vector(-1, 1)),
    SOUTH(Vector(-1, 0)),
    SOUTH_WEST(Vector(-1, -1)),
    WEST(Vector(0, -1)),
    NORTH_WEST(Vector(1, -1)),
}

fun Vector.add(direction: Vector) = Vector((this.x + direction.x), (this.y + direction.y))
fun Vector.minus(direction: Vector) = Vector((this.x - direction.x), (this.y - direction.y))

fun Vector.addMod(direction: Vector, dimensions: Pair<Long, Long>) =
    Vector((this.x + direction.x).mod(dimensions.first), (this.y + direction.y).mod(dimensions.second))

fun Map<Vector, String>.draw(xDim: Long? = null, yDim: Long? = null, missing: String = ".") {
    val xLength = xDim ?: this.keys.maxOf { it.x }.inc()
    val yLength = yDim ?: this.keys.maxOf { it.y }.inc()

    for (y in 0..<yLength) {
        for (x in 0..<xLength) {
            val vec = Vector(x, y)
            if (this.containsKey(vec)) {
                print(this[vec])
            } else {
                print(missing)
            }
        }
        println()
    }
    println()
}

fun Vector.neighbours(xyDimensions: Vector? = null): List<Vector> = DIRECTIONS4.entries
    .map { this.add(it.vector) }
    .filter {
        xyDimensions == null ||
                (it.x in 0 until xyDimensions.x && it.y in 0 until xyDimensions.y)
    }
    .toList()
