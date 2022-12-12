package org.plaehn.adventofcode.day07

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class NoSpaceLeftOnDeviceTest {

    @Test
    fun `Compute sum of total sizes of directories for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val noSpaceLeftOnDevice = NoSpaceLeftOnDevice.fromInput(lines)
        val totalSum = noSpaceLeftOnDevice.computeSumOfTotalSizesOfDirectories(limit = 100000)

        assertThat(totalSum).isEqualTo(95437)
    }

    @Test
    fun `Compute sum of total sizes of directories for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val noSpaceLeftOnDevice = NoSpaceLeftOnDevice.fromInput(lines)
        val totalSum = noSpaceLeftOnDevice.computeSumOfTotalSizesOfDirectories(limit = 100000)

        assertThat(totalSum).isEqualTo(1)
    }
}