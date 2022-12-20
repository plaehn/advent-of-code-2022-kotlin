package org.plaehn.adventofcode.day13

import org.plaehn.adventofcode.common.groupByBlankLines


class DistressSignal(private val signal: List<Pair<Element, Element>>) {

    fun solvePart1(): Int {
        println(signal.joinToString("\n"))
        TODO()
    }

    companion object {
        fun fromInput(input: String) =
            DistressSignal(
                input
                    .groupByBlankLines()
                    .map { group -> group.lines().let { it[0].toElement() to it[1].toElement() } }
            )

        private fun String.toElement() =
            when {
                startsWith("[") -> parseAsList()
                else -> IntElement(toInt())
            }

        private fun String.parseAsList(): Element {
            var depth = 0
            var token = ""
            val tokens = sequence {
                removePrefix("[").removeSuffix("]").forEach { ch ->
                    when (ch) {
                        '[' -> {
                            token += ch
                            ++depth
                        }
                        ']' -> {
                            token += ch
                            --depth
                        }
                        ',' -> {
                            if (depth == 0) {
                                yield(token)
                                token = ""
                            } else {
                                token += ch
                            }
                        }
                        else -> token += ch
                    }
                }
                if (token.isNotEmpty()) yield(token)
            }.toList()

            return ListElement(children = tokens.map { it.toElement() })
        }
    }
}

sealed interface Element

data class IntElement(val value: Int) : Element {
    override fun toString() = value.toString()
}

data class ListElement(val children: List<Element>) : Element {
    override fun toString() = "[" + children.joinToString(",") + "]"
}