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

import com.github.bhlangonijr.chesslib.CastleRight
import com.github.bhlangonijr.chesslib.Constants
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move

/**
 * The definition of a game context, a support structure used to provide contextual information to a chess position, and
 * most importantly to validate special moves in a uniform and consistent way according to the chess variation (e.g.
 * castle moves).
 */
class GameContext @JvmOverloads constructor(
    gameMode: GameMode? = GameMode.HUMAN_VS_HUMAN,
    variationType: VariationType = VariationType.NORMAL
) {
    /**
     * Returns white king move in case of short castle.
     *
     * @return the short castle white king move
     */
    /**
     * Sets the white king move in case of short castle.
     *
     * @param whiteoo the short castle white king move to set
     */
    /**
     * The definition of the white king shift in the default short castle move.
     */
    lateinit var whiteoo: Move
    /**
     * Returns white king move in case of long castle.
     *
     * @return the long castle white king move
     */
    /**
     * Sets the white king move in case of long castle.
     *
     * @param whiteooo the long castle white king move to set
     */
    /**
     * The definition of the white king shift in the default long castle move.
     */
    lateinit var whiteooo: Move
    /**
     * Returns black king move in case of short castle.
     *
     * @return the short castle black king move
     */
    /**
     * Sets the black king move in case of short castle.
     *
     * @param blackoo the short castle black king move to set
     */
    /**
     * The definition of the black king shift in the default short castle move.
     */
    lateinit var blackoo: Move
    /**
     * Returns black king move in case of long castle.
     *
     * @return the long castle black king move
     */
    /**
     * Sets the black king move in case of long castle.
     *
     * @param blackooo the long castle black king move to set
     */
    /**
     * The definition of the black king shift in the default long castle move.
     */
    lateinit var blackooo: Move

    /**
     * Returns white rook move in case of short castle.
     *
     * @return the short castle white rook move
     */
    /**
     * Sets the white rook move in case of short castle.
     *
     * @param whiteRookoo the short castle white rook move to set
     */
    /**
     * The definition of the white rook shift in the default short castle move.
     */
    lateinit var whiteRookoo: Move
    /**
     * Returns white rook move in case of long castle.
     *
     * @return the long castle white rook move
     */
    /**
     * Sets the white rook move in case of long castle.
     *
     * @param whiteRookooo the long castle white rook move to set
     */
    /**
     * The definition of the white rook shift in the default long castle move.
     */
    lateinit var whiteRookooo: Move
    /**
     * Returns black rook move in case of short castle.
     *
     * @return the short castle black rook move
     */
    /**
     * Sets the black rook move in case of short castle.
     *
     * @param blackRookoo the short castle black rook move to set
     */
    /**
     * The definition of the black rook shift in the default short castle move.
     */
    lateinit var blackRookoo: Move
    /**
     * Returns black rook move in case of long castle.
     *
     * @return the long castle black rook move
     */
    /**
     * Sets the black rook move in case of long castle.
     *
     * @param blackRookooo the long castle black rook move to set
     */
    /**
     * The definition of the black rook shift in the default long castle move.
     */
    lateinit var blackRookooo: Move

    /**
     * Returns the list of squares crossed by the white king in the short castle move.
     *
     * @return the squares crossed by the white king in the short castle move
     */
    /**
     * Sets the list of squares crossed by the white king in the short castle move.
     *
     * @param whiteooSquares the list of squares to set
     */
    /**
     * The list of squares crossed by the white king in the default short castle move.
     */
    lateinit var whiteooSquares: List<Square?>
    /**
     * Returns the list of squares crossed by the white king in the long castle move.
     *
     * @return the squares crossed by the white king in the long castle move
     */
    /**
     * Sets the list of squares crossed by the white king in the long castle move.
     *
     * @param whiteoooSquares the list of squares to set
     */
    /**
     * The list of squares crossed by the white king in the default long castle move.
     */
    lateinit var whiteoooSquares: List<Square?>
    /**
     * Returns the list of squares crossed by the black king in the short castle move.
     *
     * @return the squares crossed by the black king in the short castle move
     */
    /**
     * Sets the list of squares crossed by the black king in the short castle move.
     *
     * @param blackooSquares the list of squares to set
     */
    /**
     * The list of squares crossed by the black king in the default short castle move.
     */
    lateinit var blackooSquares: List<Square?>
    /**
     * Returns the list of squares crossed by the black king in the long castle move.
     *
     * @return the squares crossed by the black king in the long castle move
     */
    /**
     * Sets the list of squares crossed by the black king in the long castle move.
     *
     * @param blackoooSquares the list of squares to set
     */
    /**
     * The list of squares crossed by the black king in the default long castle move.
     */
    lateinit var blackoooSquares: List<Square?>

    /**
     * Returns the bitboard representation of the squares crossed by the white king in the short castle move.
     *
     * @return the bitboard representing the squares crossed by the white king in the short castle move
     */
    /**
     * Sets the bitboard representation of the squares crossed by the white king in the short castle move.
     *
     * @param whiteooSquaresBb the bitboard to set
     */
    /**
     * The bitboard representation of the squares crossed by the white king in the default short castle move.
     */
    var whiteooSquaresBb: Long = 0
    /**
     * Returns the bitboard representation of the squares crossed by the white king in the long castle move.
     *
     * @return the bitboard representing the squares crossed by the white king in the long castle move
     */
    /**
     * Sets the bitboard representation of the squares crossed by the white king in the long castle move.
     *
     * @param whiteoooSquaresBb the bitboard to set
     */
    /**
     * The bitboard representation of the squares crossed by the white king in the default long castle move.
     */
    var whiteoooSquaresBb: Long = 0
    /**
     * Returns the bitboard representation of the squares crossed by the black king in the short castle move.
     *
     * @return the bitboard representing the squares crossed by the black king in the short castle move
     */
    /**
     * Sets the bitboard representation of the squares crossed by the black king in the short castle move.
     *
     * @param blackooSquaresBb the bitboard to set
     */
    /**
     * The bitboard representation of the squares crossed by the black king in the default short castle move.
     */
    var blackooSquaresBb: Long = 0
    /**
     * Returns the bitboard representation of the squares crossed by the black king in the long castle move.
     *
     * @return the bitboard representing the squares crossed by the black king in the long castle move
     */
    /**
     * Sets the bitboard representation of the squares crossed by the black king in the long castle move.
     *
     * @param blackoooSquaresBb the bitboard to set
     */
    /**
     * The bitboard representation of the squares crossed by the black king in the default long castle move.
     */
    var blackoooSquaresBb: Long = 0

    /**
     * Returns the bitboard representation of all the squares involved in the short castle move of white.
     *
     * @return the bitboard representing the squares involved in the short castle move of white
     */
    /**
     * The bitboard representation of all the squares involved in the default short castle move of white.
     */
    var whiteooAllSquaresBb: Long = 0
        protected set
    /**
     * Returns the bitboard representation of all the squares involved in the long castle move of white.
     *
     * @return the bitboard representing the squares involved in the long castle move of white
     */
    /**
     * The bitboard representation of all the squares involved in the default long castle move of white.
     */
    var whiteoooAllSquaresBb: Long = 0
        protected set
    /**
     * Returns the bitboard representation of all the squares involved in the short castle move of black.
     *
     * @return the bitboard representing the squares involved in the short castle move of black
     */
    /**
     * The bitboard representation of all the squares involved in the default short castle move of black.
     */
    var blackooAllSquaresBb: Long = 0
        protected set
    /**
     * Returns the bitboard representation of all the squares involved in the long castle move of black.
     *
     * @return the bitboard representing the squares involved in the long castle move of black
     */
    /**
     * The bitboard representation of all the squares involved in the default long castle move of black.
     */
    var blackoooAllSquaresBb: Long = 0
        protected set

    /**
     * Returns the initial position of the game as a Forsyth-Edwards Notation (FEN) string.
     *
     * @return the initial position in FEN notation
     */
    /**
     * Sets the initial position of the game, provided as a Forsyth-Edwards Notation (FEN).
     *
     * @param startFEN the initial position to set
     */
    /**
     * The initial position of the game, as a Forsyth-Edwards Notation (FEN) string.
     */
    lateinit var startFEN: String

    /**
     * Returns the game mode.
     *
     * @return the game mode
     */
    /**
     * Sets the game mode.
     *
     * @param gameMode the game mode to set
     */
    /**
     * The game mode.
     */
    var gameMode: GameMode? = null
    /**
     * Returns the type of the chess variation.
     *
     * @return the type of the chess variation
     */
    /**
     * Sets the type of the chess variation.
     *
     * @param variationType the chess variation type to set
     */
    /**
     * The type of the chess variation.
     */
    var variationType: VariationType? = null
    /**
     * Returns the chess event.
     *
     * @return the chess event
     */
    /**
     * Sets the chess event.
     *
     * @param event the chess event to set
     */
    /**
     * The chess event.
     */
    var event: Event? = null

    /**
     * Constructs a new game context using the provided game mode and chess variation.
     *
     * @param gameMode      the game mode
     * @param variationType the chess variation
     */
    /**
     * Constructs a new game context using the default game mode and chess variation.
     *
     *
     * Same as invoking `new GameContext(GameMode.HUMAN_VS_HUMAN, VariationType.NORMAL)`.
     */
    init {
        this.gameMode = gameMode
        this.variationType = variationType
        if (variationType == VariationType.NORMAL) {
            loadDefaults()
        }
    }

    private fun loadDefaults() {
        //load standard values
        whiteoo = Constants.DEFAULT_WHITE_OO
        whiteooo = Constants.DEFAULT_WHITE_OOO
        blackoo = Constants.DEFAULT_BLACK_OO
        blackooo = Constants.DEFAULT_BLACK_OOO

        whiteRookoo = Constants.DEFAULT_WHITE_ROOK_OO
        whiteRookooo = Constants.DEFAULT_WHITE_ROOK_OOO
        blackRookoo = Constants.DEFAULT_BLACK_ROOK_OO
        blackRookooo = Constants.DEFAULT_BLACK_ROOK_OOO

        whiteooSquares = Constants.DEFAULT_WHITE_OO_SQUARES
        whiteoooSquares = Constants.DEFAULT_WHITE_OOO_SQUARES
        blackooSquares = Constants.DEFAULT_BLACK_OO_SQUARES
        blackoooSquares = Constants.DEFAULT_BLACK_OOO_SQUARES

        whiteooSquaresBb = squareListToBb(Constants.DEFAULT_WHITE_OO_SQUARES)
        whiteoooSquaresBb = squareListToBb(Constants.DEFAULT_WHITE_OOO_SQUARES)
        blackooSquaresBb = squareListToBb(Constants.DEFAULT_BLACK_OO_SQUARES)
        blackoooSquaresBb = squareListToBb(Constants.DEFAULT_BLACK_OOO_SQUARES)

        setWhiteooAllSquaresBb(squareListToBb(Constants.DEFAULT_WHITE_OO_ALL_SQUARES))
        setWhiteoooAllSquaresBb(squareListToBb(Constants.DEFAULT_WHITE_OOO_ALL_SQUARES))
        setBlackooAllSquaresBb(squareListToBb(Constants.DEFAULT_BLACK_OO_ALL_SQUARES))
        setBlackoooAllSquaresBb(squareListToBb(Constants.DEFAULT_BLACK_OOO_ALL_SQUARES))

        startFEN = Constants.startStandardFENPosition
    }

    /**
     * Returns an unambiguous castle move by king given the provided side and castle rights, if possible. A castle move
     * is not ambiguous if only one side of the board has castle rights, either king-side or queen-side (not both).
     *
     * @param side        the side to move
     * @param castleRight the castle rights available for the moving side
     * @return the move representing the castle move of the king, if available and not ambiguous, or null otherwise
     */
    fun getKingCastleMove(side: Side, castleRight: CastleRight): Move? {
        var move: Move? = null
        if (Side.WHITE == side) {
            if (CastleRight.KING_SIDE == castleRight) {
                move = whiteoo
            } else if (CastleRight.QUEEN_SIDE == castleRight) {
                move = whiteooo
            }
        } else {
            if (CastleRight.KING_SIDE == castleRight) {
                move = blackoo
            } else if (CastleRight.QUEEN_SIDE == castleRight) {
                move = blackooo
            }
        }
        return move
    }

    /**
     * Returns an unambiguous castle move by rook given the provided side and castle rights, if possible. A castle move
     * is not ambiguous if only one side of the board has castle rights, either king-side or queen-side (not both).
     *
     * @param side        the side to move
     * @param castleRight the castle rights available for the moving side
     * @return the move representing the castle move of the rook, if available and not ambiguous, or null otherwise
     */
    fun getRookCastleMove(side: Side?, castleRight: CastleRight): Move? {
        var move: Move? = null
        if (Side.WHITE == side) {
            if (CastleRight.KING_SIDE == castleRight) {
                move = whiteRookoo
            } else if (CastleRight.QUEEN_SIDE == castleRight) {
                move = whiteRookooo
            }
        } else {
            if (CastleRight.KING_SIDE == castleRight) {
                move = blackRookoo
            } else if (CastleRight.QUEEN_SIDE == castleRight) {
                move = blackRookooo
            }
        }
        return move
    }

    /**
     * Checks if the move is a castle move or not.
     *
     * @param move the move to check
     * @return `true` if the move is a castle one
     */
    fun isCastleMove(move: Move): Boolean {
        return move == whiteoo || move == whiteooo || move == blackoo || move == blackooo
    }

    /**
     * Checks if the castle move is valid according to the castle rights.
     *
     * @param move        the move to check
     * @param castleRight the castle rights to compare the move against
     * @return `true` if the castle move is valid
     */
    fun hasCastleRight(move: Move, castleRight: CastleRight?): Boolean {
        val r = castleRight

        return (CastleRight.KING_AND_QUEEN_SIDE == r) ||
                (move == whiteoo && CastleRight.KING_SIDE == r) ||
                (move == blackoo && CastleRight.KING_SIDE == r) ||
                (move == whiteooo && CastleRight.QUEEN_SIDE == r) ||
                (move == blackooo && CastleRight.QUEEN_SIDE == r)
    }

    /**
     * Checks if the move is a king-side (short) castle move.
     *
     * @param move the move to check
     * @return `true` if the move is a king-side castle one
     */
    fun isKingSideCastle(move: Move): Boolean {
        return move == whiteoo || move == blackoo
    }

    /**
     * Checks if the move is a queen-side (long) castle move.
     *
     * @param move the move to check
     * @return `true` if the move is a queen-side castle one
     */
    fun isQueenSideCastle(move: Move): Boolean {
        return move == whiteooo || move == blackooo
    }

    /**
     * Sets the bitboard representation of all the squares involved in the short castle move of white.
     *
     * @param whiteooAllSquaresBb the bitboard to set
     * @return this game context instance
     */
    fun setWhiteooAllSquaresBb(whiteooAllSquaresBb: Long): GameContext {
        this.whiteooAllSquaresBb = whiteooAllSquaresBb
        return this
    }

    /**
     * Sets the bitboard representation of all the squares involved in the long castle move of white.
     *
     * @param whiteoooAllSquaresBb the bitboard to set
     * @return this game context instance
     */
    fun setWhiteoooAllSquaresBb(whiteoooAllSquaresBb: Long): GameContext {
        this.whiteoooAllSquaresBb = whiteoooAllSquaresBb
        return this
    }

    /**
     * Sets the bitboard representation of all the squares involved in the short castle move of black.
     *
     * @param blackooAllSquaresBb the bitboard to set
     * @return this game context instance
     */
    fun setBlackooAllSquaresBb(blackooAllSquaresBb: Long): GameContext {
        this.blackooAllSquaresBb = blackooAllSquaresBb
        return this
    }

    /**
     * Sets the bitboard representation of all the squares involved in the long castle move of black.
     *
     * @param blackoooAllSquaresBb the bitboard to set
     * @return this game context instance
     */
    fun setBlackoooAllSquaresBb(blackoooAllSquaresBb: Long): GameContext {
        this.blackoooAllSquaresBb = blackoooAllSquaresBb
        return this
    }

    /**
     * Returns the short castle king move based on the side to move.
     *
     * @param side the side to move
     * @return the short castle king move
     */
    fun getoo(side: Side?): Move {
        return if (Side.WHITE == side) whiteoo else blackoo
    }

    /**
     * Returns the long castle king move based on the side to move.
     *
     * @param side the side to move
     * @return the long castle king move
     */
    fun getooo(side: Side?): Move {
        return if (Side.WHITE == side) whiteooo else blackooo
    }

    /**
     * Returns the short castle rook move based on the side to move.
     *
     * @param side the side to move
     * @return the short castle rook move
     */
    fun getRookoo(side: Side?): Move {
        return if (Side.WHITE == side) whiteRookoo else blackRookoo
    }

    /**
     * Returns the long castle rook move based on the side to move.
     *
     * @param side the side to move
     * @return the long castle rook move
     */
    fun getRookooo(side: Side?): Move {
        return if (Side.WHITE == side) whiteRookooo else blackRookooo
    }

    /**
     * Returns the list of squares crossed by the king in the short castle move based on the side to move.
     *
     * @param side the side to move
     * @return the squares crossed by the king in the short castle move
     */
    fun getooSquares(side: Side?): List<Square?> {
        return if (Side.WHITE == side) whiteooSquares else blackooSquares
    }

    /**
     * Returns the list of squares crossed by the king in the long castle move based on the side to move.
     *
     * @param side the side to move
     * @return the squares crossed by the king in the long castle move
     */
    fun getoooSquares(side: Side?): List<Square?> {
        return if (Side.WHITE == side) whiteoooSquares else blackoooSquares
    }

    /**
     * Returns the bitboard representation of the squares crossed by the king in the short castle move based on the side
     * to move.
     *
     * @param side the side to move
     * @return the bitboard representing the squares crossed by the king in the short castle move
     */
    fun getooSquaresBb(side: Side): Long {
        return if (Side.WHITE == side) whiteooSquaresBb else blackooSquaresBb
    }

    /**
     * Returns the bitboard representation of the squares crossed by the king in the long castle move based on the side
     * to move.
     *
     * @param side the side to move
     * @return the bitboard representing the squares crossed by the king in the long castle move
     */
    fun getoooSquaresBb(side: Side): Long {
        return if (Side.WHITE == side) whiteoooSquaresBb else blackoooSquaresBb
    }

    /**
     * Returns the bitboard representation of all the squares involved in the short castle move based on the side to
     * move.
     *
     * @param side the side to move
     * @return the bitboard representing the squares involved in the short castle move
     */
    fun getooAllSquaresBb(side: Side?): Long {
        return if (Side.WHITE == side) whiteooAllSquaresBb else blackooAllSquaresBb
    }

    /**
     * Returns the bitboard representation of all the squares involved in the long castle move based on the side to
     * move.
     *
     * @param side the side to move
     * @return the bitboard representing the squares involved in the long castle move
     */
    fun getoooAllSquaresBb(side: Side?): Long {
        return if (Side.WHITE == side) whiteoooAllSquaresBb else blackoooAllSquaresBb
    }

    companion object {
        private fun squareListToBb(list: List<Square>): Long {
            var r = 0L
            for (s in list) {
                r = r or s.bitboard
            }
            return r
        }
    }
}
