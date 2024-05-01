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
package com.github.bhlangonijr.chesslib.game

import kotlin.jvm.JvmField

/**
 * All possible results in a chess game. A game in progress is considered a result too.
 */
enum class GameResult(
    /**
     * The description of the result.
     */
    @JvmField val description: String
) {
    /**
     * The winning of white result.
     */
    WHITE_WON("1-0"),

    /**
     * The winning of black result.
     */
    BLACK_WON("0-1"),

    /**
     * The draw result.
     */
    DRAW("1/2-1/2"),

    /**
     * The result used to indicate an ongoing game. No final result is available yet.
     */
    ONGOING("*");

    /**
     * Returns the description of the result, used to annotate a game.
     *
     * @return the description of the result
     */

    /**
     * Returns the name of the game result.
     *
     * @return the name of the game result
     */
    fun value(): String {
        return name
    }

    companion object {
        /**
         * The map to correlate the chess notation for a result to one of the [GameResult] values.
         */
        val notation: MutableMap<String, GameResult> = HashMap(4)

        init {
            notation["1-0"] = WHITE_WON
            notation["0-1"] = BLACK_WON
            notation["1/2-1/2"] = DRAW
            notation["*"] = ONGOING
        }

        /**
         * Returns a game results given its name.
         *
         *
         * Same as invoking [GameResult.valueOf].
         *
         * @param v name of the result
         * @return the game result with the specified name
         * @throws IllegalArgumentException if the name does not correspond to any game result
         */
        fun fromValue(v: String): GameResult {
            return valueOf(v)
        }

        /**
         * Returns a game results given its notation.
         *
         * @param s the notation of the result
         * @return the game result with the specified notation, or null if no result corresponds to the given notation
         */
        fun fromNotation(s: String?): GameResult? {
            return notation[s]
        }
    }
}
