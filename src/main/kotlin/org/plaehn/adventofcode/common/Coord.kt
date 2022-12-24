package org.plaehn.adventofcode.common

import kotlin.math.absoluteValue
import kotlin.math.sign

data class Coord(val x: Int, val y: Int, val z: Int = 0) {

    operator fun plus(summand: Coord) = Coord(x + summand.x, y + summand.y, z + summand.z)

    operator fun minus(subtrahend: Coord) = Coord(x - subtrahend.x, y - subtrahend.y, z - subtrahend.z)

    operator fun times(factor: Coord): Coord = Coord(x * factor.x, y * factor.y, z * factor.z)

    fun isCenter() = x == 0 && y == 0 && z == 0

    override fun toString() = "$x,$y,$z"

    fun manhattanDistanceTo(other: Coord) =
        (x - other.x).absoluteValue + (y - other.y).absoluteValue + (z - other.z).absoluteValue

    fun lineTo(other: Coord): List<Coord> {
        val xDelta = (other.x - x).sign
        val yDelta = (other.y - y).sign
        val steps = maxOf((x - other.x).absoluteValue, (y - other.y).absoluteValue)
        return (1..steps).scan(this) { last, _ -> Coord(last.x + xDelta, last.y + yDelta) }
    }

    companion object {
        fun fromString(input: String) =
            input.split(",").map { it.toInt() }.run {
                Coord(this[0], this[1], this.getOrElse(2) { 0 })
            }

        fun fromList(input: List<Int>) =
            Coord(x = input[0], y = input[1], z = input[2])
    }
}