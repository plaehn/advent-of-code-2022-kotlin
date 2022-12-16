package org.plaehn.adventofcode.day10

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class CathodeRayTubeTest {

    @Test
    fun `Compute sum of signal strength for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val cathodeRayTube = CathodeRayTube.fromInput(lines)
        val sum = cathodeRayTube.computeSumOfSignalStrength()

        assertThat(sum).isEqualTo(0)
    }

    @Test
    fun `Compute sum of signal strength for large test input`() {
        val lines = this::class.java.readLines("test_large_input.txt")

        val cathodeRayTube = CathodeRayTube.fromInput(lines)
        val sum = cathodeRayTube.computeSumOfSignalStrength()

        assertThat(sum).isEqualTo(13140)
    }

    @Test
    fun `Compute sum of signal strength for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val cathodeRayTube = CathodeRayTube.fromInput(lines)
        val sum = cathodeRayTube.computeSumOfSignalStrength()

        assertThat(sum).isEqualTo(14360)
    }
}