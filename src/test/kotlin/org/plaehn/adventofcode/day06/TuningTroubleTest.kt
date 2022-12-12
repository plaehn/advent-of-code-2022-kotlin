package org.plaehn.adventofcode.day06

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class TuningTroubleTest {

    @Test
    fun `Compute number of chars until end of first packet marker for test inputs`() {
        var signal = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstPacketMarker(signal)).isEqualTo(7)

        signal = "bvwbjplbgvbhsrlpgdmjqwftvncz"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstPacketMarker(signal)).isEqualTo(5)

        signal = "nppdvjthqldpwncqszvftbrmjlhg"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstPacketMarker(signal)).isEqualTo(6)

        signal = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstPacketMarker(signal)).isEqualTo(10)

        signal = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstPacketMarker(signal)).isEqualTo(11)
    }

    @Test
    fun `Compute number of chars until end of first packet marker for puzzle inputs`() {
        val signal = this::class.java.getResource("puzzle_input.txt")?.readText()!!

        val numOfChars = TuningTrouble.computeNumberOfCharsUntilEndOfFirstPacketMarker(signal)

        assertThat(numOfChars).isEqualTo(1235)
    }
}