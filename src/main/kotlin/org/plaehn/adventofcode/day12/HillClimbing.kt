package org.plaehn.adventofcode.day12

import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder
import org.plaehn.adventofcode.common.Coord
import org.plaehn.adventofcode.common.Matrix
import org.plaehn.adventofcode.common.shortestPath
import org.plaehn.adventofcode.common.shortestPaths


class HillClimbing(private val graph: ValueGraph<Node, Int>) {

    fun computeFewestStepsToTopFromCurrentPosition(): Int {
        val start = graph.nodes().first { it.value == 'E' }
        val end = graph.nodes().first { it.value == 'S' }
        return graph.shortestPath(start, end).size - 1
    }

    fun computeFewestStepsToTopFromAnyPositionAtLowestHeight(): Int {
        val start = graph.nodes().first { it.value == 'E' }
        val shortestPaths = graph.shortestPaths(start) { it.height() == 0 }
        return shortestPaths.minOf { it.size } - 1
    }

    companion object {
        fun fromInput(lines: List<String>) = HillClimbing(buildReverseGraph(lines))

        // We consider the shortest path from end to start in order to be able to solve part 2
        // with Dijkstra, too
        private fun buildReverseGraph(lines: List<String>) =
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
                            if (node.height() - neighborNode.height() <= 1) {
                                putEdgeValue(node, neighborNode, 1)
                            }
                        }
                    }
                }
                .build()
    }

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