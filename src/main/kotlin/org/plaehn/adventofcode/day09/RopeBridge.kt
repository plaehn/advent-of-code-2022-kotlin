package org.plaehn.adventofcode.day09

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.DOWN
import org.plaehn.adventofcode.common.Coord.Companion.LEFT
import org.plaehn.adventofcode.common.Coord.Companion.RIGHT
import org.plaehn.adventofcode.common.Coord.Companion.UP
import kotlin.math.absoluteValue
import kotlin.math.sign


class RopeBridge(private val motions: List<Motion>, private val numberOfKnots: Int) {

    fun countPositionsTailTouched(): Int {
        val knots = MutableList(numberOfKnots) { Coord(0, 0) }
        val touchedByTail = mutableSetOf<Coord>()

        motions.forEach { motion ->
            motion.perform(knots, touchedByTail)
        }

        return touchedByTail.size
    }

    private fun Motion.perform(knots: MutableList<Coord>, touchedByTail: MutableSet<Coord>) {
        repeat(steps) {
            knots.forEachIndexed { idx, knot ->
                val offset = if (idx == 0) offset else computeTailOffset(knots[idx - 1], knot)
                knots[idx] += offset
            }
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
    val offset: Coord,
    val steps: Int
) {
    companion object {
        fun fromInput(input: String): Motion {
            val (direction, steps) = input.split(' ')
            return Motion(direction.toOffset(), steps.toInt())
        }

        private fun String.toOffset() =
            when (this) {
                "U" -> UP
                "D" -> DOWN
                "R" -> RIGHT
                "L" -> LEFT
                else -> throw IllegalArgumentException("Unknown direction $this")
            }
    }
}