package org.plaehn.adventofcode.day06


object TuningTrouble {

    fun computeNumberOfCharsUntilEndOfFirstStartOfPacketMarker(signal: String) =
        computeNumberOfCharsUntilDistinctCharCountReached(signal, 4)

    fun computeNumberOfCharsUntilEndOfFirstStartOfMessageMarker(signal: String) =
        computeNumberOfCharsUntilDistinctCharCountReached(signal, 14)

    private fun computeNumberOfCharsUntilDistinctCharCountReached(signal: String, numberOfDistinctChars: Int) =
        signal
            .windowed(numberOfDistinctChars)
            .takeWhile { it.toSet().size < numberOfDistinctChars }
            .size + numberOfDistinctChars
}