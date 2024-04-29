package com.github.bhlangonijr.chesslib

import com.github.bhlangonijr.chesslib.PieceType.Companion.fromSanSymbol
import org.junit.Assert
import org.junit.Test

class PieceTypeTest {
    @Test
    fun fromSanSymbol() {
        Assert.assertEquals(PieceType.PAWN, fromSanSymbol(""))
        Assert.assertEquals(PieceType.KNIGHT, fromSanSymbol("N"))
        Assert.assertEquals(PieceType.BISHOP, fromSanSymbol("B"))
        Assert.assertEquals(PieceType.ROOK, fromSanSymbol("R"))
        Assert.assertEquals(PieceType.QUEEN, fromSanSymbol("Q"))
        Assert.assertEquals(PieceType.KING, fromSanSymbol("K"))
        Assert.assertEquals(PieceType.NONE, fromSanSymbol("NONE"))

        try {
            fromSanSymbol("X")
            Assert.fail("There should have been an exception")
        } catch (expected: Exception) {
            Assert.assertTrue(expected is IllegalArgumentException)
            Assert.assertEquals("Unknown piece 'X'", expected.message)
        }
    }
}