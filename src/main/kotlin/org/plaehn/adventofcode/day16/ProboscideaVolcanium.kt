package org.plaehn.adventofcode.day16


class ProboscideaVolcanium(private val rooms: List<Room>) {

    fun computeMaximumPressureRelease(): Int {
        println(rooms.joinToString("\n"))
        TODO()
    }

    companion object {
        fun fromInput(input: List<String>) =
            ProboscideaVolcanium(input.map { Room.fromInput(it) })
    }
}

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