package org.plaehn.adventofcode.day13

import org.plaehn.adventofcode.common.groupByBlankLines
import org.plaehn.adventofcode.common.product


class DistressSignal(private val signal: List<Pair<Element, Element>>) {

    fun solvePart1() =
        signal
            .mapIndexed { index, pair -> (index + 1) to (pair.first < pair.second) }
            .filter { it.second }
            .sumOf { it.first }

    fun solvePart2() =
        (dividerPackets + signal.flatMap { it.toList() })
            .sortedBy { it }
            .mapIndexed { index, element -> if (element in dividerPackets) index + 1 else 1 }
            .product()

    companion object {

        val dividerPackets = setOf("[[2]]".toElement(), "[[6]]".toElement())

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


sealed interface Element : Comparable<Element> {

    override operator fun compareTo(other: Element): Int {
        val cmp = if (this is IntElement && other is IntElement) {
            value - other.value
        } else if (this is ListElement && other is ListElement) {
            this.compare(other)
        } else if (this is IntElement) {
            ListElement(listOf(this)).compare(other as ListElement)
        } else {
            (this as ListElement).compare(ListElement(listOf(other)))
        }
        return cmp
    }
}

data class IntElement(val value: Int) : Element {
    override fun toString() = value.toString()
}

data class ListElement(val children: List<Element>) : Element {
    override fun toString() = "[" + children.joinToString(",") + "]"

    fun compare(other: ListElement): Int {
        children.zip(other.children).forEach { pair ->
            val cmp = pair.first.compareTo(pair.second)
            if (cmp != 0) return cmp
        }
        return children.size - other.children.size
    }
}