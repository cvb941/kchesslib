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

import com.github.bhlangonijr.chesslib.move.Move
import kotlin.jvm.JvmField

/**
 * A structure that can be used to cancel the effects of a move and to restore the board to a previous status. The
 * board context is memorized at the *backup* is created, and it could be subsequently re-applied to the sourcing
 * board.
 *
 *
 * The move backup is also a [BoardEvent], and hence it can be passed to the observers of the
 * [BoardEventType.ON_UNDO_MOVE] events, emitted when a move is reverted on a board.
 */
class MoveBackup() : BoardEvent {
    /**
     * Returns the castle rights used for restoring the board.
     *
     * @return the castle rights
     */
    val castleRight: MutableMap<Side, CastleRight?> = mutableMapOf()
    /**
     * Returns the next side to move used for restoring the board.
     *
     * @return the next side to move
     */
    /**
     * Sets the next side to move used for restoring the board.
     *
     * @param sideToMove the next side to move
     */
    lateinit var sideToMove: Side
    /**
     * Returns the target square of an en passant capture used for restoring the board.
     *
     * @return the en passant target square, or [Square.NONE] if en passant was not possible at the time the
     * backup was created
     */
    /**
     * Sets the target square of an en passant capture used for restoring the board.
     *
     * @param enPassant the en passant target square
     */
    lateinit var enPassantTarget: Square
    /**
     * Returns the destination square of an en passant capture used for restoring the board.
     *
     * @return the en passant destination square, or [Square.NONE] if en passant was not possible at the time the
     * backup was created
     */
    /**
     * Sets the destination square of an en passant capture used for restoring the board.
     *
     * @param enPassant the en passant destination square
     */
    lateinit var enPassant: Square
    /**
     * Returns the counter of full moves used for restoring the board.
     *
     * @return the counter of full moves
     */
    /**
     * Sets the counter of full moves used for restoring the board.
     *
     * @param moveCounter the counter of full moves
     */
    var moveCounter: Int = 0
    /**
     * Returns the counter of half moves used for restoring the board.
     *
     * @return the counter of half moves
     */
    /**
     * Sets the counter of half moves used for restoring the board.
     *
     * @param halfMoveCounter the counter of half moves
     */
    var halfMoveCounter: Int = 0
    /**
     * Returns the move to revert in the case a board has to be restored.
     *
     * @return the move to revert
     */
    /**
     * Sets the move to revert in the case a board has to be restored.
     *
     * @param move the move to revert
     */
    lateinit var move: Move
    /**
     * Returns the rook move to apply in order to revert a castle move in the case a board has to be restored.
     *
     * @return the rook move to apply to revert a castle move, or null if the move to revert is not a castle move
     */
    /**
     * Sets the rook move to apply in order to revert a castle move in the case a board has to be restored.
     *
     * @param rookCastleMove the rook move to apply to revert a castle move
     */
    @JvmField
    var rookCastleMove: Move? = null
    /**
     * Returns the piece captured with the move to revert in the case a board has to be restored.
     *
     * @return the captured piece, or [Piece.NONE] if no piece was captured at the time the backup was created
     */
    /**
     * Sets the captured piece used for restoring the board.
     *
     * @param capturedPiece the captured piece
     */
    var capturedPiece: Piece? = null
    /**
     * Returns the square of the piece captured with the move to revert in the case a board has to be restored.
     *
     * @return the square of the captured piece, or [Square.NONE] if no piece was captured at the time the backup
     * was created
     */
    /**
     * Sets the square of the captured piece used for restoring the board.
     *
     * @param capturedSquare the square of the captured piece
     */
    var capturedSquare: Square? = null
    /**
     * Returns the piece moved in the move to revert in the case a board has to be restored.
     *
     * @return the moved piece
     */
    /**
     * Sets the moving piece used for restoring the board.
     *
     * @param movingPiece the moving piece
     */
    lateinit var movingPiece: Piece
    /**
     * Checks if the move to revert in the case a board has to be restored is a castle move.
     *
     * @return `true` if the move is a castle move
     */
    /**
     * Sets whether the move to revert in the case a board has to be restored is a castle move or not.
     *
     * @param castleMove whether the move to restore is a castle move or not
     */
    var isCastleMove: Boolean = false
    /**
     * Checks if the move to revert in the case a board has to be restored is an en passant move.
     *
     * @return `true` if the move is an en passant move
     */
    /**
     * Sets whether the move to revert in the case a board has to be restored is an en passant move or not.
     *
     * @param enPassantMove whether the move to restore is an en passant move or not
     */
    var isEnPassantMove: Boolean = false
    /**
     * Returns the incremental hash key used for restoring the board.
     *
     * @return the incremental hash key
     */
    /**
     * Sets the incremental hash key used for restoring the board.
     *
     * @param incrementalHashKey the incremental hash key
     */
    var incrementalHashKey: Long = 0

    /**
     * Constructs a new move backup taking a board and a move. At the same time, it both instantiates the data structure
     * and takes a snapshot of the board status for a later restore.
     *
     * @param board the board that describes the status at the time of the move
     * @param move  the move which could be potentially restored later in time
     */
    constructor(board: Board, move: Move) : this() {
        makeBackup(board, move)
    }

    /**
     * Initiates a new move backup, possibly overwriting any previously existing backup.
     *
     * @param board the board that describes the status at the time of the move
     * @param move  the move which could be potentially restored later in time
     */
    fun makeBackup(board: Board, move: Move) {
        incrementalHashKey = board.incrementalHashKey
        sideToMove = board.sideToMove
        enPassantTarget = board.enPassantTarget
        enPassant = board.enPassant
        moveCounter = board.moveCounter
        halfMoveCounter = board.halfMoveCounter
        this.move = move
        castleRight[Side.WHITE] = board.getCastleRight(Side.WHITE)
        castleRight[Side.BLACK] = board.getCastleRight(Side.BLACK)
        capturedPiece = board.getPiece(move.to)
        capturedSquare = move.to
        val moving = board.getPiece(move.from)
        movingPiece = moving
        if (board.context.isCastleMove(move) && movingPiece == Piece.Companion.make(
                board.sideToMove,
                PieceType.KING
            )
        ) {
            val c =
                if (board.context.isKingSideCastle(move)) CastleRight.KING_SIDE else CastleRight.QUEEN_SIDE
            val rookMove = board.context.getRookCastleMove(board.sideToMove, c)
            rookCastleMove = rookMove
            isCastleMove = true
        } else {
            rookCastleMove = null
            isCastleMove = false
        }
    }

    /**
     * Restores the previously stored status backup to the board passed as an argument, effectively cancelling the
     * consequences of the move memorized at the time the backup was created, as well as any potential move executed on
     * the board after that moment.
     *
     *
     * It is a responsibility of the caller to make sure the board used for creating the backup is also the board passed
     * in input to this method. No check is performed to prevent another board is used instead.
     *
     * @param board the board to be restored to a previous status
     */
    fun restore(board: Board) {
        board.sideToMove = sideToMove
        board.enPassantTarget = enPassantTarget
        board.enPassant = enPassant
        board.moveCounter = moveCounter
        board.halfMoveCounter = halfMoveCounter
        val movingPiece =
            if (move.promotion == Piece.NONE) movingPiece else move.promotion
        board.castleRight[Side.WHITE] = castleRight[Side.WHITE]
        board.castleRight[Side.BLACK] = castleRight[Side.BLACK]

        if (move !== Constants.emptyMove) {
            val isCastle = board.context.isCastleMove(move)

            if (PieceType.KING == movingPiece.pieceType && isCastle) {
                board.undoMovePiece(rookCastleMove!!)
            }
            board.unsetPiece(movingPiece!!, move.to)
            if (Piece.NONE == move.promotion) {
                board.setPiece(movingPiece, move.from)
            } else {
                board.setPiece(Piece.Companion.make(sideToMove!!, PieceType.PAWN), move.from)
            }
            if (Piece.NONE != capturedPiece) {
                board.setPiece(capturedPiece!!, capturedSquare!!)
            }
        }
        board.incrementalHashKey = incrementalHashKey
    }

    override val type: BoardEventType
        /**
         * The type of board events this data structure represents when notified to its observers.
         *
         * @return the board event type [BoardEventType.ON_UNDO_MOVE]
         */
        get() = BoardEventType.ON_UNDO_MOVE
}
