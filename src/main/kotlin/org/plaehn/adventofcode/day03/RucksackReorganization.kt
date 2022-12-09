package org.plaehn.adventofcode.day03


object RucksackReorganization {

    fun computeSumOfPrioritiesOfItemsToBeRearranged(rucksacks: List<String>) =
        rucksacks
            .map { it.toCompartments() }
            .map { it.findCommonItem() }
            .map { it.computePriority() }
            .sumOf { it }

    private fun String.toCompartments(): Pair<Set<Char>, Set<Char>> {
        val middle = length / 2
        val lhs = substring(0, middle)
        val rhs = substring(middle)

        return lhs.toSet() to rhs.toSet()
    }

    private fun Pair<Set<Char>, Set<Char>>.findCommonItem() =
        first.intersect(second).first()

    private fun Char.computePriority() = if (isUpperCase()) this - 'A' + 27 else this - 'a' + 1
}