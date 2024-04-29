package com.github.bhlangonijr.chesslib.move

import com.github.bhlangonijr.chesslib.Bitboard.bbToSquareList
import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Constants
import com.github.bhlangonijr.chesslib.Piece.Companion.make
import com.github.bhlangonijr.chesslib.PieceType
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.MoveGenerator.generateLegalMoves
import com.github.bhlangonijr.chesslib.move.MoveGenerator.generatePseudoLegalCaptures
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException
import org.junit.Assert
import org.junit.Test
import java.util.Arrays

/**
 * The type Move generator test.
 */
class MoveGeneratorTest {
    /**
     * Test b bto square.
     */
    @Test
    fun testBBtoSquare() {
        Assert.assertEquals(make(Side.BLACK, PieceType.PAWN).value(), "BLACK_PAWN")
        val pieces = (1L shl 10) or (1L shl 63) or (1L shl 45)

        val sqs = bbToSquareList(pieces)
        Assert.assertEquals(
            sqs.size.toLong(),
            Arrays.asList(Square.C2, Square.F6, Square.H8).size.toLong()
        )
    }

    /**
     * Test all move generation.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testAllMoveGeneration() {
        val board = Board()

        Assert.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", board.fen)
        var moves = generateLegalMoves(board)
        Assert.assertEquals(moves.size.toLong(), 20)

        var fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 0"
        board.loadFromFen(fen)
        Assert.assertEquals(
            "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 0",
            board.fen
        )
        moves = generateLegalMoves(board)
        Assert.assertEquals(moves.size.toLong(), 48)

        fen = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 0"
        board.loadFromFen(fen)
        Assert.assertEquals("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 0", board.fen)
        moves = generateLegalMoves(board)
        Assert.assertEquals(moves.size.toLong(), 14)
    }

    @Test
    fun testAllCapturesGeneration() {
        val board = Board()
        board.loadFromFen("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 0")

        var moves: List<Move?> = generatePseudoLegalCaptures(board)

        Assert.assertTrue(moves.contains(Move("g2h3", Side.WHITE)))
        Assert.assertTrue(moves.contains(Move("d5e6", Side.WHITE)))
        Assert.assertTrue(moves.contains(Move("e5g6", Side.WHITE)))
        Assert.assertTrue(moves.contains(Move("e5d7", Side.WHITE)))
        Assert.assertTrue(moves.contains(Move("e5f7", Side.WHITE)))
        Assert.assertTrue(moves.contains(Move("e2a6", Side.WHITE)))
        Assert.assertTrue(moves.contains(Move("f3h3", Side.WHITE)))
        Assert.assertTrue(moves.contains(Move("f3f6", Side.WHITE)))
        Assert.assertEquals(8, moves.size.toLong())

        board.loadFromFen("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 0")

        moves = generatePseudoLegalCaptures(board)

        Assert.assertTrue(moves.contains(Move("b4f4", Side.WHITE)))
        Assert.assertEquals(1, moves.size.toLong())

        board.loadFromFen(Constants.startStandardFENPosition)

        moves = generatePseudoLegalCaptures(board)

        Assert.assertEquals(0, moves.size.toLong())
    }

    @Test
    @Throws(MoveGeneratorException::class)
    fun testIlegalEnPassant() {
        val board = Board()

        board.loadFromFen("8/1pp3p1/4pq1p/PP1bp3/1Q2pPk1/4P1P1/2B5/6K1 b - f3 0 34")

        val moves = generateLegalMoves(board)
        Assert.assertEquals(25, moves.size.toLong())
        junit.framework.Assert.assertFalse(
            "Illegal move generated",
            moves.contains(Move(Square.E4, Square.F3))
        )
    }
}
