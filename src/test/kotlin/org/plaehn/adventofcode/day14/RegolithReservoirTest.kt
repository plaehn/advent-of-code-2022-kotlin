package org.plaehn.adventofcode.day14

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class RegolithReservoirTest {

    @Test
    fun `Count unit of sands for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val reservoir = RegolithReservoir.fromInput(input)
        val count = reservoir.countUnitOfSands()

        assertThat(count).isEqualTo(24)
    }

    @Test
    fun `Count unit of sands for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val reservoir = RegolithReservoir.fromInput(input)
        val count = reservoir.countUnitOfSands()

        assertThat(count).isEqualTo(1513)
    }
}