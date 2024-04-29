package com.github.bhlangonijr.chesslib

import com.github.bhlangonijr.chesslib.move.Move
import com.github.bhlangonijr.chesslib.move.MoveConversionException
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException
import com.github.bhlangonijr.chesslib.move.MoveList
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * The type Board test.
 */
class BoardTest {
    /**
     * Test move and fen parsing.
     */
    @Test
    fun testMoveAndFENParsing() {
        val fen1 = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
        val fen2 = "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2"
        val fen3 = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2"
        val fen4 = "rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2"
        val fen5 = "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3"
        val fen6 = "rnbqkbnr/ppp1p1pp/5P2/3p4/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3"
        val fen7 = "rnbqkbnr/ppp3pp/5p2/3p4/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 4"

        val board = Board()

        board.loadFromFen(fen1)

        Assert.assertEquals(Piece.BLACK_BISHOP, board.getPiece(Square.C8))
        Assert.assertEquals(Piece.WHITE_BISHOP, board.getPiece(Square.C1))
        Assert.assertEquals(Piece.BLACK_ROOK, board.getPiece(Square.H8))
        Assert.assertEquals(Piece.WHITE_ROOK, board.getPiece(Square.H1))

        assertEquals(0, board.halfMoveCounter)
        assertEquals(1, board.moveCounter)
        Assert.assertEquals(Square.E3, board.enPassant)
        Assert.assertEquals(Square.NONE, board.enPassantTarget)

        Assert.assertEquals(fen1, board.fen)

        var move = Move(Square.E7, Square.E5) //sm: b
        board.doMove(move, true)
        println("new FEN after: " + move.toString() + ": " + board.fen)
        println("hash code is: " + board.hashCode())

        Assert.assertEquals(fen2, board.fen) //sm: w

        board.undoMove()
        println("new FEN after: undo: " + board.fen)
        println("hash code is: " + board.hashCode())
        Assert.assertEquals(fen1, board.fen) // sm: b

        move = Move(Square.D7, Square.D5) //sm: b
        board.doMove(move, true)
        println("new FEN after: " + move.toString() + ": " + board.fen)
        println("hash code is: " + board.hashCode())
        Assert.assertEquals(fen3, board.fen) // sm: w

        move = Move(Square.E4, Square.E5) //sm: w
        board.doMove(move, true)
        println("new FEN after: " + move.toString() + ": " + board.fen)
        println("hash code is: " + board.hashCode())
        Assert.assertEquals(fen4, board.fen) // sm: b

        move = Move(Square.F7, Square.F5) //sm: b
        board.doMove(move, true)
        println("new FEN after: " + move.toString() + ": " + board.fen)
        println("hash code is: " + board.hashCode())
        Assert.assertEquals(fen5, board.fen) // sm: w

        move = Move(Square.E5, Square.F6) //sm: w
        board.doMove(move, true)
        println("new FEN after: " + move.toString() + ": " + board.fen)
        println("hash code is: " + board.hashCode())
        Assert.assertEquals(fen6, board.fen) // sm: b

        move = Move(Square.E7, Square.F6) //sm: b
        board.doMove(move, true)
        println("new FEN after: " + move.toString() + ": " + board.fen)
        println("hash code is: " + board.hashCode())
        Assert.assertEquals(fen7, board.fen) // sm: w
    }

    /**
     * Test castle and fen parsing.
     */
    @Test
    fun testCastleAndFENParsing() {
        val fen1 = "rnbqk2r/ppp1b1pp/5p1n/3p4/8/3B1N2/PPPP1PPP/RNBQK2R w KQkq - 4 1"
        val fen2 = "rnbqk2r/ppp1b1pp/5p1n/3p4/8/3B1N2/PPPP1PPP/RNBQ1RK1 b kq - 5 1"
        val fen3 = "rnbq1rk1/ppp1b1pp/5p1n/3p4/8/3B1N2/PPPP1PPP/RNBQ1RK1 w - - 6 2"

        val board = Board()

        board.loadFromFen(fen1)

        Assert.assertEquals(fen1, board.fen)

        var move = Move(Square.E1, Square.G1) //sm: b
        board.doMove(move, true)
        println("new FEN after: " + move.toString() + ": " + board.fen)
        println("hash code is: " + board.hashCode())

        Assert.assertEquals(fen2, board.fen) //sm: w

        move = Move(Square.E8, Square.G8) //sm: w
        board.doMove(move, true)
        println("new FEN after: " + move.toString() + ": " + board.fen)
        println("hash code is: " + board.hashCode())

        Assert.assertEquals(fen3, board.fen) //sm: b

        board.undoMove()
        println("new FEN after: undo: " + board.fen)
        println("hash code is: " + board.hashCode())
        Assert.assertEquals(fen2, board.fen) // sm: w
    }

    /**
     * Test FEN formatting with `onlyOutputEnPassantIfCapturable` set to true,
     * in a position where capturing en passant is possible.
     */
    @Test
    fun testFENFormattingOnlyOutputEnPassantIfCapturable_EnPassantPossible() {
        val fenWhereEnPassantPossible =
            "rnbqkbnr/1pp1pppp/p7/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 3"
        val expectedOutputFen = "rnbqkbnr/1pp1pppp/p7/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d6"
        val board = Board()
        board.loadFromFen(fenWhereEnPassantPossible)
        Assert.assertEquals(expectedOutputFen, board.getFen(false, true))
    }

    /**
     * Test FEN formatting with `onlyOutputEnPassantIfCapturable` set to true,
     * in a position where capturing en passant is not possible.
     */
    @Test
    fun testFENFormattingOnlyOutputEnPassantIfCapturable_EnPassantNotPossible() {
        val fenWhereEnPassantNotPossible =
            "rnbqkbnr/1pp1pppp/p7/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 3"
        val expectedOutputFen = "rnbqkbnr/1pp1pppp/p7/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq -"
        val board = Board()
        board.loadFromFen(fenWhereEnPassantNotPossible)
        Assert.assertEquals(expectedOutputFen, board.getFen(false, true))
    }

    /**
     * Test FEN formatting with `onlyOutputEnPassantIfCapturable` set to true,
     * in a position where a 2-square pawn move just happened, but capturing en
     * passant is not possible.
     */
    @Test
    fun testFENFormattingOnlyOutputEnPassantIfCapturable_2SquarePawnMoveButEnPassantPossible() {
        val fenWithTwoSquarePawnMoveNoEnPassantPossible =
            "rnbqkbnr/1pp1pppp/p7/3pP3/3P4/8/PPP2PPP/RNBQKBNR b KQkq d3 0 3"
        val expectedOutputFen = "rnbqkbnr/1pp1pppp/p7/3pP3/3P4/8/PPP2PPP/RNBQKBNR b KQkq -"
        val board = Board()
        board.loadFromFen(fenWithTwoSquarePawnMoveNoEnPassantPossible)
        Assert.assertEquals(expectedOutputFen, board.getFen(false, true))
    }


    /**
     * Test clone.
     */
    @Test
    fun testClone() {
        val fen1 = "rnbqk2r/ppp1b1pp/5p1n/3p4/8/3B1N2/PPPP1PPP/RNBQK2R w KQkq - 4 3"
        val b1 = Board()
        b1.loadFromFen(fen1)

        val b2 = b1.clone()

        Assert.assertEquals(b1.hashCode().toLong(), b2.hashCode().toLong())
    }

    /**
     * Test equality.
     */
    @Test
    fun testEquality() {
        val fen1 = "rnbqk2r/ppp1b1pp/5p1n/3p4/8/3B1N2/PPPP1PPP/RNBQK2R w KQkq - 4 3"
        val b1 = Board()
        b1.loadFromFen(fen1)
        println("hash code is: " + b1.hashCode())

        val b2 = Board()
        b2.loadFromFen(fen1)
        println("hash code is: " + b2.hashCode())

        Assert.assertEquals(b1, b2)
        Assert.assertEquals(b1.positionId, b2.positionId)
    }

    /**
     * Test undo move.
     */
    @Test
    fun testUndoMove() {
        val fen1 = "rnbqkbnr/1p1ppppp/p7/1Pp5/8/8/P1PPPPPP/RNBQKBNR w KQkq c6 0 5"
        val b1 = Board()
        b1.loadFromFen(fen1)

        b1.doMove(Move(Square.B5, Square.A6))
        b1.undoMove()

        Assert.assertEquals(fen1, b1.fen)
    }

    /**
     * Test legal move.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testLegalMove() {
        val fen1 = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/5Q2/PPPBBPpP/RN2K2R w KQkq - 0 2"
        val b1 = Board()
        b1.loadFromFen(fen1)

        val moves = b1.legalMoves()

        Assert.assertEquals(47, moves.size.toLong())
    }

    /**
     * Test legal move 1.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testLegalMove1() {
        val fen = "1r6/3k2p1/7p/Ppp2r1P/K1N1B1p1/2P2NP1/b7/4b3 w - - 0 56"
        val b = Board()
        b.loadFromFen(fen)

        val moves = b.legalMoves()

        Assert.assertEquals(Move(Square.A4, Square.A3), moves[0])
    }

    /**
     * Test legal move 3.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testLegalMove3() {
        val fen = "2r3r3/4n3/p1kp3p/1p3pP1/1p1bPPKP/1PPP4/BR1R4/8 w - - 0 73"
        val b = Board()
        b.loadFromFen(fen)

        val moves = b.legalMoves()

        Assert.assertTrue(moves.contains(Move(Square.E4, Square.F5)))
        Assert.assertTrue(moves.contains(Move(Square.G4, Square.F3)))
        Assert.assertTrue(moves.contains(Move(Square.G4, Square.G3)))
        Assert.assertTrue(moves.contains(Move(Square.G4, Square.H3)))
        Assert.assertTrue(moves.contains(Move(Square.G4, Square.H5)))
    }

    /**
     * Test legal move 4.
     *
     * @throws MoveGeneratorException the move generator exception
     */
    @Test
    @Throws(MoveGeneratorException::class)
    fun testLegalMove4() {
        val fen = "7k/8/R5Q1/1BpP4/3K4/8/8/8 w - c6 0 0"
        val b = Board()
        b.loadFromFen(fen)

        val moves = b.legalMoves()

        Assert.assertTrue(moves.contains(Move(Square.D5, Square.C6)))
        Assert.assertTrue(moves.contains(Move(Square.D4, Square.C3)))
        Assert.assertTrue(moves.contains(Move(Square.D4, Square.D3)))
        Assert.assertTrue(moves.contains(Move(Square.D4, Square.E3)))
        Assert.assertTrue(moves.contains(Move(Square.D4, Square.C4)))
        Assert.assertTrue(moves.contains(Move(Square.D4, Square.E4)))
        Assert.assertTrue(moves.contains(Move(Square.D4, Square.C5)))
        Assert.assertTrue(moves.contains(Move(Square.D4, Square.E5)))
        Assert.assertEquals(8, moves.size.toLong())
    }

    @Test
    fun testIncrementalHashKey() {
        val fen = "r1b1kb1r/ppp2ppp/8/4n3/8/PPP1P1P1/5n1P/RNBK1BNR w KQkq - 1 21"
        val b = Board()
        b.loadFromFen(fen)
        val b2 = b.clone()

        val initialHash = b.zobristKey

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.D1, Square.E1))
        b2.doMove(Move(Square.D1, Square.E1))
        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.F2, Square.H1))
        b2.doMove(Move(Square.F2, Square.H1))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.F8, Square.C5))
        b2.doMove(Move(Square.F8, Square.C5))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())
        println(b.zobristKey)

        b.doMove(Move(Square.G1, Square.E2))
        b2.doMove(Move(Square.G1, Square.E2))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())
        println(b.zobristKey)

        b.doMove(Move(Square.C1, Square.D2))
        b2.doMove(Move(Square.C1, Square.D2))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.C8, Square.E6))
        b2.doMove(Move(Square.C8, Square.E6))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.E2, Square.F4))
        b2.doMove(Move(Square.E2, Square.F4))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.C3, Square.C4))
        b2.doMove(Move(Square.C3, Square.C4))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.A8, Square.D8))
        b2.doMove(Move(Square.A8, Square.D8))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.B1, Square.C3))
        b2.doMove(Move(Square.B1, Square.C3))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.D8, Square.D6))
        b2.doMove(Move(Square.D8, Square.D6))

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        for (i in 1..11) {
            b.undoMove()
            b2.undoMove()
        }

        Assert.assertEquals(initialHash, b.zobristKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())
        Assert.assertEquals(b, b2)
    }

    @Test
    fun testIncrementalHashKey2() {
        val b = Board()
        val b2 = b.clone()
        val initialHash = b.zobristKey

        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.E2, Square.E4))
        b2.doMove(Move(Square.E2, Square.E4))
        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.E7, Square.E5))
        b2.doMove(Move(Square.E7, Square.E5))
        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.G1, Square.F3))
        b2.doMove(Move(Square.G1, Square.F3))
        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.B8, Square.C6))
        b2.doMove(Move(Square.B8, Square.C6))
        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.F1, Square.B5))
        b2.doMove(Move(Square.F1, Square.B5))
        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.G8, Square.F6))
        b2.doMove(Move(Square.G8, Square.F6))
        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        b.doMove(Move(Square.E1, Square.G1))
        b2.doMove(Move(Square.E1, Square.G1))
        Assert.assertEquals(b.zobristKey, b.incrementalHashKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())
        println(b.fen)
        for (i in 1..7) {
            b.undoMove()
            b2.undoMove()
        }

        Assert.assertEquals(initialHash, b.zobristKey)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())

        Assert.assertEquals(b, b2)
    }

    @Test
    fun testNullMove() {
        val b = Board()
        val b2 = b.clone()

        b.doNullMove()

        Assert.assertNotSame(b.sideToMove, b2.sideToMove)
        Assert.assertNotSame(b.hashCode(), b2.hashCode())

        b.undoMove()
        Assert.assertEquals(b.sideToMove, b2.sideToMove)
        Assert.assertEquals(b.hashCode().toLong(), b2.hashCode().toLong())
    }

    @Test
    fun testDraws() {
        val b = Board()
        b.loadFromFen("rnbqkbnr/p1pppppp/8/8/1p2P3/1P6/P1PP1PPP/RNBQKBNR w KQkq - 0 1")

        b.doMove(Move(Square.D1, Square.E2))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.C8, Square.B7))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.E2, Square.D1))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.B7, Square.C8))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.D1, Square.E2))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.C8, Square.B7))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.E2, Square.D1))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.B7, Square.C8))
        Assert.assertTrue(b.isDraw)

        b.loadFromFen("1kr5/8/Q7/8/8/7q/4r3/6K1 w - - 0 1")

        b.doMove(Move(Square.A6, Square.B6))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.B8, Square.A8))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.B6, Square.A6))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.A8, Square.B8))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.A6, Square.B6))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.B8, Square.A8))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.B6, Square.A6))
        Assert.assertFalse(b.isDraw)

        b.doMove(Move(Square.A8, Square.B8))
        Assert.assertTrue(b.isDraw)
    }

    @Test
    fun testCastleMove() {
        val board = Board()
        board.loadFromFen("r1bqk1nr/pppp1ppp/2n5/2b1p3/4P3/5N2/PPPPBPPP/RNBQK2R w KQkq - 0 1")
        Assert.assertEquals(CastleRight.KING_AND_QUEEN_SIDE, board.getCastleRight(Side.WHITE))
        board.doMove(Move(Square.E1, Square.G1)) // castle
        val moveBackup = board.backup.last!!
        Assert.assertTrue(moveBackup.isCastleMove)
        Assert.assertEquals(Move(Square.H1, Square.F1), moveBackup.rookCastleMove)
    }

    @Test
    fun testInvalidCastleMove() {
        val board = Board()
        board.loadFromFen("8/5k2/8/8/8/8/5K2/4R3 w - - 0 1")

        val whiteRookMoveE1G1 = Move("e1g1", Side.WHITE)
        board.doMove(whiteRookMoveE1G1)
        val moveBackup = board.backup.last!!
        Assert.assertFalse(moveBackup.isCastleMove)
        Assert.assertNull(moveBackup.rookCastleMove)
    }

    @Test
    fun testInsufficientMaterial() {
        val board = Board()
        board.loadFromFen("8/8/8/4k3/8/3K4/8/2BB4 w - - 0 1")
        Assert.assertFalse(board.isInsufficientMaterial)
        board.loadFromFen("8/8/8/4k3/5b2/3K4/8/2B5 w - - 0 1")
        Assert.assertTrue(board.isInsufficientMaterial)
    }

    @Test
    fun testInsufficientMaterial1() {
        val board = Board()
        board.loadFromFen("B3k3/8/8/8/8/8/8/4KB2 w - - 0 1")
        Assert.assertTrue(board.isInsufficientMaterial)
        board.loadFromFen("B1b1k3/3b4/4b3/8/8/8/8/4KB2 w - - 0 1")
        Assert.assertTrue(board.isInsufficientMaterial)
    }

    @Test
    fun testInsufficientMaterial2() {
        val board = Board()
        val bishopOnSameColorSquares = "8/8/8/4k3/5b2/3K4/8/2B5 w - - 0 1"
        board.loadFromFen(bishopOnSameColorSquares)
        Assert.assertTrue(board.isInsufficientMaterial)
        val bishopOnDifferentColorSquares = "8/8/8/4k3/5b2/3K4/2B5/8 w - - 0 1"
        board.loadFromFen(bishopOnDifferentColorSquares)
        Assert.assertFalse(board.isInsufficientMaterial)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testThreefoldRepetition() {
        val moveList = MoveList()
        moveList.loadFromSan("1. e4 e5 2. Be2 Be7 3. Bf1 Bf8 4. Bd3 Bd6 5. Bf1 Bf8 6. Bd3 Bd6 7. Bf1 Bf8")

        val board = Board()
        for (move in moveList) {
            board.doMove(move)
        }
        Assert.assertTrue(board.isRepetition)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testThreefoldRepetition1() {
        val moveList = MoveList()
        moveList.loadFromSan("1. e4 e5 2. Nf3 Nf6 3. Ng1 Ng8 4. Ke2 Ke7 5. Ke1 Ke8 6. Na3 Na6 7. Nb1 Nb8")

        val board = Board()
        for (move in moveList) {
            board.doMove(move)
        }
        Assert.assertFalse(board.isRepetition)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testThreefoldRepetition2() {
        val moves = MoveList()
        moves.loadFromSan("1. Nf3 Nf6 2. c4 c5 3. b3 d6 4. d4 cxd4 5. Nxd4 e5 6. Nb5 Be6 7. g3 a6 8. N5c3 d5 9. cxd5 Nxd5 10. Bg2 Bb4 11. Bd2 Nc6 12. O-O O-O 13. Na4 Rc8 14. a3 Be7 15. e3 b5 16. Nb2 Qb6 17. Nd3 Rfd8 18. Qe2 Nf6 19. Nc1 e4 20. Bc3 Nd5 21. Bxe4 Nxc3 22. Nxc3 Na5 23. N1a2 Nxb3 24. Rad1 Bc4 25. Qf3 Qf6 26. Qg4 Be6 27. Qe2 Rxc3 28. Nxc3 Qxc3 29. Rxd8+ Bxd8 30. Rd1 Be7 31. Bb7 Nc5 32. Qf3 g6 33. Bd5 Bxd5 34. Qxd5 Qxa3 35. Qe5 Ne6 36. Ra1 Qd6 37. Qxd6 Bxd6 38. Rxa6 Bc5 39. Kf1 Kf8 40. Ke2 Ke7 41. Kd3 Kd7 42. g4 Kc7 43. Ra8 Kc6 44. f4 Be7 45. Rc8+ Kd5 46. Re8 Kd6 47. g5 f5 48. Rb8 Kc6 49. Re8 Kd6 50. Rb8 Kc6 51. Re8 Kd6")

        val board = Board()
        for (move in moves) {
            board.doMove(move)
            println(board.zobristKey.toString() + "\t = " + move + "\n = " + board.fen)
        }
        Assert.assertFalse(board.isRepetition)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testThreefoldRepetition3() {
        val moves = MoveList()
        moves.loadFromSan("1. Nf3 Nf6 2. Nc3 c5 3. e3 d5 4. Be2 Ne4 5. Bf1 Nf6 6. Be2 Ne4 7. Bf1 Nf6")

        val board = Board()
        for (move in moves) {
            board.doMove(move)
        }
        Assert.assertTrue(board.isRepetition)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testThreefoldRepetition4() {
        val moves = MoveList()
        moves.loadFromSan("1. d4 d5 2. Nf3 Nf6 3. c4 e6 4. Bg5 Nbd7 5. e3 Be7 6. Nc3 O-O 7. Rc1 b6 8. cxd5 exd5 9. Qa4 c5 10. Qc6 Rb8 11. Nxd5 Bb7 12. Nxe7+ Qxe7 13. Qa4 Rbc8 14. Qa3 Qe6 15. Bxf6 Qxf6 16. Ba6 Bxf3 17. Bxc8 Rxc8 18. gxf3 Qxf3 19. Rg1 Re8 20. Qd3 g6 21. Kf1 Re4 22. Qd1 Qh3+ 23. Rg2 Nf6 24. Kg1 cxd4 25. Rc4 dxe3 26. Rxe4 Nxe4 27. Qd8+ Kg7 28. Qd4+ Nf6 29. fxe3 Qe6 30. Rf2 g5 31. h4 gxh4 32. Qxh4 Ng4 33. Qg5+ Kf8 34. Rf5 h5 35. Qd8+ Kg7 36. Qg5+ Kf8 37. Qd8+ Kg7 38. Qg5+ Kf8")

        val board = Board()
        println(board.hashCode())
        for (move in moves) {
            board.doMove(move)
        }
        Assert.assertTrue(board.isRepetition)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testThreefoldRepetition5() {
        // en passant capture not possible for would expose own king to check

        val board = Board()
        board.loadFromFen("6k1/8/8/8/6p1/8/5PR1/6K1 w - - 0 32")

        board.doMove(Move(Square.F2, Square.F4)) // initial position - two square pawn advance
        board.doMove(
            Move(
                Square.G8,
                Square.F7
            )
        ) // en passant capture not possible - would expose own king to check
        board.doMove(Move(Square.G1, Square.F2))
        board.doMove(Move(Square.F7, Square.G8))

        board.doMove(Move(Square.F2, Square.G1)) // twofold repetition
        board.doMove(Move(Square.G8, Square.H7))

        board.doMove(Move(Square.G1, Square.H2))
        board.doMove(Move(Square.H7, Square.G8))

        board.doMove(Move(Square.H2, Square.G1)) // threefold repetiton
        Assert.assertTrue(board.isRepetition)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testThreefoldRepetition6() {
        // en passant capture not possible for own king in check

        val board = Board()
        board.loadFromFen("8/8/8/8/4p3/8/R2P3k/K7 w - - 0 37")

        board.doMove(Move(Square.D2, Square.D4)) // initial position - two square pawn advance
        board.doMove(
            Move(
                Square.H2,
                Square.H3
            )
        ) // en passant capture not possible - own king in check

        board.doMove(Move(Square.A2, Square.A3))
        board.doMove(Move(Square.H3, Square.H2))

        board.doMove(Move(Square.A3, Square.A2)) // twofold repetition
        board.doMove(Move(Square.H2, Square.H1))

        board.doMove(Move(Square.A2, Square.A3))
        board.doMove(Move(Square.H1, Square.H2))

        board.doMove(Move(Square.A3, Square.A2)) // threefold repetiton
        Assert.assertTrue(board.isRepetition)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testThreefoldRepetition7() {
        val moves = MoveList()
        moves.loadFromSan("1. e4 Nf6 2. e5 d5 3. Bc4 Nc6 4. Bf1 Nb8 5. Bc4 Nc6 6. Bf1 Nb8")

        val board = Board()
        for (move in moves) {
            board.doMove(move)
        }
        Assert.assertFalse(board.isRepetition)
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testBoardToString() {
        // Creates a new chessboard in the standard initial position

        val board = Board()

        board.doMove(Move(Square.E2, Square.E4))
        board.doMove(Move(Square.B8, Square.C6))
        board.doMove(Move(Square.F1, Square.C4))

        //print the chessboard in a human-readable form
        println(board.toString())

        val expected =
            """
            r.bqkbnr
            pppppppp
            ..n.....
            ........
            ..B.P...
            ........
            PPPP.PPP
            RNBQK.NR
            Side: BLACK
            """.trimIndent()
        Assert.assertEquals(expected, board.toString())
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testToStringFromWhiteViewPoint() {
        // Creates a new chessboard in the standard initial position

        val board = Board()

        board.doMove("e4")
        board.doMove("Nc6")
        board.doMove("Bc4")

        val expected =
            """
            r.bqkbnr
            pppppppp
            ..n.....
            ........
            ..B.P...
            ........
            PPPP.PPP
            RNBQK.NR
            
            """.trimIndent()
        Assert.assertEquals(expected, board.toStringFromWhiteViewPoint())
        Assert.assertEquals(expected, board.toStringFromViewPoint(Side.WHITE))
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testToStringFromBlackViewPoint() {
        // Creates a new chessboard in the standard initial position

        val board = Board()

        board.doMove("e4")
        board.doMove("Nc6")
        board.doMove("Bc4")

        val expected =
            """
            RN.KQBNR
            PPP.PPPP
            ........
            ...P.B..
            ........
            .....n..
            pppppppp
            rnbkqb.r
            
            """.trimIndent()
        Assert.assertEquals(expected, board.toStringFromBlackViewPoint())
        Assert.assertEquals(expected, board.toStringFromViewPoint(Side.BLACK))
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testBoardStrictEquals() {
        val board = Board()

        board.doMove(Move(Square.E2, Square.E4))
        board.doMove(Move(Square.E7, Square.E5))

        val board2 = board.clone()

        Assert.assertEquals(board, board2)
        Assert.assertTrue(board.strictEquals(board2))

        val board3 = Board()
        board3.loadFromFen(board.fen)

        Assert.assertEquals(board, board3)
        Assert.assertFalse(board.strictEquals(board3))
    }

    @Test
    @Throws(MoveConversionException::class)
    fun testBoardConsistencyAfterUndoingMove() {
        val board = Board()
        val e2e4 = Move(Square.E2, Square.E4)
        val e7e5 = Move(Square.E7, Square.E5)
        board.doMove(e2e4)
        board.doMove(e7e5)
        val initialKey = board.incrementalHashKey

        board.undoMove()
        board.doMove(e7e5)
        Assert.assertEquals(initialKey, board.incrementalHashKey)
        Assert.assertEquals(board.history.last as Long, board.incrementalHashKey)
        Assert.assertEquals(board.zobristKey, initialKey)
    }

    @Test
    fun testDoSanMove() {
        val board = Board()
        board.loadFromFen("4k3/8/8/8/1b6/2N5/8/4K1N1 w - - 0 1")
        board.doMove("Ne2")
        Assert.assertEquals("4k3/8/8/8/1b6/2N5/4N3/4K3 b - - 1 1", board.fen)
        board.doMove("Bxc3")
        Assert.assertEquals("4k3/8/8/8/8/2b5/4N3/4K3 w - - 0 2", board.fen)
        board.doMove("Nxc3")
        Assert.assertEquals("4k3/8/8/8/8/2N5/8/4K3 b - - 0 2", board.fen)
    }

    @Test
    fun testDoSanMove2() {
        val board = Board()
        board.doMove("e4")
        Assert.assertEquals(
            "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1",
            board.fen
        )
    }
}