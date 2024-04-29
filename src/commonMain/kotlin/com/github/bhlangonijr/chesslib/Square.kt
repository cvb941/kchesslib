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

/**
 * All possible squares on a board.
 *
 *
 * Each value defines a single square, except for the special value [Square.NONE] which identifies that no
 * square is selected or assigned.
 */
enum class Square {
    /**
     * The `A1` square.
     */
    A1,

    /**
     * The `B1` square.
     */
    B1,

    /**
     * The `C1` square.
     */
    C1,

    /**
     * The `D1` square.
     */
    D1,

    /**
     * The `E1` square.
     */
    E1,

    /**
     * The `F1` square.
     */
    F1,

    /**
     * The `G1` square.
     */
    G1,

    /**
     * The `H1` square.
     */
    H1,

    /**
     * The `A2` square.
     */
    A2,

    /**
     * The `B2` square.
     */
    B2,

    /**
     * The `C2` square.
     */
    C2,

    /**
     * The `D2` square.
     */
    D2,

    /**
     * The `E2` square.
     */
    E2,

    /**
     * The `F2` square.
     */
    F2,

    /**
     * The `G2` square.
     */
    G2,

    /**
     * The `H2` square.
     */
    H2,

    /**
     * The `A3` square.
     */
    A3,

    /**
     * The `B3` square.
     */
    B3,

    /**
     * The `C3` square.
     */
    C3,

    /**
     * The `D3` square.
     */
    D3,

    /**
     * The `E3` square.
     */
    E3,

    /**
     * The `F3` square.
     */
    F3,

    /**
     * The `G3` square.
     */
    G3,

    /**
     * The `H3` square.
     */
    H3,

    /**
     * The `A4` square.
     */
    A4,

    /**
     * The `B4` square.
     */
    B4,

    /**
     * The `C4` square.
     */
    C4,

    /**
     * The `D4` square.
     */
    D4,

    /**
     * The `E4` square.
     */
    E4,

    /**
     * The `F4` square.
     */
    F4,

    /**
     * The `G4` square.
     */
    G4,

    /**
     * The `H4` square.
     */
    H4,

    /**
     * The `A5` square.
     */
    A5,

    /**
     * The `B5` square.
     */
    B5,

    /**
     * The `C5` square.
     */
    C5,

    /**
     * The `D5` square.
     */
    D5,

    /**
     * The `E5` square.
     */
    E5,

    /**
     * The `F5` square.
     */
    F5,

    /**
     * The `G5` square.
     */
    G5,

    /**
     * The `H5` square.
     */
    H5,

    /**
     * The `A6` square.
     */
    A6,

    /**
     * The `B6` square.
     */
    B6,

    /**
     * The `C6` square.
     */
    C6,

    /**
     * The `D6` square.
     */
    D6,

    /**
     * The `E6` square.
     */
    E6,

    /**
     * The `F6` square.
     */
    F6,

    /**
     * The `G6` square.
     */
    G6,

    /**
     * The `H6` square.
     */
    H6,

    /**
     * The `A7` square.
     */
    A7,

    /**
     * The `B7` square.
     */
    B7,

    /**
     * The `C7` square.
     */
    C7,

    /**
     * The `D7` square.
     */
    D7,

    /**
     * The `E7` square.
     */
    E7,

    /**
     * The `F7` square.
     */
    F7,

    /**
     * The `G7` square.
     */
    G7,

    /**
     * The `H7` square.
     */
    H7,

    /**
     * The `A8` square.
     */
    A8,

    /**
     * The `B8` square.
     */
    B8,

    /**
     * The `C8` square.
     */
    C8,

    /**
     * The `D8` square.
     */
    D8,

    /**
     * The `E8` square.
     */
    E8,

    /**
     * The `F8` square.
     */
    F8,

    /**
     * The `G8` square.
     */
    G8,

    /**
     * The `H8` square.
     */
    H8,

    /**
     * Special value that represents no square in particular.
     */
    NONE;

    val sideSquares: Array<Square?>
        /**
         * Returns the squares on the side of the given square. A square on the side is on the same rank and in adjacent
         * files. For instance, side squares of [Square.D4] are the two adjacent squares on the 4th rank,
         * [Square.C4] and [Square.E4].
         *
         *
         * Note that squares on the edge files (`A` and `H`) have only one side square instead of two (on files
         * `B` and `G` respectively).
         *
         * @return the side squares of this square
         */
        get() = sideSquare[this]!!

    val rank: Rank
        /**
         * Returns the rank of the square.
         *
         * @return the rank of the square
         */
        get() = rankValues[ordinal / 8]

    val file: File
        /**
         * Returns the file of the square.
         *
         * @return the file of the square
         */
        get() = fileValues[ordinal % 8]

    /**
     * Returns the name of the square.
     *
     * @return the name of the square
     */
    fun value(): String {
        return name
    }

    val bitboard: Long
        /**
         * Returns the bitboard representation of this square, that is, the single bit in a 64-bits bitmap at the same index
         * of this square. If square is [Square.NONE], an empty bitboard is returned.
         *
         * @return the bitboard representation of this square, as a long value
         */
        get() {
            if (this == NONE) {
                return 0L
            }
            return Companion.bitboard[ordinal]
        }

    val isLightSquare: Boolean
        /**
         * Returns whether this is a light-square or not (i.e. a dark-square).
         *
         * @return `true` if the square is a light-square
         */
        get() = (this.bitboard and Bitboard.lightSquares) != 0L

    companion object {
        private val allSquares = entries.toTypedArray()
        private val rankValues = Rank.entries.toTypedArray()
        private val fileValues = File.entries.toTypedArray()
        private val bitboard = LongArray(allSquares.size)

        private val sideSquare = mutableMapOf<Square, Array<Square?>>()

        init {
            for (sq in allSquares) {
                bitboard[sq.ordinal] = 1L shl sq.ordinal
                if (NONE != sq) {
                    var a: Array<Square?>? = null
                    if (File.FILE_A == sq.file) {
                        a = arrayOfNulls(1)
                        a[0] = encode(sq.rank, File.FILE_B)
                    } else if (File.FILE_H == sq.file) {
                        a = arrayOfNulls(1)
                        a[0] = encode(sq.rank, File.FILE_G)
                    } else {
                        a = arrayOfNulls(2)
                        a[0] = encode(
                            sq.rank, fileValues[sq.file.ordinal - 1]
                        )
                        a[1] = encode(
                            sq.rank, fileValues[sq.file.ordinal + 1]
                        )
                    }
                    sideSquare[sq] = a
                }
            }
        }

        /**
         * Encodes a rank and a file into a square, returning the square value corresponding to the input values.
         *
         * @param rank a rank in the board
         * @param file a file in the board
         * @return the square that corresponds to the rank and file provided in input
         */
        fun encode(rank: Rank, file: File): Square {
            return allSquares[rank.ordinal * 8 + file.ordinal]
        }

        /**
         * Returns a square given its name.
         *
         *
         * Same as invoking [Square.valueOf].
         *
         * @param v name of the square
         * @return the square with the specified name
         * @throws IllegalArgumentException if the name does not correspond to any square
         */
        fun fromValue(v: String?): Square {
            return valueOf(v!!)
        }

        /**
         * Returns the square at position `index` in the board, or [Square.NONE] if the index is invalid.
         * Valid indexes are included between 0 ([Square.A1]) and 63 ([Square.H8]), increasing with files and
         * ranks respectively. Thus, index 1 corresponds to [Square.B1] and index 8 to [Square.A2].
         *
         * @param index the index of the square
         * @return the corresponding square, if index is valid, otherwise [Square.NONE]
         */
        fun squareAt(index: Int): Square {
            if (index < 0 || index >= allSquares.size) {
                return NONE
            }
            return allSquares[index]
        }
    }
}
