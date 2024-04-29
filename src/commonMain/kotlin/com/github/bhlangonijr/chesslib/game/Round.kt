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
 * A round of a chess event.
 */
class Round
/**
 * Constructs a new event round.
 *
 * @param event the event the round belongs to
 */(
    /**
     * Returns the chess event the round refers to.
     *
     * @return the event the round belongs to
     */
    @JvmField val event: Event
) {
    /**
     * Returns the list of games scheduled in the round.
     *
     * @return the games of the round
     */
    val game: MutableList<Game?> = ArrayList()

    /**
     * Returns the number of the round.
     *
     * @return the number of the round
     */
    /**
     * Sets the number of the round.
     *
     * @param number the number of the round
     */
    @JvmField
    var number: Int = 0
}
