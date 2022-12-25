package org.plaehn.adventofcode.day16

import com.github.shiguruikai.combinatoricskt.permutations


class ProboscideaVolcanium(private val rooms: List<ValveRoom>) {

    private val roomsByName: Map<String, ValveRoom> = rooms.associateBy { it.name }

    private val cheapestPathCosts: Map<String, Map<String, Int>> = calculateShortestPaths()

    fun computeMaximumPressureRelease() = searchPaths("AA", 30)

    private fun searchPaths(
        location: String,
        timeAllowed: Int,
        seen: Set<String> = emptySet(),
        timeTaken: Int = 0,
        totalFlow: Int = 0
    ): Int = cheapestPathCosts
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
                totalFlow + ((timeAllowed - timeTaken - traversalCost - 1) * roomsByName.getValue(nextRoom).flowRate)
            )
        } ?: totalFlow


    private fun calculateShortestPaths(): Map<String, Map<String, Int>> {
        val shortestPaths: MutableMap<String, MutableMap<String, Int>> = roomsByName.values.associate {
            it.name to it.neighbors.associateWith { 1 }.toMutableMap()
        }.toMutableMap()

        shortestPaths.keys.permutations(3).forEach { (waypoint, from, to) ->
            shortestPaths[from, to] = minOf(
                shortestPaths[from, to], // Existing Path
                shortestPaths[from, waypoint] + shortestPaths[waypoint, to] // New Path
            )
        }
        val zeroFlowRooms = roomsByName.values.filter { it.flowRate == 0 || it.name == "AA" }.map { it.name }.toSet()
        shortestPaths.values.forEach { it.keys.removeAll(zeroFlowRooms) }
        val canGetToFromAA: Set<String> = shortestPaths.getValue("AA").keys
        return shortestPaths.filter { it.key in canGetToFromAA || it.key == "AA" }
    }

    private operator fun Map<String, MutableMap<String, Int>>.set(key1: String, key2: String, value: Int) {
        getValue(key1)[key2] = value
    }

    private operator fun Map<String, Map<String, Int>>.get(
        key1: String, key2: String, defaultValue: Int = 31000 // XXX
    ): Int =
        get(key1)?.get(key2) ?: defaultValue

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