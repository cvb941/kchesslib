
package com.github.bhlangonijr.chesslib

external fun require(name: String): dynamic
external val __dirname: dynamic

val fs = require("fs")
val path = require("path");

actual fun Any.getResourcePath(name: String): String {
    return path.join(
        __dirname,
        name.substringAfterLast("/")
    ).toString()
}