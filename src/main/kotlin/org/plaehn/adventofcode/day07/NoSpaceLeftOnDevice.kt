package org.plaehn.adventofcode.day07


class NoSpaceLeftOnDevice(val lines: List<Line>) {

    fun computeSumOfTotalSizesOfDirectories(limit: Int): Int {
        println(lines.joinToString(separator = "\n"))
        TODO()
    }

    companion object {
        fun fromInput(input: List<String>) =
            NoSpaceLeftOnDevice(input.map { it.toLine() })

        private fun String.toLine() =
            if (startsWith("$ cd ")) {
                ChangeDir(substringAfter("$ cd ").trim())
            } else if (startsWith("$ ls")) {
                ListDir
            } else if (startsWith("dir ")) {
                Directory(substringAfter("dir ").trim())
            } else {
                val (size, name) = split(' ')
                File(size.toInt(), name)
            }
    }
}
