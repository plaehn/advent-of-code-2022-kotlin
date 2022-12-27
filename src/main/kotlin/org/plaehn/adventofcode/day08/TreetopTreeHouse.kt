package org.plaehn.adventofcode.day08

import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Coord.Companion.DOWN
import org.plaehn.adventofcode.common.Coord.Companion.LEFT
import org.plaehn.adventofcode.common.Coord.Companion.RIGHT
import org.plaehn.adventofcode.common.Coord.Companion.UP
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.product


class TreetopTreeHouse(private val grid: Matrix<Int>) {

    fun computeScenicScore() =
        grid.toMap().keys
            .maxOfOrNull { it.computeScenicScore(grid[it]) }!!

    private fun Coord.computeScenicScore(height: Int) =
        enumerateSightLines()
            .map { sightline -> sightline.countVisibleTrees(height) }
            .product()

    private fun Coord.enumerateSightLines(): List<List<Int>> =
        listOf(LEFT, RIGHT, DOWN, UP)
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

    private fun List<Int>.countVisibleTrees(height: Int): Int {
        var visibleCount = takeWhile { it < height }.size
        if (visibleCount < size) visibleCount += 1
        return visibleCount
    }

    fun countVisibleTrees() =
        grid.toMap().keys
            .filter { tree -> tree.isVisible() }
            .size

    private fun Coord.isVisible() =
        enumerateSightLines().any { sightline -> sightline.allLessTallThan(grid[this]) }

    private fun List<Int>.allLessTallThan(height: Int) = isEmpty() || all { it < height }

    companion object {
        fun fromInput(input: List<String>) =
            TreetopTreeHouse(Matrix.fromRows(input.map { line -> line.toList().map { it.digitToInt() } }, 0))
    }
}
