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

    fun computeTuningFrequency(caveSize: Int): Long {
        val cave = 0..caveSize
        val distressBeacon = pairs.firstNotNullOf { pair ->
            pair.computeHullAroundSensorReach()
                .filter { it.x in cave && it.y in cave }
                .firstOrNull { candidate ->
                    pairs.none { pair -> pair.canSense(candidate) }
                }
        }
        return distressBeacon.x * 4000000L + distressBeacon.y
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
    private val reach = sensor.manhattanDistanceTo(beacon)

    fun coordsCoveredIn(row: Int): List<Coord> {
        val rowDist = reach - (sensor.y - row).absoluteValue
        return ((sensor.x - rowDist)..(sensor.x + rowDist)).map { Coord(it, row) }
    }

    fun computeHullAroundSensorReach(): List<Coord> {
        val distance = 1 + reach
        val up = Coord(sensor.x, sensor.y - distance)
        val down = Coord(sensor.x, sensor.y + distance)
        val left = Coord(sensor.x - distance, sensor.y)
        val right = Coord(sensor.x + distance, sensor.y)
        return up.lineTo(right) + right.lineTo(down) + down.lineTo(left) + left.lineTo(up)
    }

    fun canSense(candidate: Coord) = sensor.manhattanDistanceTo(candidate) <= reach

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
