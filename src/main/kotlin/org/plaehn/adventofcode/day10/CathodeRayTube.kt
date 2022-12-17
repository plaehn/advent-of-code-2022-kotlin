package org.plaehn.adventofcode.day10


class CathodeRayTube(private val instructions: List<Instruction>) {

    fun drawCrtImage(): String =
        processInstructions("") { cycle, x, image ->
            var newImage = image
            newImage += if ((cycle - 1) % 40 in (x - 1..x + 1)) '#' else '.'
            if (cycle % 40 == 0) {
                newImage += "\n"
            }
            newImage
        }.trim()

    fun computeSumOfSignalStrength(): Int =
        processInstructions(0) { cycle, x, sum ->
            sum + if (cycle.isInterestingSignal()) {
                cycle * x
            } else {
                0
            }
        }

    private fun <T> processInstructions(
        initialValue: T,
        processCycle: (Int, Int, T) -> T
    ): T {
        var output: T = initialValue
        var x = 1
        var cycle = 0

        instructions.forEach { instruction ->
            repeat(instruction.cycles) {
                ++cycle
                output = processCycle(cycle, x, output)
            }
            x += instruction.amount
        }
        return output
    }

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
    val amount: Int,
    val cycles: Int
)