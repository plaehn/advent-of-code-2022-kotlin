package org.plaehn.adventofcode.day14

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.day14.Element.*
import java.lang.Integer.max
import java.lang.Integer.min


class RegolithReservoir(private val paths: List<List<Coord>>) {

    fun countUnitOfSands(withFloor: Boolean) = pourSand(buildScan(withFloor))

    private fun buildScan(withFloor: Boolean): Matrix<Element> {
        val (width, height) = computeDimensions(paths, withFloor)
        val cave = Matrix.fromDimensions(width, height, AIR)

        paths.forEach { path ->
            path.zipWithNext().forEach { pair ->
                pair.toCoords().forEach { cave[it] = ROCK }
            }
        }

        if (withFloor) {
            val floor = Coord(0, height - 1) to Coord(width - 1, height - 1)
            floor.toCoords().forEach { cave[it] = ROCK }
        }

        return cave
    }

    private fun computeDimensions(paths: List<List<Coord>>, withFloor: Boolean): Pair<Int, Int> {
        val coords = paths.flatten()
        var width = 1 + coords.maxOf { it.x }
        var height = 1 + coords.maxOf { it.y }
        if (withFloor) {
            width *= 10
            height += 2
        }
        return width to height
    }

    private fun pourSand(cave: Matrix<Element>): Int {
        var units = 0

        do {
            val newPosition = computeNewSandPosition(cave)

            if (!cave.isInsideBounds(newPosition)) break

            cave[newPosition] = SAND
            ++units

            if (newPosition == sandStart) break
        } while (true)

        return units
    }

    private fun computeNewSandPosition(cave: Matrix<Element>): Coord {
        var position = sandStart
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
        val sandStart = Coord(500, 0)
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
