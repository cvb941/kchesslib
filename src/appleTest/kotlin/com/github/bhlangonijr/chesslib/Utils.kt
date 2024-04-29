
package com.github.bhlangonijr.chesslib

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.toKString
import kotlinx.io.files.SystemTemporaryDirectory
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.posix.getcwd

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
actual fun Any.getResourcePath(name: String): String {
    val cwd = memScoped {
        val tmp = allocArray<ByteVar>(512)
        getcwd(tmp, 512.convert())
        tmp.toKString()
    }
//    println("cwd=$cwd")
    return "$cwd/$name"
}