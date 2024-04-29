package com.github.bhlangonijr.chesslib

import com.github.bhlangonijr.chesslib.game.GameResult
import com.github.bhlangonijr.chesslib.pgn.PgnIterator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * The type Pgn iterator test.
 */
class PgnIteratorTest {
    @Test
    @Throws(Exception::class)
    fun testPGNOrder() {
        val games = PgnIterator("src/commonTest/resources/pgn_order.pgn")
        val game = games.iterator().next()!!
        assertEquals("1st World U20 ch", game.round.event.name)
        assertEquals("Birmingham ENG", game.round.event.site)
        assertEquals("1951.06.20", game.date)
        assertEquals("1951.06.20", game.round.event.startDate)
        assertEquals(9, game.round.number.toLong())
        assertEquals(GameResult.WHITE_WON, game.result)
        assertEquals("Bent Larsen", game.whitePlayer!!.name)
        assertEquals("Lionel Joyner", game.blackPlayer!!.name)
        assertEquals("C30", game.eco)
        assertEquals(0, game.whitePlayer!!.elo) //TODO have a null (not inserted), vs unknown (?) ?
        assertEquals(0, game.blackPlayer!!.elo)
        assertEquals("63", game.plyCount) //TODO why is it a string?
        //assertEquals("...", game.getHalfMoves().toString()); //TODO
    }

    @Test
    @Throws(Exception::class)
    fun testPGNIteration() {
        val games = PgnIterator("src/commonTest/resources/cct131.pgn")

        var count = 0
        for (game in games.filterNotNull()) {
            count++
            when (count) {
                1 -> {
                    assertEquals("Rookie", game.whitePlayer!!.name)
                    assertEquals("JabbaChess", game.blackPlayer!!.name)
                    assertEquals("2011.01.29", game.date)
                    assertEquals(2, game.round.number.toLong())
                    assertEquals("1-0", game.result!!.description)
                    assertEquals("67", game.plyCount)
                    assertEquals("Albert Silver", game.annotator)
                    assertEquals(2285, game.whitePlayer!!.elo)
                    assertEquals(1680, game.blackPlayer!!.elo)

                    assertEquals("C00", game.eco)
                    assertEquals(67, game.halfMoves.size.toLong())
                    assertEquals(
                        "e2e4 e7e6 d2d4 a7a6 g1f3 d7d5 e4d5 e6d5 f1d3 b8c6 e1g1 g8f6 f1e1 f8e7 c2c3 e8g8 b1d2 f8e8 f3e5 " +
                                "c6e5 d4e5 f6d7 d2b3 g7g6 b3d4 c7c5 d4f3 b7b5 c1h6 c8b7 h2h4 e7h4 a2a4 b5b4 c3b4 c5b4 d1c1 h4e7 c1f4 " +
                                "d7c5 a1d1 b7c6 e5e6 f7f6 f3h4 c6a4 h4g6 c5e6 e1e6 e7d6 f4g4 d6h2 g1h2 d8c7 h2g1 c7g7 e6e7 e8e7 g6e7 " +
                                "g8f7 g4g7 f7e8 e7d5 a8a7 d3b5 a6b5 d5f6", game.halfMoves.toString()
                    )
                }

                2 -> {
                    assertEquals("Chirone", game.whitePlayer!!.name)
                    assertEquals("crafty", game.blackPlayer!!.name)
                    assertEquals("2011.01.29", game.date)
                    assertEquals(2, game.round.number.toLong())
                    assertEquals("1-0", game.result!!.description)
                    assertEquals("143", game.plyCount)
                    assertEquals("Albert Silver", game.annotator)
                    assertEquals(2557, game.whitePlayer!!.elo)
                    assertEquals(2308, game.blackPlayer!!.elo)

                    assertEquals("C67", game.eco)
                    assertEquals(143, game.halfMoves.size.toLong())
                    assertEquals(
                        "e2e4 e7e5 g1f3 b8c6 f1b5 g8f6 e1g1 f6e4 d2d4 f8e7 d1e2 e4d6 b5c6 b7c6 " +
                                "d4e5 d6b7 f3d4 e8g8 b1c3 e7c5 c1e3 f8e8 f2f4 d7d6 a1d1 c8d7 e2f3 c5b6 e3f2 d8c8 h2h3 " +
                                "c6c5 d4b5 d6d5 d1d5 d7c6 b5a3 b6a5 c3d1 c6d5 f3d5 a5b6 a3c4 c8e6 d5e6 f7e6 f2h4 b7d8 " +
                                "d1e3 e8f8 g2g3 d8c6 g1g2 h7h6 g3g4 a8e8 a2a3 a7a5 a3a4 f8f7 c2c3 b6a7 h4g3 f7d7 f4f5 " +
                                "a7b6 g2f3 b6a7 f3e4 d7f7 f1d1 g8h7 g3h4 a7b8 f5f6 g7g5 h4f2 b8a7 h3h4 e8g8 h4g5 h6g5 " +
                                "e3f1 h7g6 f1d2 f7h7 d2f3 g8d8 d1g1 h7h3 f2e3 a7b6 e3c1 h3f3 e4f3 d8d5 f3e4 c6d8 c1d2 " +
                                "d8f7 g1g2 b6a7 c4a5 d5e5 e4f3 e5d5 a5c6 a7b6 c6e7 g6f6 e7d5 e6d5 b2b4 f7e5 f3e2 c5b4 " +
                                "c3b4 f6g6 a4a5 b6a7 a5a6 c7c6 g2f2 e5g4 f2f8 g4e5 f8g8 g6f5 d2g5 e5d7 g8a8 a7b6 g5d8 " +
                                "b6g1 a8c8 d5d4 c8c6 d4d3 e2d2 d7e5 c6c1 g1d4 b4b5",
                        game.halfMoves.toString()
                    )
                }

                3 -> {
                    assertEquals("JabbaChess", game.whitePlayer!!.name)
                    assertEquals("CapivaraLK", game.blackPlayer!!.name)
                    assertEquals("2011.01.29", game.date)
                    assertEquals(3, game.round.number.toLong())
                    assertEquals("1-0", game.result!!.description)
                    assertEquals("64", game.plyCount)
                    assertEquals("Albert Silver", game.annotator)
                    assertEquals(1680, game.whitePlayer!!.elo)
                    assertEquals(0, game.blackPlayer!!.elo)

                    assertEquals("E62", game.eco)
                    assertEquals(64, game.halfMoves.size.toLong())
                    assertEquals(
                        "d2d4 g8f6 c2c4 g7g6 g1f3 f8g7 g2g3 e8g8 b1c3 d7d6 f1g2 b8c6 e1g1 c8g4 " +
                                "d4d5 c6a5 b2b3 f6d5 c3d5 g7a1 c1d2 g4f3 g2f3 a5c4 b3c4 a1g7 d1b3 c7c6 d5b4 e7e5 f1d1 " +
                                "f7f5 d2c3 f8f7 c4c5 d6d5 b4d5 c6d5 f3d5 d8c7 d5e6 g8f8 c3b4 f7e7 c5c6 c7c6 e6g8 c6f6 " +
                                "g8h7 f8e8 b3g8 g7f8 h7g6 f6g6 g8g6 e7f7 g6e6 f7e7 b4e7 f8e7 d1d7 e8f8 e6e7 f8g8",
                        game.halfMoves.toString()
                    )
                }

                else -> {}

            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun testPGNIterationWithComments() {
        val games = PgnIterator("src/commonTest/resources/rav_alternative.pgn")
        val commentedMoves: MutableMap<String, String> = HashMap()
        for (game in games.filterNotNull()) {
            val moves = game.halfMoves.toSanArray()
            val comments: Map<Int, String>? = game.comments
            for (i in moves!!.indices) {
                val halfMove =
                    ((i + 2) / 2).toString() + (if (i % 2 != 0) ".." else " ")
                val move = moves[i]
                val comment = comments!![i + 1].toString()
                commentedMoves[halfMove + move] = comment.trim { it <= ' ' }
            }
        }

        assertEquals(
            "Ponomariov plays 1. e4 in much the same way as any of the other top-level GMs.",
            commentedMoves["1 e4"]
        )
        assertEquals(
            "Now, along with Pe4 there is an indication Black will place pawns on light-color " +
                    "squares to prevent Bf1 from ever being dangerous. White will probably have to meet 2...d5 " +
                    "with e4-e5 to open the d3-h7 diagonal. So, White needs a Pd4 to support Pe5.",
            commentedMoves["1..e6"]
        )
        assertEquals(
            "While Black is moving the pawns in front of his king White aims to control f4, " +
                    "leaving Black's center and king weak. Those two factors ( if achieved by White ) " +
                    "could lead to a won position.",
            commentedMoves["38..Nd2"]
        )
        assertEquals(
            "Ponomariov prefers to simplify the position; rightly so I think. His pieces need " +
                    "room to fight, before Black gets in ...e6-e5.",
            commentedMoves["42..g4"]
        )
    }
}