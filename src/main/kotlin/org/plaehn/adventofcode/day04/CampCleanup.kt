package org.plaehn.adventofcode.day04


class CampCleanup(private val assignmentPairs: List<Pair<IntRange, IntRange>>) {

    fun countPairsWhereOneRangeFullyContainsTheOther() =
        assignmentPairs.count { it.first.contains(it.second) || it.second.contains(it.first) }

    private fun IntRange.contains(other: IntRange) = (other - this).isEmpty()

    fun countPairsWhereRangesOverlap() =
        assignmentPairs.count { it.first.intersect(it.second).isNotEmpty() }

    companion object {

        fun fromInput(lines: List<String>) = CampCleanup(lines.toAssignmentPairs())

        private fun List<String>.toAssignmentPairs() =
            map { line ->
                val (lhs, rhs) = line.split(",")
                lhs.toIntRange() to rhs.toIntRange()
            }

        private fun String.toIntRange(): IntRange {
            val (from, to) = split("-")
            return IntRange(from.toInt(), to.toInt())
        }
    }
}
