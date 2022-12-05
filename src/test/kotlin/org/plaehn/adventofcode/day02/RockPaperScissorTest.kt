package org.plaehn.adventofcode.day02

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.plaehn.adventofcode.common.readLines

internal class RockPaperScissorTest {

    @Test
    fun `Compute total score for test input`() {
        val lines = readInput("test_input.txt")

        val rockPaperScissors = RockPaperScissors.fromInputLines(lines)
        val totalScore = rockPaperScissors.computeTotalScore()

        assertThat(totalScore).isEqualTo(15)
    }

    @Test
    fun `Compute total score for puzzle input`() {
        val lines = readInput("puzzle_input.txt")

        val rockPaperScissors = RockPaperScissors.fromInputLines(lines)
        val totalScore = rockPaperScissors.computeTotalScore()

        assertThat(totalScore).isEqualTo(13565)
    }

    private fun readInput(resource: String): List<String> =
        this::class.java.readLines(resource)
}
