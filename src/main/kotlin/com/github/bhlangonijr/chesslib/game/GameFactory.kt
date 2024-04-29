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
 * A factory for game-related components, such as games, events, rounds, and players.
 */
object GameFactory {
    /**
     * Returns a new instance of a chess event.
     *
     * @param name the name of the event
     * @return the newly created instance of a chess event
     */
    fun newEvent(name: String?): Event {
        val event = Event()
        event.name = name
        event.id = name

        return event
    }

    /**
     * Returns a new instance of a round of a chess event.
     *
     * @param event  the chess event the round belongs to
     * @param number the number of the round
     * @return the newly created instance of a round
     */
    fun newRound(event: Event, number: Int): Round {
        val round = Round(event)
        round.number = number

        return round
    }

    /**
     * Returns a new instance of a chess game.
     *
     * @param gameId the game ID
     * @param round  the round the game belongs to
     * @return the newly created instance of a game
     */
    fun newGame(gameId: String, round: Round): Game {
        return Game(gameId, round)
    }

    /**
     * Creates a new instance of a chess player.
     *
     * @param type the type of the player
     * @param name the name of the player
     * @return he newly created instance of a player
     */
    fun newPlayer(type: PlayerType?, name: String?): Player {
        val player: Player = GenericPlayer(name, name)
        player.type = type
        return player
    }
}
