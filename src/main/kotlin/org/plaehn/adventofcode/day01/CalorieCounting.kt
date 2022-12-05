package org.plaehn.adventofcode.day01

object CalorieCounting {

    fun computeMaxTotalCalories(caloriesPerElf: List<List<Int>>) =
        caloriesPerElf.maxOf { it.sum().toLong() }
}
