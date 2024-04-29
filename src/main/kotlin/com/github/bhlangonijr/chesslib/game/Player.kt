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
 * A player of a chess game.
 */
interface Player {
    /**
     * Returns the ID of the player.
     *
     * @return the ID
     */
    /**
     * Sets the ID of the player.
     *
     * @param id the ID to set
     */
    var id: String?

    /**
     * Returns the ELO rating of the player.
     *
     * @return the ELO rating
     */
    /**
     * Sets the ELO rating of the player.
     *
     * @param elo the ELO rating to set
     */
    var elo: Int

    /**
     * Returns the name of the player.
     *
     * @return the name
     */
    /**
     * Sets the name of the player.
     *
     * @param name the name to set
     */
    var name: String?

    /**
     * Returns the type of the player.
     *
     * @return the type
     */
    /**
     * Sets the type of the player.
     *
     * @param type the type to set
     */
    var type: PlayerType?

    /**
     * Returns the description of the player.
     *
     * @return the description
     */
    /**
     * Sets the description of the player.
     *
     * @param description the description to set
     */
    var description: String?

    /**
     * Returns the long description of the player.
     *
     * @return the long description
     */
    val longDescription: String?
}