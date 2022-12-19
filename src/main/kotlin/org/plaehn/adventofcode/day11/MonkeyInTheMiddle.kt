package org.plaehn.adventofcode.day11

import org.plaehn.adventofcode.common.groupByBlankLines


class MonkeyInTheMiddle(private val monkeys: List<Monkey>) {

    fun computeMonkeyBusinessLevel(): Int {
        println(monkeys)
        println(monkeys[1].operation(3).toString() + "== 9?")
        println(monkeys[2].operation(4).toString() + "== 16?")
        println(monkeys[0].test(22).toString() + "== false?")
        println(monkeys[0].test(23).toString() + "== true?")
        println(monkeys[0].test(24).toString() + "== false?")
        TODO()
    }

    companion object {
        fun fromInput(input: String) =
            MonkeyInTheMiddle(input.groupByBlankLines().map { Monkey.fromInput(it) })
    }
}

data class Monkey(
    val number: Int,
    val worryLevels: List<Int>,
    val operation: (Int) -> Int,
    val test: (Int) -> Boolean,
    val testTrueMonkey: Int,
    val testFalseMonkey: Int
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

        private fun String.toWorryLevels() = substringAfter(':').split(',').map { it.trim().toInt() }

        private fun String.toOperation(): (Int) -> Int {
            val (lhs, operand, rhs) = substringAfter("Operation: new = ").split(' ')
            return when (operand) {
                "*" -> toOperation(Int::times, lhs, rhs)
                "+" -> toOperation(Int::plus, lhs, rhs)
                else -> throw IllegalArgumentException("Unhandled operand: $operand")
            }
        }

        private fun toOperation(
            operator: (Int, Int) -> Int,
            lhs: String,
            rhs: String
        ): (Int) -> Int =
            if (lhs == "old" && rhs == "old") {
                { old -> operator(old, old) }
            } else if (lhs == "old") {
                { old -> operator(old, rhs.toInt()) }
            } else if (rhs == "old") {
                { old -> operator(old, lhs.toInt()) }
            } else {
                { operator(lhs.toInt(), rhs.toInt()) }
            }

        private fun String.toTest(): (Int) -> Boolean {
            val divisor = substringAfter("divisible by ").toInt()
            return { it % divisor == 0 }
        }

        private fun String.toThrowToMonkey() = substringAfter("throw to monkey ").toInt()
    }
}
