package org.plaehn.adventofcode.day17

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class PyroclasticFlowTest {

    @Test
    fun `Compute tower height for test input`() {
        val jetPattern = this::class.java.slurp("test_input.txt")
        val shapes = this::class.java.slurp("rock_shapes.txt")

        val flow = PyroclasticFlow.fromInput(jetPattern, shapes)
        val height = flow.computeTowerHeight(numberOfRocks = 2022)

        assertThat(height).isEqualTo(3068)
    }

    @Test
    fun `Compute tower height for puzzle input`() {
        val jetPattern = this::class.java.slurp("puzzle_input.txt")
        val shapes = this::class.java.slurp("rock_shapes.txt")

        val flow = PyroclasticFlow.fromInput(jetPattern, shapes)
        val height = flow.computeTowerHeight(numberOfRocks = 2022)

        assertThat(height).isEqualTo(3147)
    }
}