package com.github.bhlangonijr.chesslib

import com.github.bhlangonijr.chesslib.Piece.Companion.fromFenSymbol
import com.github.bhlangonijr.chesslib.Piece.Companion.fromValue
import com.github.bhlangonijr.chesslib.Piece.Companion.make
import org.junit.Assert
import org.junit.Test

class PieceTest {
    @Test
    fun testPieceType() {
        // test code
        Assert.assertEquals(PieceType.PAWN, Piece.WHITE_PAWN.pieceType)
        Assert.assertEquals(PieceType.PAWN, Piece.BLACK_PAWN.pieceType)
        Assert.assertEquals(PieceType.KNIGHT, Piece.WHITE_KNIGHT.pieceType)
        Assert.assertEquals(PieceType.KNIGHT, Piece.BLACK_KNIGHT.pieceType)
        Assert.assertEquals(PieceType.BISHOP, Piece.WHITE_BISHOP.pieceType)
        Assert.assertEquals(PieceType.BISHOP, Piece.BLACK_BISHOP.pieceType)
        Assert.assertEquals(PieceType.ROOK, Piece.WHITE_ROOK.pieceType)
        Assert.assertEquals(PieceType.ROOK, Piece.BLACK_ROOK.pieceType)
        Assert.assertEquals(PieceType.QUEEN, Piece.WHITE_QUEEN.pieceType)
        Assert.assertEquals(PieceType.QUEEN, Piece.BLACK_QUEEN.pieceType)
        Assert.assertEquals(PieceType.KING, Piece.WHITE_KING.pieceType)
        Assert.assertEquals(PieceType.KING, Piece.BLACK_KING.pieceType)
        Assert.assertNull(Piece.NONE.pieceType)
    }


    @Test
    fun testPieceSide() {
        Assert.assertEquals(Side.WHITE, Piece.WHITE_PAWN.pieceSide)
        Assert.assertEquals(Side.BLACK, Piece.BLACK_PAWN.pieceSide)
        Assert.assertEquals(Side.WHITE, Piece.WHITE_KNIGHT.pieceSide)
        Assert.assertEquals(Side.BLACK, Piece.BLACK_KNIGHT.pieceSide)
        Assert.assertEquals(Side.WHITE, Piece.WHITE_BISHOP.pieceSide)
        Assert.assertEquals(Side.BLACK, Piece.BLACK_BISHOP.pieceSide)
        Assert.assertEquals(Side.WHITE, Piece.WHITE_ROOK.pieceSide)
        Assert.assertEquals(Side.BLACK, Piece.BLACK_ROOK.pieceSide)
        Assert.assertEquals(Side.WHITE, Piece.WHITE_QUEEN.pieceSide)
        Assert.assertEquals(Side.BLACK, Piece.BLACK_QUEEN.pieceSide)
        Assert.assertEquals(Side.WHITE, Piece.WHITE_KING.pieceSide)
        Assert.assertEquals(Side.BLACK, Piece.BLACK_KING.pieceSide)
        Assert.assertNull(Piece.NONE.pieceSide)
    }

    @Test
    fun make() {
        Assert.assertEquals(Piece.WHITE_PAWN, make(Side.WHITE, PieceType.PAWN))
        Assert.assertEquals(Piece.BLACK_PAWN, make(Side.BLACK, PieceType.PAWN))
        Assert.assertEquals(Piece.WHITE_KNIGHT, make(Side.WHITE, PieceType.KNIGHT))
        Assert.assertEquals(Piece.BLACK_KNIGHT, make(Side.BLACK, PieceType.KNIGHT))
        Assert.assertEquals(Piece.WHITE_BISHOP, make(Side.WHITE, PieceType.BISHOP))
        Assert.assertEquals(Piece.BLACK_BISHOP, make(Side.BLACK, PieceType.BISHOP))
        Assert.assertEquals(Piece.WHITE_ROOK, make(Side.WHITE, PieceType.ROOK))
        Assert.assertEquals(Piece.BLACK_ROOK, make(Side.BLACK, PieceType.ROOK))
        Assert.assertEquals(Piece.WHITE_QUEEN, make(Side.WHITE, PieceType.QUEEN))
        Assert.assertEquals(Piece.BLACK_QUEEN, make(Side.BLACK, PieceType.QUEEN))
        Assert.assertEquals(Piece.WHITE_KING, make(Side.WHITE, PieceType.KING))
        Assert.assertEquals(Piece.BLACK_KING, make(Side.BLACK, PieceType.KING))
        Assert.assertEquals(Piece.NONE, make(Side.WHITE, PieceType.NONE))
        Assert.assertEquals(Piece.NONE, make(Side.BLACK, PieceType.NONE))
    }

    @Test
    fun fromValue() {
        Assert.assertEquals(Piece.WHITE_PAWN, fromValue("WHITE_PAWN"))
        Assert.assertEquals(Piece.BLACK_PAWN, fromValue("BLACK_PAWN"))
        Assert.assertEquals(Piece.WHITE_KNIGHT, fromValue("WHITE_KNIGHT"))
        Assert.assertEquals(Piece.BLACK_KNIGHT, fromValue("BLACK_KNIGHT"))
        Assert.assertEquals(Piece.WHITE_BISHOP, fromValue("WHITE_BISHOP"))
        Assert.assertEquals(Piece.BLACK_BISHOP, fromValue("BLACK_BISHOP"))
        Assert.assertEquals(Piece.WHITE_ROOK, fromValue("WHITE_ROOK"))
        Assert.assertEquals(Piece.BLACK_ROOK, fromValue("BLACK_ROOK"))
        Assert.assertEquals(Piece.WHITE_QUEEN, fromValue("WHITE_QUEEN"))
        Assert.assertEquals(Piece.BLACK_QUEEN, fromValue("BLACK_QUEEN"))
        Assert.assertEquals(Piece.WHITE_KING, fromValue("WHITE_KING"))
        Assert.assertEquals(Piece.BLACK_KING, fromValue("BLACK_KING"))
        Assert.assertEquals(Piece.NONE, fromValue("NONE"))
    }

    @Test
    fun value() {
        Assert.assertEquals("WHITE_PAWN", Piece.WHITE_PAWN.value())
        Assert.assertEquals("BLACK_PAWN", Piece.BLACK_PAWN.value())
        Assert.assertEquals("WHITE_KNIGHT", Piece.WHITE_KNIGHT.value())
        Assert.assertEquals("BLACK_KNIGHT", Piece.BLACK_KNIGHT.value())
        Assert.assertEquals("WHITE_BISHOP", Piece.WHITE_BISHOP.value())
        Assert.assertEquals("BLACK_BISHOP", Piece.BLACK_BISHOP.value())
        Assert.assertEquals("WHITE_ROOK", Piece.WHITE_ROOK.value())
        Assert.assertEquals("BLACK_ROOK", Piece.BLACK_ROOK.value())
        Assert.assertEquals("WHITE_QUEEN", Piece.WHITE_QUEEN.value())
        Assert.assertEquals("BLACK_QUEEN", Piece.BLACK_QUEEN.value())
        Assert.assertEquals("WHITE_KING", Piece.WHITE_KING.value())
        Assert.assertEquals("BLACK_KING", Piece.BLACK_KING.value())
        Assert.assertEquals("NONE", Piece.NONE.value())
    }

    @Test
    fun fromFenSymbol() {
        Assert.assertEquals(Piece.WHITE_PAWN, fromFenSymbol("P"))
        Assert.assertEquals(Piece.WHITE_KNIGHT, fromFenSymbol("N"))
        Assert.assertEquals(Piece.WHITE_BISHOP, fromFenSymbol("B"))
        Assert.assertEquals(Piece.WHITE_ROOK, fromFenSymbol("R"))
        Assert.assertEquals(Piece.WHITE_QUEEN, fromFenSymbol("Q"))
        Assert.assertEquals(Piece.WHITE_KING, fromFenSymbol("K"))
        Assert.assertEquals(Piece.BLACK_PAWN, fromFenSymbol("p"))
        Assert.assertEquals(Piece.BLACK_KNIGHT, fromFenSymbol("n"))
        Assert.assertEquals(Piece.BLACK_BISHOP, fromFenSymbol("b"))
        Assert.assertEquals(Piece.BLACK_ROOK, fromFenSymbol("r"))
        Assert.assertEquals(Piece.BLACK_QUEEN, fromFenSymbol("q"))
        Assert.assertEquals(Piece.BLACK_KING, fromFenSymbol("k"))
        Assert.assertEquals(Piece.NONE, fromFenSymbol("."))

        try {
            fromFenSymbol("X")
            Assert.fail("There should have been an exception")
        } catch (expected: Exception) {
            Assert.assertTrue(expected is IllegalArgumentException)
            Assert.assertEquals("Unknown piece 'X'", expected.message)
        }
    }
}