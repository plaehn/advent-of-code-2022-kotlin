package org.plaehn.adventofcode.day17

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.LEFT
import org.plaehn.adventofcode.common.Coord.Companion.RIGHT
import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.common.nth
import kotlin.math.absoluteValue


class PyroclasticFlow(val jetPattern: List<Coord>, val rockShapes: List<Set<Coord>>) {

    private val chamber = createChamber()
    private var rockCounter = 0
    private var jetCounter = 0

    private fun createChamber() = Coord(0, 0).lineTo(Coord(6, 0)).toMutableSet()

    fun solvePart1(): Int {
        repeat(2022) {
            drop(rockShapes.nth(rockCounter))
            ++rockCounter
        }

        return towerHeight()
    }

    private fun towerHeight() = chamber.minY().absoluteValue

    private fun drop(rockShape: Set<Coord>) {
        var position = Coord(2, chamber.minY() - 4)

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

        chamber += rockShape.at(position)
    }

    private fun overlap(rockShape: Set<Coord>, position: Coord): Boolean {
        val rock = rockShape.at(position)
        return chamber.intersect(rock).isNotEmpty() || rock.any { it.x !in (0..6) }
    }

    private fun Set<Coord>.at(position: Coord) = map { it + position }.toSet()

    fun solvePart2(): Long {
        val seen: MutableMap<State, Pair<Int, Int>> = mutableMapOf()
        while (true) {
            drop(rockShapes.nth(rockCounter++))

            val state = State(
                chamber.caveCeiling(),
                rockCounter % rockShapes.size,
                jetCounter % jetPattern.size
            )
            if (state in seen) {
                val (rockCountAtLoopStart, heightAtLoopStart) = seen.getValue(state)
                val blocksPerLoop: Long = rockCounter - 1L - rockCountAtLoopStart
                val totalLoops: Long = (1000000000000 - 1 - rockCountAtLoopStart) / blocksPerLoop
                val remainingBlocksFromClosestLoopToGoal: Long =
                    (1000000000000 - 1 - rockCountAtLoopStart) - (totalLoops * blocksPerLoop)
                val heightGainedSinceLoop = towerHeight() - heightAtLoopStart
                repeat(remainingBlocksFromClosestLoopToGoal.toInt()) {
                    drop(rockShapes.nth(rockCounter))
                    ++rockCounter
                }
                return towerHeight() + (heightGainedSinceLoop * (totalLoops - 1))
            }
            seen[state] = rockCounter - 1 to towerHeight()
        }
    }

    private fun MutableSet<Coord>.caveCeiling(): List<Int> =
        groupBy { it.x }
            .entries
            .sortedBy { it.key }
            .map { pointList -> pointList.value.minByOrNull { point -> point.y } }
            .let {
                val normalTo = this.minY()
                it.map { point -> normalTo - point!!.y }
            }

    private fun Set<Coord>.minY(): Int = minOf { it.y }

    data class State(
        val ceiling: List<Int>,
        val rockCounterMod: Int,
        val jetCounterMod: Int
    )

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

