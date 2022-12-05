package org.plaehn.adventofcode.day01

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.common.slurp
import org.plaehn.adventofcode.day01.CalorieCounting.computeMaxTotalCalories

internal class CalorieCountingTest {

    @Test
    fun `Compute max total calories for test input`() {
        val caloriesPerElf = readInput("test_input.txt")

        val maxTotalCalories = computeMaxTotalCalories(caloriesPerElf)

        assertThat(maxTotalCalories).isEqualTo(24000)
    }

    @Test
    fun `Compute max total calories for puzzle input`() {
        val caloriesPerElf = readInput("puzzle_input.txt")

        val maxTotalCalories = computeMaxTotalCalories(caloriesPerElf)

        assertThat(maxTotalCalories).isEqualTo(67622)
    }

    private fun readInput(resource: String): List<List<Int>> =
        this::class.java
            .slurp(resource)
            .groupByBlankLines()
            .map { it.lines().map { line -> line.toInt() } }
}
