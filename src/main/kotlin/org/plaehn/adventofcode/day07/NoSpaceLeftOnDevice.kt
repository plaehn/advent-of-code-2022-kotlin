package org.plaehn.adventofcode.day07


class NoSpaceLeftOnDevice(private val lines: List<Line>) {

    fun computeSumOfTotalSizesOfDirectories(limit: Int) =
        buildTree(lines)
            .filter { it.isDir }
            .map { it.totalSize() }
            .filter { it <= limit }
            .sum()

    private fun buildTree(lines: List<Line>): Node {
        require(lines.first() == ChangeDir("/"))
        val root = Node("/", isDir = true)
        var current = root

        lines.drop(1).forEach { line ->
            when (line) {
                is ChangeDir -> {
                    current = if (line.dir == "..") current.parent!! else current.children.first { it.name == line.dir }
                }
                is ListDir -> {
                    // nothing to do
                }
                is Directory -> {
                    current.children.add(Node(name = line.name, isDir = true, parent = current))
                }
                is File -> {
                    current.children.add(Node(name = line.name, isDir = false, size = line.size, parent = current))
                }
            }
        }

        return root
    }

    companion object {

        fun fromInput(input: List<String>) = NoSpaceLeftOnDevice(input.map { it.toLine() })

        private fun String.toLine() =
            if (startsWith("$ cd ")) {
                ChangeDir(substringAfter("$ cd "))
            } else if (startsWith("$ ls")) {
                ListDir
            } else if (startsWith("dir ")) {
                Directory(substringAfter("dir "))
            } else {
                val (size, name) = split(' ')
                File(size.toInt(), name)
            }
    }
}

