package org.plaehn.adventofcode.day16

import com.github.shiguruikai.combinatoricskt.permutations

// Cf. https://todd.ginsberg.com/post/advent-of-code/2022/day16/
class ProboscideaVolcanium(input: List<ValveRoom>) {

    private val rooms = input.associateBy { it.name }

    private val cheapestPathCosts: Map<String, Map<String, Int>> = calculateShortestPaths()

    private fun calculateShortestPaths(): Map<String, Map<String, Int>> {
        val shortestPaths: MutableMap<String, MutableMap<String, Int>> =
            rooms.values.associate {
                it.name to it.neighbors.associateWith { 1 }.toMutableMap()
            }.toMutableMap()

        shortestPaths.keys.permutations(3).forEach { (waypoint, from, to) ->
            val existingPath = shortestPaths[from, to]
            val newPath = shortestPaths[from, waypoint] + shortestPaths[waypoint, to]
            shortestPaths[from, to] = minOf(existingPath, newPath)
        }

        shortestPaths.removePathsToZeroFlowRooms()
        return shortestPaths.removeRoomsUnreachableFromStart()
    }

    private fun MutableMap<String, MutableMap<String, Int>>.removePathsToZeroFlowRooms() {
        val zeroFlowRooms = rooms.values.filter { it.flowRate == 0 || it.name == "AA" }.map { it.name }.toSet()
        values.forEach { it.keys.removeAll(zeroFlowRooms) }
    }

    private fun MutableMap<String, MutableMap<String, Int>>.removeRoomsUnreachableFromStart(): Map<String, MutableMap<String, Int>> {
        val canGetToFromAA = getValue("AA").keys
        return filter { it.key in canGetToFromAA || it.key == "AA" }
    }

    private operator fun Map<String, MutableMap<String, Int>>.set(key1: String, key2: String, value: Int) {
        getValue(key1)[key2] = value
    }

    private operator fun Map<String, Map<String, Int>>.get(key1: String, key2: String, defaultValue: Int = 31000) =
        get(key1)?.get(key2) ?: defaultValue

    fun computeMaximumPressureRelease() = searchPaths("AA", 30)

    private fun searchPaths(
        location: String,
        timeAllowed: Int,
        seen: Set<String> = emptySet(),
        timeTaken: Int = 0,
        totalFlow: Int = 0
    ): Int =
        cheapestPathCosts
            .getValue(location)
            .asSequence()
            .filterNot { (nextRoom, _) -> nextRoom in seen }
            .filter { (_, traversalCost) -> timeTaken + traversalCost + 1 < timeAllowed }
            .maxOfOrNull { (nextRoom, traversalCost) ->
                searchPaths(
                    nextRoom,
                    timeAllowed,
                    seen + nextRoom,
                    timeTaken + traversalCost + 1,
                    totalFlow + ((timeAllowed - timeTaken - traversalCost - 1) * rooms.getValue(nextRoom).flowRate)
                )
            } ?: totalFlow

    companion object {
        fun fromInput(input: List<String>) =
            ProboscideaVolcanium(input.map { ValveRoom.fromInput(it) })
    }
}

data class State(
    val minute: Int,
    val room: String,
    val openValves: Set<String>,
    val pressureReleasedPerMinute: Int,
    val totalPressureReleased: Int = 0
)

data class ValveRoom(
    val name: String,
    val flowRate: Int,
    val neighbors: List<String>
) {
    companion object {
        fun fromInput(input: String): ValveRoom {
            val tokens = input.split('=', ';', ',', ' ')
            val name = tokens[1]
            val flowRate = tokens[5].toInt()
            val neighbors = tokens.drop(11).filter { it.isNotEmpty() }
            return ValveRoom(name, flowRate, neighbors)
        }
    }
}