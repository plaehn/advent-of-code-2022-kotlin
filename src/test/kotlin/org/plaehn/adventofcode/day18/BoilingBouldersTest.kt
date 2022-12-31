package org.plaehn.adventofcode.day18

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class BoilingBouldersTest {


    @Test
    fun `Compute surface area for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val boilingBoulders = BoilingBoulders.fromInput(input)
        val area = boilingBoulders.computeSurfaceArea()

        assertThat(area).isEqualTo(64)
    }

    @Test
    fun `Compute surface area for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val boilingBoulders = BoilingBoulders.fromInput(input)
        val area = boilingBoulders.computeSurfaceArea()

        assertThat(area).isEqualTo(4536)
    }
}