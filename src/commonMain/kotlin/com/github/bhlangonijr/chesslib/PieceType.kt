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
 * All possible piece types in a chess game.
 *
 *
 * Each value defines a single piece type, except for the special value [PieceType.NONE] which represents no type
 * in particular.
 */
enum class PieceType(
    /**
     * Returns the Short Algebraic Notation (SAN) symbol for this piece type. For example, `R` for the rook type,
     * `K` for the king type, or an empty string for the pawn type.
     *
     * @return the SAN symbol of this piece type
     */
    val sanSymbol: String
) {
    /**
     * The pawn piece type.
     */
    PAWN(""),

    /**
     * The knight piece type.
     */
    KNIGHT("N"),

    /**
     * The bishop piece type.
     */
    BISHOP("B"),

    /**
     * The rook piece type.
     */
    ROOK("R"),

    /**
     * The queen piece type.
     */
    QUEEN("Q"),

    /**
     * The king piece type.
     */
    KING("K"),

    /**
     * Special value that represents no piece type in particular.
     */
    NONE("NONE");

    /**
     * Returns the name of the piece type.
     *
     * @return the name of the piece type
     */
    fun value(): String {
        return name
    }

    companion object {
        private val sanToType: MutableMap<String, PieceType> = HashMap(7)

        init {
            for (type in entries) {
                sanToType[type.sanSymbol] = type
            }
        }

        /**
         * Returns a piece type given its name.
         *
         *
         * Same as invoking [PieceType.valueOf].
         *
         * @param v name of the piece type
         * @return the piece type with the specified name
         * @throws IllegalArgumentException if the name does not correspond to any piece type
         */
        fun fromValue(v: String?): PieceType {
            return valueOf(v!!)
        }

        /**
         * Returns the piece type corresponding to the given Short Algebraic Notation (SAN) symbol.
         *
         * @param sanSymbol a piece symbol in SAN notation, such as `K` or `B`, or the empty string for the pawn
         * type
         * @return the piece type that corresponds to the SAN symbol provided in input
         * @throws IllegalArgumentException if the input symbol does not correspond to any standard chess piece type
         */
        @JvmStatic
        fun fromSanSymbol(sanSymbol: String): PieceType {
            val pieceType = sanToType[sanSymbol]
                ?: throw IllegalArgumentException("Unknown piece '$sanSymbol'")
            return pieceType
        }
    }
}
