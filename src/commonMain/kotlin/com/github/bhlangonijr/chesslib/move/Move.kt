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

import com.github.bhlangonijr.chesslib.BoardEvent
import com.github.bhlangonijr.chesslib.BoardEventType
import com.github.bhlangonijr.chesslib.Piece
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import org.apache.commons.lang3.StringUtils
import java.util.Locale

/**
 * The definition of a chess move, that is, a piece movement from its starting square (the origin square) to a
 * destination square. Optionally, the move could specify a promotion piece used to replace a pawn in case of promotion.
 *
 *
 * The move is also a [BoardEvent], and hence it can be passed to the observers of the
 * [BoardEventType.ON_MOVE] events, emitted when a move is executed on a board.
 */
class Move
/**
 * Creates a new move, using its origin and destination squares.
 *
 *
 * Same as `new Move(from, to, Piece.NONE)`.
 *
 * @param from the origin square
 * @param to   the destination square
 */ @JvmOverloads constructor(
    /**
     * Returns the origin square.
     *
     * @return the origin square
     */
    val from: Square,
    /**
     * Returns the destination square.
     *
     * @return the destination square
     */
    val to: Square,
    /**
     * Returns the promotion piece, if present.
     *
     * @return the promotion piece, or [Piece.NONE] if move is not a promotion
     */
    val promotion: Piece = Piece.NONE
) : BoardEvent {
    /**
     * Returns the Short Algebraic Notation (SAN) of the move, if previously set.
     *
     * @return the representation of the move in SAN notation, or null if not present
     * @see Move.setSan
     */
    /**
     * Sets the Short Algebraic Notation (SAN) of the move.
     *
     *
     * The SAN notation should be set explicitly after the instantiation of the move because it can not be inferred
     * without the full context of the specific position.
     *
     * @param san the representation of the move in SAN notation
     */
    var san: String? = null

    /**
     * Creates a new move, defined by its origin square, its destination, and a promotion piece.
     *
     * @param from      the origin square
     * @param to        the destination square
     * @param promotion the promotion piece
     */

    /**
     * Creates a new move using a string representing the coordinates of the origin and destination squares, and
     * possibly a promotion piece. The side is used to disambiguate the color of the promotion piece.
     *
     *
     * Valid examples of strings that can be used to instantiate the move are `"e2e4"`, `"f1b5"` or
     * `"a7a8Q"`.
     *
     * @param move the string representing the coordinates of the move
     * @param side the side used to disambiguate the promotion piece
     */
    constructor(move: String, side: Side?) : this(
        Square.valueOf(move.substring(0, 2).uppercase(Locale.getDefault())),
        Square.valueOf(move.substring(2, 4).uppercase(Locale.getDefault())),
        if (move.length < 5) Piece.NONE else if (Side.WHITE == side) Piece.Companion.fromFenSymbol(
            move.substring(4, 5).uppercase(Locale.getDefault())
        ) else Piece.Companion.fromFenSymbol(
            move.substring(4, 5).lowercase(Locale.getDefault())
        )
    )

    /**
     * Checks if this move is equivalent to another, according to its definition.
     *
     * @param obj the other object reference to compare to this move
     * @return `true` if this move and the object reference are equivalent
     */
    override fun equals(obj: Any?): Boolean {
        if (obj == null || obj !is Move) {
            return false
        }
        val move = obj
        return move.from == from && move.to == to && move.promotion == promotion
    }

    /**
     * Returns a hash code value for this move.
     *
     * @return a hash value for this move
     */
    override fun hashCode(): Int {
        return toString().hashCode()
    }

    /**
     * Returns a string representation of this move.
     *
     * @return a string representation of this move
     */
    override fun toString(): String {
        var promo: String? = StringUtils.EMPTY
        if (Piece.NONE != promotion) {
            promo = promotion.fenSymbol
        }
        return from.toString().lowercase(Locale.getDefault()) +
                to.toString().lowercase(Locale.getDefault()) +
                promo!!.lowercase(Locale.getDefault())
    }

    override val type: BoardEventType
        /**
         * The type of board events this data structure represents when notified to its observers.
         *
         * @return the board event type [BoardEventType.ON_MOVE]
         */
        get() = BoardEventType.ON_MOVE
}
