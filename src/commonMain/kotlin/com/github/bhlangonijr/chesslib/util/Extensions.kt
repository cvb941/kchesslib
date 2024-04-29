package com.github.bhlangonijr.chesslib.util

import kotlinx.io.Source
import kotlinx.io.readLine

fun <K, V> MutableMap<K, V>.compute(key: K, remappingFunction: (K, V?) -> V): V? {
    val oldValue = this[key]
    val newValue = remappingFunction(key, oldValue)
    if (oldValue != null) {
        if (newValue != null)
            put(key, newValue)
        else
            remove(key)
    } else {
        if (newValue != null)
            put(key, newValue)
        else
            return null
    }
    return newValue
}

fun Source.readLines(): Sequence<String> {
    return sequence {
        while (true) {
            val line = readLine() ?: break
            yield(line)
        }
    }
}