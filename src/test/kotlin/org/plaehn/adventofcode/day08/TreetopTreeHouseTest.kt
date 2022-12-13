package org.plaehn.adventofcode.day08

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class TreetopTreeHouseTest {

    @Test
    fun `Count visible trees for test input`() {
        val lines = this::class.java.readLines("test_input.txt")

        val treetopTreeHouse = TreetopTreeHouse.fromInput(lines)
        val count = treetopTreeHouse.countVisibleTrees()

        assertThat(count).isEqualTo(21)
    }

    @Test
    fun `Count visible trees for puzzle input`() {
        val lines = this::class.java.readLines("puzzle_input.txt")

        val treetopTreeHouse = TreetopTreeHouse.fromInput(lines)
        val count = treetopTreeHouse.countVisibleTrees()

        assertThat(count).isEqualTo(1796)
    }

}