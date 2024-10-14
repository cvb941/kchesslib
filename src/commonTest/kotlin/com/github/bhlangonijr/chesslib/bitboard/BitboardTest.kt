package com.github.bhlangonijr.chesslib.bitboard

import com.github.bhlangonijr.chesslib.Bitboard
import com.github.bhlangonijr.chesslib.Bitboard.bitScanForward
import com.github.bhlangonijr.chesslib.Bitboard.bitScanReverse
import com.github.bhlangonijr.chesslib.Bitboard.bitboardToString
import com.github.bhlangonijr.chesslib.Bitboard.extractLsb
import com.github.bhlangonijr.chesslib.Bitboard.getBishopAttacks
import com.github.bhlangonijr.chesslib.Bitboard.getRookAttacks
import com.github.bhlangonijr.chesslib.Bitboard.hasOnly1Bit
import com.github.bhlangonijr.chesslib.Square
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/**
 * The type Bitboard test.
 */
class BitboardTest {
    /**
     * Test bb functions.
     */
    @Test
    fun testBBFunctions() {
        for (x in 0..63) {
            assertEquals(bitScanForward(1L shl x).toLong(), x.toLong())
            assertEquals(bitScanReverse(1L shl x).toLong(), x.toLong())
        }
        val t = (1L shl 10) or (1L shl 20)
        var lsb = extractLsb(t)

        assertEquals(1L shl 20, lsb)

        lsb = extractLsb(0L)

        assertEquals(0L, lsb)

        val ba = getBishopAttacks(0L, Square.D5)

        assertEquals(
            bitboardToString(ba),
            """
                00000001
                10000010
                01000100
                00101000
                00000000
                00101000
                01000100
                10000010
                
                """.trimIndent()
        )

        val ra = getRookAttacks(0L, Square.D5)

        assertEquals(
            bitboardToString(ra),
            """
                00010000
                00010000
                00010000
                00010000
                11101111
                00010000
                00010000
                00010000
                
                """.trimIndent()
        )
    }

    @Test
    fun testHasOnlyOneBit() {
        for (i in 0 until Square.entries.size - 1) {
            assertTrue(hasOnly1Bit(Square.squareAt(i).bitboard))
        }
    }
}
