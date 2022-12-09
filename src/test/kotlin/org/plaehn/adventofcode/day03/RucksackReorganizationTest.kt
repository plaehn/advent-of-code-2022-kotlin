package org.plaehn.adventofcode.day03

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class RucksackReorganizationTest {

    @Test
    fun `Compute total score for test input`() {
        val rucksacks = this::class.java.readLines("test_input.txt")

        val sum = RucksackReorganization.computeSumOfPrioritiesOfItemsToBeRearranged(rucksacks)

        assertThat(sum).isEqualTo(157)
    }

    @Test
    fun `Compute total score for puzzle input`() {
        val rucksacks = this::class.java.readLines("puzzle_input.txt")

        val sum = RucksackReorganization.computeSumOfPrioritiesOfItemsToBeRearranged(rucksacks)

        assertThat(sum).isEqualTo(8252)
    }
}