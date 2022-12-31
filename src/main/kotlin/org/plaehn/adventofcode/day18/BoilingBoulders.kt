package org.plaehn.adventofcode.day18

import org.plaehn.adventofcode.common.Coord


class BoilingBoulders(val cubes: Set<Coord>) {

    fun computeSurfaceArea() = cubes.sumOf { 6 - it.countCubeNeighbors() }

    fun computeSurfaceAreaWithoutAirPockets() = computeSurfaceArea() - computeAirPocketSurfaceArea()

    private fun computeAirPocketSurfaceArea(): Int {
        val areaSpanned = sequence {
            (cubes.minOf { it.x }..cubes.maxOf { it.x }).forEach { x ->
                (cubes.minOf { it.y }..cubes.maxOf { it.y }).forEach { y ->
                    (cubes.minOf { it.z }..cubes.maxOf { it.z }).forEach { z ->
                        yield(Coord(x, y, z))
                    }
                }
            }
        }.toSet()

        val reachable = mutableSetOf<Coord>()
        do {
            val newReachable = areaSpanned.filter { coord ->
                coord !in reachable && coord !in cubes && coord.neighbors(dimensions = 3).any { neighbor ->
                    neighbor in reachable || neighbor !in areaSpanned
                }
            }.toSet()
            reachable += newReachable
        } while (newReachable.isNotEmpty())

        return areaSpanned
            .filter { coord -> coord !in cubes && coord !in reachable }
            .sumOf { coord -> coord.countCubeNeighbors() }
    }

    private fun Coord.countCubeNeighbors() = neighbors(dimensions = 3).count { it in cubes }

    companion object {
        fun fromInput(input: List<String>) =
            BoilingBoulders(cubes = input.map { Coord.fromString(it) }.toSet())
    }
}
