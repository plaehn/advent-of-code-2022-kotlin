package org.plaehn.adventofcode.day12

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class HillClimbingTest {

    @Test
    fun `Compute fewest steps from current position for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val hillClimbing = HillClimbing.fromInput(lines)
        val steps = hillClimbing.computeFewestStepsToTopFromCurrentPosition()

        assertThat(steps).isEqualTo(31)
    }

    @Test
    fun `Compute fewest steps from current position for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val hillClimbing = HillClimbing.fromInput(lines)
        val steps = hillClimbing.computeFewestStepsToTopFromCurrentPosition()

        assertThat(steps).isEqualTo(437)
    }

    @Test
    fun `Compute fewest steps from any position at lowest height for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val hillClimbing = HillClimbing.fromInput(lines)
        val steps = hillClimbing.computeFewestStepsToTopFromAnyPositionAtLowestHeight()

        assertThat(steps).isEqualTo(29)
    }

    @Test
    fun `Compute fewest steps any position at lowest height for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val hillClimbing = HillClimbing.fromInput(lines)
        val steps = hillClimbing.computeFewestStepsToTopFromAnyPositionAtLowestHeight()

        assertThat(steps).isEqualTo(430)
    }
}