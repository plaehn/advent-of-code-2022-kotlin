package org.plaehn.adventofcode.day09

import org.plaehn.adventofcode.common.Coord
import kotlin.math.absoluteValue
import kotlin.math.sign


class RopeBridge(private val motions: List<Motion>) {

    private var head = Coord(0, 0)
    private var tail = Coord(0, 0)
    private val touched = mutableSetOf<Coord>()

    fun countPositionsTailTouched(): Int {
        motions.forEach { motion ->
            motion.perform()
        }

        return touched.size
    }

    private fun Motion.perform() {
        repeat(steps) {
            head += direction.offset
            tail += computeTailOffset()
            touched.add(tail)
        }
    }

    private fun computeTailOffset(): Coord {
        val dX = head.x - tail.x
        val dY = head.y - tail.y

        val diagonalMove = dX.absoluteValue > 1 || dY.absoluteValue > 1

        val offsetX = if (diagonalMove || dX.absoluteValue > 1) dX.sign else 0
        val offsetY = if (diagonalMove || dY.absoluteValue > 1) dY.sign else 0

        return Coord(offsetX, offsetY)
    }

    companion object {
        fun fromInput(input: List<String>) =
            RopeBridge(input.map { Motion.fromInput(it) })
    }
}

data class Motion(
    val direction: Direction,
    val steps: Int
) {
    companion object {
        fun fromInput(input: String): Motion {
            val (direction, steps) = input.split(' ')
            return Motion(Direction.fromInput(direction), steps.toInt())
        }
    }
}

enum class Direction(val offset: Coord) {

    UP(Coord(0, -1)),
    DOWN(Coord(0, 1)),
    LEFT(Coord(-1, 0)),
    RIGHT(Coord(1, 0));

    companion object {
        fun fromInput(input: String) =
            when (input) {
                "U" -> UP
                "D" -> DOWN
                "R" -> RIGHT
                "L" -> LEFT
                else -> throw IllegalArgumentException("Unknown direction $input")
            }
    }
}
