package org.plaehn.adventofcode.day11

import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.common.product


class MonkeyInTheMiddle(private val monkeys: MutableList<Monkey>) {

    fun part1() = computeMonkeyBusinessLevel(numberOfRounds = 20) { it / 3 }

    fun part2(): Long {
        // Cf. https://todd.ginsberg.com/post/advent-of-code/2022/day11/ for an explanation
        val testDivisorProduct = monkeys.map { it.testDivisor }.product()
        return computeMonkeyBusinessLevel(numberOfRounds = 10000) { it % testDivisorProduct }
    }

    private fun computeMonkeyBusinessLevel(numberOfRounds: Int, changeWorryLevel: (Long) -> Long): Long {
        repeat(numberOfRounds) {
            playRound(changeWorryLevel)
        }
        return monkeys
            .map { it.numberOfInspections }
            .sortedByDescending { it }
            .take(2)
            .product()
    }

    private fun playRound(changeWorryLevel: (Long) -> Long) {
        monkeys.forEach { monkey -> monkey.takeTurn(changeWorryLevel) }
    }

    private fun Monkey.takeTurn(changeWorryLevel: (Long) -> Long) {
        numberOfInspections += worryLevels.size
        while (worryLevels.isNotEmpty()) {
            val worryLevel = changeWorryLevel(operation(worryLevels.removeFirst()))
            val monkeyToThrowTo = if (worryLevel % testDivisor == 0L) testTrueMonkey else testFalseMonkey
            monkeys[monkeyToThrowTo].worryLevels.addLast(worryLevel)
        }
    }

    companion object {
        fun fromInput(input: String) =
            MonkeyInTheMiddle(input.groupByBlankLines().map { Monkey.fromInput(it) }.toMutableList())
    }
}

data class Monkey(
    val number: Int,
    val worryLevels: ArrayDeque<Long>,
    val operation: (Long) -> Long,
    val testDivisor: Long,
    val testTrueMonkey: Int,
    val testFalseMonkey: Int,
    var numberOfInspections: Long = 0
) {
    companion object {
        fun fromInput(input: String): Monkey {
            val lines = input.lines()
            require(lines.size == 6)

            return Monkey(
                number = lines[0].toNumber(),
                worryLevels = lines[1].toWorryLevels(),
                operation = lines[2].toOperation(),
                testDivisor = lines[3].toTestDivisor(),
                testTrueMonkey = lines[4].toThrowToMonkey(),
                testFalseMonkey = lines[5].toThrowToMonkey()
            )
        }

        private fun String.toNumber() = substringAfter(' ').trimEnd(':').toInt()

        private fun String.toWorryLevels() =
            ArrayDeque<Long>().apply { addAll(substringAfter(':').split(',').map { it.trim().toLong() }) }

        private fun String.toOperation(): (Long) -> Long {
            val (lhs, operand, rhs) = substringAfter("Operation: new = ").split(' ')
            return when (operand) {
                "*" -> toOperation(Long::times, lhs, rhs)
                "+" -> toOperation(Long::plus, lhs, rhs)
                else -> throw IllegalArgumentException("Unhandled operand: $operand")
            }
        }

        private fun toOperation(
            operator: (Long, Long) -> Long,
            lhs: String,
            rhs: String
        ): (Long) -> Long =
            if (lhs == "old" && rhs == "old") {
                { old -> operator(old, old) }
            } else if (lhs == "old") {
                { old -> operator(old, rhs.toLong()) }
            } else if (rhs == "old") {
                { old -> operator(old, lhs.toLong()) }
            } else {
                { operator(lhs.toLong(), rhs.toLong()) }
            }

        private fun String.toTestDivisor() = substringAfter("divisible by ").toLong()

        private fun String.toThrowToMonkey() = substringAfter("throw to monkey ").toInt()
    }
}
