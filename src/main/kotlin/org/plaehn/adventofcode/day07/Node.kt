package org.plaehn.adventofcode.day07

data class Node(
    val name: String,
    val isDir: Boolean,
    val size: Int = 0,
    val parent: Node? = null,
    val children: MutableSet<Node> = mutableSetOf()
) : Iterable<Node> {

    override fun iterator() = traverseBreadthFirst(this).iterator()

    private fun traverseBreadthFirst(rootNode: Node): Sequence<Node> =
        sequence {
            val stack = ArrayDeque<Node>()
            stack.addFirst(rootNode)

            while (stack.isNotEmpty()) {
                val currentNode = stack.removeFirst()

                yield(currentNode)

                stack.addAll(currentNode.children.toMutableList())
            }
        }

    fun totalSize() = sumOf { it.size }

    override fun toString() =
        "Node[name=$name, size=$size, parent=${parent?.name}, children=[${children.joinToString { it.name }}]]"

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Node
        return name == that.name
    }
}