package org.plaehn.adventofcode.day05

import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.common.tokenize
import org.plaehn.adventofcode.common.transpose


data class SupplyStacks(
    private val initialStacks: List<String>,
    private val rearrangementProcedure: List<Instruction>
) {

    fun rearrangeAndReturnTopCrates(): String =
        rearrangementProcedure
            .fold(initialStacks) { stacks, instruction -> stacks.rearrange(instruction) }
            .map { it.firstOrNull() ?: "" }
            .joinToString(separator = "")

    private fun List<String>.rearrange(instruction: Instruction) =
        mapIndexed { idx, stack ->
            when (idx + 1) {
                instruction.from -> stack.drop(instruction.numberOfCratesToMove)
                instruction.to -> this[instruction.from - 1].take(instruction.numberOfCratesToMove).reversed() + stack
                else -> stack
            }
        }

    companion object {
        fun fromInput(input: String): SupplyStacks {
            val (initialStacksDrawing, instructions) = input.groupByBlankLines()
            return SupplyStacks(
                initialStacks = initialStacksDrawing.toStacks(),
                rearrangementProcedure = instructions.lines().map { Instruction.fromInput(it) })
        }

        private fun String.toStacks(): List<String> {
            val stackSlicesTopDown = lines()
                .dropLast(1)
                .map { line -> line.chunked(4) { it[1] } }

            return stackSlicesTopDown
                .transpose(defaultIfMissing = ' ')
                .map { it.joinToString(separator = "").trim() }
        }
    }
}

data class Instruction(
    val numberOfCratesToMove: Int,
    val from: Int,
    val to: Int
) {

    companion object {
        fun fromInput(input: String): Instruction {
            val words = input.tokenize()
            return Instruction(
                numberOfCratesToMove = words[1].toInt(),
                from = words[3].toInt(),
                to = words[5].toInt()
            )
        }
    }
}
