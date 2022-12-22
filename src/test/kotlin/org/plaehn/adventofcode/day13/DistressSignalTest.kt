package org.plaehn.adventofcode.day13

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class DistressSignalTest {

    @Test
    fun `Solve part 1 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val distressSignal = DistressSignal.fromInput(input)
        val solution = distressSignal.solvePart1()

        assertThat(solution).isEqualTo(13)
    }

    @Test
    fun `Solve part 1 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val distressSignal = DistressSignal.fromInput(input)
        val solution = distressSignal.solvePart1()

        assertThat(solution).isEqualTo(5882)
    }

    @Test
    fun `Solve part 2 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val distressSignal = DistressSignal.fromInput(input)
        val solution = distressSignal.solvePart2()

        assertThat(solution).isEqualTo(140)
    }

    @Test
    fun `Solve part 2 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val distressSignal = DistressSignal.fromInput(input)
        val solution = distressSignal.solvePart2()

        assertThat(solution).isEqualTo(24948)
    }
}