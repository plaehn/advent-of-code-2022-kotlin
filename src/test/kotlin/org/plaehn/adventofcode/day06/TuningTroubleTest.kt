package org.plaehn.adventofcode.day06

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class TuningTroubleTest {

    @Test
    fun `Compute number of chars until end of first start of packet marker for test inputs`() {
        var signal = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfPacketMarker(signal)).isEqualTo(7)

        signal = "bvwbjplbgvbhsrlpgdmjqwftvncz"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfPacketMarker(signal)).isEqualTo(5)

        signal = "nppdvjthqldpwncqszvftbrmjlhg"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfPacketMarker(signal)).isEqualTo(6)

        signal = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfPacketMarker(signal)).isEqualTo(10)

        signal = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfPacketMarker(signal)).isEqualTo(11)
    }

    @Test
    fun `Compute number of chars until end of first start of packet marker for puzzle inputs`() {
        val signal = this::class.java.getResource("puzzle_input.txt")?.readText()!!

        val numOfChars = TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfPacketMarker(signal)

        assertThat(numOfChars).isEqualTo(1235)
    }

    @Test
    fun `Compute number of chars until end of first start of message marker for test inputs`() {
        var signal = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfMessageMarker(signal)).isEqualTo(19)

        signal = "bvwbjplbgvbhsrlpgdmjqwftvncz"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfMessageMarker(signal)).isEqualTo(23)

        signal = "nppdvjthqldpwncqszvftbrmjlhg"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfMessageMarker(signal)).isEqualTo(23)

        signal = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfMessageMarker(signal)).isEqualTo(29)

        signal = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
        assertThat(TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfMessageMarker(signal)).isEqualTo(26)
    }

    @Test
    fun `Compute number of chars until end of first start of message marker for puzzle inputs`() {
        val signal = this::class.java.getResource("puzzle_input.txt")?.readText()!!

        val numOfChars = TuningTrouble.computeNumberOfCharsUntilEndOfFirstStartOfMessageMarker(signal)

        assertThat(numOfChars).isEqualTo(3051)
    }
}