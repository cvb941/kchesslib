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
package com.github.bhlangonijr.chesslib.pgn

import com.github.bhlangonijr.chesslib.game.Game
import com.github.bhlangonijr.chesslib.util.LargeFile

/**
 * A Portable Game Notation (PGN) iterator, used to navigate the games contained in PGN file.
 *
 *
 * The iterator permits iterating over large PGN files without piling up every game into the memory.
 */
@OptIn(ExperimentalStdlibApi::class)
class PgnIterator : Iterable<Game?>, AutoCloseable {
    private val pgnLines: Iterable<String?>
    private val pgnLinesIterator: Iterator<String?>

    private var game: Game? = null

    /**
     * Constructs a new PGN iterator from the filename of the PGN file.
     *
     * @param filename the PGN filename
     * @throws Exception in case the PGN file can not be accessed
     */
    constructor(filename: String) : this(LargeFile(filename))

    /**
     * Constructs a new PGN iterator from the PGN file.
     *
     * @param file the PGN file
     */
    constructor(file: LargeFile) {
        this.pgnLines = file
        this.pgnLinesIterator = pgnLines.iterator()
        loadNextGame()
    }

    /**
     * Constructs a new PGN iterator from an [Iterable] object that can iterate over the lines of the PGN file.
     *
     * @param pgnLines an iterable over the PGN lines
     */
    constructor(pgnLines: Iterable<String?>) {
        this.pgnLines = pgnLines
        this.pgnLinesIterator = pgnLines.iterator()
        loadNextGame()
    }

    /**
     * Returns an iterator over the games included in the PGN file.
     *
     * @return the iterator to navigate the games stored in the PGN file
     */
    override fun iterator(): MutableIterator<Game?> {
        return GameIterator()
    }

    /**
     * Attempts to close the PGN file and releases any system resources associated with it.
     */
    override fun close() {
        if (pgnLines is LargeFile) {
            pgnLines.close()
        }
    }

    private fun loadNextGame() {
        game = GameLoader.loadNextGame(pgnLinesIterator)
    }

    private inner class GameIterator : MutableIterator<Game?> {
        override fun hasNext(): Boolean {
            return game != null
        }

        override fun next(): Game? {
            val current = game
            loadNextGame()
            return current
        }

        override fun remove() {
        }
    }
}