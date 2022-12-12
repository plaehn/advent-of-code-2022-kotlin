package org.plaehn.adventofcode.day07


sealed interface Line

object ListDir : Line {
    override fun toString() = "$ ls"
}

data class ChangeDir(
    val dir: String
) : Line {
    override fun toString() = "$ cd $dir"
}

data class Directory(
    val name: String
) : Line {
    override fun toString() = "dir $name"
}

data class File(
    val size: Int,
    val name: String
) : Line {
    override fun toString() = "$size $name"
}

