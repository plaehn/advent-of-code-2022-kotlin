package org.plaehn.adventofcode.day15

import org.plaehn.adventofcode.common.Coord
import kotlin.math.absoluteValue


class BeaconExclusionZone(private val sensors: List<Sensor>) {

    fun countPositionsWhereBeaconCannotBeIn(row: Int): Int {
        val excludedPositions = sensors
            .map { sensor -> sensor.coordsCoveredIn(row) }
            .flatten()
            .toSet()
        val existingBeaconsOnRow = sensors
            .map { it.closestBeacon }
            .filter { it.y == row }
            .toSet()
        return (excludedPositions - existingBeaconsOnRow).size
    }

    fun computeTuningFrequency(caveSize: Int): Long {
        val cave = 0..caveSize
        val distressBeacon = sensors.firstNotNullOf { sensor ->
            sensor.computeHullAroundReach()
                .filter { it.x in cave && it.y in cave }
                .firstOrNull { candidate ->
                    sensors.none { it.canSense(candidate) }
                }
        }
        return distressBeacon.x * 4000000L + distressBeacon.y
    }

    companion object {
        fun fromInput(input: List<String>) =
            BeaconExclusionZone(sensors = input.map { Sensor.fromInput(it) })
    }
}

data class Sensor(
    val location: Coord,
    val closestBeacon: Coord
) {
    private val reach = location.manhattanDistanceTo(closestBeacon)

    fun coordsCoveredIn(row: Int): List<Coord> {
        val rowDist = reach - (location.y - row).absoluteValue
        return ((location.x - rowDist)..(location.x + rowDist)).map { Coord(it, row) }
    }

    fun computeHullAroundReach(): List<Coord> {
        val distance = 1 + reach
        val up = Coord(location.x, location.y - distance)
        val down = Coord(location.x, location.y + distance)
        val left = Coord(location.x - distance, location.y)
        val right = Coord(location.x + distance, location.y)
        return up.lineTo(right) + right.lineTo(down) + down.lineTo(left) + left.lineTo(up)
    }

    fun canSense(candidate: Coord) = location.manhattanDistanceTo(candidate) <= reach

    override fun toString() =
        "Sensor at ($location); beacon at ($closestBeacon); dist: " + location.manhattanDistanceTo(closestBeacon)

    companion object {
        fun fromInput(input: String): Sensor {
            val tokens = input.split('=', ',', ':', ' ')
            val sensor = Coord(x = tokens[3].toInt(), y = tokens[6].toInt())
            val beacon = Coord(x = tokens[13].toInt(), y = tokens[16].toInt())
            return Sensor(sensor, beacon)
        }
    }
}
