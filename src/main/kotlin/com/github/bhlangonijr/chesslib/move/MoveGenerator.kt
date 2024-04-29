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
package com.github.bhlangonijr.chesslib.move

import com.github.bhlangonijr.chesslib.Bitboard
import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.CastleRight
import com.github.bhlangonijr.chesslib.Piece
import com.github.bhlangonijr.chesslib.PieceType
import com.github.bhlangonijr.chesslib.Rank
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import java.util.LinkedList
import java.util.function.Predicate

/**
 * A handy collection of static utility methods for generating moves from a chess position.
 */
object MoveGenerator {
    /**
     * Generates all pawn captures for the playing side in the given position, and appends them to the list passed as an
     * argument. That implies the list must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the captures are legal according to the standard rules of
     * pawn movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the pawn captures
     * @param moves a mutable list in which to append the generated pawn captures
     */
    fun generatePawnCaptures(board: Board, moves: MutableList<Move>) {
        val side = board.sideToMove
        var pieces: Long = board.getBitboard(Piece.Companion.make(side!!, PieceType.PAWN))
        while (pieces != 0L) {
            val sourceIndex = Bitboard.bitScanForward(pieces)
            pieces = Bitboard.extractLsb(pieces)
            val sqSource: Square = Square.Companion.squareAt(sourceIndex)
            var attacks = Bitboard.getPawnCaptures(
                side!!, sqSource,
                board.getBitboard(), board.enPassantTarget
            ) and board.getBitboard(side).inv()
            while (attacks != 0L) {
                val targetIndex = Bitboard.bitScanForward(attacks)
                attacks = Bitboard.extractLsb(attacks)
                val sqTarget: Square = Square.Companion.squareAt(targetIndex)
                addPromotions(moves, side, sqTarget, sqSource)
            }
        }
    }

    /**
     * Generates all pawn moves, excluding captures, for the playing side in the given position, and appends them to the
     * list passed as an argument. That implies the list must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * pawn movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the pawn moves
     * @param moves a mutable list in which to append the generated pawn moves
     */
    fun generatePawnMoves(board: Board, moves: MutableList<Move>) {
        val side = board.sideToMove
        var pieces: Long = board.getBitboard(Piece.Companion.make(side!!, PieceType.PAWN))
        while (pieces != 0L) {
            val sourceIndex = Bitboard.bitScanForward(pieces)
            pieces = Bitboard.extractLsb(pieces)
            val sqSource: Square = Square.Companion.squareAt(sourceIndex)
            var attacks = Bitboard.getPawnMoves(side!!, sqSource, board.getBitboard())
            while (attacks != 0L) {
                val targetIndex = Bitboard.bitScanForward(attacks)
                attacks = Bitboard.extractLsb(attacks)
                val sqTarget: Square = Square.Companion.squareAt(targetIndex)
                addPromotions(moves, side, sqTarget, sqSource)
            }
        }
    }

    private fun addPromotions(
        moves: MutableList<Move>,
        side: Side?,
        sqTarget: Square,
        sqSource: Square
    ) {
        if (Side.WHITE == side && Rank.RANK_8 == sqTarget.rank) {
            moves.add(Move(sqSource, sqTarget, Piece.WHITE_QUEEN))
            moves.add(Move(sqSource, sqTarget, Piece.WHITE_ROOK))
            moves.add(Move(sqSource, sqTarget, Piece.WHITE_BISHOP))
            moves.add(Move(sqSource, sqTarget, Piece.WHITE_KNIGHT))
        } else if (Side.BLACK == side && Rank.RANK_1 == sqTarget.rank) {
            moves.add(Move(sqSource, sqTarget, Piece.BLACK_QUEEN))
            moves.add(Move(sqSource, sqTarget, Piece.BLACK_ROOK))
            moves.add(Move(sqSource, sqTarget, Piece.BLACK_BISHOP))
            moves.add(Move(sqSource, sqTarget, Piece.BLACK_KNIGHT))
        } else {
            moves.add(Move(sqSource, sqTarget, Piece.NONE))
        }
    }

    /**
     * Generates all knight moves for the playing side in the given position, according to a bitboard mask used to
     * specify the allowed target squares on the board. The generated moves are appended to the list passed as an
     * argument, which must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * knight movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the knight moves
     * @param moves a mutable list in which to append the generated knight moves
     * @param mask  bitboard mask of allowed targets
     */
    /**
     * Generates all knight moves for the playing side in the given position, and appends them to the list passed as an
     * argument. That implies the list must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * knight movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the knight moves
     * @param moves a mutable list in which to append the generated knight moves
     * @see MoveGenerator.generateKnightMoves
     */
    @JvmOverloads
    fun generateKnightMoves(
        board: Board,
        moves: MutableList<Move>,
        mask: Long = board.getBitboard(board.sideToMove).inv()
    ) {
        val side = board.sideToMove
        var pieces: Long = board.getBitboard(Piece.Companion.make(side!!, PieceType.KNIGHT))
        while (pieces != 0L) {
            val knightIndex = Bitboard.bitScanForward(pieces)
            pieces = Bitboard.extractLsb(pieces)
            val sqSource: Square = Square.Companion.squareAt(knightIndex)
            var attacks = Bitboard.getKnightAttacks(sqSource, mask)
            while (attacks != 0L) {
                val attackIndex = Bitboard.bitScanForward(attacks)
                attacks = Bitboard.extractLsb(attacks)
                val sqTarget: Square = Square.Companion.squareAt(attackIndex)
                moves.add(Move(sqSource, sqTarget, Piece.NONE))
            }
        }
    }

    /**
     * Generates all bishop moves for the playing side in the given position, according to a bitboard mask used to
     * specify the allowed target squares on the board. The generated moves are appended to the list passed as an
     * argument, which must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * bishop movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the bishop moves
     * @param moves a mutable list in which to append the generated bishop moves
     * @param mask  bitboard mask of allowed targets
     */
    /**
     * Generates all bishop moves for the playing side in the given position, and appends them to the list passed as an
     * argument. That implies the list must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * bishop movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the bishop moves
     * @param moves a mutable list in which to append the generated bishop moves
     * @see MoveGenerator.generateBishopMoves
     */
    @JvmOverloads
    fun generateBishopMoves(
        board: Board,
        moves: MutableList<Move>,
        mask: Long = board.getBitboard(board.sideToMove).inv()
    ) {
        val side = board.sideToMove
        var pieces: Long = board.getBitboard(Piece.Companion.make(side!!, PieceType.BISHOP))
        while (pieces != 0L) {
            val sourceIndex = Bitboard.bitScanForward(pieces)
            pieces = Bitboard.extractLsb(pieces)
            val sqSource: Square = Square.Companion.squareAt(sourceIndex)
            var attacks = Bitboard.getBishopAttacks(board.getBitboard(), sqSource) and mask
            while (attacks != 0L) {
                val attackIndex = Bitboard.bitScanForward(attacks)
                attacks = Bitboard.extractLsb(attacks)
                val sqTarget: Square = Square.Companion.squareAt(attackIndex)
                moves.add(Move(sqSource, sqTarget, Piece.NONE))
            }
        }
    }

    /**
     * Generates all rook moves for the playing side in the given position, according to a bitboard mask used to specify
     * the allowed target squares on the board. The generated moves are appended to the list passed as an argument,
     * which must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * rook movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the rook moves
     * @param moves a mutable list in which to append the generated rook moves
     * @param mask  bitboard mask of allowed targets
     */
    /**
     * Generates all rook moves for the playing side in the given position, and appends them to the list passed as an
     * argument. That implies the list must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * rook movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the rook moves
     * @param moves a mutable list in which to append the generated rook moves
     * @see MoveGenerator.generateRookMoves
     */
    @JvmOverloads
    fun generateRookMoves(
        board: Board,
        moves: MutableList<Move>,
        mask: Long = board.getBitboard(board.sideToMove).inv()
    ) {
        val side = board.sideToMove
        var pieces: Long = board.getBitboard(Piece.Companion.make(side!!, PieceType.ROOK))
        while (pieces != 0L) {
            val sourceIndex = Bitboard.bitScanForward(pieces)
            pieces = Bitboard.extractLsb(pieces)
            val sqSource: Square = Square.Companion.squareAt(sourceIndex)
            var attacks = Bitboard.getRookAttacks(board.getBitboard(), sqSource) and mask
            while (attacks != 0L) {
                val attackIndex = Bitboard.bitScanForward(attacks)
                attacks = Bitboard.extractLsb(attacks)
                val sqTarget: Square = Square.Companion.squareAt(attackIndex)
                moves.add(Move(sqSource, sqTarget, Piece.NONE))
            }
        }
    }

    /**
     * Generates all queen moves for the playing side in the given position, according to a bitboard mask used to
     * specify the allowed target squares on the board. The generated moves are appended to the list passed as an
     * argument, which must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * queen movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the queen moves
     * @param moves a mutable list in which to append the generated queen moves
     * @param mask  bitboard mask of allowed targets
     */
    /**
     * Generates all queen moves for the playing side in the given position, and appends them to the list passed as an
     * argument. That implies the list must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * queen movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the queen moves
     * @param moves a mutable list in which to append the generated queen moves
     * @see MoveGenerator.generateQueenMoves
     */
    @JvmOverloads
    fun generateQueenMoves(
        board: Board,
        moves: MutableList<Move>,
        mask: Long = board.getBitboard(board.sideToMove).inv()
    ) {
        val side = board.sideToMove
        var pieces: Long = board.getBitboard(Piece.Companion.make(side!!, PieceType.QUEEN))
        while (pieces != 0L) {
            val sourceIndex = Bitboard.bitScanForward(pieces)
            pieces = Bitboard.extractLsb(pieces)
            val sqSource: Square = Square.Companion.squareAt(sourceIndex)
            var attacks = Bitboard.getQueenAttacks(board.getBitboard(), sqSource) and mask
            while (attacks != 0L) {
                val attackIndex = Bitboard.bitScanForward(attacks)
                attacks = Bitboard.extractLsb(attacks)
                val sqTarget: Square = Square.Companion.squareAt(attackIndex)
                moves.add(Move(sqSource, sqTarget, Piece.NONE))
            }
        }
    }

    /**
     * Generates all king moves for the playing side in the given position, according to a bitboard mask used to specify
     * the allowed target squares on the board. The generated moves are appended to the list passed as an argument,
     * which must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * king movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the king moves
     * @param moves a mutable list in which to append the generated king moves
     * @param mask  bitboard mask of allowed targets
     */
    /**
     * Generates all king moves for the playing side in the given position, and appends them to the list passed as an
     * argument. That implies the list must be mutable in order for this method to work.
     *
     *
     * All moves have to be considered pseudo-legal: although the moves are legal according to the standard rules of
     * rook movements, the resulting position might not be considered legal after they are played on the board.
     *
     * @param board the board from which to generate the king moves
     * @param moves a mutable list in which to append the generated king moves
     * @see MoveGenerator.generateKingMoves
     */
    @JvmOverloads
    fun generateKingMoves(
        board: Board,
        moves: MutableList<Move>,
        mask: Long = board.getBitboard(board.sideToMove).inv()
    ) {
        val side = board.sideToMove
        var pieces: Long = board.getBitboard(Piece.Companion.make(side!!, PieceType.KING))
        while (pieces != 0L) {
            val sourceIndex = Bitboard.bitScanForward(pieces)
            pieces = Bitboard.extractLsb(pieces)
            val sqSource: Square = Square.Companion.squareAt(sourceIndex)
            var attacks = Bitboard.getKingAttacks(sqSource, mask)
            while (attacks != 0L) {
                val attackIndex = Bitboard.bitScanForward(attacks)
                attacks = Bitboard.extractLsb(attacks)
                val sqTarget: Square = Square.Companion.squareAt(attackIndex)
                moves.add(Move(sqSource, sqTarget, Piece.NONE))
            }
        }
    }

    /**
     * Generates all castle moves for the playing side in the given position, and appends them to the list passed as an
     * argument. That implies the list must be mutable in order for this method to work.
     *
     * @param board the board from which to generate the castle moves
     * @param moves a mutable list in which to append the generated castle moves
     */
    fun generateCastleMoves(board: Board, moves: MutableList<Move>) {
        val side = board.sideToMove
        if (board.isKingAttacked) {
            return
        }
        if (board.getCastleRight(side) == CastleRight.KING_AND_QUEEN_SIDE ||
            (board.getCastleRight(side) == CastleRight.KING_SIDE)
        ) {
            if ((board.getBitboard() and board.context.getooAllSquaresBb(side)) == 0L) {
                if (!board.isSquareAttackedBy(board.context.getooSquares(side), side!!.flip())) {
                    moves.add(board.context.getoo(side))
                }
            }
        }
        if (board.getCastleRight(side) == CastleRight.KING_AND_QUEEN_SIDE ||
            (board.getCastleRight(side) == CastleRight.QUEEN_SIDE)
        ) {
            if ((board.getBitboard() and board.context.getoooAllSquaresBb(side)) == 0L) {
                if (!board.isSquareAttackedBy(board.context.getoooSquares(side), side!!.flip())) {
                    moves.add(board.context.getooo(side))
                }
            }
        }
    }

    /**
     * Returns the list of all possible pseudo-legal moves for the given position.
     *
     *
     * A move is considered pseudo-legal when it is legal according to the standard rules of chess piece movements, but
     * the resulting position might not be legal because of other rules (e.g. checks to the king).
     *
     * @param board the board from which to generate the pseudo-legal moves
     * @return the list of pseudo-legal moves available in the position
     */
    fun generatePseudoLegalMoves(board: Board): MutableList<Move> {
        val moves: MutableList<Move> = LinkedList()
        generatePawnCaptures(board, moves)
        generatePawnMoves(board, moves)
        generateKnightMoves(board, moves)
        generateBishopMoves(board, moves)
        generateRookMoves(board, moves)
        generateQueenMoves(board, moves)
        generateKingMoves(board, moves)
        generateCastleMoves(board, moves)
        return moves
    }

    /**
     * Returns the list of all possible pseudo-legal captures for the given position.
     *
     *
     * A move is considered a pseudo-legal capture when it takes an enemy piece and it is legal according to the
     * standard rules of chess piece movements, but the resulting position might not be legal because of other rules
     * (e.g. checks to the king).
     *
     * @param board the board from which to generate the pseudo-legal captures
     * @return the list of pseudo-legal captures available in the position
     */
    @JvmStatic
    fun generatePseudoLegalCaptures(board: Board): List<Move> {
        val moves: MutableList<Move> = LinkedList()
        val other = board.sideToMove.flip()
        generatePawnCaptures(board, moves)
        generateKnightMoves(board, moves, board.getBitboard(other))
        generateBishopMoves(board, moves, board.getBitboard(other))
        generateRookMoves(board, moves, board.getBitboard(other))
        generateQueenMoves(board, moves, board.getBitboard(other))
        generateKingMoves(board, moves, board.getBitboard(other))
        return moves
    }

    /**
     * Returns the list of all possible legal moves for the position according to the standard rules of chess.
     *
     * @param board the board from which to generate the legal moves
     * @return the list of legal moves available in the position
     * @throws MoveGeneratorException if it is not possible to generate the moves
     */
    @JvmStatic
    @Throws(MoveGeneratorException::class)
    fun generateLegalMoves(board: Board): List<Move?> {
        try {
            val moves = generatePseudoLegalMoves(board)
            moves.removeIf { move -> !board.isMoveLegal(move, false) }
            return moves
        } catch (e: Exception) {
            throw MoveGeneratorException("Couldn't generate Legal moves: ", e)
        }
    }
}
