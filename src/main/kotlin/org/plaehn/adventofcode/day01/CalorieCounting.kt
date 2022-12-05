package org.plaehn.adventofcode.day01

object CalorieCounting {

    fun computeMaxTotalCalories(
        caloriesPerElf: List<List<Int>>,
        useTopNElves: Int = 1
    ) =
        caloriesPerElf
            .map { it.sum().toLong() }
            .sortedDescending()
            .take(useTopNElves)
            .sum()
}
