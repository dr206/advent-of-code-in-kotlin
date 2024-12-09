package com.dr206.adventOfCode.year24

import com.dr206.adventOfCode.readInput

fun main() {

    fun part1(data: List<String>): Long {

        data class Id(val value: Long? = null)

        tailrec fun addDots(line: String, ids: MutableList<Id>, count: Long, isDot: Boolean): MutableList<Id> {
            if (line.isEmpty()) {
                return ids
            }

            if (isDot) {
                repeat(line.first().digitToInt()) {
                    ids.add(Id())
                }
                return addDots(line.drop(1), ids, count, false)
            } else {
                repeat(line.first().digitToInt()) {
                    ids.add(Id(count))
                }
                return addDots(line.drop(1), ids, count+1, true)
            }
        }

        val p1: List<Id> = addDots(data.first(), mutableListOf(), 0, false)

        tailrec fun rearrange(head: Id, tail: List<Id>, defragged: MutableList<Id>): MutableList<Id> {
            if (tail.isEmpty()) {
                return defragged
            }

            if (head.value == null) {
                val usableTail: List<Id> = tail.dropLastWhile { it.value == null }
                val newVal: Id = usableTail.takeLast(1).first()
                defragged.add(newVal)

                val shortTail = usableTail.dropLast(1).toMutableList()
                val newTail: List<Id> = shortTail.also { it.add(Id()) }
                return rearrange(newTail.take(1).first(), newTail.drop(1), defragged)
            } else {
                defragged.add(head)
                return rearrange(tail.take(1).first(), tail.drop(1), defragged)
            }
        }

        val p2: List<Id> = rearrange(p1.take(1).first(), p1.drop(1), mutableListOf())//addDots(data.first(), StringBuilder(), 0, false)

        val p3: Long = p2.indices.sumOf { it * (p2[it].value!!) }

        return p3
            .also { println("Result: $it") }
    }

    fun part2(data: List<String>): Long {

        data class Id(val value: Long? = null)
        data class Memory(val size: Int, val value: Long? = null)

        tailrec fun addDots(line: String, ids: MutableList<Id>, count: Long, isDot: Boolean): MutableList<Id> {
            if (line.isEmpty()) {
                return ids
            }

            if (isDot) {
                repeat(line.first().digitToInt()) {
                    ids.add(Id())
                }
                return addDots(line.drop(1), ids, count, false)
            } else {
                repeat(line.first().digitToInt()) {
                    ids.add(Id(count))
                }
                return addDots(line.drop(1), ids, count+1, true)
            }
        }

        val p1: List<Id> = addDots(data.first(), mutableListOf(), 0, false)

        fun MutableList<Memory>.reduce(): List<Id> = this.fold(mutableListOf()) { acc, mem ->
            if (mem.value == null) {
                repeat(mem.size) {
                    acc.add(Id())
                }
            } else {
                repeat(mem.size) {
                    acc.add(Id(mem.value))
                }
            }
            acc
        }

        fun rearrange(blocks: MutableList<Id>): List<Id> {
            val memories = mutableListOf<Memory>()
            var count = 0
            var lastId: Long? = blocks.first().value
            for (id in blocks) {
                if (id.value == lastId) {
                    lastId = id.value
                    count++
                } else {
                    memories.add(Memory(count, lastId))
                    lastId = id.value
                    count = 1
                }
            }
            memories.add(Memory(count, lastId))

            tailrec fun defrag(head: List<Memory>, tail: Memory, output: MutableList<Memory>): List<Memory> {
                if (head.isEmpty()) {
                    output.add(0, tail)
                    return output
                }

                if (tail.value == null) {
                    output.add(0, tail)
                    return defrag(head.dropLast(1), head.takeLast(1).first(), output)
                } else {
                    val indexToReplace = head.indexOfFirst { it.value==null && (it.size >= tail.size) }
                    if (indexToReplace == -1) {
                        output.add(0, tail)
                        return defrag(head.dropLast(1), head.takeLast(1).first(), output)
                    }

                    val blockToReplace = head[indexToReplace]
                    val freeSpaceToInsert = blockToReplace.size - tail.size

                    val updatedHead = if (freeSpaceToInsert > 0) {
                        val updatedHead = head.toMutableList()
                        updatedHead[indexToReplace] = Memory(freeSpaceToInsert)
                        updatedHead.add(indexToReplace, tail)
                        updatedHead
                    } else {
                        val updatedHead = head.toMutableList()
                        updatedHead[indexToReplace] = tail
                        updatedHead
                    }
                    output.add(0, Memory(tail.size))

                    return defrag(updatedHead.dropLast(1), updatedHead.takeLast(1).first(), output)
                }
            }

            val out = defrag(memories.dropLast(1), memories.takeLast(1).first(), mutableListOf())
            return out.toMutableList().reduce()
        }

        val p2: List<Id> = rearrange(p1.toMutableList())

        val p3: Long = p2.indices
            .filter { p2[it].value != null }
            .sumOf { it * (p2[it].value!!) }
        return p3
            .also { println("Result: $it") }
//            .also { println("result: ${it.toBigDecimal().toPlainString()}") }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    check(part1(testInput) == 1928L)
    println("Part 1 ${part1(input)}")


    check(part2(testInput) == 2858L)
    println("Part 2 ${part2(input)}")


}
