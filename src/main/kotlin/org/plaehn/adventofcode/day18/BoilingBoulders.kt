package org.plaehn.adventofcode.day18

import org.plaehn.adventofcode.common.Coord


class BoilingBoulders(val cubes: Set<Coord>) {

    fun computeSurfaceArea() = cubes.sumOf { 6 - it.countCubeNeighbors() }

    private fun Coord.countCubeNeighbors() = neighbors(dimensions = 3).count { it in cubes }

    fun computeSurfaceAreaWithoutAirPockets() = computeSurfaceArea() - computeAirPocketSurfaceArea()

    private fun computeAirPocketSurfaceArea(): Int {
        val areaSpanned = computeAreaSpannedByCubes()
        val reachable = computeReachableSpots(areaSpanned)
        val spotsInAirPockets = areaSpanned.filter { it !in cubes && it !in reachable }
        return spotsInAirPockets.sumOf { it.countCubeNeighbors() }
    }

    private fun computeAreaSpannedByCubes() =
        sequence {
            (cubes.minOf { it.x }..cubes.maxOf { it.x }).forEach { x ->
                (cubes.minOf { it.y }..cubes.maxOf { it.y }).forEach { y ->
                    (cubes.minOf { it.z }..cubes.maxOf { it.z }).forEach { z ->
                        this.yield(Coord(x, y, z))
                    }
                }
            }
        }.toSet()

    private fun computeReachableSpots(areaSpanned: Set<Coord>): MutableSet<Coord> {
        val reachable = mutableSetOf<Coord>()
        do {
            val newReachable = computeNewReachableSpots(areaSpanned, reachable)
            reachable += newReachable
        } while (newReachable.isNotEmpty())
        return reachable
    }

    private fun computeNewReachableSpots(areaSpanned: Set<Coord>, reachable: MutableSet<Coord>) =
        areaSpanned.filter { spot ->
            spot !in reachable
                && spot !in cubes
                && spot.hasReachableNeighbor(reachable, areaSpanned)
        }.toSet()

    private fun Coord.hasReachableNeighbor(reachable: Set<Coord>, areaSpanned: Set<Coord>) =
        neighbors(dimensions = 3).any { neighbor -> neighbor in reachable || neighbor !in areaSpanned }

    companion object {
        fun fromInput(input: List<String>) =
            BoilingBoulders(cubes = input.map { Coord.fromString(it) }.toSet())
    }
}
