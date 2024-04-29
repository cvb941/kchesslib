package com.github.bhlangonijr.chesslib.unicode

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import com.github.bhlangonijr.chesslib.unicode.UnicodePrinter
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertTrue

class UnicodePrinterTest {
    @Test
    fun testPrintBoard() {
        val baos = ByteArrayOutputStream(256)
        val printer = UnicodePrinter(PrintStream(baos))

        val board = Board()
        board.doMove(Move(Square.E2, Square.E4))
        board.doMove(Move(Square.E7, Square.E5))
        printer.print(board)

        val repr = baos.toString(StandardCharsets.UTF_8)
        assertEquals(
            '\u2656'.code.toLong(),
            repr[0].code.toLong(),
            "Should be a white rook at pos 0\n$repr",
        )
        assertEquals(
            '\u265C'.code.toLong(),
            repr.split("\n")[7][7].code.toLong(),
            "Should be a black rook at pos 63\n$repr"
        )
    }
}
