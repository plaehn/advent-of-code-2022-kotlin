package org.plaehn.adventofcode.day10


class CathodeRayTube(private val instructions: List<Instruction>) {

    fun drawCrtImage(): String =
        processInstructions(initialValue = "") { state: State<String> ->
            state.output + drawPixel(state) + optionallyDrawLinefeed(state.cycle)
        }.trim()

    private fun drawPixel(state: State<String>) =
        if (drawPosition(state) in spritePosition(state)) '#' else '.'

    private fun drawPosition(state: State<String>) = (state.cycle - 1) % 40

    private fun spritePosition(state: State<String>) = state.x - 1..state.x + 1

    private fun optionallyDrawLinefeed(cycle: Int) =
        if (cycle % 40 == 0) "\n" else ""

    fun computeSumOfSignalStrength(): Int =
        processInstructions(initialValue = 0) { state: State<Int> ->
            state.output + if (state.cycle.isInterestingSignal()) state.cycle * state.x else 0
        }

    private fun Int.isInterestingSignal() = this in listOf(20, 60, 100, 140, 180, 220)

    private fun <T> processInstructions(
        initialValue: T,
        processCycle: (State<T>) -> T
    ): T =
        instructions.fold(State(0, 1, initialValue)) { state, instruction ->
            computeNewState(state, instruction, processCycle)
        }.output

    private fun <T> computeNewState(
        state: State<T>,
        instruction: Instruction,
        createNewOutput: (State<T>) -> T
    ): State<T> =
        (1..instruction.cycles).fold(state) { newState, _ ->
            newState.nextCycle().run { newOutput(createNewOutput(this)) }
        }.addToRegister(instruction.amount)

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

data class State<T>(
    val cycle: Int,
    val x: Int,
    val output: T
) {
    fun nextCycle(): State<T> = copy(cycle = cycle + 1)

    fun newOutput(newOutput: T): State<T> = copy(output = newOutput)

    fun addToRegister(amount: Int): State<T> = copy(x = x + amount)
}