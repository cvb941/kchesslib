package com.github.bhlangonijr.chesslib.unicode

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import kotlinx.io.Buffer
import kotlinx.io.readString
import kotlin.test.Test
import kotlin.test.assertEquals

class UnicodePrinterTest {
    @Test
    fun testPrintBoard() {
        val baos = Buffer()
        val printer = UnicodePrinter(baos)

        val board = Board()
        board.doMove(Move(Square.E2, Square.E4))
        board.doMove(Move(Square.E7, Square.E5))
        printer.print(board)

        val repr = baos.readString()
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
