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
package com.github.bhlangonijr.chesslib.game

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.move.MoveConversionException
import com.github.bhlangonijr.chesslib.move.MoveException
import com.github.bhlangonijr.chesslib.move.MoveList
import com.github.bhlangonijr.chesslib.pgn.PgnException
import com.github.bhlangonijr.chesslib.util.StringUtil
import org.apache.commons.lang3.StringUtils
import java.util.LinkedList
import java.util.Locale

/**
 * A chess game, as defined by the specifications of the Portable Game Notation (PGN) format.
 */
class Game(
    /**
     * Sets the game ID.
     *
     * @param gameId the game ID to set
     */
    var gameId: String,
    /**
     * Returns the round of the event this game belongs to.
     *
     * @return the round of the game
     */
    @JvmField val round: Round
) {
    /**
     * Returns the game ID.
     *
     * @return the game ID
     */
    /**
     * Returns the date of the game, when the game was played.
     *
     * @return the date
     */
    /**
     * Sets the date of the game.
     *
     * @param date the date to set
     * @see Game.getDate
     */
    @JvmField
    var date: String? = null
    /**
     * Returns the time of the game, at what time the game was played.
     *
     * @return the time
     */
    /**
     * Sets the time of the game.
     *
     * @param time the time to set
     * @see Game.getTime
     */
    var time: String? = null
    /**
     * Returns the termination mode of the game.
     *
     * @return the termination mode
     */
    /**
     * Sets the termination of the game.
     *
     * @param termination the termination to set
     */
    var termination: Termination? = null
    /**
     * Returns the white player.
     *
     * @return the white player
     */
    /**
     * Sets the white player.
     *
     * @param whitePlayer the white player to set
     */
    @JvmField
    var whitePlayer: Player? = null
    /**
     * Returns the black player.
     *
     * @return the black player
     */
    /**
     * Sets the black player.
     *
     * @param blackPlayer the black player to set
     */
    @JvmField
    var blackPlayer: Player? = null
    /**
     * Returns the annotator.
     *
     * @return the annotator
     */
    /**
     * Sets the annotator.
     *
     * @param annotator the annotator to set
     */
    @JvmField
    var annotator: String? = null
    /**
     * Returns the ply count, that is, the number of moves played in the game.
     *
     * @return the ply count
     */
    /**
     * Sets the ply count.
     *
     * @param plyCount the ply count to set
     * @see Game.getPlyCount
     */
    @JvmField
    var plyCount: String? = null
    /**
     * Returns the game result.
     *
     * @return the game result
     */
    /**
     * Sets the game result.
     *
     * @param result the result to set
     */
    @JvmField
    var result: GameResult? = GameResult.ONGOING
    var halfMoves: MoveList = MoveList()
        /**
         * Sets the moves of the game.
         *
         * @param halfMoves the moves to set
         */
        set(halfMoves) {
            field = halfMoves
            currentMoveList = halfMoves
        }
    /**
     * Returns the variations present in the game. Each variation is defined by a [MoveList] indexed by an
     * identifier of the move from which it branches out.
     *
     * @return the variations
     */
    /**
     * Sets the variations of the game.
     *
     * @param variations the variations to set
     * @see Game.getVariations
     */
    var variations: MutableMap<Int, MoveList>? = null
    /**
     * Returns the commentary of the game. Each comment is indexed by an identifier of the move it refers to.
     *
     * @return the commentary
     */
    /**
     * Sets the commentary of the game.
     *
     * @param comments the commentary to set
     * @see Game.getComments
     */
    @JvmField
    var comments: MutableMap<Int, String>? = null
    /**
     * Returns the Numeric Annotation Glyphs (NAG) values present in the game, indexed by the identifiers of the moves
     * they refer to.
     *
     * @return the nag values
     */
    /**
     * Sets the Numeric Annotation Glyphs (NAG) values of the game.
     *
     * @param nag the nag values to set
     * @see Game.getNag
     */
    var nag: MutableMap<Int, String>? = null
    /**
     * Returns the PGN properties of the game.
     *
     * @return the PGN properties
     */
    /**
     * Sets the PGN properties of the game.
     *
     * @param property the properties to set
     */
    var property: MutableMap<String?, String?>? = null

    /**
     * Returns the initial position of the game as a Forsyth-Edwards Notation (FEN) string.
     *
     * @return the initial position of the game in FEN notation
     */
    /**
     * Sets the initial position of the game, provided as a Forsyth-Edwards Notation (FEN) string.
     *
     * @param fen the initial position to set
     */
    @JvmField
    var fen: String? = null
    /**
     * Returns the board representing the updated position of the game.
     *
     * @return the updated position of the game
     */
    /**
     * Sets the board used to represent the updated position of the game.
     *
     * @param board the board to set
     */
    var board: Board? = null
    /**
     * Returns the index of the current move.
     *
     * @return the index of the current move
     */
    /**
     * Sets the index of the current move.
     *
     * @param position the index of the current move to set
     */
    var position: Int = 0
    /**
     * Returns the index of the initial position.
     *
     * @return the index of the initial position
     */
    /**
     * Sets the index of the initial position.
     *
     * @param initialPosition the index of the initial position to set
     */
    var initialPosition: Int = 0 // when loaded from FEN
    /**
     * Returns the current list of moves, used to navigate the game.
     *
     * @return the current list of moves
     */
    /**
     * Sets the current list of moves.
     *
     * @param currentMoveList the current list of moves to set
     */
    var currentMoveList: MoveList? = null
    /**
     * Returns the Encyclopedia of Chess Openings (ECO) code of the game.
     *
     * @return the ECO code
     */
    /**
     * Sets the Encyclopedia of Chess Openings (ECO) code of the game.
     *
     * @param eco the ECO code to set
     */
    @JvmField
    var eco: String? = null
    /**
     * Returns the PGN textual representation of the moves of the game.
     *
     * @return the moves of the game
     */
    /**
     * Sets the moves of the game as a PGN textual representation.
     *
     * @param moveText the moves to set
     */
    var moveText: StringBuilder? = null
    /**
     * Returns the name of the opening.
     *
     * @return the opening name
     */
    /**
     * Sets the name of the opening.
     *
     * @param opening the opening name to set
     */
    var opening: String? = null
    /**
     * Returns the name of the variation.
     *
     * @return the variation name
     */
    /**
     * Sets the name of the variation.
     *
     * @param variation the variation name to set
     */
    var variation: String? = null

    /**
     * Constructs a new chess game.
     *
     * @param gameId the game ID
     * @param round  the round the game belongs to
     */
    init {
        this.position = 0
    }

    /**
     * Generates the Portable Game Notation (PGN) representation of this game and its data. Variations and comments are
     * included by default.
     *
     * @param includeVariations currently ignored: variations are included regardless the values of this flag
     * @param includeComments   currently ignored: comments are included regardless the values of this flag
     * @return the PGN representation of this game
     * @throws MoveConversionException if the move conversion fails
     */
    @Throws(MoveConversionException::class)
    fun toPgn(includeVariations: Boolean, includeComments: Boolean): String {
        val sb = StringBuilder()

        sb.append(makeProp("Event", round.event.name))
        sb.append(makeProp("Site", round.event.site))
        sb.append(makeProp("Date", round.event.startDate))
        sb.append(makeProp("Round", round.number.toString()))
        sb.append(makeProp("White", whitePlayer?.name))
        sb.append(makeProp("Black", blackPlayer?.name))
        sb.append(makeProp("Result", result?.description))
        sb.append(makeProp("PlyCount", plyCount))
        if (termination != null) {
            sb.append(
                makeProp(
                    "Termination",
                    termination.toString().lowercase(Locale.getDefault())
                )
            )
        }
        if (round.event.timeControl != null) {
            sb.append(makeProp("TimeControl", round.event.timeControl!!.toPGNString()))
        } else {
            sb.append(makeProp("TimeControl", "-"))
        }
        if (StringUtils.isNotEmpty(annotator)) {
            sb.append(makeProp("Annotator", annotator))
        }
        if (StringUtils.isNotEmpty(fen)) {
            sb.append(makeProp("FEN", fen))
        }
        if (StringUtils.isNotEmpty(eco)) {
            sb.append(makeProp("ECO", eco))
        }
        if (StringUtils.isNotEmpty(opening)) {
            sb.append(makeProp("Opening", opening))
        }
        if (whitePlayer != null && whitePlayer!!.elo > 0) {
            sb.append(makeProp("WhiteElo", whitePlayer!!.elo.toString()))
        }
        if (blackPlayer != null && blackPlayer!!.elo > 0) {
            sb.append(makeProp("BlackElo", blackPlayer!!.elo.toString()))
        }
        if (property != null) {
            for ((key, value) in property!!) {
                sb.append(makeProp(key, value))
            }
        }

        sb.append('\n')
        var index = 0
        var moveCounter = initialPosition + 1
        var variantIndex = 0
        var lastSize = sb.length

        if (halfMoves.size == 0) {
            sb.append(moveText.toString())
        } else {
            sb.append(moveCounter)
            if (moveCounter % 2 == 0) {
                sb.append(".. ")
            } else {
                sb.append(". ")
            }
            val sanArray = halfMoves.toSanArray()
            for (i in sanArray!!.indices) {
                val san = sanArray[i]
                index++
                variantIndex++

                sb.append(san)
                sb.append(' ')

                if (sb.length - lastSize > 80) {
                    sb.append("\n")
                    lastSize = sb.length
                }
                if (nag != null) {
                    val nag = nag!![variantIndex]
                    if (nag != null) {
                        sb.append(nag)
                        sb.append(' ')
                    }
                }

                if (comments != null) {
                    val comment = comments!![variantIndex]
                    if (comment != null) {
                        sb.append("{")
                        sb.append(comment)
                        sb.append("}")
                    }
                }
                if (variations != null) {
                    val `var` = variations!![variantIndex]
                    if (`var` != null) {
                        variantIndex = translateVariation(
                            sb, `var`, -1,
                            variantIndex, index, moveCounter, lastSize
                        )
                        if (index % 2 != 0) {
                            sb.append(moveCounter)
                            sb.append("... ")
                        }
                    }
                }
                if (i < sanArray.size - 1 && index % 2 == 0 && index >= 2) {
                    moveCounter++

                    sb.append(moveCounter)
                    sb.append(". ")
                }
            }
        }
        sb.append(result?.description)
        return sb.toString()
    }

    @Throws(MoveConversionException::class)
    private fun translateVariation(
        sb: StringBuilder, variation: MoveList?, parent: Int,
        variantIndex: Int, index: Int, moveCounter: Int, lastSize: Int
    ): Int {
        var variantIndex = variantIndex
        val variantIndexOld = variantIndex
        if (variation != null) {
            var terminated = false
            sb.append("(")
            var i = 0
            var mc = moveCounter
            var idx = index
            val sanArray = variation.toSanArray()
            i = 0
            while (i < sanArray!!.size) {
                val sanMove = sanArray[i]
                if (i == 0) {
                    sb.append(mc)
                    if (idx % 2 == 0) {
                        sb.append("... ")
                    } else {
                        sb.append(". ")
                    }
                }

                variantIndex++

                sb.append(sanMove)
                sb.append(' ')
                val child = variations!![variantIndex]
                if (child != null) {
                    if (i == sanArray.size - 1 &&
                        variantIndexOld != child.parent
                    ) {
                        terminated = true
                        sb.append(") ")
                    }
                    variantIndex = translateVariation(
                        sb, child, variantIndexOld,
                        variantIndex, idx, mc, lastSize
                    )
                }
                if (idx % 2 == 0 && idx >= 2 && i < sanArray.size - 1) {
                    mc++

                    sb.append(mc)
                    sb.append(". ")
                }
                idx++

                i++
            }
            if (!terminated) {
                sb.append(") ")
            }
        }
        return variantIndex
    }

    /**
     * Returns a string representation of this chess game.
     *
     *
     * The result of [Game.toPgn] is used to represent this game.
     *
     * @return a string representation of this game
     * @see Game.toPgn
     */
    override fun toString(): String {
        return try {
            toPgn(true, true) //TODO this throws NPE in debugger sometimes
        } catch (e: MoveConversionException) {
            "null"
        }
    }

    /**
     * Loads an already existing PGN textual representation of moves into this game data structure. The internal status
     * of this instance is updated to reflect the loaded moves.
     *
     *
     * It is possible to load a list of moves in their PGN textual representation without setting them in advance in the
     * game invoking [Game.loadMoveText].
     *
     * @throws Exception if it is not possible to load the moves
     * @see Game.setMoveText
     * @see Game.loadMoveText
     */
    @Throws(Exception::class)
    fun loadMoveText() {
        if (moveText != null) {
            loadMoveText(moveText!!)
        }
    }

    /**
     * Loads a PGN textual representation of moves into this game data structure. The internal status of this instance
     * is updated to reflect the loaded moves.
     *
     * @param moveText the moves to load
     * @throws Exception if it is not possible to load the moves
     */
    @Throws(Exception::class)
    fun loadMoveText(moveText: StringBuilder) {
        if (variations != null) {
            variations!!.clear()
        }
        if (comments != null) {
            comments!!.clear()
        }
        if (nag != null) {
            nag!!.clear()
        }

        StringUtil.replaceAll(moveText, "\n", " \n ")
        StringUtil.replaceAll(moveText, "{", " { ")
        StringUtil.replaceAll(moveText, "}", " } ")
        StringUtil.replaceAll(moveText, "(", " ( ")
        StringUtil.replaceAll(moveText, ")", " ) ")

        val text = moveText.toString()

        if (!fen.isNullOrBlank()) {
            halfMoves = MoveList(fen!!)
        } else {
            halfMoves = MoveList()
        }

        val moves = StringBuilder()
        var comment: StringBuilder? = null
        val variation =
            LinkedList<RTextEntry>()

        var halfMove = 0
        var variantIndex = 0

        var onCommentBlock = false
        var onVariationBlock = false
        var onLineCommentBlock = false
        for (token in text.split(" ")) {
            var token = token
            if (StringUtils.isBlank(token)) {
                continue
            }
            if (!(onLineCommentBlock || onCommentBlock) &&
                token.contains("...")
            ) {
                token = StringUtil.afterSequence(token, "...")
                if (token.trim { it <= ' ' }.length == 0) {
                    continue
                }
            }
            if (!(onLineCommentBlock || onCommentBlock) &&
                token.contains(".")
            ) {
                token = StringUtil.afterSequence(token, ".")
                if (token.trim { it <= ' ' }.length == 0) {
                    continue
                }
            }
            if (!(onLineCommentBlock || onCommentBlock) &&
                token.startsWith("$")
            ) {
                if (nag == null) {
                    nag = HashMap()
                }
                nag!![variantIndex] = token
                continue
            }
            if (token == "{" &&
                !(onLineCommentBlock || onCommentBlock)
            ) {
                onCommentBlock = true
                comment = StringBuilder()
                continue
            } else if (token == "}" && !onLineCommentBlock) {
                onCommentBlock = false
                if (comment != null) {
                    if (comments == null) {
                        comments = HashMap()
                    }
                    comments!![variantIndex] = comment.toString()
                }
                comment = null
                continue
            } else if (token == ";" && !onCommentBlock) {
                onLineCommentBlock = true
                comment = StringBuilder()
                continue
            } else if (token == "\n" && onLineCommentBlock) {
                onLineCommentBlock = false
                if (comment != null) {
                    comments!![variantIndex] = comment.toString()
                }
                comment = null
                continue
            } else if (token == "(" &&
                !(onCommentBlock) || onLineCommentBlock
            ) {
                onVariationBlock = true
                variation.add(RTextEntry(variantIndex))
                continue
            } else if (token == ")" && onVariationBlock &&
                !(onCommentBlock) || onLineCommentBlock
            ) {
                onVariationBlock = false
                if (variation != null) {
                    val last = variation.pollLast()
                    val currentLine =
                        StringBuilder(getMovesAt(moves.toString(), halfMove))
                    try {
                        onVariationBlock = variation.size > 0

                        for (entry in variation) {
                            currentLine.append(getMovesAt(entry.text.toString(), entry.size))
                        }

                        val tmp = MoveList()
                        tmp.loadFromSan(getMovesAt(currentLine.toString(), last!!.index))
                        val `var`: MoveList = MoveList.Companion.createMoveListFrom(tmp, tmp.size)
                        `var`.loadFromSan(last.text.toString())
                        val parent = variation.peekLast()
                        if (onVariationBlock && parent != null) {
                            `var`.parent = parent.index
                        } else {
                            `var`.parent = -1
                        }
                        if (variations == null) {
                            variations = HashMap()
                        }
                        variations!![last.index] = `var`
                    } catch (e: Exception) {
                        if (last != null) {
                            throw PgnException(
                                "Error while reading variation: " +
                                        getMovesAt(currentLine.toString(), last.index) + " - " +
                                        last.text.toString(), e
                            )
                        } else {
                            throw PgnException("Error while reading variation: ", e)
                        }
                    }
                }
                continue
            }

            if (onCommentBlock || onLineCommentBlock) {
                if (comment != null) {
                    comment.append(token)
                    comment.append(StringUtils.SPACE)
                }
                continue
            }

            if (onVariationBlock) {
                if (variation != null) {
                    variation.last.text.append(token)
                    variation.last.text.append(StringUtils.SPACE)
                    variation.last.size++
                    variantIndex++
                }
                continue
            }
            variantIndex++
            halfMove++
            moves.append(token)
            moves.append(StringUtils.SPACE)
        }

        StringUtil.replaceAll(moves, "\n", " ")
        halfMoves.loadFromSan(moves.toString())
    }

    /**
     * Navigates the list of moves from the initial position of the game until a given position, defined by the provided
     * index passed in input. In other words, updates the status of the board to reflect the game up to move
     * `index` (included). The provided list of moves becomes active and the pointer to the current position is
     * updated.
     *
     * @param moves the moves to navigate
     * @param index the index of the move to reach
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     */
    @Throws(MoveException::class)
    fun gotoMove(moves: MoveList, index: Int) {
        currentMoveList = moves
        if (board != null && index >= 0 && index < moves.size) {
            board!!.loadFromFen(moves.startFen)

            var i = 0
            for (move in moves) {
                if (!board!!.doMove(move, true)) {
                    throw MoveException("Couldn't load board state. Reason: Illegal move in PGN MoveText.")
                }
                i++
                if (i - 1 == index) {
                    break
                }
            }
            position = i - 1
        }
    }

    /**
     * Navigates the list of moves from the initial position of the game to the first move in the list. In other words,
     * updates the status of the board to reflect the game up to the first move.
     *
     *
     * Same as invoking `gotoMove(moves, 0)`.
     *
     * @param moves the moves to navigate
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     * @see Game.gotoMove
     */
    /**
     * Navigates the current list of moves to the first one.
     *
     *
     * Same as invoking `gotoMove(getCurrentMoveList(), 0)`.
     *
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     * @see Game.gotoMove
     */
    @JvmOverloads
    @Throws(MoveException::class)
    fun gotoFirst(moves: MoveList = currentMoveList!!) {
        gotoMove(moves, 0)
    }

    /**
     * Navigates the list of moves from the initial position of the game to the last move in the list. In other words,
     * updates the status of the board to reflect the game up to the last move.
     *
     *
     * Same as invoking `gotoMove(moves, moves.size() - 1)`.
     *
     * @param moves the moves to navigate
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     * @see Game.gotoMove
     */
    /**
     * Navigates the current list of moves to the last one.
     *
     *
     * Same as invoking `gotoMove(getCurrentMoveList(), getCurrentMoveList().size() - 1)`.
     *
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     * @see Game.gotoMove
     */
    @JvmOverloads
    @Throws(MoveException::class)
    fun gotoLast(moves: MoveList = currentMoveList!!) {
        gotoMove(moves, halfMoves.size - 1)
    }

    /**
     * Navigates the list of moves to the next one in the position.
     *
     *
     * Same as invoking `gotoMove(moves, getPosition() + 1)`.
     *
     * @param moves the moves to navigate
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     * @see Game.gotoMove
     */
    /**
     * Navigates the current list of moves to the next one in the position.
     *
     *
     * Same as invoking `gotoMove(getCurrentMoveList(), getPosition() + 1)`.
     *
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     * @see Game.gotoMove
     */
    @JvmOverloads
    @Throws(MoveException::class)
    fun gotoNext(moves: MoveList = currentMoveList!!) {
        gotoMove(moves, position + 1)
    }

    /**
     * Navigates the list of moves to the previous one in the position.
     *
     *
     * Same as invoking `gotoMove(moves, getPosition() - 1)`.
     *
     * @param moves the moves to navigate
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     * @see Game.gotoMove
     */
    /**
     * Navigates the current list of moves to the previous one in the position.
     *
     *
     * Same as invoking `gotoMove(getCurrentMoveList(), getPosition() - 1)`.
     *
     * @throws MoveException if any error occurs browsing the list of moves, for instance if a move is illegal for the
     * position
     * @see Game.gotoMove
     */
    @JvmOverloads
    @Throws(MoveException::class)
    fun gotoPrior(moves: MoveList = currentMoveList!!) {
        gotoMove(moves, position - 1)
    }

    val isEndOfMoveList: Boolean
        /**
         * Checks if the current move in the position has reached the end of the active list of moves.
         *
         * @return `true` if the position has reached the last move in the active list of moves, or the latter is
         * empty
         */
        get() = currentMoveList == null || position >= currentMoveList!!.size - 1

    val isStartOfMoveList: Boolean
        /**
         * Checks if the current move in the position points to the fist one in the active list of moves.
         *
         * @return `true` if the current move is the first one in the active list of moves, or the latter is empty
         */
        get() = currentMoveList == null && position == 0

    /**
     * Internal game structure used to define text moves variations.
     */
    internal class RTextEntry
    /**
     * Constructs a new variation.
     *
     * @param index the move index in the parent line from which the variation branches out
     */(
        /**
         * The index in the parent line from which this variation branches out.
         */
        var index: Int
    ) {
        /**
         * The size of this variation.
         */
        var size: Int = 0

        /**
         * The textual representation of moves of this variation.
         */
        var text: StringBuilder = StringBuilder()
    }

    companion object {
        private fun makeProp(name: String?, value: String?): String {
            return "[$name \"$value\"]\n"
        }

        private fun getMovesAt(moves: String, index: Int): String {
            val b = StringBuilder()
            var count = 0
            for (m in moves.split(StringUtils.SPACE)) {
                count++
                if (count >= index) {
                    break
                }
                b.append(m)
                b.append(' ')
            }
            return b.toString()
        }
    }
}
