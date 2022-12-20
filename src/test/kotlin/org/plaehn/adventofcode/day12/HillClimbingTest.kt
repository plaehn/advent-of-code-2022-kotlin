package org.plaehn.adventofcode.day12

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class HillClimbingTest {

    @Test
    fun `Compute fewest steps for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val steps = HillClimbing.computeFewestStepToTop(input)

        assertThat(steps).isEqualTo(31)
    }

    @Test
    fun `Compute fewest steps for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val steps = HillClimbing.computeFewestStepToTop(input)

        assertThat(steps).isEqualTo(437)
    }
}