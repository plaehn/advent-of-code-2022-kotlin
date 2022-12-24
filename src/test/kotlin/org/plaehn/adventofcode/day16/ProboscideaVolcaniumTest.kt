package org.plaehn.adventofcode.day16

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class ProboscideaVolcaniumTest {

    @Test
    fun `Compute maximum pressure release for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val volcano = ProboscideaVolcanium.fromInput(input)
        val maxRelease = volcano.computeMaximumPressureRelease()

        assertThat(maxRelease).isEqualTo(1651)
    }

    @Test
    fun `Compute maximum pressure release for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val volcano = ProboscideaVolcanium.fromInput(input)
        val maxRelease = volcano.computeMaximumPressureRelease()

        assertThat(maxRelease).isEqualTo(1)
    }

}