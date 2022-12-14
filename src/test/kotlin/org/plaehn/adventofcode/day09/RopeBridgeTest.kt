package org.plaehn.adventofcode.day09

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class RopeBridgeTest {

    @Test
    fun `Count positions tail touched for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val ropeBridge = RopeBridge.fromInput(lines)
        val count = ropeBridge.countPositionsTailTouched()

        assertThat(count).isEqualTo(13)
    }

    @Test
    fun `Count positions tail touched for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val ropeBridge = RopeBridge.fromInput(lines)
        val count = ropeBridge.countPositionsTailTouched()

        assertThat(count).isEqualTo(6236)
    }
}