package org.plaehn.adventofcode.day11

import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.common.product


class MonkeyInTheMiddle(private val monkeys: MutableList<Monkey>) {

    fun computeMonkeyBusinessLevel(): Long {
        repeat(20) {
            playRound()
        }
        return monkeys
            .map { it.numberOfInspections }
            .sortedByDescending { it }
            .take(2)
            .product()
    }

    private fun playRound() {
        monkeys.forEach { monkey -> monkey.takeTurn() }
    }

    private fun Monkey.takeTurn() {
        numberOfInspections += worryLevels.size
        while (worryLevels.isNotEmpty()) {
            val worryLevel = operation(worryLevels.removeFirst()) / 3
            val monkeyToThrowTo = if (test(worryLevel)) testTrueMonkey else testFalseMonkey
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
    val test: (Long) -> Boolean,
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
                test = lines[3].toTest(),
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

        private fun String.toTest(): (Long) -> Boolean {
            val divisor = substringAfter("divisible by ").toLong()
            return { it % divisor == 0L }
        }

        private fun String.toThrowToMonkey() = substringAfter("throw to monkey ").toInt()
    }
}
