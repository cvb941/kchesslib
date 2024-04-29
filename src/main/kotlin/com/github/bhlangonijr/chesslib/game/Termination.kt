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

/**
 * The types of game termination.
 */
enum class Termination(
    /**
     * The description of the termination mode.
     */
    val description: String
) {
    /**
     * Termination by abandon: the losing player abandoned the game.
     */
    ABANDONED("ABANDONED"),

    /**
     * Termination by adjudication: the result is determined by a third-party adjudication.
     */
    ADJUDICATION("ADJUDICATION"),

    /**
     * Termination by death: the losing player passed away.
     */
    DEATH("DEATH"),

    /**
     * Termination by emergency: the game concluded due to unforeseen circumstances.
     */
    EMERGENCY("EMERGENCY"),

    /**
     * Normal termination: the game finished in a normal fashion.
     */
    NORMAL("NORMAL"),

    /**
     * The rules infraction termination: the game is forfeit due to the losing player's failure to observe either the
     * chess rules or the event regulations.
     */
    RULES_INFRACTION("RULES INFRACTION"),

    /**
     * The time forfeit termination: the losing player ran out of time.
     */
    TIME_FORFEIT("TIME FORFEIT"),

    /**
     * The game is not terminated yet.
     */
    UNTERMINATED("UNTERMINATED"),

    /**
     * The time termination: alias of [Termination.TIME_FORFEIT].
     */
    TIME("TIME");

    /**
     * Returns the name of the termination mode.
     *
     * @return the name of the termination mode
     */
    fun value(): String {
        return name
    }

    /**
     * Returns a string representation of this termination mode.
     *
     * @return a string representation of this termination mode
     */
    override fun toString(): String {
        return description
    }

    companion object {
        /**
         * Returns a termination mode given its name.
         *
         *
         * Same as invoking [Termination.valueOf].
         *
         * @param v name of the termination mode
         * @return the termination mode with the specified name
         * @throws IllegalArgumentException if the name does not correspond to any termination mode
         */
        fun fromValue(v: String): Termination {
            return valueOf(v.replace(' ', '_'))
        }
    }
}
