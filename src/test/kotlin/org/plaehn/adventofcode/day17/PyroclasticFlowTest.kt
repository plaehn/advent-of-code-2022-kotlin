package org.plaehn.adventofcode.day17

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class PyroclasticFlowTest {

    @Test
    fun `Solve part one for test input`() {
        val jetPattern = this::class.java.slurp("test_input.txt")
        val shapes = this::class.java.slurp("rock_shapes.txt")

        val flow = PyroclasticFlow.fromInput(jetPattern, shapes)
        val height = flow.solvePart1()

        assertThat(height).isEqualTo(3068)
    }

    @Test
    fun `Solve part one for puzzle input`() {
        val jetPattern = this::class.java.slurp("puzzle_input.txt")
        val shapes = this::class.java.slurp("rock_shapes.txt")

        val flow = PyroclasticFlow.fromInput(jetPattern, shapes)
        val height = flow.solvePart1()

        assertThat(height).isEqualTo(3147)
    }

    @Test
    fun `Solve part two for test input`() {
        val jetPattern = this::class.java.slurp("test_input.txt")
        val shapes = this::class.java.slurp("rock_shapes.txt")

        val flow = PyroclasticFlow.fromInput(jetPattern, shapes)
        val height = flow.solvePart2()

        assertThat(height).isEqualTo(1514285714288)
    }

    @Test
    fun `Solve part two for puzzle input`() {
        val jetPattern = this::class.java.slurp("puzzle_input.txt")
        val shapes = this::class.java.slurp("rock_shapes.txt")

        val flow = PyroclasticFlow.fromInput(jetPattern, shapes)
        val height = flow.solvePart2()

        assertThat(height).isEqualTo(1532163742758)
    }
}