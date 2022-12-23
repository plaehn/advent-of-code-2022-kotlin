package org.plaehn.adventofcode.day15

import org.plaehn.adventofcode.common.Coord
import kotlin.math.absoluteValue


class BeaconExclusionZone(private val pairs: List<SensorBeaconPair>) {

    fun countPositionsWhereBeaconCannotBeIn(row: Int): Int {
        val excludedPositions = pairs
            .map { pair -> pair.coordsCoveredIn(row) }
            .flatten()
            .toSet()
        val existingBeaconsOnRow = pairs
            .map { it.beacon }
            .filter { it.y == row }
            .toSet()
        return (excludedPositions - existingBeaconsOnRow).size
    }

    companion object {
        fun fromInput(input: List<String>) =
            BeaconExclusionZone(pairs = input.map { SensorBeaconPair.fromInput(it) })
    }
}

data class SensorBeaconPair(
    val sensor: Coord,
    val beacon: Coord
) {

    fun coordsCoveredIn(row: Int): List<Coord> {
        val rowDist = sensor.manhattanDistanceTo(beacon) - (sensor.y - row).absoluteValue
        return ((sensor.x - rowDist)..(sensor.x + rowDist)).map { Coord(it, row) }
    }

    override fun toString() =
        "Sensor at ($sensor); beacon at ($beacon); dist: " + sensor.manhattanDistanceTo(beacon)


    companion object {
        fun fromInput(input: String): SensorBeaconPair {
            val tokens = input.split('=', ',', ':', ' ')
            val sensor = Coord(x = tokens[3].toInt(), y = tokens[6].toInt())
            val beacon = Coord(x = tokens[13].toInt(), y = tokens[16].toInt())
            return SensorBeaconPair(sensor, beacon)
        }
    }
}
