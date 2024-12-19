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

/**
 * A handy collection of constant values to be used in common scenarios.
 */
object Constants {
    /**
     * The FEN definition of the standard starting position.
     */
    const val startStandardFENPosition: String =
        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

    /**
     * The shift of the white king in a default short castle move.
     */
    val DEFAULT_WHITE_OO: Move = Move(Square.E1, Square.G1)

    /**
     * The shift of the white king in a default long castle move.
     */
    val DEFAULT_WHITE_OOO: Move = Move(Square.E1, Square.C1)

    /**
     * The shift of the black king in a default short castle move.
     */
    val DEFAULT_BLACK_OO: Move = Move(Square.E8, Square.G8)

    /**
     * The shift of the black king in a default long castle move.
     */
    val DEFAULT_BLACK_OOO: Move = Move(Square.E8, Square.C8)

    /**
     * The shift of the white rook in a default short castle move.
     */
    val DEFAULT_WHITE_ROOK_OO: Move = Move(Square.H1, Square.F1)

    /**
     * The shift of the white rook in a default long castle move.
     */
    val DEFAULT_WHITE_ROOK_OOO: Move = Move(Square.A1, Square.D1)

    /**
     * The shift of the black rook in a default short castle move.
     */
    val DEFAULT_BLACK_ROOK_OO: Move = Move(Square.H8, Square.F8)

    /**
     * The shift of the black rook in a default long castle move.
     */
    val DEFAULT_BLACK_ROOK_OOO: Move = Move(Square.A8, Square.D8)

    /**
     * The list of squares crossed by the white king in the case of short castle.
     */
    val DEFAULT_WHITE_OO_SQUARES: MutableList<Square> = ArrayList()

    /**
     * The list of squares crossed by the white king in the case of long castle.
     */
    val DEFAULT_WHITE_OOO_SQUARES: MutableList<Square> = ArrayList()

    /**
     * The list of squares crossed by the black king in the case of short castle.
     */
    val DEFAULT_BLACK_OO_SQUARES: MutableList<Square> = ArrayList()

    /**
     * The list of squares crossed by the black king in the case of long castle.
     */
    val DEFAULT_BLACK_OOO_SQUARES: MutableList<Square> = ArrayList()

    /**
     * The list of all squares involved in the case of short castle of white.
     */
    val DEFAULT_WHITE_OO_ALL_SQUARES: MutableList<Square> = ArrayList()

    /**
     * The list of all squares involved in the case of long castle of white.
     */
    val DEFAULT_WHITE_OOO_ALL_SQUARES: MutableList<Square> = ArrayList()

    /**
     * The list of all squares involved in the case of short castle of black.
     */
    val DEFAULT_BLACK_OO_ALL_SQUARES: MutableList<Square> = ArrayList()

    /**
     * The list of all squares involved in the case of long castle of black.
     */
    val DEFAULT_BLACK_OOO_ALL_SQUARES: MutableList<Square> = ArrayList()

    /**
     * A useful special value that represents an empty move, that is, a move that does nothing and leaves the board
     * unchanged.
     */
    val emptyMove: Move = Move(Square.NONE, Square.NONE)

    init {
        DEFAULT_WHITE_OO_SQUARES.add(Square.F1)
        DEFAULT_WHITE_OO_SQUARES.add(Square.G1)
        DEFAULT_WHITE_OOO_SQUARES.add(Square.D1)
        DEFAULT_WHITE_OOO_SQUARES.add(Square.C1)

        DEFAULT_BLACK_OO_SQUARES.add(Square.F8)
        DEFAULT_BLACK_OO_SQUARES.add(Square.G8)
        DEFAULT_BLACK_OOO_SQUARES.add(Square.D8)
        DEFAULT_BLACK_OOO_SQUARES.add(Square.C8)

        DEFAULT_WHITE_OO_ALL_SQUARES.add(Square.F1)
        DEFAULT_WHITE_OO_ALL_SQUARES.add(Square.G1)
        DEFAULT_WHITE_OOO_ALL_SQUARES.add(Square.D1)
        DEFAULT_WHITE_OOO_ALL_SQUARES.add(Square.C1)
        DEFAULT_WHITE_OOO_ALL_SQUARES.add(Square.B1)

        DEFAULT_BLACK_OO_ALL_SQUARES.add(Square.F8)
        DEFAULT_BLACK_OO_ALL_SQUARES.add(Square.G8)
        DEFAULT_BLACK_OOO_ALL_SQUARES.add(Square.D8)
        DEFAULT_BLACK_OOO_ALL_SQUARES.add(Square.C8)
        DEFAULT_BLACK_OOO_ALL_SQUARES.add(Square.B8)
    }
}
