package org.plaehn.adventofcode.day02

import org.plaehn.adventofcode.day02.Result.*

class RockPaperScissors(private val strategyGuide: List<Round>) {

    fun computeTotalScore() = strategyGuide.sumOf { round -> round.computeScore() }

    companion object {
        fun fromInputLines(lines: List<String>) =
            RockPaperScissors(lines.toStrategyGuide())

        private fun List<String>.toStrategyGuide(): List<Round> =
            map { Round.fromInput(it) }
    }
}

data class Round(
    val opponent: Shape,
    val you: Shape
) {
    fun computeScore() = you.score + computeResult().score

    // Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock
    private fun computeResult() =
        when (you.score - opponent.score) {
            0 -> DRAW
            1, -2 -> WIN
            else -> LOSS
        }

    companion object {
        fun fromInput(input: String): Round {
            val (opponent, you) = input.split(' ')
            return Round(Shape.fromInput(opponent), Shape.fromInput(you))
        }
    }
}

enum class Shape(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun fromInput(input: String) =
            when (input.trim().uppercase()) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw IllegalArgumentException("Unknown shape $input")
            }
    }
}

enum class Result(val score: Int) {
    WIN(6),
    DRAW(3),
    LOSS(0)
}