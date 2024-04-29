package com.github.bhlangonijr.chesslib

import org.junit.Test
import javax.swing.text.html.HTML.Tag.U

actual fun Any.getResourcePath(name: String): String {
    val name = name.substringAfterLast("/", "")
    return this::class.java.classLoader.getResource(name)!!.file
}