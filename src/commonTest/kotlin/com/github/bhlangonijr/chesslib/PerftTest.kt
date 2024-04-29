package com.github.bhlangonijr.chesslib

import com.github.bhlangonijr.chesslib.move.MoveGeneratorException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertTrue
/**
 * The type Perft test.
 */
class PerftTest {
    /**
     * Test perft 1.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft1() {
        val nodes = testPerft("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 5)
        assertEquals(4865609, nodes)
    }

    /**
     * Test perft 2.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft2() {
        val nodes = testPerft("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8", 4)
        assertEquals(2103487, nodes)
    }

    /**
     * Test perft 3.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft3() {
        val nodes =
            testPerft("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10", 4)
        assertEquals(3894594, nodes)
    }

    /**
     * Test perft 4.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft4() {
        val nodes = testPerft("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -", 4)
        assertEquals(4085603, nodes)
    }

    /**
     * Test perft 5.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft5() {
        val nodes = testPerft("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8", 4)
        assertEquals(2103487, nodes)
    }

    /**
     * Test perft 6.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft6() {
        val nodes = testPerft("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1", 4)
        assertEquals(422333, nodes)
    }

    /**
     * Test perft 7.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft7() {
        val nodes = testPerft("r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1", 4)
        assertEquals(1274206, nodes)
    }

    /**
     * Test perft 8.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft8() {
        val nodes = testPerft("r3k2r/8/3Q4/8/8/5q2/8/R3K2R b KQkq - 0 1", 4)
        assertEquals(1720476, nodes)
    }

    /**
     * Test perft 9.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft9() {
        val nodes = testPerft("8/8/1P2K3/8/2n5/1q6/8/5k2 b - - 0 1", 5)
        assertEquals(1004658, nodes)
    }

    /**
     * Test perft 10.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft10() {
        val nodes = testPerft("4k3/1P6/8/8/8/8/K7/8 w - - 0 1", 6)
        assertEquals(217342, nodes)
    }

    /**
     * Test perft 11.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft11() {
        val nodes = testPerft("8/P1k5/K7/8/8/8/8/8 w - - 0 1", 6)
        assertEquals(92683, nodes)
    }

    /**
     * Test perft 12.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft12() {
        val nodes = testPerft("K1k5/8/P7/8/8/8/8/8 w - - 0 1", 6)
        assertEquals(2217, nodes)
    }

    /**
     * Test perft 13.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft13() {
        val nodes = testPerft("8/k1P5/8/1K6/8/8/8/8 w - - 0 1", 7)
        assertEquals(567584, nodes)
    }

    /**
     * Test perft 14.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft14() {
        val nodes = testPerft("8/8/2k5/5q2/5n2/8/5K2/8 b - - 0 1", 4)
        assertEquals(23527, nodes)
    }

    @Test
    @Throws(MoveGeneratorException::class)
    fun testPerft15() {
        val nodes = testPerft("8/1pp3p1/4pq1p/PP1bpk2/1Q2p3/4P1P1/2B2P2/6K1 b - - 2 33", 5)
        assertEquals(6421514, nodes)
    }

    /**
     * Test perft long.
     *
     * @param fen   the fen
     * @param depth the depth
     * @return the long
     * @throws MoveGeneratorException the move generator exception
     */
    @Throws(MoveGeneratorException::class)
    fun testPerft(fen: String?, depth: Int): Long {
        val board = Board()
        board.isEnableEvents = false
        board.loadFromFen(fen!!)

        return perft(board, depth, 1)
    }


    @Throws(MoveGeneratorException::class)
    private fun perft(board: Board, depth: Int, ply: Int): Long {
        if (depth == 0) {
            return 1
        }
        var time: Long = 0
        if (ply == 1) {
            time = System.currentTimeMillis()
        }
        var nodes: Long = 0
        var partialNodes: Long
        var hash = 0
        if (CHECK_BOARD_STATE) hash = board.hashCode()
        val moves = board.legalMoves()
        for (move in moves) {
            try {
                if (!board.doMove(move!!, false)) {
                    continue
                }
                partialNodes = perft(board, depth - 1, ply + 1)
                nodes += partialNodes
                if (ply == 1) {
                    println("$move: $partialNodes")
                }
                board.undoMove()
                require(!(CHECK_BOARD_STATE && hash != board.hashCode())) { "Illegal board state after move: $move" }
            } catch (e: Exception) {
                println("depth $depth - ply $ply")
                e.printStackTrace()
                throw IllegalArgumentException(e)
            }
        }
        if (ply == 1) {
            println("Node count: $nodes")
            println("Time: " + (System.currentTimeMillis() - time))
        }
        return nodes
    }

    companion object {
        private const val CHECK_BOARD_STATE = false
    }
}
