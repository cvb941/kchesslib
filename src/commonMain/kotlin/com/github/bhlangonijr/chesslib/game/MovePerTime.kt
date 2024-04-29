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
 * The representation of the time control moves-per-time, that specifies how many moves must be played within a certain
 * amount of time, for example `"40/9000"`, 40 moves in 9000 seconds, i.e. 2 hours and 30 minutes.
 */
class MovePerTime {
    /**
     * Returns the moves component of the time control.
     *
     * @return the number of moves
     */
    /**
     * Sets the number of moves component of the time control.
     *
     * @param moves the number of moves to set
     */
    var moves: Int = 0
    /**
     * Returns the time component of the time control, expressed in milliseconds.
     *
     * @return the amount of time, in milliseconds
     */
    /**
     * Sets the time component of the time control, in milliseconds.
     *
     * @param milliseconds the amount of time to set
     */
    var milliseconds: Long = 0

    /**
     * Constructs a new moves-per-time time control.
     */
    constructor()

    /**
     * Constructs a new moves-per-time time control from its components, the number of moves and the amount of time.
     *
     * @param moves        the number of moves
     * @param milliseconds the amount of time, expressed in milliseconds
     */
    constructor(moves: Int, milliseconds: Long) {
        this.moves = moves
        this.milliseconds = milliseconds
    }

    /**
     * Returns the PGN notation that represents this time control.
     *
     * @return the PGN representation of this time control
     */
    fun toPGNString(): String {
        return moves.toString() + "/" + (milliseconds / 1000)
    }

    /**
     * Returns a string representation of this time control.
     *
     * @return a string representation of this time control
     */
    override fun toString(): String {
        return moves.toString() + " Moves / " + (milliseconds / 1000) + " Sec"
    }
}
