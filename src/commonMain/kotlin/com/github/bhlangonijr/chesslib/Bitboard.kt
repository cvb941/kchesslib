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
 * A collection of bitboards and related constant values useful to perform efficient board manipulations, fast squares
 * comparisons, and to mask some operations to limited portions of the board.
 *
 *
 * A bitboard is a specialized bit array data structure used in chess programming, where each bit corresponds to some
 * binary information stored in a board (e.g. the presence of a piece, the property of a square, etc.). Given
 * chessboards have 64 squares, bitboards can be represented by 64-bits numbers (long unsigned integer values). Data
 * manipulation and comparison of different bitboards can be performed using bitwise operations.
 */
object Bitboard {
    /**
     * The bitboard representing the light squares on a chessboard.
     */
    const val lightSquares: Long = 0x55AA55AA55AA55AAL

    /**
     * The bitboard representing the dark squares on a chessboard.
     */
    const val darkSquares: Long = -0x55aa55aa55aa55abL

    /**
     * Returns the bitboards representing the ranks on a chessboard.
     *
     * @return the bitboards representing the ranks
     */
    /**
     * The bitboards representing the ranks on a chessboard. Bitboard at index 0 identifies the 1st rank on a board,
     * bitboard at index 1 the 2nd rank, etc.
     */
    val rankbb: LongArray = longArrayOf(
        0x00000000000000FFL, 0x000000000000FF00L, 0x0000000000FF0000L, 0x00000000FF000000L,
        0x000000FF00000000L, 0x0000FF0000000000L, 0x00FF000000000000L, -0x100000000000000L
    )
    /**
     * Returns the bitboards representing the files on a chessboard.
     *
     * @return the bitboards representing the files
     */
    /**
     * The bitboards representing the files on a chessboard. Bitboard at index 0 identifies the 1st file on a board,
     * bitboard at index 1 the 2nd file, etc.
     */
    val filebb: LongArray = longArrayOf(
        0x0101010101010101L, 0x0202020202020202L, 0x0404040404040404L, 0x0808080808080808L,
        0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, -0x7f7f7f7f7f7f7f80L
    )

    /**
     * Table of bitboards that represent portions of board included between two squares, specified by the indexes used
     * to access the table. A portion of board is the two-dimensional space defined by the ranks and files of the
     * squares.
     *
     *
     * For instance, the bitboard looked up by coordinates `(0, 18)` defines the portion of the board included
     * between squares `A1` and `C3` (indexes 0 and 18 respectively), i.e. the set of squares
     * `[A1, B1, C1, A2, B2, C2, A3, B3, C3]`.
     */
    val bbTable: Array<LongArray> = Array(64) { LongArray(64) }

    /**
     * Table of *right-pointing* diagonals accessed by square index. For example, the diagonal looked up by index
     * 1 is the diagonal `B1-H7`, the diagonal the square `B1` (index 1) belongs to.
     */
    val squareToDiagonalA1H8: Array<DiagonalA1H8> = arrayOf(
        DiagonalA1H8.H8_A1,
        DiagonalA1H8.B1_H7,
        DiagonalA1H8.C1_H6,
        DiagonalA1H8.D1_H5,
        DiagonalA1H8.E1_H4,
        DiagonalA1H8.F1_H3,
        DiagonalA1H8.G1_H2,
        DiagonalA1H8.H1_H1,
        DiagonalA1H8.G8_A2,
        DiagonalA1H8.H8_A1,
        DiagonalA1H8.B1_H7,
        DiagonalA1H8.C1_H6,
        DiagonalA1H8.D1_H5,
        DiagonalA1H8.E1_H4,
        DiagonalA1H8.F1_H3,
        DiagonalA1H8.G1_H2,
        DiagonalA1H8.F8_A3,
        DiagonalA1H8.G8_A2,
        DiagonalA1H8.H8_A1,
        DiagonalA1H8.B1_H7,
        DiagonalA1H8.C1_H6,
        DiagonalA1H8.D1_H5,
        DiagonalA1H8.E1_H4,
        DiagonalA1H8.F1_H3,
        DiagonalA1H8.E8_A4,
        DiagonalA1H8.F8_A3,
        DiagonalA1H8.G8_A2,
        DiagonalA1H8.H8_A1,
        DiagonalA1H8.B1_H7,
        DiagonalA1H8.C1_H6,
        DiagonalA1H8.D1_H5,
        DiagonalA1H8.E1_H4,
        DiagonalA1H8.D8_A5,
        DiagonalA1H8.E8_A4,
        DiagonalA1H8.F8_A3,
        DiagonalA1H8.G8_A2,
        DiagonalA1H8.H8_A1,
        DiagonalA1H8.B1_H7,
        DiagonalA1H8.C1_H6,
        DiagonalA1H8.D1_H5,
        DiagonalA1H8.C8_A6,
        DiagonalA1H8.D8_A5,
        DiagonalA1H8.E8_A4,
        DiagonalA1H8.F8_A3,
        DiagonalA1H8.G8_A2,
        DiagonalA1H8.H8_A1,
        DiagonalA1H8.B1_H7,
        DiagonalA1H8.C1_H6,
        DiagonalA1H8.B8_A7,
        DiagonalA1H8.C8_A6,
        DiagonalA1H8.D8_A5,
        DiagonalA1H8.E8_A4,
        DiagonalA1H8.F8_A3,
        DiagonalA1H8.G8_A2,
        DiagonalA1H8.H8_A1,
        DiagonalA1H8.B1_H7,
        DiagonalA1H8.A8_A8,
        DiagonalA1H8.B8_A7,
        DiagonalA1H8.C8_A6,
        DiagonalA1H8.D8_A5,
        DiagonalA1H8.E8_A4,
        DiagonalA1H8.F8_A3,
        DiagonalA1H8.G8_A2,
        DiagonalA1H8.H8_A1
    )

    /**
     * Table of *left-pointing* diagonals accessed by square index. For example, the diagonal looked up by index
     * 1 is the diagonal `B1-A2`, the diagonal the square `B1` (index 1) belongs to.
     */
    val squareToDiagonalH1A8: Array<DiagonalH1A8> = arrayOf(
        DiagonalH1A8.A1_A1,
        DiagonalH1A8.B1_A2,
        DiagonalH1A8.C1_A3,
        DiagonalH1A8.D1_A4,
        DiagonalH1A8.E1_A5,
        DiagonalH1A8.F1_A6,
        DiagonalH1A8.G1_A7,
        DiagonalH1A8.H1_A8,
        DiagonalH1A8.B1_A2,
        DiagonalH1A8.C1_A3,
        DiagonalH1A8.D1_A4,
        DiagonalH1A8.E1_A5,
        DiagonalH1A8.F1_A6,
        DiagonalH1A8.G1_A7,
        DiagonalH1A8.H1_A8,
        DiagonalH1A8.B8_H2,
        DiagonalH1A8.C1_A3,
        DiagonalH1A8.D1_A4,
        DiagonalH1A8.E1_A5,
        DiagonalH1A8.F1_A6,
        DiagonalH1A8.G1_A7,
        DiagonalH1A8.H1_A8,
        DiagonalH1A8.B8_H2,
        DiagonalH1A8.C8_H3,
        DiagonalH1A8.D1_A4,
        DiagonalH1A8.E1_A5,
        DiagonalH1A8.F1_A6,
        DiagonalH1A8.G1_A7,
        DiagonalH1A8.H1_A8,
        DiagonalH1A8.B8_H2,
        DiagonalH1A8.C8_H3,
        DiagonalH1A8.D8_H4,
        DiagonalH1A8.E1_A5,
        DiagonalH1A8.F1_A6,
        DiagonalH1A8.G1_A7,
        DiagonalH1A8.H1_A8,
        DiagonalH1A8.B8_H2,
        DiagonalH1A8.C8_H3,
        DiagonalH1A8.D8_H4,
        DiagonalH1A8.E8_H5,
        DiagonalH1A8.F1_A6,
        DiagonalH1A8.G1_A7,
        DiagonalH1A8.H1_A8,
        DiagonalH1A8.B8_H2,
        DiagonalH1A8.C8_H3,
        DiagonalH1A8.D8_H4,
        DiagonalH1A8.E8_H5,
        DiagonalH1A8.F8_H6,
        DiagonalH1A8.G1_A7,
        DiagonalH1A8.H1_A8,
        DiagonalH1A8.B8_H2,
        DiagonalH1A8.C8_H3,
        DiagonalH1A8.D8_H4,
        DiagonalH1A8.E8_H5,
        DiagonalH1A8.F8_H6,
        DiagonalH1A8.G8_H7,
        DiagonalH1A8.H1_A8,
        DiagonalH1A8.B8_H2,
        DiagonalH1A8.C8_H3,
        DiagonalH1A8.D8_H4,
        DiagonalH1A8.E8_H5,
        DiagonalH1A8.F8_H6,
        DiagonalH1A8.G8_H7,
        DiagonalH1A8.H8_H8
    )

    /**
     * The bitboards representing the *right-pointing* diagonals on a chessboard. For example, the bitboard at
     * index 0 identifies the *right-pointing* diagonal at position 0, as defined by [DiagonalA1H8].
     */
    val diagonalA1H8BB: LongArray = longArrayOf(
        sq2Bb(Square.A8),
        sq2Bb(Square.B8) or sq2Bb(Square.A7),
        sq2Bb(Square.C8) or sq2Bb(Square.B7) or sq2Bb(Square.A6),
        sq2Bb(Square.D8) or sq2Bb(Square.C7) or sq2Bb(Square.B6) or sq2Bb(Square.A5),
        sq2Bb(Square.E8) or sq2Bb(Square.D7) or sq2Bb(Square.C6) or sq2Bb(Square.B5) or sq2Bb(Square.A4),
        sq2Bb(Square.F8) or sq2Bb(Square.E7) or sq2Bb(Square.D6) or sq2Bb(Square.C5) or sq2Bb(Square.B4) or sq2Bb(
            Square.A3
        ),
        sq2Bb(Square.G8) or sq2Bb(Square.F7) or sq2Bb(Square.E6) or sq2Bb(Square.D5) or sq2Bb(Square.C4) or sq2Bb(
            Square.B3
        ) or sq2Bb(Square.A2),
        sq2Bb(Square.H8) or sq2Bb(Square.G7) or sq2Bb(Square.F6) or sq2Bb(Square.E5) or sq2Bb(Square.D4) or sq2Bb(
            Square.C3
        ) or sq2Bb(Square.B2) or sq2Bb(Square.A1),
        sq2Bb(Square.B1) or sq2Bb(Square.C2) or sq2Bb(Square.D3) or sq2Bb(Square.E4) or sq2Bb(Square.F5) or sq2Bb(
            Square.G6
        ) or sq2Bb(Square.H7),
        sq2Bb(Square.C1) or sq2Bb(Square.D2) or sq2Bb(Square.E3) or sq2Bb(Square.F4) or sq2Bb(Square.G5) or sq2Bb(
            Square.H6
        ),
        sq2Bb(Square.D1) or sq2Bb(Square.E2) or sq2Bb(Square.F3) or sq2Bb(Square.G4) or sq2Bb(Square.H5),
        sq2Bb(Square.E1) or sq2Bb(Square.F2) or sq2Bb(Square.G3) or sq2Bb(Square.H4),
        sq2Bb(Square.F1) or sq2Bb(Square.G2) or sq2Bb(Square.H3),
        sq2Bb(Square.G1) or sq2Bb(Square.H2),
        sq2Bb(Square.H1)
    )

    /**
     * The bitboards representing the *left-pointing* diagonals on a chessboard. For example, the bitboard at index
     * 0 identifies the *left-pointing* diagonal at position 0, as defined by [DiagonalH1A8].
     */
    val diagonalH1A8BB: LongArray = longArrayOf(
        sq2Bb(Square.A1),
        sq2Bb(Square.B1) or sq2Bb(Square.A2),
        sq2Bb(Square.C1) or sq2Bb(Square.B2) or sq2Bb(Square.A3),
        sq2Bb(Square.D1) or sq2Bb(Square.C2) or sq2Bb(Square.B3) or sq2Bb(Square.A4),
        sq2Bb(Square.E1) or sq2Bb(Square.D2) or sq2Bb(Square.C3) or sq2Bb(Square.B4) or sq2Bb(Square.A5),
        sq2Bb(Square.F1) or sq2Bb(Square.E2) or sq2Bb(Square.D3) or sq2Bb(Square.C4) or sq2Bb(Square.B5) or sq2Bb(
            Square.A6
        ),
        sq2Bb(Square.G1) or sq2Bb(Square.F2) or sq2Bb(Square.E3) or sq2Bb(Square.D4) or sq2Bb(Square.C5) or sq2Bb(
            Square.B6
        ) or sq2Bb(Square.A7),
        sq2Bb(Square.H1) or sq2Bb(Square.G2) or sq2Bb(Square.F3) or sq2Bb(Square.E4) or sq2Bb(Square.D5) or sq2Bb(
            Square.C6
        ) or sq2Bb(Square.B7) or sq2Bb(Square.A8),
        sq2Bb(Square.B8) or sq2Bb(Square.C7) or sq2Bb(Square.D6) or sq2Bb(Square.E5) or sq2Bb(Square.F4) or sq2Bb(
            Square.G3
        ) or sq2Bb(Square.H2),
        sq2Bb(Square.C8) or sq2Bb(Square.D7) or sq2Bb(Square.E6) or sq2Bb(Square.F5) or sq2Bb(Square.G4) or sq2Bb(
            Square.H3
        ),
        sq2Bb(Square.D8) or sq2Bb(Square.E7) or sq2Bb(Square.F6) or sq2Bb(Square.G5) or sq2Bb(Square.H4),
        sq2Bb(Square.E8) or sq2Bb(Square.F7) or sq2Bb(Square.G6) or sq2Bb(Square.H5),
        sq2Bb(Square.F8) or sq2Bb(Square.G7) or sq2Bb(Square.H6),
        sq2Bb(Square.G8) or sq2Bb(Square.H7),
        sq2Bb(Square.H8)
    )

    /**
     * The bitboards representing the squares attacked by a knight placed in any given square on the board. Bitboard at
     * index 0 identifies the squares attacked by a knight placed at square `A1` (index 0), bitboard at index 1
     * the squares attacked from square `B1` (index 1), etc.
     *
     *
     * Contextually, the bitboards can also represent the squares from which a knight can attack the square.
     */
    val knightAttacks: LongArray = longArrayOf(
        0x0000000000020400L,
        0x0000000000050800L,
        0x00000000000a1100L,
        0x0000000000142200L,
        0x0000000000284400L,
        0x0000000000508800L,
        0x0000000000a01000L,
        0x0000000000402000L,
        0x0000000002040004L,
        0x0000000005080008L,
        0x000000000a110011L,
        0x0000000014220022L,
        0x0000000028440044L,
        0x0000000050880088L,
        0x00000000a0100010L,
        0x0000000040200020L,
        0x0000000204000402L,
        0x0000000508000805L,
        0x0000000a1100110aL,
        0x0000001422002214L,
        0x0000002844004428L,
        0x0000005088008850L,
        0x000000a0100010a0L,
        0x0000004020002040L,
        0x0000020400040200L,
        0x0000050800080500L,
        0x00000a1100110a00L,
        0x0000142200221400L,
        0x0000284400442800L,
        0x0000508800885000L,
        0x0000a0100010a000L,
        0x0000402000204000L,
        0x0002040004020000L,
        0x0005080008050000L,
        0x000a1100110a0000L,
        0x0014220022140000L,
        0x0028440044280000L,
        0x0050880088500000L,
        0x00a0100010a00000L,
        0x0040200020400000L,
        0x0204000402000000L,
        0x0508000805000000L,
        0x0a1100110a000000L,
        0x1422002214000000L,
        0x2844004428000000L,
        0x5088008850000000L,
        -0x5fefffef60000000L,
        0x4020002040000000L,
        0x0400040200000000L,
        0x0800080500000000L,
        0x1100110a00000000L,
        0x2200221400000000L,
        0x4400442800000000L,
        -0x77ff77b000000000L,
        0x100010a000000000L,
        0x2000204000000000L,
        0x0004020000000000L,
        0x0008050000000000L,
        0x00110a0000000000L,
        0x0022140000000000L,
        0x0044280000000000L,
        0x0088500000000000L,
        0x0010a00000000000L,
        0x0020400000000000L
    )

    /**
     * The bitboards representing the squares attacked by a white pawn placed in any given square on the board. Bitboard
     * at index 8 identifies the squares attacked by a white pawn placed at square `A2` (index 8), bitboard at
     * index 9 the squares attacked from square `B2` (index 9), etc.
     */
    val whitePawnAttacks: LongArray = longArrayOf(
        0x0000000000000200L,
        0x0000000000000500L,
        0x0000000000000a00L,
        0x0000000000001400L,
        0x0000000000002800L,
        0x0000000000005000L,
        0x000000000000a000L,
        0x0000000000004000L,
        0x0000000000020000L,
        0x0000000000050000L,
        0x00000000000a0000L,
        0x0000000000140000L,
        0x0000000000280000L,
        0x0000000000500000L,
        0x0000000000a00000L,
        0x0000000000400000L,
        0x0000000002000000L,
        0x0000000005000000L,
        0x000000000a000000L,
        0x0000000014000000L,
        0x0000000028000000L,
        0x0000000050000000L,
        0x00000000a0000000L,
        0x0000000040000000L,
        0x0000000200000000L,
        0x0000000500000000L,
        0x0000000a00000000L,
        0x0000001400000000L,
        0x0000002800000000L,
        0x0000005000000000L,
        0x000000a000000000L,
        0x0000004000000000L,
        0x0000020000000000L,
        0x0000050000000000L,
        0x00000a0000000000L,
        0x0000140000000000L,
        0x0000280000000000L,
        0x0000500000000000L,
        0x0000a00000000000L,
        0x0000400000000000L,
        0x0002000000000000L,
        0x0005000000000000L,
        0x000a000000000000L,
        0x0014000000000000L,
        0x0028000000000000L,
        0x0050000000000000L,
        0x00a0000000000000L,
        0x0040000000000000L,
        0x0200000000000000L,
        0x0500000000000000L,
        0x0a00000000000000L,
        0x1400000000000000L,
        0x2800000000000000L,
        0x5000000000000000L,
        -0x6000000000000000L,
        0x4000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L
    )

    /**
     * The bitboards representing the squares attacked by a black pawn placed in any given square on the board. Bitboard
     * at index 48 identifies the squares attacked by a black pawn placed at square `A7` (index 48), bitboard at
     * index 49 the squares attacked from square `B7` (index 49), etc.
     */
    val blackPawnAttacks: LongArray = longArrayOf(
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000002L,
        0x0000000000000005L,
        0x000000000000000aL,
        0x0000000000000014L,
        0x0000000000000028L,
        0x0000000000000050L,
        0x00000000000000a0L,
        0x0000000000000040L,
        0x0000000000000200L,
        0x0000000000000500L,
        0x0000000000000a00L,
        0x0000000000001400L,
        0x0000000000002800L,
        0x0000000000005000L,
        0x000000000000a000L,
        0x0000000000004000L,
        0x0000000000020000L,
        0x0000000000050000L,
        0x00000000000a0000L,
        0x0000000000140000L,
        0x0000000000280000L,
        0x0000000000500000L,
        0x0000000000a00000L,
        0x0000000000400000L,
        0x0000000002000000L,
        0x0000000005000000L,
        0x000000000a000000L,
        0x0000000014000000L,
        0x0000000028000000L,
        0x0000000050000000L,
        0x00000000a0000000L,
        0x0000000040000000L,
        0x0000000200000000L,
        0x0000000500000000L,
        0x0000000a00000000L,
        0x0000001400000000L,
        0x0000002800000000L,
        0x0000005000000000L,
        0x000000a000000000L,
        0x0000004000000000L,
        0x0000020000000000L,
        0x0000050000000000L,
        0x00000a0000000000L,
        0x0000140000000000L,
        0x0000280000000000L,
        0x0000500000000000L,
        0x0000a00000000000L,
        0x0000400000000000L,
        0x0002000000000000L,
        0x0005000000000000L,
        0x000a000000000000L,
        0x0014000000000000L,
        0x0028000000000000L,
        0x0050000000000000L,
        0x00a0000000000000L,
        0x0040000000000000L
    )

    /**
     * The bitboards representing the squares a white pawn can move to from any given square on the board. Bitboard at
     * index 8 identifies the squares a white pawn can reach from square `A2` (index 8), bitboard at index 9 the
     * squares reachable from square `B2` (index 9), etc.
     */
    val whitePawnMoves: LongArray = longArrayOf(
        0x0000000000000100L,
        0x0000000000000200L,
        0x0000000000000400L,
        0x0000000000000800L,
        0x0000000000001000L,
        0x0000000000002000L,
        0x0000000000004000L,
        0x0000000000008000L,
        0x0000000001010000L,
        0x0000000002020000L,
        0x0000000004040000L,
        0x0000000008080000L,
        0x0000000010100000L,
        0x0000000020200000L,
        0x0000000040400000L,
        0x0000000080800000L,
        0x0000000001000000L,
        0x0000000002000000L,
        0x0000000004000000L,
        0x0000000008000000L,
        0x0000000010000000L,
        0x0000000020000000L,
        0x0000000040000000L,
        0x0000000080000000L,
        0x0000000100000000L,
        0x0000000200000000L,
        0x0000000400000000L,
        0x0000000800000000L,
        0x0000001000000000L,
        0x0000002000000000L,
        0x0000004000000000L,
        0x0000008000000000L,
        0x0000010000000000L,
        0x0000020000000000L,
        0x0000040000000000L,
        0x0000080000000000L,
        0x0000100000000000L,
        0x0000200000000000L,
        0x0000400000000000L,
        0x0000800000000000L,
        0x0001000000000000L,
        0x0002000000000000L,
        0x0004000000000000L,
        0x0008000000000000L,
        0x0010000000000000L,
        0x0020000000000000L,
        0x0040000000000000L,
        0x0080000000000000L,
        0x0100000000000000L,
        0x0200000000000000L,
        0x0400000000000000L,
        0x0800000000000000L,
        0x1000000000000000L,
        0x2000000000000000L,
        0x4000000000000000L,
        (0x8000000000000000u).toLong(),
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L
    )

    /**
     * The bitboards representing the squares a black pawn can move to from any given square on the board. Bitboard at
     * index 48 identifies the squares a black pawn can reach from square `A7` (index 48), bitboard at index 49
     * the squares reachable from square `B7` (index 49), etc.
     */
    val blackPawnMoves: LongArray = longArrayOf(
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000000L,
        0x0000000000000001L,
        0x0000000000000002L,
        0x0000000000000004L,
        0x0000000000000008L,
        0x0000000000000010L,
        0x0000000000000020L,
        0x0000000000000040L,
        0x0000000000000080L,
        0x0000000000000100L,
        0x0000000000000200L,
        0x0000000000000400L,
        0x0000000000000800L,
        0x0000000000001000L,
        0x0000000000002000L,
        0x0000000000004000L,
        0x0000000000008000L,
        0x0000000000010000L,
        0x0000000000020000L,
        0x0000000000040000L,
        0x0000000000080000L,
        0x0000000000100000L,
        0x0000000000200000L,
        0x0000000000400000L,
        0x0000000000800000L,
        0x0000000001000000L,
        0x0000000002000000L,
        0x0000000004000000L,
        0x0000000008000000L,
        0x0000000010000000L,
        0x0000000020000000L,
        0x0000000040000000L,
        0x0000000080000000L,
        0x0000000100000000L,
        0x0000000200000000L,
        0x0000000400000000L,
        0x0000000800000000L,
        0x0000001000000000L,
        0x0000002000000000L,
        0x0000004000000000L,
        0x0000008000000000L,
        0x0000010100000000L,
        0x0000020200000000L,
        0x0000040400000000L,
        0x0000080800000000L,
        0x0000101000000000L,
        0x0000202000000000L,
        0x0000404000000000L,
        0x0000808000000000L,
        0x0001000000000000L,
        0x0002000000000000L,
        0x0004000000000000L,
        0x0008000000000000L,
        0x0010000000000000L,
        0x0020000000000000L,
        0x0040000000000000L,
        0x0080000000000000L
    )

    /**
     * The bitboards representing the adjacent squares of any given square on the board. For instance, bitboard at index
     * 0 identifies the squares `B1`, `A2` and `B2`, adjacent to square `A1`, identified by
     * index 0.
     */
    val adjacentSquares: LongArray = longArrayOf(
        0x0000000000000302L,
        0x0000000000000705L,
        0x0000000000000e0aL,
        0x0000000000001c14L,
        0x0000000000003828L,
        0x0000000000007050L,
        0x000000000000e0a0L,
        0x000000000000c040L,
        0x0000000000030203L,
        0x0000000000070507L,
        0x00000000000e0a0eL,
        0x00000000001c141cL,
        0x0000000000382838L,
        0x0000000000705070L,
        0x0000000000e0a0e0L,
        0x0000000000c040c0L,
        0x0000000003020300L,
        0x0000000007050700L,
        0x000000000e0a0e00L,
        0x000000001c141c00L,
        0x0000000038283800L,
        0x0000000070507000L,
        0x00000000e0a0e000L,
        0x00000000c040c000L,
        0x0000000302030000L,
        0x0000000705070000L,
        0x0000000e0a0e0000L,
        0x0000001c141c0000L,
        0x0000003828380000L,
        0x0000007050700000L,
        0x000000e0a0e00000L,
        0x000000c040c00000L,
        0x0000030203000000L,
        0x0000070507000000L,
        0x00000e0a0e000000L,
        0x00001c141c000000L,
        0x0000382838000000L,
        0x0000705070000000L,
        0x0000e0a0e0000000L,
        0x0000c040c0000000L,
        0x0003020300000000L,
        0x0007050700000000L,
        0x000e0a0e00000000L,
        0x001c141c00000000L,
        0x0038283800000000L,
        0x0070507000000000L,
        0x00e0a0e000000000L,
        0x00c040c000000000L,
        0x0302030000000000L,
        0x0705070000000000L,
        0x0e0a0e0000000000L,
        0x1c141c0000000000L,
        0x3828380000000000L,
        0x7050700000000000L,
        -0x1f5f200000000000L,
        -0x3fbf400000000000L,
        0x0203000000000000L,
        0x0507000000000000L,
        0x0a0e000000000000L,
        0x141c000000000000L,
        0x2838000000000000L,
        0x5070000000000000L,
        -0x5f20000000000000L,
        0x40c0000000000000L
    )

    /**
     * The bitboards representing the squares a piece that moves horizontally can attack from any given square on the
     * board. For example, bitboard at index 0 identifies the squares on the same rank attacked from square `A1`
     * (index 0), that is, all squares of the 1st rank except the square `A1` itself (i.e. `B1`, `C1`,
     * etc.).
     *
     *
     * Contextually, the bitboards can also represent the squares from which a piece on the same rank can attack the
     * square.
     */
    val rankAttacks: LongArray = longArrayOf(
        sq2RA(Square.A1),
        sq2RA(Square.B1),
        sq2RA(Square.C1),
        sq2RA(Square.D1),
        sq2RA(Square.E1),
        sq2RA(Square.F1),
        sq2RA(Square.G1),
        sq2RA(Square.H1),
        sq2RA(Square.A2),
        sq2RA(Square.B2),
        sq2RA(Square.C2),
        sq2RA(Square.D2),
        sq2RA(Square.E2),
        sq2RA(Square.F2),
        sq2RA(Square.G2),
        sq2RA(Square.H2),
        sq2RA(Square.A3),
        sq2RA(Square.B3),
        sq2RA(Square.C3),
        sq2RA(Square.D3),
        sq2RA(Square.E3),
        sq2RA(Square.F3),
        sq2RA(Square.G3),
        sq2RA(Square.H3),
        sq2RA(Square.A4),
        sq2RA(Square.B4),
        sq2RA(Square.C4),
        sq2RA(Square.D4),
        sq2RA(Square.E4),
        sq2RA(Square.F4),
        sq2RA(Square.G4),
        sq2RA(Square.H4),
        sq2RA(Square.A5),
        sq2RA(Square.B5),
        sq2RA(Square.C5),
        sq2RA(Square.D5),
        sq2RA(Square.E5),
        sq2RA(Square.F5),
        sq2RA(Square.G5),
        sq2RA(Square.H5),
        sq2RA(Square.A6),
        sq2RA(Square.B6),
        sq2RA(Square.C6),
        sq2RA(Square.D6),
        sq2RA(Square.E6),
        sq2RA(Square.F6),
        sq2RA(Square.G6),
        sq2RA(Square.H6),
        sq2RA(Square.A7),
        sq2RA(Square.B7),
        sq2RA(Square.C7),
        sq2RA(Square.D7),
        sq2RA(Square.E7),
        sq2RA(Square.F7),
        sq2RA(Square.G7),
        sq2RA(Square.H7),
        sq2RA(Square.A8),
        sq2RA(Square.B8),
        sq2RA(Square.C8),
        sq2RA(Square.D8),
        sq2RA(Square.E8),
        sq2RA(Square.F8),
        sq2RA(Square.G8),
        sq2RA(Square.H8)
    )

    /**
     * The bitboards representing the squares a piece that moves vertically can attack from any given square on the
     * board. For example, bitboard at index 0 identifies the squares on the same file attacked from square `A1`
     * (index 0), that is, all squares of the 1st file except the square `A1` itself (i.e. `A2`, `A3`,
     * etc.).
     *
     *
     * Contextually, the bitboards can also represent the squares from which a piece on the same file can attack the
     * square.
     */
    val fileAttacks: LongArray = longArrayOf(
        sq2FA(Square.A1),
        sq2FA(Square.B1),
        sq2FA(Square.C1),
        sq2FA(Square.D1),
        sq2FA(Square.E1),
        sq2FA(Square.F1),
        sq2FA(Square.G1),
        sq2FA(Square.H1),
        sq2FA(Square.A2),
        sq2FA(Square.B2),
        sq2FA(Square.C2),
        sq2FA(Square.D2),
        sq2FA(Square.E2),
        sq2FA(Square.F2),
        sq2FA(Square.G2),
        sq2FA(Square.H2),
        sq2FA(Square.A3),
        sq2FA(Square.B3),
        sq2FA(Square.C3),
        sq2FA(Square.D3),
        sq2FA(Square.E3),
        sq2FA(Square.F3),
        sq2FA(Square.G3),
        sq2FA(Square.H3),
        sq2FA(Square.A4),
        sq2FA(Square.B4),
        sq2FA(Square.C4),
        sq2FA(Square.D4),
        sq2FA(Square.E4),
        sq2FA(Square.F4),
        sq2FA(Square.G4),
        sq2FA(Square.H4),
        sq2FA(Square.A5),
        sq2FA(Square.B5),
        sq2FA(Square.C5),
        sq2FA(Square.D5),
        sq2FA(Square.E5),
        sq2FA(Square.F5),
        sq2FA(Square.G5),
        sq2FA(Square.H5),
        sq2FA(Square.A6),
        sq2FA(Square.B6),
        sq2FA(Square.C6),
        sq2FA(Square.D6),
        sq2FA(Square.E6),
        sq2FA(Square.F6),
        sq2FA(Square.G6),
        sq2FA(Square.H6),
        sq2FA(Square.A7),
        sq2FA(Square.B7),
        sq2FA(Square.C7),
        sq2FA(Square.D7),
        sq2FA(Square.E7),
        sq2FA(Square.F7),
        sq2FA(Square.G7),
        sq2FA(Square.H7),
        sq2FA(Square.A8),
        sq2FA(Square.B8),
        sq2FA(Square.C8),
        sq2FA(Square.D8),
        sq2FA(Square.E8),
        sq2FA(Square.F8),
        sq2FA(Square.G8),
        sq2FA(Square.H8)
    )

    /**
     * The bitboards representing the squares a piece that moves diagonally can attack on the same *right-pointing*
     * diagonal from any given square on the board. For example, bitboard at index 1 identifies the squares on the same
     * diagonal attacked from square `B1` (index 1), that is, all squares of the B1-H7 diagonal (as defined by
     * [DiagonalA1H8]) except the square `B1` itself (i.e. `B2`, `C3`, etc.).
     *
     *
     * Contextually, the bitboards can also represent the squares from which a piece on the same *right-pointing*
     * diagonal can attack the square.
     */
    val diagA1H8Attacks: LongArray = longArrayOf(
        sq2A1(Square.A1),
        sq2A1(Square.B1),
        sq2A1(Square.C1),
        sq2A1(Square.D1),
        sq2A1(Square.E1),
        sq2A1(Square.F1),
        sq2A1(Square.G1),
        sq2A1(Square.H1),
        sq2A1(Square.A2),
        sq2A1(Square.B2),
        sq2A1(Square.C2),
        sq2A1(Square.D2),
        sq2A1(Square.E2),
        sq2A1(Square.F2),
        sq2A1(Square.G2),
        sq2A1(Square.H2),
        sq2A1(Square.A3),
        sq2A1(Square.B3),
        sq2A1(Square.C3),
        sq2A1(Square.D3),
        sq2A1(Square.E3),
        sq2A1(Square.F3),
        sq2A1(Square.G3),
        sq2A1(Square.H3),
        sq2A1(Square.A4),
        sq2A1(Square.B4),
        sq2A1(Square.C4),
        sq2A1(Square.D4),
        sq2A1(Square.E4),
        sq2A1(Square.F4),
        sq2A1(Square.G4),
        sq2A1(Square.H4),
        sq2A1(Square.A5),
        sq2A1(Square.B5),
        sq2A1(Square.C5),
        sq2A1(Square.D5),
        sq2A1(Square.E5),
        sq2A1(Square.F5),
        sq2A1(Square.G5),
        sq2A1(Square.H5),
        sq2A1(Square.A6),
        sq2A1(Square.B6),
        sq2A1(Square.C6),
        sq2A1(Square.D6),
        sq2A1(Square.E6),
        sq2A1(Square.F6),
        sq2A1(Square.G6),
        sq2A1(Square.H6),
        sq2A1(Square.A7),
        sq2A1(Square.B7),
        sq2A1(Square.C7),
        sq2A1(Square.D7),
        sq2A1(Square.E7),
        sq2A1(Square.F7),
        sq2A1(Square.G7),
        sq2A1(Square.H7),
        sq2A1(Square.A8),
        sq2A1(Square.B8),
        sq2A1(Square.C8),
        sq2A1(Square.D8),
        sq2A1(Square.E8),
        sq2A1(Square.F8),
        sq2A1(Square.G8),
        sq2A1(Square.H8)
    )

    /**
     * The bitboards representing the squares a piece that moves diagonally can attack on the same *left-pointing*
     * diagonal from any given square on the board. For example, bitboard at index 1 identifies the squares on the same
     * diagonal attacked from square `B1` (index 1), that is, all squares of the B1-A2 diagonal (as defined by
     * [DiagonalH1A8]) except the square `B1` itself (i.e. only square `A2`).
     *
     *
     * Contextually, the bitboards can also represent the squares from which a piece on the same *left-pointing*
     * diagonal can attack the square.
     */
    val diagH1A8Attacks: LongArray = longArrayOf(
        sq2H1(Square.A1),
        sq2H1(Square.B1),
        sq2H1(Square.C1),
        sq2H1(Square.D1),
        sq2H1(Square.E1),
        sq2H1(Square.F1),
        sq2H1(Square.G1),
        sq2H1(Square.H1),
        sq2H1(Square.A2),
        sq2H1(Square.B2),
        sq2H1(Square.C2),
        sq2H1(Square.D2),
        sq2H1(Square.E2),
        sq2H1(Square.F2),
        sq2H1(Square.G2),
        sq2H1(Square.H2),
        sq2H1(Square.A3),
        sq2H1(Square.B3),
        sq2H1(Square.C3),
        sq2H1(Square.D3),
        sq2H1(Square.E3),
        sq2H1(Square.F3),
        sq2H1(Square.G3),
        sq2H1(Square.H3),
        sq2H1(Square.A4),
        sq2H1(Square.B4),
        sq2H1(Square.C4),
        sq2H1(Square.D4),
        sq2H1(Square.E4),
        sq2H1(Square.F4),
        sq2H1(Square.G4),
        sq2H1(Square.H4),
        sq2H1(Square.A5),
        sq2H1(Square.B5),
        sq2H1(Square.C5),
        sq2H1(Square.D5),
        sq2H1(Square.E5),
        sq2H1(Square.F5),
        sq2H1(Square.G5),
        sq2H1(Square.H5),
        sq2H1(Square.A6),
        sq2H1(Square.B6),
        sq2H1(Square.C6),
        sq2H1(Square.D6),
        sq2H1(Square.E6),
        sq2H1(Square.F6),
        sq2H1(Square.G6),
        sq2H1(Square.H6),
        sq2H1(Square.A7),
        sq2H1(Square.B7),
        sq2H1(Square.C7),
        sq2H1(Square.D7),
        sq2H1(Square.E7),
        sq2H1(Square.F7),
        sq2H1(Square.G7),
        sq2H1(Square.H7),
        sq2H1(Square.A8),
        sq2H1(Square.B8),
        sq2H1(Square.C8),
        sq2H1(Square.D8),
        sq2H1(Square.E8),
        sq2H1(Square.F8),
        sq2H1(Square.G8),
        sq2H1(Square.H8)
    )

    init {
        for (x in 0..63) {
            for (y in 0..63) {
                bbTable[x][y] = ((1L shl y) or ((1L shl y) - (1L shl x)))
            }
        }
    }

    /**
     * Returns the bitboard representing the single square provided in input.
     *
     * @param sq the square for which the bitboard must be returned
     * @return the bitboard representation of the square
     */
    fun sq2Bb(sq: Square): Long {
        return sq.bitboard
    }

    /**
     * Returns the bitboard representing the squares on the same rank attacked from the square provided in input. For
     * example, the bitboard of square `A1` includes all squares of the 1st rank except the square `A1`
     * itself (i.e. `B1`, `C1`, etc.).
     *
     * @param x the square for which the bitboard must be returned
     * @return the bitboard representation of the attacked squares on the same rank
     */
    fun sq2RA(x: Square): Long {
        return (rankbb[x.rank.ordinal] xor sq2Bb(x))
    }

    /**
     * Returns the bitboard representing the squares on the same file attacked from the square provided in input. For
     * example, the bitboard of square `A1` includes all squares of the 1st file except the square `A1`
     * itself (i.e. `A2`, `A3`, etc.).
     *
     * @param x the square for which the bitboard must be returned
     * @return the bitboard representation of the attacked squares on the same file
     */
    fun sq2FA(x: Square): Long {
        return (filebb[x.file.ordinal] xor x.bitboard)
    }

    /**
     * Returns the bitboard representing the squares on the same *right-pointing* diagonal attacked from the square
     * provided in input. For example, the bitboard of square `B1` includes all squares of the B1-H7 diagonal
     * (as defined by [DiagonalA1H8]) except the square `B1` itself (i.e. `B2`, `C3`, etc.).
     *
     * @param x the square for which the bitboard must be returned
     * @return the bitboard representation of the attacked squares on the same *right-pointing* diagonal
     */
    fun sq2A1(x: Square): Long {
        return (diagonalA1H8BB[squareToDiagonalA1H8[x.ordinal].ordinal] xor sq2Bb(x))
    }

    /**
     * Returns the bitboard representing the squares on the same *left-pointing* diagonal attacked from the square
     * provided in input. For example, the bitboard of square `B1` includes all squares of the B1-A2 diagonal
     * (as defined by [DiagonalH1A8]) except the square `B1` itself (i.e. only the square `A2`).
     *
     * @param x the square for which the bitboard must be returned
     * @return the bitboard representation of the attacked squares on the same *left-pointing* diagonal
     */
    fun sq2H1(x: Square): Long {
        return (diagonalH1A8BB[squareToDiagonalH1A8[x.ordinal].ordinal] xor sq2Bb(x))
    }

    /**
     * Returns the index of the first (*rightmost*) bit set to 1 in the bitboard provided in input. The bit is the
     * Least Significant 1-bit (LS1B).
     *
     * @param bb the bitboard for which the LS1B is to be returned
     * @return the index of the first bit set to 1
     */
    @JvmStatic
    fun bitScanForward(bb: Long): Int {
        return bb.countTrailingZeroBits()
    }

    /**
     * Returns the index of the last (*leftmost*) bit set to 1 in the bitboard provided in input. The bit is the
     * Most Significant 1-bit (MS1B).
     *
     * @param bb the bitboard for which the MS1B is to be returned
     * @return the index of the last bit set to 1
     */
    @JvmStatic
    @OptIn(ExperimentalStdlibApi::class)
    fun bitScanReverse(bb: Long): Int {
        return 63 - bb.countLeadingZeroBits()
    }

    /**
     * Returns the *sub-bitboard* included between the two squares provided in input, that is, the portion of the
     * bitboard included between two squares. The *sub-bitboard* is a two-dimensional space defined by the ranks
     * and files of the two squares. For example, if squares `A1` and `C3` are provided in input, the method
     * returns only the following squares of the original bitboard: `[A1, B1, C1, A2, B2, C2, A3, B3, C3]`. All
     * other bits are set to 0.
     *
     * @param bb  the bitboard the portion between the two squares must be returned
     * @param sq1 the first square that defines the two-dimensional space
     * @param sq2 the second square that defines the two-dimensional space
     * @return the portion of the original bitboard included between the two squares
     */
    fun bitsBetween(bb: Long, sq1: Int, sq2: Int): Long {
        return bbTable[sq1][sq2] and bb
    }

    /**
     * Unsets the first bit set to 1. In other words, it sets to 0 the Least Significant 1-bit (LS1B).
     *
     * @param bb the bitboard to compute
     * @return the resulting bitboard, from which the first bit set to 1 has been unset
     */
    @JvmStatic
    fun extractLsb(bb: Long): Long {
        return bb and (bb - 1)
    }

    /**
     * Check whether the given bitboard has only one bit set to 1.
     *
     * @param bb the bitboard to check
     * @return `true` if the bitboard has only one bit set to 1
     */
    fun hasOnly1Bit(bb: Long): Boolean {
        return bb > 0L && extractLsb(bb) == 0L
    }

    /**
     * Returns the bitboard representing the square provided in input.
     *
     * @param sq the square for which the bitboard must be returned
     * @return the bitboard representing the single square
     */
    fun getBbtable(sq: Square): Long {
        return 1L shl sq.ordinal
    }

    /**
     * Returns the bitboard representing the bishop movement attacks, computed applying the provided mask. It could
     * either refer to the squares attacked by a bishop placed on the input square, or conversely the bishops that can
     * attack the square.
     *
     * @param square the square for which to calculate the bishop attacks
     * @param mask   the mask to apply to the bishop attacks
     * @return the bitboard of bishop movement attacks
     */
    @JvmStatic
    fun getBishopAttacks(mask: Long, square: Square): Long {
        return getSliderAttacks(diagA1H8Attacks[square.ordinal], mask, square.ordinal) or
                getSliderAttacks(diagH1A8Attacks[square.ordinal], mask, square.ordinal)
    }

    /**
     * Returns the bitboard representing the rook movement attacks, computed applying the provided mask. It could either
     * refer to the squares attacked by a rook placed on the input square, or conversely the rooks that can attack the
     * square.
     *
     * @param square the square for which to calculate the rook attacks
     * @param mask   the mask to apply to the rook attacks
     * @return the bitboard of rook movement attacks
     */
    @JvmStatic
    fun getRookAttacks(mask: Long, square: Square): Long {
        return getSliderAttacks(fileAttacks[square.ordinal], mask, square.ordinal) or
                getSliderAttacks(rankAttacks[square.ordinal], mask, square.ordinal)
    }

    private fun getSliderAttacks(attacks: Long, mask: Long, index: Int): Long {
        val occ = mask and attacks
        if (occ == 0L) {
            return attacks
        }
        val m = (1L shl index) - 1L
        val lowerMask = occ and m
        val upperMask = occ and m.inv()
        val minor = if (lowerMask == 0L) 0 else bitScanReverse(lowerMask)
        val major = if (upperMask == 0L) 63 else bitScanForward(upperMask)
        return bitsBetween(attacks, minor, major)
    }

    /**
     * Returns the bitboard representing the queen movement attacks, computed applying the provided mask. It could
     * either refer to the squares attacked by a queen placed on the input square, or conversely the queens that can
     * attack the square.
     *
     * @param square the square for which to calculate the queen attacks
     * @param mask   the mask to apply to the queen attacks
     * @return the bitboard of queen movement attacks
     */
    fun getQueenAttacks(mask: Long, square: Square): Long {
        return getRookAttacks(mask, square) or
                getBishopAttacks(mask, square)
    }

    /**
     * Returns the bitboard representing the knight movement attacks, computed applying the provided mask. It could
     * either refer to the squares attacked by a knight placed on the input square, or conversely the knights that can
     * attack the square.
     *
     * @param square the square for which to calculate the knight attacks
     * @param mask   the mask to apply to the knight attacks
     * @return the bitboard of knight movement attacks
     */
    fun getKnightAttacks(square: Square, mask: Long): Long {
        return knightAttacks[square.ordinal] and mask
    }

    /**
     * Returns the bitboard representing the squares attacked by a pawn placed on the input square for a given side.
     *
     * @param side   the side to move
     * @param square the square the pawn is placed
     * @return the bitboard of the squares attacked by the pawn
     */
    fun getPawnAttacks(side: Side, square: Square): Long {
        return (if (side == Side.WHITE) whitePawnAttacks[square.ordinal] else blackPawnAttacks[square.ordinal])
    }

    /**
     * Returns the bitboard representing the possible captures by a pawn placed on the input square for a given side.
     * The method expects a bitboard of possible targets, the occupied squares, and the square for which en passant is
     * playable ([Square.NONE] if en passant can not be played).
     *
     * @param side      the side to move
     * @param square    the square the pawn is placed
     * @param occupied  a bitboard of possible targets
     * @param enPassant the square in which en passant capture is possible, [Square.NONE] otherwise
     * @return the bitboard of the squares where the pawn can move to capturing a piece
     */
    fun getPawnCaptures(
        side: Side, square: Square,
        occupied: Long, enPassant: Square
    ): Long {
        var occupied = occupied
        val pawnAttacks =
            (if (side == Side.WHITE) whitePawnAttacks[square.ordinal] else blackPawnAttacks[square.ordinal])
        if (enPassant != Square.NONE) {
            val ep = enPassant.bitboard
            occupied = occupied or if (side == Side.WHITE) ep shl 8L.toInt() else ep shr 8L.toInt()
        }
        return pawnAttacks and occupied
    }

    /**
     * Returns the bitboard representing the possible moves, excluding captures, by a pawn placed on the input square
     * for a given side. The method expects a bitboard of occupied squares where the pawn can not move to.
     *
     * @param side     the side to move
     * @param square   the square the pawn is placed
     * @param occupied a bitboard of occupied squares
     * @return the bitboard of the squares where the pawn can move to
     */
    fun getPawnMoves(side: Side, square: Square, occupied: Long): Long {
        val pawnMoves =
            (if (side == Side.WHITE) whitePawnMoves[square.ordinal] else blackPawnMoves[square.ordinal])
        var occ = occupied

        if (square.rank == Rank.RANK_2 && side == Side.WHITE) {
            if ((square.bitboard shl 8 and occ) != 0L) {
                occ = occ or (square.bitboard shl 16) // double move
            }
        } else if (square.rank == Rank.RANK_7 && side == Side.BLACK) {
            if ((square.bitboard shr 8 and occ) != 0L) {
                occ = occ or (square.bitboard shr 16) // double move
            }
        }
        return pawnMoves and occ.inv()
    }

    /**
     * Returns the bitboard representing the king movement attacks, computed applying the provided mask. It could either
     * refer to the squares attacked by a king placed on the input square, or conversely the kings that can attack the
     * square.
     *
     * @param square the square for which to calculate the king attacks
     * @param mask   the mask to apply to the king attacks
     * @return the bitboard of king movement attacks
     */
    fun getKingAttacks(square: Square, mask: Long): Long {
        return adjacentSquares[square.ordinal] and mask
    }

    /**
     * Returns the list of squares that are set in the bitboard provided in input, that is, the squares corresponding to
     * the indexes set to 1 in the bitboard.
     *
     * @param bb the bitboard from which the list of squares must be returned
     * @return the list of squares corresponding to the bits set to 1 in the bitboard
     */
    @JvmStatic
    fun bbToSquareList(bb: Long): List<Square> {
        var bb = bb
        val squares: MutableList<Square> = mutableListOf()
        while (bb != 0L) {
            val sq = bitScanForward(bb)
            bb = extractLsb(bb)
            squares.add(Square.Companion.squareAt(sq))
        }
        return squares
    }

    /**
     * Returns the array of squares that are set in the bitboard provided in input, that is, the squares corresponding
     * to the indexes set to 1 in the bitboard.
     *
     * @param bb the bitboard from which the array of squares must be returned
     * @return the array of squares corresponding to the bits set to 1 in the bitboard
     */
    fun bbToSquareArray(bb: Long): Array<Square?> {
        var bb = bb
        val squares = arrayOfNulls<Square>(bb.countOneBits())
        var index = 0
        while (bb != 0L) {
            val sq = bitScanForward(bb)
            bb = extractLsb(bb)
            squares[index++] = Square.Companion.squareAt(sq)
        }
        return squares
    }

    /**
     * Returns the bitboard representing the entire rank of the square given in input.
     *
     * @param sq the square for which the bitboard rank must be returned
     * @return the bitboards representing the rank of the square
     */
    fun getRankbb(sq: Square): Long {
        return rankbb[sq.rank.ordinal]
    }

    /**
     * Returns the bitboard representing the entire file of the square given in input.
     *
     * @param sq the square for which the bitboard file must be returned
     * @return the bitboards representing the file of the square
     */
    fun getFilebb(sq: Square): Long {
        return filebb[sq.file.ordinal]
    }

    /**
     * Returns the bitboard representing the rank given in input.
     *
     * @param rank the rank for which the corresponding bitboard must be returned
     * @return the bitboards representing the rank
     */
    fun getRankbb(rank: Rank): Long {
        return rankbb[rank.ordinal]
    }

    /**
     * Returns the bitboard representing the file given in input.
     *
     * @param file the file for which the corresponding bitboard must be returned
     * @return the bitboards representing the file
     */
    fun getFilebb(file: File): Long {
        return filebb[file.ordinal]
    }

    /**
     * Returns the string representation of a bitboard in a readable format.
     *
     * @param bb the bitboard to print
     * @return the string representation of the bitboard
     */
    @JvmStatic
    fun bitboardToString(bb: Long): String {
        val b = StringBuilder()
        for (x in 0..63) {
            if (((1L shl x) and bb) != 0L) {
                b.append("1")
            } else {
                b.append("0")
            }
            if ((x + 1) % 8 == 0) {
                b.append("\n")
            }
        }
        return b.toString()
    }
}
