package org.plaehn.adventofcode.day05

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SupplyStacksTest {

    @Test
    fun `Compute top crates after rearrangement with CrateMover 9000 for test input`() {
        val input = this::class.java.getResource("test_input.txt")?.readText()!!

        val supplyStacks = SupplyStacks.fromInput(input)

        val topCrates = supplyStacks.rearrangeAndReturnTopCrates(cratesToMoveMapper = { it.reversed() })

        assertThat(topCrates).isEqualTo("CMZ")
    }

    @Test
    fun `Compute top crates after rearrangement with CrateMover 9000 for puzzle input`() {
        val input = this::class.java.getResource("puzzle_input.txt")?.readText()!!

        val supplyStacks = SupplyStacks.fromInput(input)

        val topCrates = supplyStacks.rearrangeAndReturnTopCrates(cratesToMoveMapper = { it.reversed() })

        assertThat(topCrates).isEqualTo("WSFTMRHPP")
    }

    @Test
    fun `Compute top crates after rearrangement with CrateMover 9001 for test input`() {
        val input = this::class.java.getResource("test_input.txt")?.readText()!!

        val supplyStacks = SupplyStacks.fromInput(input)

        val topCrates = supplyStacks.rearrangeAndReturnTopCrates()

        assertThat(topCrates).isEqualTo("MCD")
    }

    @Test
    fun `Compute top crates after rearrangement with CrateMover 9001 for puzzle input`() {
        val input = this::class.java.getResource("puzzle_input.txt")?.readText()!!

        val supplyStacks = SupplyStacks.fromInput(input)

        val topCrates = supplyStacks.rearrangeAndReturnTopCrates()

        assertThat(topCrates).isEqualTo("GSLCMFBRP")
    }

}