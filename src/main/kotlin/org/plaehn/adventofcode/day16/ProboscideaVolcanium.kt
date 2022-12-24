package org.plaehn.adventofcode.day16

import java.lang.Integer.max


class ProboscideaVolcanium(rooms: List<Room>) {

    private val roomByValve: Map<String, Room> = rooms.associateBy { it.valve }

    fun computeMaximumPressureRelease() =
        computeMaximumPressureRelease(
            current = State(minute = 0, room = "AA", openValves = emptySet(), pressureReleasedPerMinute = 0),
            seen = mutableMapOf()
        )

    private fun computeMaximumPressureRelease(current: State, seen: MutableMap<State, Int>): Int {
        if (current.minute == 30) return current.totalPressureReleased
        if (current in seen) return seen[current]!!

        val openValveFlowRate = roomByValve[current.room]!!.flowRate
        val openValveMax = if (openValveFlowRate > 0 && current.room !in current.openValves) {
            computeMaximumPressureRelease(
                current = State(
                    minute = current.minute + 1,
                    room = current.room,
                    openValves = current.openValves + current.room,
                    pressureReleasedPerMinute = current.pressureReleasedPerMinute + openValveFlowRate,
                    totalPressureReleased = current.totalPressureReleased + current.pressureReleasedPerMinute
                ),
                seen
            )
        } else {
            0
        }

        val neighborMax = roomByValve[current.room]!!.neighbors.maxOf { neighbor ->
            computeMaximumPressureRelease(
                current = State(
                    minute = current.minute + 1,
                    room = neighbor,
                    openValves = current.openValves,
                    pressureReleasedPerMinute = current.pressureReleasedPerMinute,
                    totalPressureReleased = current.totalPressureReleased + current.pressureReleasedPerMinute
                ),
                seen
            )
        }
        val maximumPressureReleased = max(openValveMax, neighborMax)
        seen[current] = maximumPressureReleased

        return maximumPressureReleased
    }

    companion object {
        fun fromInput(input: List<String>) =
            ProboscideaVolcanium(input.map { Room.fromInput(it) })
    }
}

data class State(
    val minute: Int,
    val room: String,
    val openValves: Set<String>,
    val pressureReleasedPerMinute: Int,
    val totalPressureReleased: Int = 0
)

data class Room(
    val valve: String,
    val flowRate: Int,
    val neighbors: List<String>
) {
    companion object {
        fun fromInput(input: String): Room {
            val tokens = input.split('=', ';', ',', ' ')
            val valve = tokens[1]
            val flowRate = tokens[5].toInt()
            val neighbors = tokens.drop(11).filter { it.isNotEmpty() }
            return Room(valve, flowRate, neighbors)
        }
    }
}