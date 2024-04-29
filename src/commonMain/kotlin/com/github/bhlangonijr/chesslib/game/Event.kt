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

import com.github.bhlangonijr.chesslib.pgn.PgnHolder

/**
 * A chess event.
 */
class Event {
    /**
     * Returns the rounds of the event.
     *
     * @return the rounds
     */
    val round: MutableMap<Int, Round> = HashMap()
    /**
     * Returns the ID of the event.
     *
     * @return the ID
     */
    /**
     * Sets the ID of the event.
     *
     * @param id the ID to set
     */
    var id: String? = null
    /**
     * Returns the name of the event.
     *
     * @return the name
     */
    /**
     * Sets the name of the event.
     *
     * @param name the name to set
     */
    @JvmField
    var name: String? = null
    /**
     * Returns the type of the event.
     *
     * @return the type
     */
    /**
     * Sets the type of the player.
     *
     * @param eventType the type to set
     */
    var eventType: EventType? = null
    /**
     * Returns the start date of the event.
     *
     * @return the start date
     */
    /**
     * Sets the start date of the event.
     *
     * @param startDate the start date to set
     */
    @JvmField
    var startDate: String? = null
    /**
     * Returns the end date of the event.
     *
     * @return the end date
     */
    /**
     * Sets the end date of the event.
     *
     * @param endDate the end date to set
     */
    var endDate: String? = null
    /**
     * Returns the site (location) of the event.
     *
     * @return the site
     */
    /**
     * Sets the site (location) of the event.
     *
     * @param site the site to set
     */
    @JvmField
    var site: String? = null
    /**
     * Returns the specific timestamp of the event.
     *
     * @return the timestamp
     */
    /**
     * Sets the specific timestamp of the event.
     *
     * @param timestamp the timestamp to set
     */
    var timestamp: Long = 0
    /**
     * Returns the number of rounds of the event.
     *
     * @return the number of rounds
     */
    /**
     * Sets the number of rounds of the event.
     *
     * @param rounds the number of rounds to set
     */
    var rounds: Int = 0
    /**
     * Returns the main time control of the event.
     *
     * @return the main time control
     */
    /**
     * Sets the main time control of the event.
     *
     * @param timeControl the main time control to set
     */
    var timeControl: TimeControl? = null
    /**
     * Returns the secondary time control of the event.
     *
     * @return the secondary time control
     */
    /**
     * Sets the secondary time control of the event.
     *
     * @param timeControl2 the secondary time control to set
     */
    var timeControl2: TimeControl? = null
    /**
     * Returns the PGN holder used to access the PGN file used for the event.
     *
     * @return the PGN holder reference
     */
    /**
     * Sets the PGN holder used to access the PGN file used for the event.
     *
     * @param pgnHolder the PGN holder to use
     */
    var pgnHolder: PgnHolder? = null
}
