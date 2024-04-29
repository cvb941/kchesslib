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

import kotlin.jvm.JvmStatic

/**
 * A chess piece on the board, that is, a specific combination of a [Side] and a [PieceType].
 *
 *
 * Each value defines a single piece, except for the special value [Piece.NONE] which identifies that no piece is
 * selected or assigned.
 */
enum class Piece(
    /**
     * Returns the side of this piece.
     *
     * @return the side of this piece
     */
    val pieceSide: Side?,
    /**
     * Returns the type of this piece.
     *
     * @return the piece type of this piece
     */
    val pieceType: PieceType?,
    /**
     * Returns the Figurine Algebraic Notation (FAN) symbol for this piece. For example, `♜` for a black rook, or
     * `♙` for a white pawn.
     *
     * @return the FAN symbol of this piece
     */
    val fanSymbol: String,
    /**
     * Returns the Forsyth-Edwards Notation (FEN) symbol for this piece. For example, `r` for a black rook, or
     * `P` for a white pawn.
     *
     * @return the FEN symbol of this piece
     */
    val fenSymbol: String
) {
    /**
     * A white pawn.
     */
    WHITE_PAWN(Side.WHITE, PieceType.PAWN, "♙", "P"),

    /**
     * A white knight.
     */
    WHITE_KNIGHT(Side.WHITE, PieceType.KNIGHT, "♘", "N"),

    /**
     * A white bishop.
     */
    WHITE_BISHOP(Side.WHITE, PieceType.BISHOP, "♗", "B"),

    /**
     * A white rook.
     */
    WHITE_ROOK(Side.WHITE, PieceType.ROOK, "♖", "R"),

    /**
     * A white queen.
     */
    WHITE_QUEEN(Side.WHITE, PieceType.QUEEN, "♕", "Q"),

    /**
     * A white king.
     */
    WHITE_KING(Side.WHITE, PieceType.KING, "♔", "K"),

    /**
     * A black pawn.
     */
    BLACK_PAWN(Side.BLACK, PieceType.PAWN, "♟", "p"),

    /**
     * A black knight.
     */
    BLACK_KNIGHT(Side.BLACK, PieceType.KNIGHT, "♞", "n"),

    /**
     * A black bishop.
     */
    BLACK_BISHOP(Side.BLACK, PieceType.BISHOP, "♝", "b"),

    /**
     * A black rook.
     */
    BLACK_ROOK(Side.BLACK, PieceType.ROOK, "♜", "r"),

    /**
     * A black queen.
     */
    BLACK_QUEEN(Side.BLACK, PieceType.QUEEN, "♛", "q"),

    /**
     * A black king.
     */
    BLACK_KING(Side.BLACK, PieceType.KING, "♚", "k"),

    /**
     * Special value that represents no piece in particular.
     */
    NONE(null, null, "NONE", ".");

    /**
     * Returns the name of the piece.
     *
     * @return the name of the piece
     */
    fun value(): String {
        return name
    }

    val sanSymbol: String?
        /**
         * Returns the Short Algebraic Notation (SAN) symbol for this piece. For example, `R` for a rook, `K`
         * for a king, or an empty string for a pawn.
         *
         * @return the SAN symbol of this piece
         */
        get() = pieceType?.sanSymbol

    companion object {
        val allPieces: Array<Piece> = entries.toTypedArray()
        private val fenToPiece: MutableMap<String, Piece> = HashMap(13)
        private val pieceMake = arrayOf(
            arrayOf(WHITE_PAWN, BLACK_PAWN),
            arrayOf(WHITE_KNIGHT, BLACK_KNIGHT),
            arrayOf(WHITE_BISHOP, BLACK_BISHOP),
            arrayOf(WHITE_ROOK, BLACK_ROOK),
            arrayOf(WHITE_QUEEN, BLACK_QUEEN),
            arrayOf(WHITE_KING, BLACK_KING),
            arrayOf(NONE, NONE),
        )

        init {
            for (piece in entries) {
                fenToPiece[piece.fenSymbol] = piece
            }
        }

        /**
         * Returns a piece given its name.
         *
         *
         * Same as invoking [Piece.valueOf].
         *
         * @param v name of the piece
         * @return the piece with the specified name
         * @throws IllegalArgumentException if the name does not correspond to any piece
         */
        @JvmStatic
        fun fromValue(v: String?): Piece {
            return valueOf(v!!)
        }

        /**
         * Returns the piece corresponding to the provided pair of side and type. If [PieceType.NONE] is requested,
         * [Piece.NONE] is returned regardless of the side.
         *
         * @param side the side of the wanted piece
         * @param type the type of the wanted piece
         * @return the piece corresponding to the given combination of side and type
         */
        @JvmStatic
        fun make(side: Side, type: PieceType): Piece {
            return pieceMake[type.ordinal][side.ordinal]
        }

        /**
         * Returns the piece corresponding to the given Forsyth-Edwards Notation (FEN) symbol.
         *
         * @param fenSymbol a piece FEN symbol, such as `K`, `b` or `p`
         * @return the piece that corresponds to the FEN symbol provided in input
         * @throws IllegalArgumentException if the input symbol does not correspond to any standard chess piece
         */
        @JvmStatic
        fun fromFenSymbol(fenSymbol: String): Piece {
            val piece = fenToPiece[fenSymbol]
                ?: throw IllegalArgumentException("Unknown piece '$fenSymbol'")
            return piece
        }
    }
}
