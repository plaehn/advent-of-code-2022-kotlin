package org.plaehn.adventofcode.day18

import org.plaehn.adventofcode.common.Coord


class BoilingBoulders(val cubes: List<Coord>) {

    fun computeSurfaceArea() = cubes.sumOf { 6 - it.countNeighbors() }

    private fun Coord.countNeighbors() = neighbors(dimensions = 3).count { it in cubes }

    companion object {
        fun fromInput(input: List<String>) =
            BoilingBoulders(cubes = input.map { Coord.fromString(it) })
    }
}
