package org.plaehn.adventofcode.day12

import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder
import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.shortestPath


object HillClimbing {

    fun computeFewestStepToTop(lines: List<String>): Int {
        val graph = buildGraph(lines)
        val start = graph.nodes().first { it.value == 'S' }
        val end = graph.nodes().first { it.value == 'E' }
        return graph.shortestPath(start, end).size - 1
    }

    private fun buildGraph(lines: List<String>): ValueGraph<Node, Int> =
        ValueGraphBuilder
            .directed()
            .expectedNodeCount(lines.size * lines.first().length)
            .immutable<Node, Int>()
            .apply {
                val matrix = Matrix.fromRows(lines.map { it.toList() }, ' ')

                matrix.toMap().keys.forEach { coord ->
                    val node = Node(coord, matrix[coord])
                    addNode(node)
                    matrix.neighbors(coord).forEach { neighbor ->
                        val neighborNode = Node(neighbor, matrix[neighbor])
                        addNode(neighborNode)
                        if (neighborNode.height() - node.height() <= 1) {
                            putEdgeValue(node, neighborNode, 1)
                        }
                    }
                }
            }
            .build()

    data class Node(
        val coord: Coord,
        val value: Char
    ) {
        fun height() =
            when (value) {
                'S' -> 0
                'E' -> 25
                else -> value.code - 'a'.code
            }
    }
}