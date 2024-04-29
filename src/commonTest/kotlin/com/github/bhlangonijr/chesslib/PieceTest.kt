package com.github.bhlangonijr.chesslib

import com.github.bhlangonijr.chesslib.Piece.Companion.fromFenSymbol
import com.github.bhlangonijr.chesslib.Piece.Companion.fromValue
import com.github.bhlangonijr.chesslib.Piece.Companion.make
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class PieceTest {
    @Test
    fun testPieceType() {
        // test code
        assertEquals(PieceType.PAWN, Piece.WHITE_PAWN.pieceType)
        assertEquals(PieceType.PAWN, Piece.BLACK_PAWN.pieceType)
        assertEquals(PieceType.KNIGHT, Piece.WHITE_KNIGHT.pieceType)
        assertEquals(PieceType.KNIGHT, Piece.BLACK_KNIGHT.pieceType)
        assertEquals(PieceType.BISHOP, Piece.WHITE_BISHOP.pieceType)
        assertEquals(PieceType.BISHOP, Piece.BLACK_BISHOP.pieceType)
        assertEquals(PieceType.ROOK, Piece.WHITE_ROOK.pieceType)
        assertEquals(PieceType.ROOK, Piece.BLACK_ROOK.pieceType)
        assertEquals(PieceType.QUEEN, Piece.WHITE_QUEEN.pieceType)
        assertEquals(PieceType.QUEEN, Piece.BLACK_QUEEN.pieceType)
        assertEquals(PieceType.KING, Piece.WHITE_KING.pieceType)
        assertEquals(PieceType.KING, Piece.BLACK_KING.pieceType)
        assertNull(Piece.NONE.pieceType)
    }


    @Test
    fun testPieceSide() {
        assertEquals(Side.WHITE, Piece.WHITE_PAWN.pieceSide)
        assertEquals(Side.BLACK, Piece.BLACK_PAWN.pieceSide)
        assertEquals(Side.WHITE, Piece.WHITE_KNIGHT.pieceSide)
        assertEquals(Side.BLACK, Piece.BLACK_KNIGHT.pieceSide)
        assertEquals(Side.WHITE, Piece.WHITE_BISHOP.pieceSide)
        assertEquals(Side.BLACK, Piece.BLACK_BISHOP.pieceSide)
        assertEquals(Side.WHITE, Piece.WHITE_ROOK.pieceSide)
        assertEquals(Side.BLACK, Piece.BLACK_ROOK.pieceSide)
        assertEquals(Side.WHITE, Piece.WHITE_QUEEN.pieceSide)
        assertEquals(Side.BLACK, Piece.BLACK_QUEEN.pieceSide)
        assertEquals(Side.WHITE, Piece.WHITE_KING.pieceSide)
        assertEquals(Side.BLACK, Piece.BLACK_KING.pieceSide)
        assertNull(Piece.NONE.pieceSide)
    }

    @Test
    fun make() {
        assertEquals(Piece.WHITE_PAWN, make(Side.WHITE, PieceType.PAWN))
        assertEquals(Piece.BLACK_PAWN, make(Side.BLACK, PieceType.PAWN))
        assertEquals(Piece.WHITE_KNIGHT, make(Side.WHITE, PieceType.KNIGHT))
        assertEquals(Piece.BLACK_KNIGHT, make(Side.BLACK, PieceType.KNIGHT))
        assertEquals(Piece.WHITE_BISHOP, make(Side.WHITE, PieceType.BISHOP))
        assertEquals(Piece.BLACK_BISHOP, make(Side.BLACK, PieceType.BISHOP))
        assertEquals(Piece.WHITE_ROOK, make(Side.WHITE, PieceType.ROOK))
        assertEquals(Piece.BLACK_ROOK, make(Side.BLACK, PieceType.ROOK))
        assertEquals(Piece.WHITE_QUEEN, make(Side.WHITE, PieceType.QUEEN))
        assertEquals(Piece.BLACK_QUEEN, make(Side.BLACK, PieceType.QUEEN))
        assertEquals(Piece.WHITE_KING, make(Side.WHITE, PieceType.KING))
        assertEquals(Piece.BLACK_KING, make(Side.BLACK, PieceType.KING))
        assertEquals(Piece.NONE, make(Side.WHITE, PieceType.NONE))
        assertEquals(Piece.NONE, make(Side.BLACK, PieceType.NONE))
    }

    @Test
    fun fromValue() {
        assertEquals(Piece.WHITE_PAWN, fromValue("WHITE_PAWN"))
        assertEquals(Piece.BLACK_PAWN, fromValue("BLACK_PAWN"))
        assertEquals(Piece.WHITE_KNIGHT, fromValue("WHITE_KNIGHT"))
        assertEquals(Piece.BLACK_KNIGHT, fromValue("BLACK_KNIGHT"))
        assertEquals(Piece.WHITE_BISHOP, fromValue("WHITE_BISHOP"))
        assertEquals(Piece.BLACK_BISHOP, fromValue("BLACK_BISHOP"))
        assertEquals(Piece.WHITE_ROOK, fromValue("WHITE_ROOK"))
        assertEquals(Piece.BLACK_ROOK, fromValue("BLACK_ROOK"))
        assertEquals(Piece.WHITE_QUEEN, fromValue("WHITE_QUEEN"))
        assertEquals(Piece.BLACK_QUEEN, fromValue("BLACK_QUEEN"))
        assertEquals(Piece.WHITE_KING, fromValue("WHITE_KING"))
        assertEquals(Piece.BLACK_KING, fromValue("BLACK_KING"))
        assertEquals(Piece.NONE, fromValue("NONE"))
    }

    @Test
    fun value() {
        assertEquals("WHITE_PAWN", Piece.WHITE_PAWN.value())
        assertEquals("BLACK_PAWN", Piece.BLACK_PAWN.value())
        assertEquals("WHITE_KNIGHT", Piece.WHITE_KNIGHT.value())
        assertEquals("BLACK_KNIGHT", Piece.BLACK_KNIGHT.value())
        assertEquals("WHITE_BISHOP", Piece.WHITE_BISHOP.value())
        assertEquals("BLACK_BISHOP", Piece.BLACK_BISHOP.value())
        assertEquals("WHITE_ROOK", Piece.WHITE_ROOK.value())
        assertEquals("BLACK_ROOK", Piece.BLACK_ROOK.value())
        assertEquals("WHITE_QUEEN", Piece.WHITE_QUEEN.value())
        assertEquals("BLACK_QUEEN", Piece.BLACK_QUEEN.value())
        assertEquals("WHITE_KING", Piece.WHITE_KING.value())
        assertEquals("BLACK_KING", Piece.BLACK_KING.value())
        assertEquals("NONE", Piece.NONE.value())
    }

    @Test
    fun fromFenSymbol() {
        assertEquals(Piece.WHITE_PAWN, fromFenSymbol("P"))
        assertEquals(Piece.WHITE_KNIGHT, fromFenSymbol("N"))
        assertEquals(Piece.WHITE_BISHOP, fromFenSymbol("B"))
        assertEquals(Piece.WHITE_ROOK, fromFenSymbol("R"))
        assertEquals(Piece.WHITE_QUEEN, fromFenSymbol("Q"))
        assertEquals(Piece.WHITE_KING, fromFenSymbol("K"))
        assertEquals(Piece.BLACK_PAWN, fromFenSymbol("p"))
        assertEquals(Piece.BLACK_KNIGHT, fromFenSymbol("n"))
        assertEquals(Piece.BLACK_BISHOP, fromFenSymbol("b"))
        assertEquals(Piece.BLACK_ROOK, fromFenSymbol("r"))
        assertEquals(Piece.BLACK_QUEEN, fromFenSymbol("q"))
        assertEquals(Piece.BLACK_KING, fromFenSymbol("k"))
        assertEquals(Piece.NONE, fromFenSymbol("."))

        try {
            fromFenSymbol("X")
            fail("There should have been an exception")
        } catch (expected: Exception) {
            assertTrue(expected is IllegalArgumentException)
            assertEquals("Unknown piece 'X'", expected.message)
        }
    }
}