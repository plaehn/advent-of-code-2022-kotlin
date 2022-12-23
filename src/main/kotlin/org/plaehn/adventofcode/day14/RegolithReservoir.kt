package org.plaehn.adventofcode.day14

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.day14.Element.*
import java.lang.Integer.max
import java.lang.Integer.min


class RegolithReservoir(private val paths: List<List<Coord>>) {

    fun countUnitOfSands() = pourSand(buildScan())

    private fun buildScan(): Matrix<Element> {
        val (width, height) = computeDimensions(paths)
        val cave = Matrix.fromDimensions(width, height, AIR)

        paths.forEach { path ->
            path.zipWithNext().forEach { pair ->
                pair.toCoords().forEach { cave[it] = ROCK }
            }
        }
        return cave
    }

    private fun pourSand(cave: Matrix<Element>): Int {
        var units = 0

        do {
            val newPosition = computeNewSandPosition(cave)

            if (!cave.isInsideBounds(newPosition)) break

            cave[newPosition] = SAND
            ++units
        } while (true)

        return units
    }

    private fun computeNewSandPosition(cave: Matrix<Element>): Coord {
        var position = Coord(500, 0)
        do {
            val newPosition = directions.firstNotNullOfOrNull { computeNewPosition(cave, position, it) }
            if (newPosition != null) position = newPosition
        } while (newPosition != null && cave.isInsideBounds(position))
        return position
    }

    private fun computeNewPosition(cave: Matrix<Element>, position: Coord, direction: Coord): Coord? {
        val newPosition = position + direction
        return if (!cave.isInsideBounds(newPosition)) {
            newPosition
        } else if (cave[newPosition] == ROCK || cave[newPosition] == SAND) {
            null
        } else {
            newPosition
        }
    }

    private fun computeDimensions(paths: List<List<Coord>>): Pair<Int, Int> {
        val coords = paths.flatten()
        val width = 1 + coords.maxOf { it.x }
        val height = 1 + coords.maxOf { it.y }
        return width to height
    }

    private fun Pair<Coord, Coord>.toCoords(): List<Coord> =
        if (first.x == second.x) {
            val start = min(first.y, second.y)
            val end = max(first.y, second.y)
            (start..end).map { y -> Coord(first.x, y) }
        } else if (first.y == second.y) {
            val start = min(first.x, second.x)
            val end = max(first.x, second.x)
            (start..end).map { x -> Coord(x, first.y) }
        } else {
            throw IllegalArgumentException("No diagonal paths allowed")
        }

    companion object {

        val directions = listOf(Coord(0, 1), Coord(-1, 1), Coord(1, 1))

        fun fromInput(lines: List<String>) = RegolithReservoir(lines.map { it.toPath() })

        private fun String.toPath(): List<Coord> =
            split(" -> ").map { coord -> coord.split(",").let { Coord(it[0].toInt(), it[1].toInt()) } }
    }
}

enum class Element {
    AIR,
    ROCK,
    SAND;

    override fun toString() =
        when (this) {
            AIR -> "."
            ROCK -> "#"
            SAND -> "o"
        }
}
