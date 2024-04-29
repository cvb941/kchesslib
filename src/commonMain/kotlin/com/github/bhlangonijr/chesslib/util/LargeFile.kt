/*
 * Copyright 2017 Ben-Hur Carlos Vieira Langoni Junior
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.bhlangonijr.chesslib.util

import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * An abstract representation of a potentially large text-based file that can be read line by line.
 */
class LargeFile : Iterable<String?>, AutoCloseable {
    private val reader: BufferedReader

    private var nextLine: String? = null

    /**
     * Constructs a new large file from its path.
     *
     * @param filePath the file path
     * @throws Exception in case the file can not be accessed
     */
    constructor(filePath: String) {
        reader = BufferedReader(FileReader(filePath))
        readNextLine()
    }

    /**
     * Constructs a new large file from its input stream of bytes.
     *
     * @param inputStream the input stream
     */
    constructor(inputStream: InputStream) {
        reader = BufferedReader(InputStreamReader(inputStream))
        readNextLine()
    }

    /**
     * Closes this large file and releases any system resources associated with it.
     */
    override fun close() {
        try {
            reader.close()
        } catch (ex: Exception) {
        }
    }

    /**
     * Returns an iterator over the lines of the file.
     *
     * @return the iterator to read the lines of the file
     */
    override fun iterator(): MutableIterator<String?> {
        return FileIterator()
    }

    private fun readNextLine() {
        try {
            nextLine = reader.readLine()
        } catch (ex: Exception) {
            nextLine = null
            throw IllegalStateException("Error reading file", ex)
        }
    }

    private inner class FileIterator : MutableIterator<String?> {
        override fun hasNext(): Boolean {
            return nextLine != null
        }

        override fun next(): String? {
            val currentLine = nextLine
            readNextLine()
            return currentLine
        }

        override fun remove() {
        }
    }
}
