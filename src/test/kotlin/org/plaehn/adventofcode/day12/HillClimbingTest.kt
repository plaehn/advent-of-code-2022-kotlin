package org.plaehn.adventofcode.day12

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class HillClimbingTest {

    @Test
    fun `Compute fewest steps for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val hillClimbing = HillClimbing.fromInput(lines)
        val steps = hillClimbing.computeFewestStepsToTopFromCurrentPosition()

        assertThat(steps).isEqualTo(31)
    }

    @Test
    fun `Compute fewest steps for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val hillClimbing = HillClimbing.fromInput(lines)
        val steps = hillClimbing.computeFewestStepsToTopFromCurrentPosition()

        assertThat(steps).isEqualTo(437)
    }
}