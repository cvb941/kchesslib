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
 * One of the two sides in a chess game, [Side.WHITE] or [Side.BLACK].
 */
enum class Side {
    /**
     * The white side.
     */
    WHITE,

    /**
     * The black side.
     */
    BLACK;

    /**
     * Returns the name of the side.
     *
     * @return the name of the side
     */
    fun value(): String {
        return name
    }

    /**
     * Returns the opposite of this side, that is the other side.
     *
     * @return the opposite side
     */
    fun flip(): Side {
        return if (WHITE == this) BLACK else WHITE
    }

    companion object {
        val allSides: Array<Side> = entries.toTypedArray()

        /**
         * Returns a side given its name.
         *
         *
         * Same as invoking [Side.valueOf].
         *
         * @param v name of the side
         * @return the side with the specified name
         * @throws IllegalArgumentException if the name does not correspond to any side
         */
        fun fromValue(v: String?): Side {
            return valueOf(v!!)
        }
    }
}
