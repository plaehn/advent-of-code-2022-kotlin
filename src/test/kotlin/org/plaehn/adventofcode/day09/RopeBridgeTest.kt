package org.plaehn.adventofcode.day09

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class RopeBridgeTest {

    @Test
    fun `Count positions tail touched for 2 knots for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val ropeBridge = RopeBridge.fromInput(lines, 2)
        val count = ropeBridge.countPositionsTailTouched()

        assertThat(count).isEqualTo(13)
    }

    @Test
    fun `Count positions tail touched for 2 knots for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val ropeBridge = RopeBridge.fromInput(lines, 2)
        val count = ropeBridge.countPositionsTailTouched()

        assertThat(count).isEqualTo(6236)
    }

    @Test
    fun `Count positions tail touched for 10 knots for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val ropeBridge = RopeBridge.fromInput(lines, 10)
        val count = ropeBridge.countPositionsTailTouched()

        assertThat(count).isEqualTo(1)
    }

    @Test
    fun `Count positions tail touched for 10 knots for large test input`() {
        val lines = this::class.java.readLines("test_large_input.txt")

        val ropeBridge = RopeBridge.fromInput(lines, 10)
        val count = ropeBridge.countPositionsTailTouched()

        assertThat(count).isEqualTo(36)
    }

    @Test
    fun `Count positions tail touched for 10 knots for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val ropeBridge = RopeBridge.fromInput(lines, 10)
        val count = ropeBridge.countPositionsTailTouched()

        assertThat(count).isEqualTo(2449)
    }
}