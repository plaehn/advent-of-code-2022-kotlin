package org.plaehn.adventofcode.day10


class CathodeRayTube(private val instructions: List<Instruction>) {

    fun computeSumOfSignalStrength() =
        sequence {
            var x = 1
            var cycle = 0

            instructions.forEach { instruction ->
                repeat(instruction.cycles) {
                    ++cycle
                    if (cycle.isInterestingSignal()) {
                        yield(cycle * x)
                    }
                }
                x += instruction.amount
            }
        }.sum()

    private fun Int.isInterestingSignal() = this in listOf(20, 60, 100, 140, 180, 220)

    companion object {
        fun fromInput(input: List<String>): CathodeRayTube = CathodeRayTube(input.map { it.toInstruction() })

        private fun String.toInstruction() =
            if (startsWith("noop")) {
                Instruction(amount = 0, cycles = 1)
            } else if (startsWith("addx")) {
                val (_, amount) = split(' ')
                Instruction(amount = amount.toInt(), cycles = 2)
            } else {
                throw IllegalArgumentException("Unknown instruction $this")
            }
    }
}

data class Instruction(
    val amount: Int = 0,
    val cycles: Int = 1
)
