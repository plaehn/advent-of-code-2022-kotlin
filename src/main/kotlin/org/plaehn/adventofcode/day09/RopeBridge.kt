package org.plaehn.adventofcode.day09

import org.plaehn.adventofcode.common.Coord
import kotlin.math.absoluteValue
import kotlin.math.sign


class RopeBridge(private val motions: List<Motion>, numberOfKnots: Int) {

    private var knots = List(numberOfKnots) { Coord(0, 0) }
    private val touchedByTail = mutableSetOf<Coord>()

    fun countPositionsTailTouched(): Int {
        motions.forEach { motion ->
            motion.perform()
        }

        return touchedByTail.size
    }

    private fun Motion.perform() {
        repeat(steps) {
            val newHead = knots.first() + direction.offset
            var head = newHead
            val newTails = knots
                .drop(1)
                .map { tail ->
                    val newTail = tail + computeTailOffset(head, tail)
                    head = newTail
                    newTail
                }

            knots = listOf(newHead) + newTails

            touchedByTail.add(knots.last())
        }
    }

    private fun computeTailOffset(head: Coord, tail: Coord): Coord {
        val dX = head.x - tail.x
        val dY = head.y - tail.y

        return if (dX.absoluteValue > 1 || dY.absoluteValue > 1) {
            Coord(dX.sign, dY.sign)
        } else {
            Coord(0, 0)
        }
    }

    companion object {
        fun fromInput(input: List<String>, numberOfKnots: Int) =
            RopeBridge(input.map { Motion.fromInput(it) }, numberOfKnots)
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
