package org.plaehn.adventofcode.day17

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.day17.Element.*


class PyroclasticFlow(val jetPattern: List<Direction>, val shapes: List<Matrix<Element>>) {

    private val chamber = createChamber()
    private var rockCounter = 0
    private var jetCounter = 0
    
    private fun createChamber(): Matrix<Element> {
        val chamber = Matrix.fromDimensions(WIDTH, HEIGHT, EMPTY)
        chamber.drawFloor()
        chamber.drawWalls()
        return chamber
    }

    private fun Matrix<Element>.drawFloor() {
        Coord(1, 0).lineTo(Coord(WIDTH - 2, 0)).forEach {
            this[it] = FLOOR
        }
    }

    private fun Matrix<Element>.drawWalls() {
        Coord(0, 0).lineTo(Coord(0, HEIGHT - 1)).forEach {
            this[it] = WALL
        }
        Coord(WIDTH - 1, 0).lineTo(Coord(WIDTH - 1, HEIGHT - 1)).forEach {
            this[it] = WALL
        }
    }

    fun computeTowerHeight(numberOfRocks: Int): Int {
        repeat(numberOfRocks) {
            drop(shapes.nth(rockCounter))
            ++rockCounter
        }

        return chamber.towerHeight()
    }

    private fun drop(rock: Matrix<Element>) {
        var position = Coord(3, chamber.towerHeight() + 4)

        do {
            var newPosition = position + jetPattern.nth(jetCounter).offset
            ++jetCounter
            if (!overlap(chamber, rock, newPosition)) {
                position = newPosition
            }

            newPosition = position + Coord(0, -1)
            if (overlap(chamber, rock, newPosition)) {
                break
            }
            position = newPosition
        } while (true)

        addToChamber(rock, position)
    }

    private fun overlap(chamber: Matrix<Element>, rock: Matrix<Element>, position: Coord) =
        rock.toMap().any { (coord, rockElement) ->
            chamber[coord + position] != EMPTY && rockElement != EMPTY
        }

    private fun addToChamber(rock: Matrix<Element>, position: Coord) {
        rock.toMap().forEach { (coord, rockElement) ->
            if (rockElement == ROCK) {
                chamber[coord + position] = rockElement
            }
        }
    }

    private fun <T> Matrix<T>.towerHeight() = rows().drop(1).takeWhile { !it.isFree() }.size

    private fun <E> List<E>.isFree() = drop(1).dropLast(1).all { it == EMPTY }

    fun <T> List<T>.nth(n: Int): T = this[n % size]

    companion object {
        private const val WIDTH = 9
        private const val HEIGHT = 10000

        fun fromInput(jetPattern: String, shapes: String) =
            PyroclasticFlow(
                jetPattern = jetPattern.map { Direction.fromInput(it) },
                shapes = shapes.groupByBlankLines().map { it.toShape() }
            )

        private fun String.toShape(): Matrix<Element> =
            Matrix.fromRows(lines().reversed().map { line -> line.map { Element.fromInput(it) } }, EMPTY)
    }
}

enum class Element {
    ROCK,
    EMPTY,
    WALL,
    FLOOR;

    override fun toString() =
        when (this) {
            ROCK -> "#"
            EMPTY -> "."
            WALL -> "|"
            FLOOR -> "-"
        }

    companion object {
        fun fromInput(input: Char) =
            when (input) {
                '#' -> ROCK
                '.' -> EMPTY
                else -> throw IllegalArgumentException("Invalid element $input")
            }
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