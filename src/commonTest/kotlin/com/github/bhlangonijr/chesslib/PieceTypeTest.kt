package com.github.bhlangonijr.chesslib

import com.github.bhlangonijr.chesslib.PieceType.Companion.fromSanSymbol
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class PieceTypeTest {
    @Test
    fun fromSanSymbol() {
        assertEquals(PieceType.PAWN, fromSanSymbol(""))
        assertEquals(PieceType.KNIGHT, fromSanSymbol("N"))
        assertEquals(PieceType.BISHOP, fromSanSymbol("B"))
        assertEquals(PieceType.ROOK, fromSanSymbol("R"))
        assertEquals(PieceType.QUEEN, fromSanSymbol("Q"))
        assertEquals(PieceType.KING, fromSanSymbol("K"))
        assertEquals(PieceType.NONE, fromSanSymbol("NONE"))

        try {
            fromSanSymbol("X")
            fail("There should have been an exception")
        } catch (expected: Exception) {
            assertTrue(expected is IllegalArgumentException)
            assertEquals("Unknown piece 'X'", expected.message)
        }
    }
}