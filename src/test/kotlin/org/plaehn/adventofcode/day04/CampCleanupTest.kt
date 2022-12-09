package org.plaehn.adventofcode.day04

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class CampCleanupTest {

    @Test
    fun `Count pairs where one range fully contains the other for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val campCleanup = CampCleanup.fromInput(lines)
        val count = campCleanup.countPairsWhereOneRangeFullyContainsTheOther()

        assertThat(count).isEqualTo(2)
    }

    @Test
    fun `Count pairs where one range fully contains the other for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val campCleanup = CampCleanup.fromInput(lines)
        val count = campCleanup.countPairsWhereOneRangeFullyContainsTheOther()

        assertThat(count).isEqualTo(494)
    }
}