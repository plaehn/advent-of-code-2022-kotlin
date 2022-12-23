package org.plaehn.adventofcode.day15

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class BeaconExclusionZoneTest {

    @Test
    fun `Count positions where beacon cannot be for test input`() {
        val input = this::class.java.readLines("test_input.txt")

        val beaconExclusionZone = BeaconExclusionZone.fromInput(input)
        val count = beaconExclusionZone.countPositionsWhereBeaconCannotBeIn(row = 10)

        assertThat(count).isEqualTo(26)
    }

    @Test
    fun `Count positions where beacon cannot be for puzzle input`() {
        val input = this::class.java.readLines("puzzle_input.txt")

        val beaconExclusionZone = BeaconExclusionZone.fromInput(input)
        val count = beaconExclusionZone.countPositionsWhereBeaconCannotBeIn(row = 2000000)

        assertThat(count).isEqualTo(4582667)
    }
}