package org.plaehn.adventofcode.day11

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.slurp

internal class MonkeyInTheMiddleTest {

    @Test
    fun `Compute level of monkey business for part 1 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val monkeyInTheMiddle = MonkeyInTheMiddle.fromInput(input)
        val level = monkeyInTheMiddle.part1()

        assertThat(level).isEqualTo(10605)
    }

    @Test
    fun `Compute level of monkey business for part 1 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val monkeyInTheMiddle = MonkeyInTheMiddle.fromInput(input)
        val level = monkeyInTheMiddle.part1()

        assertThat(level).isEqualTo(118674)
    }

    @Test
    fun `Compute level of monkey business for part 2 for test input`() {
        val input = this::class.java.slurp("test_input.txt")

        val monkeyInTheMiddle = MonkeyInTheMiddle.fromInput(input)
        val level = monkeyInTheMiddle.part2()

        assertThat(level).isEqualTo(2713310158)
    }

    @Test
    fun `Compute level of monkey business for part 2 for puzzle input`() {
        val input = this::class.java.slurp("puzzle_input.txt")

        val monkeyInTheMiddle = MonkeyInTheMiddle.fromInput(input)
        val level = monkeyInTheMiddle.part2()

        assertThat(level).isEqualTo(32333418600)
    }
}