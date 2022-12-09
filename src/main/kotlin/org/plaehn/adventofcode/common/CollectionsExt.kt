package org.plaehn.adventofcode.common

fun <E> List<List<E>>.transpose(defaultIfMissing: E): List<List<E>> =
    (0 until maxOf { it.size }).map { idx -> map { it.getOrNull(idx) ?: defaultIfMissing } }