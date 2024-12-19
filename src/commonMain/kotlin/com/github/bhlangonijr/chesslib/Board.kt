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
package com.github.bhlangonijr.chesslib

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.game.GameContext
import com.github.bhlangonijr.chesslib.move.Move
import com.github.bhlangonijr.chesslib.move.MoveGenerator
import com.github.bhlangonijr.chesslib.move.MoveList
import com.github.bhlangonijr.chesslib.util.XorShiftRandom
import kotlin.jvm.JvmField
import kotlin.jvm.JvmOverloads
import kotlin.math.abs
import kotlin.math.min

/**
 * The definition of a chessboard position and its status. It exposes methods to manipulate the board, evolve the
 * position moving pieces around, revert already performed moves, and retrieve the status of the current configuration
 * on the board. Furthermore, it offers a handy way for loading a position from a Forsyth-Edwards Notation (FEN) string
 * and exporting it in the same format.
 *
 *
 * Each position in uniquely identified by hashes that could be retrieved using [Board.getIncrementalHashKey]
 * and [Board.getZobristKey] methods. Also, the implementation supports comparison against other board instances
 * using either the strict ([Board.strictEquals]) or the non-strict ([Board.equals]) mode.
 *
 *
 * The board can be observed registering [BoardEventListener]s for particular types of events. Moreover, the
 * [Board] class itself is a [BoardEvent], and hence it can be passed to the observers of the
 * [BoardEventType.ON_LOAD] events, emitted when a new chess position is loaded from an external source (e.g. a
 * FEN string).
 */
class Board @JvmOverloads constructor(
    gameContext: GameContext = GameContext(),
    private val updateHistory: Boolean = true
) : BoardEvent {
    /**
     * Returns the current ordered list of move backups generated from the moves performed on the board.
     *
     * @return the list of move backups
     */
    @JvmField
    val backup: MutableList<MoveBackup> = mutableListOf()
    private val eventListener = mutableMapOf<BoardEventType, MutableList<BoardEventListener>>()
    private val bitboard = LongArray(Piece.allPieces.size)

    /**
     * Returns the bitboards that represents all the pieces present on the board, one for each side. The bitboard for
     * white is stored at index 0, the bitboard for black at index 1.
     *
     * @return the bitboards of all the pieces for both sides
     */
    val bbSide: LongArray = LongArray(Side.allSides.size)
    private val occupation = arrayOfNulls<Piece>(Square.entries.size)

    /**
     * Returns the castle rights for both sides, stored in an [EnumMap].
     *
     * @return the map containing the castle rights for both sides
     */
    val castleRight: MutableMap<Side, CastleRight?> = mutableMapOf()

    /**
     * Returns the history of the board, represented by the hashes of all the positions occurred on the board.
     *
     * @return the list of hashes of all the positions occurred on the board
     * @see Board.getIncrementalHashKey
     */
    @JvmField
    val history: MutableList<Long> = mutableListOf()
    /**
     * Returns the next side to move.
     *
     * @return the next side to move
     */
    /**
     * Sets the next side to move.
     *
     * @param sideToMove the side to move to set
     */
    @JvmField
    var sideToMove: Side
    /**
     * Returns the target square of an en passant capture, if any. In other words, the square which contains the pawn
     * that can be captured en passant.
     *
     * @return the en passant target square, or [Square.NONE] if en passant is not possible
     * @see Board.getEnPassant
     */
    /**
     * Sets the en passant target square.
     *
     * @param enPassant the en passant target square to set
     * @see Board.getEnPassantTarget
     */
    @JvmField
    var enPassantTarget: Square
    /**
     * Returns the destination square of an en passant capture, if any. In other words, the square a pawn will move to
     * in case an enemy pawn is captured en passant.
     *
     * @return the en passant destination square, or [Square.NONE] if en passant is not possible
     * @see Board.getEnPassantTarget
     */
    /**
     * Sets the en passant destination square.
     *
     * @param enPassant the en passant destination square to set
     * @see Board.getEnPassant
     */
    @JvmField
    var enPassant: Square
    /**
     * Returns the counter of full moves played. The counter is incremented after each move played by black.
     *
     * @return the counter of full moves
     */
    /**
     * Sets the counter of full moves.
     *
     * @param moveCounter the counter of full moves to set
     * @see Board.getMoveCounter
     */
    @JvmField
    var moveCounter: Int
    /**
     * Returns the counter of half moves. The counter is incremented after each capture or pawn move, and it is used to
     * apply the fifty-move rule.
     *
     * @return the counter of half moves
     */
    /**
     * Sets the counter of half moves.
     *
     * @param halfMoveCounter the counter of half moves to set
     * @see Board.getHalfMoveCounter
     */
    @JvmField
    var halfMoveCounter: Int
    /**
     * Returns the game context used for this board.
     *
     * @return the game context
     */
    /**
     * Sets the game context of the board.
     *
     * @param context the game context to set
     */
    var context: GameContext = gameContext
    /**
     * Returns whether the notifications of board events are enabled or not.
     *
     * @return `true` if board events are notified to observers
     */
    /**
     * Sets the flag that controls the notification of board events. If `true`, board events are emitted,
     * otherwise they are turned off.
     *
     * @param enableEvents whether the notification of board events is enabled or not
     */
    var isEnableEvents: Boolean = false
    /**
     * Returns the current incremental hash key. This hash value changes every time the position changes, hence it is
     * unique for every position.
     *
     * @return the current incremental hash key
     */
    /**
     * Sets the current incremental hash key, replacing the previous one.
     *
     * @param hashKey the incremental hash key to set
     */
    @JvmField
    var incrementalHashKey: Long = 0

    /**
     * Constructs a new board, using the game context provided in input. When history updates are enabled, the board
     * will keep the hashes of all positions encountered.
     *
     * @param gameContext   the game context to use for this board
     * @param updateHistory whether to keep the history updated or not
     */
    /**
     * Constructs a new board using a default game context. The board will keep its history updated, that is, will store
     * a hash value for each position encountered.
     *
     * @see Board.Board
     */
    init {
        sideToMove = Side.WHITE
        enPassantTarget = Square.NONE
        enPassant = Square.NONE
        moveCounter = 1
        halfMoveCounter = 0
        for (evt in BoardEventType.entries) {
            eventListener[evt] = mutableListOf()
        }
        loadFromFen(gameContext.startFEN)
        isEnableEvents = true
    }

    /**
     * Executes a move on the board, specified in Short Algebraic Notation (SAN). It returns `true` if the
     * operation has been successful and the position changed after the move. It performs a full validation of the board
     * status to assess the outcome of the operation.
     *
     *
     * **N.B.**: the method does not check whether the move is legal or not according to the standard chess rules,
     * but rather if the resulting configuration is valid. For instance, it is totally fine to move the king by two or
     * more squares, or a rook beyond its friendly pieces, as long as the position obtained after the move does not
     * violate any chess constraint.
     *
     * @param move the move to execute in SAN notation, such as `Nc3`
     * @return `true` if the move was successful and the resulting position is valid
     */
    fun doMove(move: String): Boolean {
        val moves = MoveList(this.fen)
        moves.addSanMove(move, true, true)
        return doMove(moves.removeLast(), true)
    }

    /**
     * Executes a move on the board. It returns `true` if the operation has been successful and the position
     * changed after the move. When a full validation is requested, additional checks are performed to assess the
     * outcome of the operation, such as if the side to move is the expected one, if castling or promotion moves are
     * allowed, if the move replaces another piece of the same side, etc.
     *
     *
     * **N.B.**: the method does not check whether the move is legal or not according to the standard chess rules,
     * but rather if the resulting configuration is valid. For instance, it is totally fine to move the king by two or
     * more squares, or a rook beyond its friendly pieces, as long as the position obtained after the move does not
     * violate any chess constraint.
     *
     * @param move           the move to execute
     * @param fullValidation whether to perform a full validation of the position or not
     * @return `true` if the move was successful and the resulting position is valid
     */
    /**
     * Executes a move on the board without performing a full validation of the position. It returns `true` if the
     * operation has been successful and the position changed after the move.
     *
     *
     * Same as invoking `doMove(move, false)`.
     *
     * @param move the move to execute
     * @return `true` if the move was successful and the resulting position is valid
     * @see .doMove
     */
    @JvmOverloads
    fun doMove(move: Move, fullValidation: Boolean = false): Boolean {
        if (!isMoveLegal(move, fullValidation)) {
            return false
        }

        val movingPiece = getPiece(move.from)
        val side = sideToMove

        val backupMove = MoveBackup(this, move)
        val isCastle = context.isCastleMove(move)

        incrementalHashKey = incrementalHashKey xor getSideKey(sideToMove)
        if (enPassantTarget != Square.NONE) {
            incrementalHashKey = incrementalHashKey xor getEnPassantKey(enPassantTarget)
        }

        if (PieceType.KING == movingPiece.pieceType) {
            if (isCastle) {
                if (context.hasCastleRight(move, getCastleRight(side))) {
                    val c =
                        if (context.isKingSideCastle(move)) CastleRight.KING_SIDE else CastleRight.QUEEN_SIDE
                    val rookMove = context.getRookCastleMove(side, c)
                    movePiece(rookMove!!, backupMove)
                } else {
                    return false
                }
            }
            if (getCastleRight(side) != CastleRight.NONE) {
                incrementalHashKey = incrementalHashKey xor getCastleRightKey(side)
                castleRight[side] = CastleRight.NONE
            }
        } else if (PieceType.ROOK == movingPiece.pieceType
            && CastleRight.NONE != getCastleRight(side)
        ) {
            val oo = context.getRookoo(side)
            val ooo = context.getRookooo(side)

            if (move.from == oo.from) {
                if (CastleRight.KING_AND_QUEEN_SIDE == getCastleRight(side)) {
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side)
                    castleRight[side] = CastleRight.QUEEN_SIDE
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side)
                } else if (CastleRight.KING_SIDE == getCastleRight(side)) {
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side)
                    castleRight[side] = CastleRight.NONE
                }
            } else if (move.from == ooo.from) {
                if (CastleRight.KING_AND_QUEEN_SIDE == getCastleRight(side)) {
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side)
                    castleRight[side] = CastleRight.KING_SIDE
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side)
                } else if (CastleRight.QUEEN_SIDE == getCastleRight(side)) {
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side)
                    castleRight[side] = CastleRight.NONE
                }
            }
        }

        val capturedPiece = movePiece(move, backupMove)

        if (PieceType.ROOK == capturedPiece.pieceType) {
            val oo = context.getRookoo(side.flip())
            val ooo = context.getRookooo(side.flip())
            if (move.to == oo.from) {
                if (CastleRight.KING_AND_QUEEN_SIDE == getCastleRight(side.flip())) {
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side.flip())
                    castleRight[side.flip()] = CastleRight.QUEEN_SIDE
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side.flip())
                } else if (CastleRight.KING_SIDE == getCastleRight(side.flip())) {
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side.flip())
                    castleRight[side.flip()] = CastleRight.NONE
                }
            } else if (move.to == ooo.from) {
                if (CastleRight.KING_AND_QUEEN_SIDE == getCastleRight(side.flip())) {
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side.flip())
                    castleRight[side.flip()] = CastleRight.KING_SIDE
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side.flip())
                } else if (CastleRight.QUEEN_SIDE == getCastleRight(side.flip())) {
                    incrementalHashKey = incrementalHashKey xor getCastleRightKey(side.flip())
                    castleRight[side.flip()] = CastleRight.NONE
                }
            }
        }

        if (Piece.NONE == capturedPiece) {
            halfMoveCounter = halfMoveCounter + 1
        } else {
            halfMoveCounter = 0
        }

        enPassantTarget = Square.NONE
        enPassant = Square.NONE

        if (PieceType.PAWN == movingPiece.pieceType) {
            if (abs(
                    (move.to.rank.ordinal - move.from.rank.ordinal)
                ) == 2
            ) {
                val otherPawn: Piece = Piece.make(side.flip(), PieceType.PAWN)
                enPassant = findEnPassant(move.to, side)
                if (hasPiece(otherPawn, move.to.sideSquares) &&
                    verifyNotPinnedPiece(side, enPassant, move.to)
                ) {
                    enPassantTarget = move.to
                    incrementalHashKey = incrementalHashKey xor getEnPassantKey(enPassantTarget)
                }
            }
            halfMoveCounter = 0
        }

        if (side == Side.BLACK) {
            moveCounter = moveCounter + 1
        }

        sideToMove = side.flip()
        incrementalHashKey = incrementalHashKey xor getSideKey(sideToMove)

        if (updateHistory) {
            history.add(incrementalHashKey)
        }

        backup.add(backupMove)
        // call listeners
        if (isEnableEvents && eventListener[BoardEventType.ON_MOVE]!!.size > 0) {
            for (evl in eventListener[BoardEventType.ON_MOVE]!!) {
                evl.onEvent(move)
            }
        }
        return true
    }

    /**
     * Executes a *null* move on the board. It returns `true` if the operation has been successful.
     *
     *
     * A null move it is a special move that does not change the position of any piece, but simply updates the history
     * of the board and switches the side to move. It could be useful in some scenarios to implement a *"passing
     * turn"* behavior.
     *
     * @return `true` if the null move was successful
     */
    fun doNullMove(): Boolean {
        val side = sideToMove
        val backupMove = MoveBackup(this, Constants.emptyMove)

        halfMoveCounter = halfMoveCounter + 1

        enPassantTarget = Square.NONE
        enPassant = Square.NONE

        incrementalHashKey = incrementalHashKey xor getSideKey(sideToMove)
        sideToMove = side.flip()
        incrementalHashKey = incrementalHashKey xor getSideKey(sideToMove)
        if (updateHistory) {
            history.add(incrementalHashKey)
        }
        backup.add(backupMove)
        return true
    }

    /**
     * Reverts the latest move played on the board and returns it. If no moves were previously executed, it returns
     * null.
     *
     * @return the reverted move, or null if no previous moves were played
     */
    fun undoMove(): Move? {
        var move: Move? = null
        val b = backup.removeLastOrNull()
        if (updateHistory) {
            history.removeLastOrNull()
        }
        if (b != null) {
            move = b.move
            b.restore(this)
        }
        // call listeners
        if (isEnableEvents &&
            eventListener[BoardEventType.ON_UNDO_MOVE]!!.size > 0
        ) {
            for (evl in eventListener[BoardEventType.ON_UNDO_MOVE]!!) {
                evl.onEvent(b)
            }
        }
        return move
    }

    /**
     * Moves a piece on the board and updates the backup passed in input. It returns the captured piece, if any, or
     * [Piece.NONE] otherwise.
     *
     *
     * Same as invoking `movePiece(move.getFrom(), move.getTo(), move.getPromotion(), backup)`.
     *
     * @param move   the move to perform
     * @param backup the move backup to update
     * @return the captured piece, if present, or [Piece.NONE] otherwise
     * @see Board.movePiece
     */
    protected fun movePiece(move: Move, backup: MoveBackup?): Piece {
        return movePiece(move.from, move.to, move.promotion, backup)
    }

    /**
     * Moves a piece on the board and updates the backup passed in input. It returns the captured piece, if any, or
     * [Piece.NONE] otherwise. The piece movement is described by its starting and destination squares, and by the
     * piece to promote the moving piece to in case of a promotion.
     *
     * @param from      the starting square of the piece
     * @param to        the destination square of the piece
     * @param promotion the piece to set on the board to replace the moving piece after its promotion, or
     * [Piece.NONE] in case the move is not a promotion
     * @param backup    the move backup to update
     * @return the captured piece, if present, or [Piece.NONE] otherwise
     */
    protected fun movePiece(
        from: Square,
        to: Square,
        promotion: Piece,
        backup: MoveBackup?
    ): Piece {
        val movingPiece = getPiece(from)
        var capturedPiece = getPiece(to)

        unsetPiece(movingPiece, from)
        if (Piece.NONE != capturedPiece) {
            unsetPiece(capturedPiece, to)
        }
        if (Piece.NONE != promotion) {
            setPiece(promotion, to)
        } else {
            setPiece(movingPiece, to)
        }

        if (PieceType.PAWN == movingPiece.pieceType &&
            Square.NONE != enPassantTarget &&
            to.file != from.file && Piece.NONE == capturedPiece
        ) {
            capturedPiece = getPiece(enPassantTarget)
            if (backup != null && Piece.NONE != capturedPiece) {
                unsetPiece(capturedPiece, enPassantTarget)
                backup.capturedSquare = enPassantTarget
                backup.capturedPiece = capturedPiece
            }
        }
        return capturedPiece
    }

    /**
     * Reverts the effects of a piece previously moved. It restores the moved piece where it was and cancels any
     * possible promotion to another piece.
     *
     * @param move the move to undo
     */
    fun undoMovePiece(move: Move) {
        val from = move.from
        val to = move.to
        val promotion = move.promotion
        val movingPiece = getPiece(to)

        unsetPiece(movingPiece, to)

        if (Piece.NONE != promotion) {
            setPiece(Piece.make(sideToMove, PieceType.PAWN), from)
        } else {
            setPiece(movingPiece, from)
        }
    }

    /**
     * Searches the piece in any of the squares provided in input and returns `true` if found.
     *
     * @param piece    the piece to search in any of the given squares
     * @param location an array of squares where to look the piece for
     * @return `true` if the piece is found
     */
    fun hasPiece(piece: Piece, location: Array<Square?>): Boolean {
        for (sq in location.filterNotNull()) {
            if ((getBitboard(piece) and sq.bitboard) != 0L) {
                return true
            }
        }
        return false
    }

    /**
     * Returns the piece at the specified square, or [Piece.NONE] if the square is empty.
     *
     * @param sq the square to get the piece from
     * @return the found piece, or [Piece.NONE] if no piece is present on the square
     */
    fun getPiece(sq: Square): Piece {
        return occupation[sq.ordinal]!!
    }

    /**
     * Returns the bitboard that represents all the pieces on the board, for both sides.
     *
     * @return the bitboard of all the pieces on the board
     */
    fun getBitboard(): Long {
        return bbSide[0] or bbSide[1]
    }

    /**
     * Returns the bitboard that represents all the pieces of a given side and type present on the board.
     *
     * @param piece the piece for which the bitboard must be returned
     * @return the bitboard of the given piece definition
     */
    fun getBitboard(piece: Piece): Long {
        return bitboard[piece.ordinal]
    }

    /**
     * Returns the bitboard that represents all the pieces of a given side present on the board.
     *
     * @param side the side for which the bitboard must be returned
     * @return the bitboard of all the pieces of the side
     */
    fun getBitboard(side: Side): Long {
        return bbSide[side.ordinal]
    }

    /**
     * Returns the list of squares that contain all the pieces of a given side and type.
     *
     * @param piece the piece for which the list of squares must be returned
     * @return the list of squares that contain the given piece definition
     */
    fun getPieceLocation(piece: Piece): List<Square?> {
        if (getBitboard(piece) != 0L) {
            return Bitboard.bbToSquareList(getBitboard(piece))
        }
        return emptyList<Square>()
    }

    /**
     * Returns the square of the first piece of a given side and type found on the board, scanning from lower
     * ranks/files. If no piece is found, [Square.NONE] is returned.
     *
     * @param piece the piece for which the first encountered square must be returned
     * @return the first square that contain the given piece definition, or [Square.NONE] if the piece is not
     * found
     */
    fun getFistPieceLocation(piece: Piece): Square {
        if (getBitboard(piece) != 0L) {
            return Square.squareAt(Bitboard.bitScanForward(getBitboard(piece)))
        }
        return Square.NONE
    }

    /**
     * Returns the castle right of a given side.
     *
     * @param side the side for which the castle right must be returned
     * @return the castle right of the side
     */
    fun getCastleRight(side: Side): CastleRight? {
        return castleRight[side]
    }

    /**
     * Clears the entire board and resets its status and all the flags to their default value.
     */
    fun clear() {
        sideToMove = Side.WHITE
        enPassantTarget = Square.NONE
        enPassant = Square.NONE
        moveCounter = 0
        halfMoveCounter = 0
        history.clear()

        bitboard.fill(0L)
        bbSide.fill(0L)
        occupation.fill(Piece.NONE)
        backup.clear()
        incrementalHashKey = 0
    }

    /**
     * Sets a piece on a square.
     *
     *
     * The operation does not perform any move, but rather simply puts a piece onto a square.
     *
     * @param piece the piece to be placed on the square
     * @param sq    the square the piece has to be set to
     */
    fun setPiece(piece: Piece, sq: Square) {
        bitboard[piece.ordinal] = bitboard[piece.ordinal] or sq.bitboard
        bbSide[piece.pieceSide!!.ordinal] = bbSide[piece.pieceSide.ordinal] or sq.bitboard
        occupation[sq.ordinal] = piece
        if (piece != Piece.NONE && sq != Square.NONE) {
            incrementalHashKey = incrementalHashKey xor getPieceSquareKey(piece, sq)
        }
    }

    /**
     * Unsets a piece from a square.
     *
     * @param piece the piece to be removed from the square
     * @param sq    the square the piece has to be unset from
     */
    fun unsetPiece(piece: Piece, sq: Square) {
        bitboard[piece.ordinal] = bitboard[piece.ordinal] xor sq.bitboard
        bbSide[piece.pieceSide!!.ordinal] = bbSide[piece.pieceSide.ordinal] xor sq.bitboard
        occupation[sq.ordinal] = Piece.NONE
        if (piece != Piece.NONE && sq != Square.NONE) {
            incrementalHashKey = incrementalHashKey xor getPieceSquareKey(piece, sq)
        }
    }

    /**
     * Loads a specific chess position from a valid Forsyth-Edwards Notation (FEN) string. The status of the current
     * board is replaced with the one of the FEN string (e.g. en passant squares, castle rights, etc.).
     *
     * @param fen the FEN string representing the chess position to load
     */
    fun loadFromFen(fen: String) {
        clear()
        val squares = fen.substring(0, fen.indexOf(' '))
        val state = fen.substring(fen.indexOf(' ') + 1)

        val ranks = squares.split("/")
        var file: Int
        var rank = 7
        for (r in ranks) {
            file = 0
            for (i in 0 until r.length) {
                val c = r[i]
                if (c.isDigit()) {
                    file += c.digitToIntOrNull() ?: -1
                } else {
                    val sq: Square = Square.encode(
                        Rank.allRanks.get(rank),
                        File.allFiles.get(file)
                    )
                    setPiece(Piece.fromFenSymbol(c.toString()), sq)
                    file++
                }
            }
            rank--
        }

        sideToMove = if (state.lowercase()[0] == 'w') Side.WHITE else Side.BLACK

        if (state.contains("KQ")) {
            castleRight[Side.WHITE] = CastleRight.KING_AND_QUEEN_SIDE
        } else if (state.contains("K")) {
            castleRight[Side.WHITE] = CastleRight.KING_SIDE
        } else if (state.contains("Q")) {
            castleRight[Side.WHITE] = CastleRight.QUEEN_SIDE
        } else {
            castleRight[Side.WHITE] = CastleRight.NONE
        }

        if (state.contains("kq")) {
            castleRight[Side.BLACK] = CastleRight.KING_AND_QUEEN_SIDE
        } else if (state.contains("k")) {
            castleRight[Side.BLACK] = CastleRight.KING_SIDE
        } else if (state.contains("q")) {
            castleRight[Side.BLACK] = CastleRight.QUEEN_SIDE
        } else {
            castleRight[Side.BLACK] = CastleRight.NONE
        }

        val flags = state.split(" ")
        if (flags.size >= 3) {
            val s = flags[2].uppercase().trim { it <= ' ' }
            if (s != "-") {
                val ep = Square.valueOf(s)
                enPassant = ep
                enPassantTarget = findEnPassantTarget(ep, sideToMove)
                if (!pawnCanBeCapturedEnPassant()) {
                    enPassantTarget = Square.NONE
                }
            } else {
                enPassant = Square.NONE
                enPassantTarget = Square.NONE
            }
            if (flags.size >= 4) {
                halfMoveCounter = flags[3].toInt()
                if (flags.size >= 5) {
                    moveCounter = flags[4].toInt()
                }
            }
        }

        incrementalHashKey = zobristKey
        if (updateHistory) {
            history.add(this.zobristKey)
        }
        // call listeners
        if (isEnableEvents &&
            eventListener[BoardEventType.ON_LOAD]!!.size > 0
        ) {
            for (evl in eventListener[BoardEventType.ON_LOAD]!!) {
                evl.onEvent(this@Board)
            }
        }
    }

    val fen: String
        /**
         * Generates the Forsyth-Edwards Notation (FEN) representation of the current position and its status. Full and half
         * moves counters are included in the output.
         *
         *
         * Same as invoking `getFen(true, false)`.
         *
         * @return the string that represents the current position in FEN notation
         * @see Board.getFen
         */
        get() = getFen(true)

    /**
     * Generates the Forsyth-Edwards Notation (FEN) representation of the current position and its status. Full and half
     * moves counters are included in the output if the relative flag is enabled.
     *
     *
     * Same as invoking `getFen(includeCounters, false)`.
     *
     * @param includeCounters if `true`, move counters are included in the resulting string
     * @return the string that represents the current position in FEN notation
     * @see Board.getFen
     */
    fun getFen(includeCounters: Boolean): String {
        return getFen(includeCounters, false)
    }

    /**
     * Generates the Forsyth-Edwards Notation (FEN) representation of the current position and its status. Full and half
     * moves counters are included in the output if the relative flag is enabled. Furthermore, it is possible to control
     * whether to include the en passant square in the result only when the pawn can be captured or every time the en
     * passant target exists.
     *
     * @param includeCounters                 if `true`, move counters are included in the resulting string
     * @param onlyOutputEnPassantIfCapturable if `true`, the en passant square is included in the output only if
     * the pawn that just moved can be captured. Otherwise, if `false`, the
     * en passant square is always included in the output when the en passant
     * target exists
     * @return the string that represents the current position in FEN notation
     */
    fun getFen(includeCounters: Boolean, onlyOutputEnPassantIfCapturable: Boolean): String {
        val fen = StringBuilder()
        var emptySquares = 0
        for (i in 7 downTo 0) {
            val r: Rank = Rank.allRanks.get(i)
            if (r == Rank.NONE) {
                continue
            }
            for (f in File.allFiles) {
                if (f == File.NONE) {
                    continue
                }
                val sq: Square = Square.encode(r, f)
                val piece = getPiece(sq)
                if (Piece.NONE == piece) {
                    emptySquares++
                } else {
                    if (emptySquares > 0) {
                        fen.append(emptySquares)
                        emptySquares = 0
                    }
                    fen.append(piece.fenSymbol)
                }
                if (f != File.FILE_H) {
                    continue
                }
                if (emptySquares > 0) {
                    fen.append(emptySquares)
                    emptySquares = 0
                }
                if (r != Rank.RANK_1) {
                    fen.append("/")
                }
            }
        }

        if (Side.WHITE == sideToMove) {
            fen.append(" w")
        } else {
            fen.append(" b")
        }

        var rights = ""
        if (CastleRight.KING_AND_QUEEN_SIDE == castleRight[Side.WHITE]) {
            rights += "KQ"
        } else if (CastleRight.KING_SIDE == castleRight[Side.WHITE]) {
            rights += "K"
        } else if (CastleRight.QUEEN_SIDE == castleRight[Side.WHITE]) {
            rights += "Q"
        }

        if (CastleRight.KING_AND_QUEEN_SIDE == castleRight[Side.BLACK]) {
            rights += "kq"
        } else if (CastleRight.KING_SIDE == castleRight[Side.BLACK]) {
            rights += "k"
        } else if (CastleRight.QUEEN_SIDE == castleRight[Side.BLACK]) {
            rights += "q"
        }

        if (rights.isEmpty()) {
            fen.append(" -")
        } else {
            fen.append(" " + rights)
        }

        if (Square.NONE == enPassant || (onlyOutputEnPassantIfCapturable
                    && !pawnCanBeCapturedEnPassant())
        ) {
            fen.append(" -")
        } else {
            fen.append(" ")
            fen.append(enPassant.toString().lowercase())
        }

        if (includeCounters) {
            fen.append(" ")
            fen.append(halfMoveCounter)
            fen.append(" ")
            fen.append(moveCounter)
        }

        return fen.toString()
    }

    /**
     * Returns an array of pieces that represents the current position on the board. For each index, the array holds
     * the piece present on the square with the same index, or [Piece.NONE] if the square is empty.
     *
     * @return the array that contains the pieces on the board
     */
    fun boardToArray(): Array<Piece> {
        val pieces = Array<Piece>(65) { Piece.NONE }

        for (square in Square.entries) {
            if (Square.NONE != square) {
                pieces[square.ordinal] = getPiece(square)
            }
        }

        return pieces
    }

    override val type: BoardEventType
        /**
         * The type of board events this data structure represents when notified to its observers.
         *
         * @return the board event type [BoardEventType.ON_LOAD]
         */
        get() = BoardEventType.ON_LOAD

    /**
     * Returns an [EnumMap] of the event listeners registered to this board. Each entry of the map contains the
     * list of observers for a particular type of events.
     *
     * @return the event listeners registered to this board
     */
    fun getEventListener(): MutableMap<BoardEventType, MutableList<BoardEventListener>> {
        return eventListener
    }

    /**
     * Registers to the board a new listener for a specified event type.
     *
     *
     * It returns a reference to this board to fluently chain other calls for registering (or deregistering) other
     * listeners.
     *
     * @param eventType the board event type observed by the listener
     * @param listener  the listener to register
     * @return this board
     */
    fun addEventListener(eventType: BoardEventType, listener: BoardEventListener): Board {
        getEventListener()[eventType]!!.add(listener)
        return this
    }

    /**
     * Deregisters from the board a listener for a specified event type.
     *
     *
     * It returns a reference to this board to fluently chain other calls for deregistering (or registering) other
     * listeners.
     *
     * @param eventType the board event type observed by the listener
     * @param listener  the listener to deregister
     * @return this board
     */
    fun removeEventListener(eventType: BoardEventType, listener: BoardEventListener): Board {
        if (getEventListener() != null && getEventListener()[eventType] != null) {
            getEventListener()[eventType]!!.remove(listener)
        }
        return this
    }

    /**
     * Returns the bitboard representing the pieces of a specific side that can attack the given square. It takes a
     * bitboard mask in input to filter the result for a specific set of occupied squares only.
     *
     * @param square the target square
     * @param side   the attacking side
     * @param occ    a mask of occupied squares
     * @return the bitboard of all the pieces of the given side that can attack the square
     */
    /**
     * Returns the bitboard representing the pieces of a specific side that can attack the given square.
     *
     *
     * Same as invoking `squareAttackedBy(square, side, getBitboard())`.
     *
     * @param square the target square
     * @param side   the attacking side
     * @return the bitboard of all the pieces of the given side that can attack the square
     * @see Board.squareAttackedBy
     */
    @JvmOverloads
    fun squareAttackedBy(square: Square, side: Side, occ: Long = getBitboard()): Long {
        if (square == Square.NONE) {
            return 0L
        }
        var result = Bitboard.getPawnAttacks(side.flip(), square) and
                getBitboard(Piece.make(side, PieceType.PAWN)) and occ
        result = result or (Bitboard.getKnightAttacks(square, occ) and
                getBitboard(Piece.make(side, PieceType.KNIGHT)))
        result = result or (Bitboard.getBishopAttacks(occ, square) and
                ((getBitboard(Piece.make(side, PieceType.BISHOP)) or
                        getBitboard(Piece.make(side, PieceType.QUEEN)))))
        result = result or (Bitboard.getRookAttacks(occ, square) and
                ((getBitboard(Piece.make(side, PieceType.ROOK)) or
                        getBitboard(Piece.make(side, PieceType.QUEEN)))))
        result = result or (Bitboard.getKingAttacks(square, occ) and
                getBitboard(Piece.make(side, PieceType.KING)))
        return result
    }

    /**
     * Returns the bitboard representing the pieces of a specific side and type that can attack the given square.
     *
     * @param square the target square
     * @param side   the attacking side
     * @param type   the type of the attacking pieces
     * @return the bitboard of all the pieces of the given side and type that can attack the square
     */
    fun squareAttackedByPieceType(square: Square, side: Side, type: PieceType?): Long {
        var result = 0L
        val occ = getBitboard()
        when (type) {
            PieceType.PAWN -> result = Bitboard.getPawnAttacks(side.flip(), square) and
                    getBitboard(Piece.make(side, PieceType.PAWN))

            PieceType.KNIGHT -> result = Bitboard.getKnightAttacks(square, occ) and
                    getBitboard(Piece.make(side, PieceType.KNIGHT))

            PieceType.BISHOP -> result = Bitboard.getBishopAttacks(occ, square) and
                    getBitboard(Piece.make(side, PieceType.BISHOP))

            PieceType.ROOK -> result = Bitboard.getRookAttacks(occ, square) and
                    getBitboard(Piece.make(side, PieceType.ROOK))

            PieceType.QUEEN -> result = Bitboard.getQueenAttacks(occ, square) and
                    getBitboard(Piece.make(side, PieceType.QUEEN))

            PieceType.KING -> result = result or (Bitboard.getKingAttacks(square, occ) and
                    getBitboard(Piece.make(side, PieceType.KING)))

            else -> {}
        }
        return result
    }

    /**
     * Returns the square occupied by the king of the given side.
     *
     * @param side the side of the king
     * @return the square occupied by the king
     */
    fun getKingSquare(side: Side): Square {
        val result = Square.NONE
        val piece: Long = getBitboard(Piece.make(side, PieceType.KING))
        if (piece != 0L) {
            val sq = Bitboard.bitScanForward(piece)
            return Square.squareAt(sq)
        }
        return result
    }

    val isKingAttacked: Boolean
        /**
         * Checks if the king of the side to move is attacked by any enemy piece.
         *
         * @return `true` if the king of the next side to move is attacked
         */
        get() = squareAttackedBy(getKingSquare(sideToMove), sideToMove.flip()) != 0L

    /**
     * Checks if any of the squares provided in input is attacked by the given side in the current position.
     *
     * @param squares the target squares
     * @param side    the attacking side
     * @return `true` if any square is attacked
     */
    fun isSquareAttackedBy(squares: List<Square?>, side: Side): Boolean {
        for (sq in squares.filterNotNull()) {
            if (squareAttackedBy(sq, side) != 0L) {
                return true
            }
        }
        return false
    }

    /**
     * Verifies if the move still to be executed will leave the resulting board in a valid (legal) position. Optionally,
     * it can perform a full validation, a stricter check to assess if the final board configuration could be considered
     * valid or not.
     *
     *
     * The full validation checks:
     *
     *  * if a piece is actually moving;
     *  * if the moving side is the next side to move in the position;
     *  * if the destination square does not contain a piece of the same side of the moving one;
     *  * in case of a promotion, if a promoting piece is present;
     *  * in case of castling, if the castle move can be performed.
     *
     * **N.B.**: the method does not check whether the move is legal or not according to the standard chess rules,
     * but only if the resulting configuration is valid. For instance, it is considered valid moving the king by two or
     * more squares, or a rook beyond its friendly pieces, as long as the position obtained after the move does not
     * violate any chess constraint.
     *
     * @param move           the move to validate
     * @param fullValidation performs a full validation of the move
     * @return `true` if the move is considered valid
     */
    fun isMoveLegal(move: Move, fullValidation: Boolean): Boolean {
        val fromPiece = getPiece(move.from)
        val side = sideToMove
        val fromType = fromPiece.pieceType
        val capturedPiece = getPiece(move.to)

        if (fullValidation) {
            if (Piece.NONE == fromPiece) {
                throw RuntimeException("From piece cannot be null")
            }

            if (fromPiece.pieceSide == capturedPiece.pieceSide) {
                return false
            }

            if (side != fromPiece.pieceSide) {
                return false
            }

            val pawnPromoting = fromPiece.pieceType == PieceType.PAWN &&
                    isPromoRank(side, move)
            val hasPromoPiece = move.promotion != Piece.NONE

            if (hasPromoPiece != pawnPromoting) {
                return false
            }
            if (fromType == PieceType.KING) {
                if (context.isKingSideCastle(move)) {
                    if (getCastleRight(side) == CastleRight.KING_AND_QUEEN_SIDE ||
                        (getCastleRight(side) == CastleRight.KING_SIDE)
                    ) {
                        if ((getBitboard() and context.getooAllSquaresBb(side)) == 0L) {
                            return !isSquareAttackedBy(context.getooSquares(side), side.flip())
                        }
                    }
                    return false
                }
                if (context.isQueenSideCastle(move)) {
                    if (getCastleRight(side) == CastleRight.KING_AND_QUEEN_SIDE ||
                        (getCastleRight(side) == CastleRight.QUEEN_SIDE)
                    ) {
                        if ((getBitboard() and context.getoooAllSquaresBb(side)) == 0L) {
                            return !isSquareAttackedBy(context.getoooSquares(side), side.flip())
                        }
                    }
                    return false
                }
            }
        }
        if (fromType == PieceType.KING) {
            if (squareAttackedBy(move.to, side.flip()) != 0L) {
                return false
            }
        }
        val kingSq = (if (fromType == PieceType.KING) move.to else getKingSquare(
            side
        ))
        val other = side.flip()
        val moveTo = move.to.bitboard
        val moveFrom = move.from.bitboard
        val ep = if (enPassantTarget != Square.NONE && move.to == enPassant &&
            (fromType == PieceType.PAWN)
        ) enPassantTarget.bitboard else 0
        val allPieces = (getBitboard() xor moveFrom xor ep) or moveTo

        val bishopAndQueens: Long = ((getBitboard(Piece.make(other, PieceType.BISHOP)) or
                getBitboard(Piece.make(other, PieceType.QUEEN)))) and moveTo.inv()

        if (bishopAndQueens != 0L &&
            (Bitboard.getBishopAttacks(allPieces, kingSq) and bishopAndQueens) != 0L
        ) {
            return false
        }

        val rookAndQueens: Long = ((getBitboard(Piece.make(other, PieceType.ROOK)) or
                getBitboard(Piece.make(other, PieceType.QUEEN)))) and moveTo.inv()

        if (rookAndQueens != 0L &&
            (Bitboard.getRookAttacks(allPieces, kingSq) and rookAndQueens) != 0L
        ) {
            return false
        }

        val knights: Long =
            (getBitboard(Piece.make(other, PieceType.KNIGHT))) and moveTo.inv()

        if (knights != 0L &&
            (Bitboard.getKnightAttacks(kingSq, allPieces) and knights) != 0L
        ) {
            return false
        }

        val pawns: Long =
            (getBitboard(Piece.make(other, PieceType.PAWN))) and moveTo.inv() and ep.inv()

        return pawns == 0L ||
                (Bitboard.getPawnAttacks(side, kingSq) and pawns) == 0L
    }

    /**
     * Checks if the squares of a move are consistent, that is, if the destination square is attacked by the piece
     * placed on the starting square.
     *
     * @param move the move to check
     * @return `true` if the move is coherent
     */
    fun isAttackedBy(move: Move): Boolean {
        val pieceType = getPiece(move.from).pieceType
        require(PieceType.NONE != pieceType)
        val side = sideToMove
        var attacks = 0L
        when (pieceType) {
            PieceType.PAWN -> attacks = if (move.from.file != move.to.file) {
                Bitboard.getPawnCaptures(
                    side, move.from,
                    getBitboard(), enPassantTarget
                )
            } else {
                Bitboard.getPawnMoves(side, move.from, getBitboard())
            }

            PieceType.KNIGHT -> attacks =
                Bitboard.getKnightAttacks(move.from, getBitboard(side).inv())

            PieceType.BISHOP -> attacks = Bitboard.getBishopAttacks(getBitboard(), move.from)
            PieceType.ROOK -> attacks = Bitboard.getRookAttacks(getBitboard(), move.from)
            PieceType.QUEEN -> attacks = Bitboard.getQueenAttacks(getBitboard(), move.from)
            PieceType.KING -> attacks =
                Bitboard.getKingAttacks(move.from, getBitboard(side).inv())

            else -> {}
        }
        return (attacks and move.to.bitboard) != 0L
    }

    val isMated: Boolean
        /**
         * Verifies in the current position if the king of the side to move is mated.
         *
         * @return `true` if the king of the side to move is checkmated
         */
        get() {
            try {
                if (isKingAttacked) {
                    val l = MoveGenerator.generateLegalMoves(this)
                    if (l.size == 0) {
                        return true
                    }
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            return false
        }

    val isDraw: Boolean
        /**
         * Verifies if the current position is a forced draw because any of the standard chess rules. Specifically, the
         * method checks for:
         *
         *  * threefold repetition;
         *  * insufficient material;
         *  * fifty-move rule;
         *  * stalemate.
         *
         *
         * @return `true` if the position is a draw
         */
        get() {
            if (isRepetition) {
                return true
            }
            if (isInsufficientMaterial) {
                return true
            }
            if (halfMoveCounter >= 100) {
                return true
            }
            return isStaleMate
        }

    /**
     * Verifies if the current position has been repeated at least *n* times, where *n* is provided in input.
     *
     * @param n the number of repetitions to check in the position
     * @return `true` if the position has been repeated at least *n* times
     */
    fun isRepetition(n: Int): Boolean {
        val i = min((history.size - 1).toDouble(), halfMoveCounter.toDouble())
            .toInt()
        if (history.size >= 4) {
            val lastKey = history[history.size - 1]
            var rep = 0
            var x = 4
            while (x <= i) {
                val k = history[history.size - x - 1]
                if (k == lastKey && ++rep >= n - 1) {
                    return true
                }
                x += 2
            }
        }
        return false
    }

    val isRepetition: Boolean
        /**
         * Verifies if the current position has been repeated at least three times (threefold repetition).
         *
         *
         * Same as invoking `isRepetition(3)`.
         *
         * @return `true` if the position has been repeated at least three times
         * @see Board.isRepetition
         */
        get() = isRepetition(3)

    val isInsufficientMaterial: Boolean
        /**
         * Verifies if the current position has insufficient material to continue the game, and thus it must be considered a
         * forced draw.
         *
         * @return `true` if the position has insufficient material
         */
        get() {
            if ((getBitboard(Piece.WHITE_QUEEN) +
                        getBitboard(Piece.BLACK_QUEEN) +
                        getBitboard(Piece.WHITE_ROOK) +
                        getBitboard(Piece.BLACK_ROOK)) != 0L
            ) {
                return false
            }

            val pawns = getBitboard(Piece.WHITE_PAWN) or getBitboard(Piece.BLACK_PAWN)
            if (pawns == 0L) {
                val count = getBitboard().countOneBits()
                val whiteCount = getBitboard(Side.WHITE).countOneBits()
                val blackCount = getBitboard(Side.BLACK).countOneBits()
                if (count == 4) {
                    val whiteBishopCount = getBitboard(Piece.WHITE_BISHOP).countOneBits()
                    val blackBishopCount = getBitboard(Piece.BLACK_BISHOP).countOneBits()
                    if (whiteCount > 1 && blackCount > 1) {
                        return !((whiteBishopCount == 1 && blackBishopCount == 1) &&
                                getFistPieceLocation(Piece.WHITE_BISHOP).isLightSquare !=
                                getFistPieceLocation(Piece.BLACK_BISHOP).isLightSquare)
                    }
                    return if (whiteCount == 3 || blackCount == 3) {
                        if (whiteBishopCount == 2 &&
                            ((Bitboard.lightSquares and getBitboard(Piece.WHITE_BISHOP)) == 0L ||
                                    (Bitboard.darkSquares and getBitboard(Piece.WHITE_BISHOP)) == 0L)
                        ) {
                            true
                        } else blackBishopCount == 2 &&
                                ((Bitboard.lightSquares and getBitboard(Piece.BLACK_BISHOP)) == 0L ||
                                        (Bitboard.darkSquares and getBitboard(Piece.BLACK_BISHOP)) == 0L)
                    } else {
                        getBitboard(Piece.WHITE_KNIGHT).countOneBits() == 2 ||
                                getBitboard(Piece.BLACK_KNIGHT).countOneBits() == 2
                    }
                } else {
                    if ((getBitboard(Piece.WHITE_KING) or getBitboard(Piece.WHITE_BISHOP)) == getBitboard(
                            Side.WHITE
                        ) &&
                        ((getBitboard(Piece.BLACK_KING) or getBitboard(Piece.BLACK_BISHOP)) == getBitboard(
                            Side.BLACK
                        ))
                    ) {
                        return (((Bitboard.lightSquares and getBitboard(Piece.WHITE_BISHOP)) == 0L) &&
                                ((Bitboard.lightSquares and getBitboard(Piece.BLACK_BISHOP)) == 0L)) ||
                                ((Bitboard.darkSquares and getBitboard(Piece.WHITE_BISHOP)) == 0L) &&
                                ((Bitboard.darkSquares and getBitboard(Piece.BLACK_BISHOP)) == 0L)
                    }
                    return count < 4
                }
            }

            return false
        }

    val isStaleMate: Boolean
        /**
         * Verifies in the current position if the king of the side to move is stalemated, and thus if the position must be
         * considered a forced draw.
         *
         * @return `true` if the king of the side to move is stalemated
         */
        get() {
            try {
                if (!isKingAttacked) {
                    val l = MoveGenerator.generateLegalMoves(this)
                    if (l.size == 0) {
                        return true
                    }
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            return false
        }

    val positionId: String
        /**
         * Returns the unique position ID for the current position and status. The identifier is nothing more than the
         * Forsyth-Edwards Notation (FEN) representation of the board without the move counters.
         *
         *
         * Although this is a reliable way for identifying a unique position, it is much slower than using
         * [Board.hashCode] or [Board.getZobristKey].
         *
         * @return the unique position ID
         * @see Board.hashCode
         * @see Board.getZobristKey
         */
        get() {
            val parts =
                this.getFen(false).split(" ")
            return parts[0] + " " + parts[1] + " " + parts[2] +
                    (if (this.enPassantTarget != Square.NONE) parts[3] else "-")
        }

    /**
     * Returns the list of all possible legal moves for the current position according to the standard rules of chess.
     * If such moves are played, it is guaranteed the resulting position will also be legal.
     *
     * @return the list of legal moves available in the current position
     */
    fun legalMoves(): List<Move> {
        return MoveGenerator.generateLegalMoves(this)
    }

    /**
     * Returns the list of all possible pseudo-legal moves for the current position.
     *
     *
     * A move is considered pseudo-legal when it is legal according to the standard rules of chess piece movements, but
     * the resulting position might not be legal because of other rules (e.g. checks to the king).
     *
     * @return the list of pseudo-legal moves available in the current position
     */
    fun pseudoLegalMoves(): List<Move> {
        return MoveGenerator.generatePseudoLegalMoves(this)
    }

    /**
     * Returns the list of all possible pseudo-legal captures for the current position.
     *
     *
     * A move is considered a pseudo-legal capture when it takes an enemy piece and it is legal according to the
     * standard rules of chess piece movements, but the resulting position might not be legal because of other rules
     * (e.g. checks to the king).
     *
     * @return the list of pseudo-legal captures available in the current position
     */
    fun pseudoLegalCaptures(): List<Move> {
        return MoveGenerator.generatePseudoLegalCaptures(this)
    }

    /**
     * Checks if this board is equivalent to another.
     *
     *
     * Two boards are considered equivalent when:
     *
     *  * the pieces are the same, placed on the very same squares;
     *  * the side to move is the same;
     *  * the castling rights are the same;
     *  * the en passant target is the same.
     *
     *
     * @param obj the other object reference to compare to this board
     * @return `true` if this board and the object reference are equivalent
     * @see Board.strictEquals
     */
    override fun equals(obj: Any?): Boolean {
        if (obj is Board) {
            val board = obj
            for (piece in Piece.allPieces) {
                if (piece != Piece.NONE && getBitboard(piece) != board.getBitboard(piece)) {
                    return false
                }
            }
            return sideToMove == board.sideToMove && getCastleRight(Side.WHITE) == board.getCastleRight(
                Side.WHITE
            ) && getCastleRight(Side.BLACK) == board.getCastleRight(Side.BLACK) && enPassant == board.enPassant && enPassantTarget == board.enPassantTarget
        }
        return false
    }

    /**
     * Checks if this board is equivalent to another performing a strict comparison.
     *
     *
     * Two boards are considered strictly equivalent when:
     *
     *  * they are equivalent;
     *  * their history is the same.
     *
     *
     * @param obj the other object reference to compare to this board
     * @return `true` if this board and the object reference are strictly equivalent
     * @see Board.equals
     */
    fun strictEquals(obj: Any?): Boolean {
        if (obj is Board) {
            val board = obj
            return equals(board) && board.history == this.history
        }
        return false
    }

    /**
     * Returns a hash code value for this board.
     *
     * @return a hash value for this board
     */
    override fun hashCode(): Int {
        return incrementalHashKey.toInt()
    }

    val zobristKey: Long
        /**
         * Returns a Zobrist hash code value for this board. A Zobrist hashing assures the same position returns the same
         * hash value. It is calculated using the position of the pieces, the side to move, the castle rights and the en
         * passant target.
         *
         * @return a Zobrist hash value for this board
         * @see [Zobrist hashing in Wikipedia](https://en.wikipedia.org/wiki/Zobrist_hashing)
         */
        get() {
            var hash: Long = 0
            if (getCastleRight(Side.WHITE) != CastleRight.NONE) {
                hash = hash xor getCastleRightKey(Side.WHITE)
            }
            if (getCastleRight(Side.BLACK) != CastleRight.NONE) {
                hash = hash xor getCastleRightKey(Side.BLACK)
            }
            for (sq in Square.entries) {
                val piece = getPiece(sq)
                if (Piece.NONE != piece && Square.NONE != sq) {
                    hash = hash xor getPieceSquareKey(piece, sq)
                }
            }
            hash = hash xor getSideKey(sideToMove)

            if (Square.NONE != enPassantTarget &&
                pawnCanBeCapturedEnPassant()
            ) {
                hash = hash xor getEnPassantKey(enPassantTarget)
            }
            return hash
        }

    private fun getCastleRightKey(side: Side): Long {
        return keys[3 * getCastleRight(side)!!.ordinal + 300 + 3 * side.ordinal]
    }

    private fun getSideKey(side: Side): Long {
        return keys[3 * side.ordinal + 500]
    }

    private fun getEnPassantKey(enPassantTarget: Square): Long {
        return keys[3 * enPassantTarget.ordinal + 400]
    }

    private fun getPieceSquareKey(piece: Piece, square: Square): Long {
        return keys[57 * piece.ordinal + 13 * square.ordinal]
    }

    /**
     * Returns a human-readable representation of the board taking the perspective of white, with the 1st rank at the
     * bottom and the 8th rank at the top.
     *
     *
     * Same as invoking `toStringFromViewPoint(Side.WHITE)`.
     *
     * @return a string representation of the board from white player's point of view
     * @see Board.toStringFromViewPoint
     */
    fun toStringFromWhiteViewPoint(): String {
        return toStringFromViewPoint(Side.WHITE)
    }

    /**
     * Returns a human-readable representation of the board taking the perspective of black, with the 8th rank at the
     * bottom and the 1st rank at the top.
     *
     *
     * Same as invoking `toStringFromViewPoint(Side.BLACK)`.
     *
     * @return a string representation of the board from black player's point of view
     * @see Board.toStringFromViewPoint
     */
    fun toStringFromBlackViewPoint(): String {
        return toStringFromViewPoint(Side.BLACK)
    }

    /**
     * Returns a human-readable representation of the board taking the perspective of one side, with the 1st rank at the
     * bottom in case of white, or the 8th rank at the bottom in case of black.
     *
     * @param side the side whose home rank should be at the bottom of the resulting representation
     * @return a string representation of the board using one of the two player's point of view
     */
    fun toStringFromViewPoint(side: Side): String {
        val sb = StringBuilder()

        val rankIterator = if (side == Side.WHITE) sevenToZero() else zeroToSeven()
        val fileIterator = if (side == Side.WHITE) zeroToSeven() else sevenToZero()

        rankIterator.forEach { i: Int ->
            val r: Rank = Rank.allRanks.get(i)
            fileIterator.forEach { n: Int ->
                val f: File = File.allFiles.get(n)
                if (File.NONE != f && Rank.NONE != r) {
                    val sq: Square = Square.encode(r, f)
                    val piece = getPiece(sq)
                    sb.append(piece.fenSymbol)
                }
            }
            sb.append("\n")
        }

        return sb.toString()
    }

    /**
     * Returns a string representation of this board.
     *
     *
     * The result of [Board.toStringFromWhiteViewPoint] is used to print the position of the board.
     *
     * @return a string representation of the board
     * @see Board.toStringFromWhiteViewPoint
     */
    override fun toString(): String {
        return toStringFromWhiteViewPoint() + "Side: " + sideToMove
    }

    /**
     * Returns a reference to a copy of the board. The board history is copied as well.
     *
     * @return a copy of the board
     */
    public fun clone(): Board {
        val copy = Board(context, this.updateHistory)
        copy.loadFromFen(this.fen)
        copy.enPassantTarget = enPassantTarget
        copy.history.clear()
        for (key in history) {
            copy.history.add(key)
        }
        return copy
    }

    private fun pawnCanBeCapturedEnPassant(): Boolean {
        return (squareAttackedByPieceType(enPassant, sideToMove, PieceType.PAWN) != 0L
                && verifyNotPinnedPiece(sideToMove.flip(), enPassant, enPassantTarget))
    }

    private fun verifyNotPinnedPiece(side: Side, enPassant: Square, target: Square): Boolean {
        val pawns = Bitboard.getPawnAttacks(
            side,
            enPassant
        ) and getBitboard(Piece.make(side.flip(), PieceType.PAWN))
        return pawns != 0L && verifyAllPins(pawns, side, enPassant, target)
    }

    private fun verifyAllPins(pawns: Long, side: Side, enPassant: Square, target: Square): Boolean {
        val onePawn = Bitboard.extractLsb(pawns)
        val otherPawn = pawns xor onePawn
        if (onePawn != 0L && verifyKingIsNotAttackedWithoutPin(side, enPassant, target, onePawn)) {
            return true
        }
        return verifyKingIsNotAttackedWithoutPin(side, enPassant, target, otherPawn)
    }

    private fun verifyKingIsNotAttackedWithoutPin(
        side: Side,
        enPassant: Square,
        target: Square,
        pawns: Long
    ): Boolean {
        return squareAttackedBy(
            getKingSquare(side.flip()),
            side,
            removePieces(enPassant, target, pawns)
        ) == 0L
    }

    private fun removePieces(enPassant: Square, target: Square, pieces: Long): Long {
        return (getBitboard() xor pieces xor target.bitboard) or enPassant.bitboard
    }

    companion object {
        private val keys: MutableList<Long> = ArrayList()
        private const val RANDOM_SEED = 49109794719L
        private const val ZOBRIST_TABLE_SIZE = 2000

        init {
            val random = XorShiftRandom(RANDOM_SEED)
            for (i in 0 until ZOBRIST_TABLE_SIZE) {
                val key = random.nextLong()
                keys.add(key)
            }
        }

        /*
     * does move lead to a promotion?
     */
        private fun isPromoRank(side: Side, move: Move): Boolean {
            return if (side == Side.WHITE && move.to.rank == Rank.RANK_8) {
                true
            } else side == Side.BLACK && move.to.rank == Rank.RANK_1
        }

        private fun findEnPassantTarget(sq: Square, side: Side): Square {
            var ep = Square.NONE
            if (Square.NONE != sq) {
                ep = if (Side.WHITE == side) Square.encode(
                    Rank.RANK_5,
                    sq.file
                ) else Square.encode(Rank.RANK_4, sq.file)
            }
            return ep
        }

        private fun findEnPassant(sq: Square, side: Side?): Square {
            var ep = Square.NONE
            if (Square.NONE != sq) {
                ep = if (Side.WHITE == side) Square.encode(
                    Rank.RANK_3,
                    sq.file
                ) else Square.encode(Rank.RANK_6, sq.file)
            }
            return ep
        }

        private fun zeroToSeven(): IntProgression {
            return 0..7
        }

        private fun sevenToZero(): IntProgression {
            return 7 downTo 0
        }
    }
}
