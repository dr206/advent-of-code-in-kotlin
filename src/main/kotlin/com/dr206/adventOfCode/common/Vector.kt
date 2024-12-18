package com.dr206.adventOfCode.common

data class Vector(val x: Long, val y: Long) {
    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())
}

fun Vector.add(direction: Vector) = Vector((this.x + direction.x), (this.y + direction.y))
fun Vector.minus(direction: Vector) = Vector((this.x - direction.x), (this.y - direction.y))

fun Vector.addMod(direction: Vector, dimensions: Pair<Long, Long>) =
    Vector((this.x + direction.x).mod(dimensions.first), (this.y + direction.y).mod(dimensions.second))

fun Map<Vector, String>.draw(xDim: Long? = null, yDim: Long? = null) {
    val xLength = xDim ?: this.keys.maxOf { it.x }.inc()
    val yLength = yDim ?: this.keys.maxOf { it.y }.inc()

    for (y in 0..<yLength) {
        for (x in 0..<xLength) {
            val vec = Vector(x, y)
            print(this[vec])
        }
        println()
    }
    println()
}
