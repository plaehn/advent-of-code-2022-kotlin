package org.plaehn.adventofcode.day11

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class MonkeyInTheMiddleTest {

    @Test
    fun `Compute level of monkey business for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val monkeyInTheMiddle = MonkeyInTheMiddle.fromInput(input)
        val level = monkeyInTheMiddle.computeMonkeyBusinessLevel()

        assertThat(level).isEqualTo(10605)
    }

    @Test
    fun `Compute level of monkey business for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val monkeyInTheMiddle = MonkeyInTheMiddle.fromInput(input)
        val level = monkeyInTheMiddle.computeMonkeyBusinessLevel()

        assertThat(level).isEqualTo(0)
    }
}