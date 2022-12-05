package org.plaehn.adventofcode.day02

import org.plaehn.adventofcode.day02.Result.*
import org.plaehn.adventofcode.day02.Shape.*

class RockPaperScissors(private val strategyGuide: List<Round>) {

    fun computeTotalScore(roundMapper: (Round) -> Round = { it }) =
        strategyGuide
            .map { roundMapper(it) }
            .sumOf { round -> round.computeScore() }

    fun computeTotalScorePartTwo() =
        computeTotalScore { round ->
            // X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win
            val expectedResult = when (round.you) {
                ROCK -> LOSS
                PAPER -> DRAW
                SCISSORS -> WIN
            }

            val newShape = when (expectedResult) {
                DRAW -> round.opponent
                LOSS -> round.opponent.worseShape()
                WIN -> round.opponent.betterShape()
            }

            round.copy(you = newShape)
        }

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

    fun betterShape() = fromScore(score % 3 + 1)

    fun worseShape() = fromScore(if (score == 1) 3 else score - 1)

    companion object {
        fun fromInput(input: String) =
            when (input.trim().uppercase()) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw IllegalArgumentException("Unknown shape $input")
            }

        fun fromScore(score: Int) =
            values().find { it.score == score } ?: throw IllegalArgumentException("Unknown score $score")
    }
}

enum class Result(val score: Int) {
    WIN(6),
    DRAW(3),
    LOSS(0)
}