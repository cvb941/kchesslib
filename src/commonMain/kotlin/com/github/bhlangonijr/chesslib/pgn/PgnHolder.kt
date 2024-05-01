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

import com.github.bhlangonijr.chesslib.game.Event
import com.github.bhlangonijr.chesslib.game.Game
import com.github.bhlangonijr.chesslib.game.Player
import com.github.bhlangonijr.chesslib.util.LargeFile
import com.github.bhlangonijr.chesslib.util.readLines
import kotlinx.io.IOException
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString
import kotlin.jvm.JvmOverloads

/**
 * A proxy for accessing a Portable Game Notation (PGN) file. The PGN holder can be used to optimize the way the
 * contents of the file are retrieved, and also to abstract the common operations that can be performed with the file,
 * such as saving to the PGN file the games held into memory, as well as retrieving the metadata stored in the PGN.
 */
class PgnHolder(filename: String?) {
    /**
     * Returns all the chess events stored in the holder, accessible by name.
     *
     * @return the chess events
     */
    val event: MutableMap<String?, Event> = HashMap()

    /**
     * Returns all the chess players stored in the holder, accessible by ID.
     *
     * @return the chess players
     */
    val player: MutableMap<String?, Player?> = HashMap()
    val games: MutableList<Game> = ArrayList()
    private val listener: MutableList<PgnLoadListener> = ArrayList()
    /**
     * Returns the filename of the PGN file.
     *
     * @return the filename of the PGN
     */
    /**
     * Sets a new filename for the PGN file.
     *
     * @param fileName the filename of the PGN
     */
    var fileName: String? = null

    /**
     * Returns the number of games stored in holder.
     *
     * @return the number of games
     */
    var size: Int? = null
        private set
    /**
     * Checks if the PGN contents are loaded lazily.
     *
     *
     * **N.B.**: at the moment lazy loading is not enabled and this flag has no impact on the behavior of the class.
     *
     * @return `true` if the PGN contents are loaded lazily
     */
    /**
     * Sets whether to activate lazy loading or not.
     *
     *
     * **N.B.**: at the moment lazy loading is not enabled and this flag has no impact on the behavior of the class.
     *
     * @param lazyLoad `true` to activate lazy loading
     */
    var isLazyLoad: Boolean = false

    /**
     * Constructs a new PGN holder using the provided filename as a reference to the PGN file.
     *
     * @param filename the PGN filename
     */
    init {
        fileName = filename
        isLazyLoad = false
    }

    /**
     * Resets the status of the holder, cleaning up all data previously stored.
     */
    fun cleanUp() {
        event.clear()
        player.clear()
        games.clear()
        listener.clear()
        size = 0
    }

    /**
     * Counts the games present in the PGN file.
     *
     *
     * It does not load the contents of the file, but rather checks into the data how many events are persisted. In
     * order to do so, the implementation counts the mandatory PGN tags.
     *
     * @return the number of games in PGN file
     * @throws IOException in case of error reading the PGN file
     */
    @Throws(IOException::class)
    fun countGamesInPgnFile(): Int {
        return SystemFileSystem.source(Path(this.fileName!!)).buffered().readLines()
            .filter { s: String -> s.startsWith("[Event ") }
            .count()
    }

    /**
     * Loads into memory the chess data stored in the given PGN file.
     *
     * @param file the PGN file to load
     * @throws Exception in case of error loading the contents of the file
     */
    /**
     * Loads into memory the chess data stored in the PGN file referred by the holder.
     *
     * @throws Exception in case of error loading the contents of the file
     */
    @JvmOverloads
    @Throws(Exception::class)
    fun loadPgn(
        file: LargeFile = LargeFile(
            fileName!!
        )
    ) {
        size = 0

        val games = PgnIterator(file)

        try {
            for (game in games.filterNotNull()) {
                addGame(game)
            }
        } finally {
            file.close()
        }
    }

    /**
     * Loads into memory the chess data of the given PGN, provided as a raw string representation.
     *
     * @param pgn the raw string representing the contents of a PGN
     */
    fun loadPgn(pgn: String) {
        val iterable = pgn.splitToSequence("\n").asIterable()
        val games = PgnIterator(iterable)
        for (game in games) {
            if (game != null) {
                addGame(game)
            }
        }
    }

    /**
     * Saves to the PGN file the current data stored in the holder.
     */
    fun savePgn() {
        try {
            val out = SystemFileSystem.sink(Path(fileName!!)).buffered()
            for (event in event.values) {
                for (round in event.round.values) {
                    for (game in round.game) {
                        if (game != null) {
                            out.writeString("\n")
                            out.writeString(game.toString())
                            out.writeString("\n")
                        }
                    }
                }
            }
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Returns the list of observers to the PGN loading events. The list can be used to add other listeners or remove
     * existing ones.
     *
     * @return the listeners to PGN loading events
     */
    fun getListener(): MutableList<PgnLoadListener> {
        return listener
    }

    /**
     * Returns a string representation of this PGN holder.
     *
     * @return a string representation of the holder
     */
    override fun toString(): String {
        val sb = StringBuilder()
        for (event in event.values) {
            for (round in event.round.values) {
                for (game in round.game) {
                    if (game != null) {
                        sb.append('\n')
                        sb.append(game)
                        sb.append('\n')
                    }
                }
            }
        }
        return sb.toString()
    }

    private fun addGame(game: Game) {
        val event = event[game.round.event.name]
        if (event == null) {
            this.event[game.round.event.name] = game.round.event
        }
        val whitePlayer = player[game.whitePlayer?.id]
        if (whitePlayer == null) {
            player[game.whitePlayer?.id] = game.whitePlayer
        }
        val blackPlayer = player[game.blackPlayer?.id]
        if (blackPlayer == null) {
            player[game.blackPlayer?.id] = game.blackPlayer
        }
        games.add(game)

        // Notify all registered listeners about added game
        getListener().forEach { pgnLoadListener: PgnLoadListener ->
            pgnLoadListener.notifyProgress(
                games.size
            )
        }
    }
}
