package org.plaehn.adventofcode.day17

import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.day17.Element.EMPTY


class PyroclasticFlow(val jetPattern: List<Direction>, val shapes: List<Matrix<Element>>) {

    fun computeTowerHeight(numberOfRocks: Int): Int {
        println(jetPattern.joinToString(""))
        println(shapes.joinToString("\n\n"))
        TODO()
    }

    companion object {
        fun fromInput(jetPattern: String, shapes: String) =
            PyroclasticFlow(
                jetPattern = jetPattern.map { Direction.fromInput(it) },
                shapes = shapes.groupByBlankLines().map { it.toShape() }
            )

        private fun String.toShape(): Matrix<Element> =
            Matrix.fromRows(lines().map { line -> line.map { Element.fromInput(it) } }, EMPTY)
    }
}


enum class Element {
    ROCK,
    EMPTY;

    override fun toString() =
        when (this) {
            ROCK -> "#"
            EMPTY -> "."
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

enum class Direction {
    LEFT,
    RIGHT;

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