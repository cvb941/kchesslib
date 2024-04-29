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
 * A generic player of a chess game.
 */
class GenericPlayer : Player {
    override var id: String? = null
    override var elo: Int = 0
    override var name: String? = null
    override var type: PlayerType? = null
    override var description: String? = null

    /**
     * Constructs a new chess player.
     */
    constructor()

    /**
     * Constructs a new chess player using their basic information.
     *
     * @param id   the ID of the player
     * @param name the name of the player
     */
    constructor(id: String?, name: String?) {
        this.id = id
        this.name = name
    }

    override val longDescription: String?
        get() {
            var desc = name
            if (elo > 0) {
                desc += " (" + elo + ")"
            }
            return desc
        }

    /**
     * Returns a string representation of this player.
     *
     * @return a string representation of this player
     */
    override fun toString(): String {
        return id!!
    }
}
