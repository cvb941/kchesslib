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

import com.benasher44.uuid.uuid4
import com.github.bhlangonijr.chesslib.game.Event
import com.github.bhlangonijr.chesslib.game.Game
import com.github.bhlangonijr.chesslib.game.GameResult
import com.github.bhlangonijr.chesslib.game.GenericPlayer
import com.github.bhlangonijr.chesslib.game.Player
import com.github.bhlangonijr.chesslib.game.PlayerType
import com.github.bhlangonijr.chesslib.game.Round
import com.github.bhlangonijr.chesslib.game.Termination
import com.github.bhlangonijr.chesslib.game.TimeControl
import com.github.bhlangonijr.chesslib.util.StringUtil
import kotlin.math.max

/**
 * A convenient loader to extract a chess game and its metadata from an iterator over the lines of the PGN file.
 *
 *
 * The implementation allows loading only a single PGN game at a time.
 */
object GameLoader {
    /**
     * Loads the next game of chess from an iterator over the lines of a Portable Game Notation (PGN) file. The
     * iteration ends when the game is fully loaded, hence the iterator is not consumed more than necessary.
     *
     * @param iterator the iterator over the lines of a PGN file
     * @return the next game read from the iterator
     */
    fun loadNextGame(iterator: Iterator<String?>): Game? {
        if (!iterator.hasNext()) {
            return null
        }

        val container = PgnTempContainer()

        while (iterator.hasNext()) {
            var line = iterator.next()!!.trim { it <= ' ' }
            if (line.startsWith(PgnProperty.Companion.UTF8_BOM)) {
                line = line.substring(1)
            }
            try {
                if (PgnProperty.Companion.isProperty(line)) {
                    addProperty(line, container)
                } else if (line.isNotEmpty()) {
                    addMoveText(line, container)
                    if (isEndGame(line)) {
                        setMoveText(container.game, container.moveText)
                        return if (container.initGame) container.game else null
                    }
                }
            } catch (e: Exception) { //TODO stricter exceptions
                val name = container.event.name
                val r = container.round.number
                throw PgnException("Error parsing PGN[$r, $name]: ", e)
            }
        }
        return if (container.initGame) container.game else null
    }

    @Throws(Exception::class)
    private fun addProperty(line: String, container: PgnTempContainer) {
        val property: PgnProperty = PgnProperty.Companion.parsePgnProperty(line) ?: return
        container.initGame = true
        val tag = property.name!!.lowercase().trim { it <= ' ' }
        when (tag) {
            "event" -> {
                if (container.moveTextParsing && container.game.halfMoves.size == 0) {
                    setMoveText(container.game, container.moveText)
                }
                container.event.name = property.value
                container.event.id = property.value
            }

            "site" -> container.event.site = property.value
            "date" -> container.event.startDate = property.value
            "round" -> {
                var r = 1
                try {
                    r = property.value!!.toInt() //TODO isParseable
                } catch (e1: Exception) {
                }
                r = max(0.0, r.toDouble()).toInt()
                container.round.number = r
                if (!container.event.round.containsKey(r)) {
                    container.event.round[r] = container.round
                }
            }

            "white" -> {
                if (container.round.number < 1) {
                    container.round.number =
                        1 //TODO this is just to have the same behaviour as before...
                }

                container.game.date = container.event.startDate //TODO this should be done only once

                container.whitePlayer.id = property.value
                container.whitePlayer.name = property.value
                container.whitePlayer.description = property.value
            }

            "black" -> {
                if (container.round.number < 1) {
                    container.round.number =
                        1 //TODO this just to have the same behaviour as before...
                }

                container.game.date = container.event.startDate //TODO this should be done only once

                container.blackPlayer.id = property.value
                container.blackPlayer.name = property.value
                container.blackPlayer.description = property.value
            }

            "result" -> container.game.result = GameResult.Companion.fromNotation(property.value)
            "plycount" -> container.game.plyCount = property.value
            "termination" -> try {
                container.game.termination =
                    Termination.Companion.fromValue(property.value!!.uppercase())
            } catch (e1: Exception) {
                container.game.termination = Termination.UNTERMINATED
            }

            "timecontrol" -> if (container.event.timeControl == null) {
                try {
                    container.event.timeControl =
                        TimeControl.Companion.parseFromString(property.value!!.uppercase())
                } catch (e1: Exception) {
                    //ignore errors in time control tag as it's not required by standards
                }
            }

            "annotator" -> container.game.annotator = property.value
            "fen" -> container.game.fen = property.value
            "eco" -> container.game.eco = property.value
            "opening" -> container.game.opening = property.value
            "variation" -> container.game.variation = property.value
            "whiteelo" -> try {
                container.whitePlayer.elo = property.value!!.toInt()
            } catch (e: NumberFormatException) {
            }

            "blackelo" -> try {
                container.blackPlayer.elo = property.value!!.toInt()
            } catch (e: NumberFormatException) {
            }

            else -> {
                if (container.game.property == null) {
                    container.game.property = HashMap()
                }
                container.game.property!![property.name] = property.value
            }
        }
    }

    private fun addMoveText(line: String, container: PgnTempContainer) {
        container.initGame = true
        container.moveText.append(line)
        container.moveText.append('\n')
        container.moveTextParsing = true
    }

    private fun isEndGame(line: String): Boolean {
        return line.endsWith("1-0") || line.endsWith("0-1") || line.endsWith("1/2-1/2") || line.endsWith(
            "*"
        )
    }

    @Throws(Exception::class)
    private fun setMoveText(game: Game, moveText: StringBuilder) {
        //clear game result

        StringUtil.replaceAll(moveText, "1-0", "")
        StringUtil.replaceAll(moveText, "0-1", "")
        StringUtil.replaceAll(moveText, "1/2-1/2", "")
        StringUtil.replaceAll(moveText, "*", "")

        game.moveText = moveText
        game.loadMoveText(moveText)

        game.plyCount = game.halfMoves.size.toString()
    }

    private class PgnTempContainer {
        //TODO many of this stuff can be accessed through game
        val event: Event = Event()
        val round: Round = Round(event)
        val game: Game = Game(uuid4().toString(), round)
        var whitePlayer: Player
        var blackPlayer: Player
        val moveText: StringBuilder
        var moveTextParsing: Boolean = false
        var initGame: Boolean = false

        init {
            round.game.add(this.game)
            this.whitePlayer = GenericPlayer()
            whitePlayer.type = PlayerType.HUMAN
            game.whitePlayer = this.whitePlayer
            this.blackPlayer = GenericPlayer()
            blackPlayer.type = PlayerType.HUMAN
            game.blackPlayer = this.blackPlayer
            this.moveText = StringBuilder()
        }
    }
}