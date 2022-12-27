package org.plaehn.adventofcode.day17

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.LEFT
import org.plaehn.adventofcode.common.Coord.Companion.RIGHT
import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.common.nth
import java.lang.Integer.min
import kotlin.math.absoluteValue


class PyroclasticFlow(val jetPattern: List<Coord>, val rockShapes: List<Set<Coord>>) {

    private val chamber = createChamber()
    private var minY = 0
    private var rockCounter = 0
    private var jetCounter = 0

    private fun createChamber() = Coord(0, 0).lineTo(Coord(6, 0)).toMutableSet()

    fun computeTowerHeight(numberOfRocks: Int): Int {
        repeat(numberOfRocks) {
            drop(rockShapes.nth(rockCounter))
            ++rockCounter
        }

        return towerHeight()
    }

    private fun towerHeight() = minY.absoluteValue

    private fun drop(rockShape: Set<Coord>) {
        var position = Coord(2, minY - 4)

        do {
            val jet = jetPattern.nth(jetCounter)
            var newPosition = position + jet
            ++jetCounter
            if (!overlap(rockShape, newPosition)) {
                position = newPosition
            }

            newPosition = position + Coord(0, 1)
            if (overlap(rockShape, newPosition)) {
                break
            }
            position = newPosition
        } while (true)

        addToChamber(rockShape, position)
    }

    private fun overlap(rockShape: Set<Coord>, position: Coord): Boolean {
        val rock = rockShape.at(position)
        return chamber.intersect(rock).isNotEmpty() || rock.any { it.x !in (0..6) }
    }

    private fun addToChamber(rockShape: Set<Coord>, position: Coord) {
        val landedRock = rockShape.at(position)
        minY = min(minY, landedRock.minOf { it.y })
        chamber += landedRock
    }

    private fun Set<Coord>.at(position: Coord) = map { it + position }.toSet()

    companion object {

        fun fromInput(jetPattern: String, shapes: String) =
            PyroclasticFlow(
                jetPattern = jetPattern.map { it.toOffset() },
                rockShapes = shapes.groupByBlankLines().map { it.toShape() }
            )

        private fun Char.toOffset() =
            when (this) {
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw IllegalArgumentException("Invalid direction $this")
            }

        private fun String.toShape(): Set<Coord> =
            sequence {
                lines().reversed().forEachIndexed { y, row ->
                    row.forEachIndexed { x, element ->
                        if (element == '#') {
                            yield(Coord(x, -y))
                        }
                    }
                }
            }.toSet()
    }
}


enum class Direction(val offset: Coord) {

    LEFT(Coord(-1, 0)),
    RIGHT(Coord(1, 0));

    override fun toString() =
        when (this) {
            LEFT -> "<"
            RIGHT -> ">"
        }

    companion object {
        fun fromInput(input: Char) =
            when (input) {
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw IllegalArgumentException("Invalid direction $input")
            }
    }
}