package org.plaehn.adventofcode.day06


object TuningTrouble {

    fun computeNumberOfCharsUntilEndOfFirstPacketMarker(signal: String) =
        signal
            .windowed(4)
            .takeWhile { it.toSet().size < 4 }
            .size + 4
}