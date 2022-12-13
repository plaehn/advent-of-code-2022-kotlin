package org.plaehn.adventofcode.day08

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix


class TreetopTreeHouse(private val grid: Matrix<Int>) {

    fun countVisibleTrees() =
        grid.toMap().keys
            .filter { tree -> tree.isVisible() }
            .size

    private fun Coord.isVisible() =
        enumerateSightLines().any { sightline -> sightline.allLessTallThan(grid[this]) }

    private fun Coord.enumerateSightLines(): List<List<Int>> =
        listOf(Coord(-1, 0), Coord(1, 0), Coord(0, -1), Coord(0, 1))
            .map { direction ->
                var current = this
                sequence {
                    do {
                        current += direction
                        if (!grid.isInsideBounds(current)) break
                        yield(current)
                    } while (true)
                }.map { grid[it] }.toList()
            }

    private fun List<Int>.allLessTallThan(height: Int) = isEmpty() || all { it < height }

    companion object {
        fun fromInput(input: List<String>) =
            TreetopTreeHouse(Matrix.fromRows(input.map { line -> line.toList().map { it.digitToInt() } }, 0))
    }
}
